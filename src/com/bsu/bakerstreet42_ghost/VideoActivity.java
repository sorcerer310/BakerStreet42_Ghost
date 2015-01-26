package com.bsu.bakerstreet42_ghost;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends Activity {
	private VideoView vv;
	private MediaController mc;
	private TextView tv;
	
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
	}
}
