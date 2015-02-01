package com.bsu.bakerstreet42_ghost;


import com.bsu.bakerstreet42_ghost.MainActivity;
import com.bsu.bakerstreet42_ghost.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
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
		
		Intent intent = this.getIntent();
		String vpath = intent.getStringExtra("vpath");
		String title = intent.getStringExtra("title");
		Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
		
		vv = (VideoView) findViewById(R.id.vv);
		mc = new MediaController(this);
		tv = (TextView) findViewById(R.id.tv_videotitle);
		tv.setText(title);
		
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
	}
}
