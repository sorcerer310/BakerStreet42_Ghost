package com.bsu.bakerstreet42_ghost;


import java.util.Properties;

import com.bsu.bakerstreet42_ghost.MainActivity;
import com.bsu.bakerstreet42_ghost.R;
import com.bsu.bakerstreet42_ghost.tools.PropertiesInstance;
import com.bsu.bakerstreet42_ghost.tools.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Activity {
	private VideoView vv;
	private MediaController mc;
	private TextView tv;
	private Button bt_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);
		
		//用来处理android.os.NetworkOnMainThreadException异常
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		Intent intent = this.getIntent();
		String vpath = intent.getStringExtra("vpath");
		final String title = intent.getStringExtra("title");

		Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
		
		vv = (VideoView) findViewById(R.id.vv);
		mc = new MediaController(this);
		tv = (TextView) findViewById(R.id.tv_videotitle);
		tv.setText(title);
		
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(1280, 720);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
		vv.setLayoutParams(lp);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		vv.setMediaController(mc);
		vv.setVideoURI(Uri.parse(vpath));
		vv.requestFocus();
		vv.start();
		
		//返回按钮
		bt_back = (Button) findViewById(R.id.bt_back);
		bt_back.setAlpha(1.0f);
		bt_back.setEnabled(true);
		bt_back.setText("返回");
		bt_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				//设置按钮不可用
				Button bt = (Button) arg0;
				bt.setEnabled(false);
				bt.setText("正在返回...");
				//转到主界面
				Intent intent = new Intent(VideoActivity.this,MainActivity.class);
				VideoActivity.this.startActivity(intent);
			}
		});

		
		//当视频播放完成,判断是否为指定视频,如果是,则访问PLC_SendSerial?watch=yes,向plc发送指令
		vv.setOnCompletionListener(new OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer arg0) {
			Properties p = PropertiesInstance.getInstance().properties;
			//如果播放的视频为视频2,则向PLCGameCenter
			if(title.equals(p.getProperty("finalvideotitle").toString())){
				try {
					byte[] bytes = Utils.sendPostRequestByForm(p.getProperty("plcgamecenter").toString(), "watch=yes");
					String respstr = new String(bytes);
					Toast.makeText(VideoActivity.this, respstr, Toast.LENGTH_SHORT).show();
					System.out.println("=================发送了watch=yes消息到PLCGameCenter");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}});
	}
}
