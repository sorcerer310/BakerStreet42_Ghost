package com.bsu.bakerstreet42_ghost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import org.androidpn.demoapp.R;


import java.util.Properties;

import org.androidpn.client.ServiceManager;

import com.bsu.bakerstreet42_ghost.tools.PropertiesInstance;
import com.bsu.bakerstreet42_ghost.tools.Utils;
import com.bsu.bakerstreet42_ghost.widget.adapter.ListViewSimpleAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
//	private NfcActivityHelper nfchelper;	//帮助类
	
	//列表控件
	private ListView lv_message;
	private List<Map<String,Object>> listdata;
	private ListViewSimpleAdapter sa;
	
	private Properties properies = new Properties();
	//视频音频资源路径
	private String vpath,vtitle1,vtitle2,vtitle3,vtitle4,vtitle5;
	
	//程序列表持久数据，防止玩家退出程序再进入获得的数据不对，如要重置需要在游戏重置功能操作
	private SharedPreferences settings;
	
	//系统数据重置密码
	private final String PREFERENCES_CLEAR_PASSWORD = "12345";
	
	private PropertiesInstance pi = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//获得配置数据
		pi = PropertiesInstance.getInstance();
		vpath = pi.properties.getProperty("vpath");
		vtitle1 = pi.properties.getProperty("vtitle1");
		vtitle2 = pi.properties.getProperty("vtitle2");
		vtitle3 = pi.properties.getProperty("vtitle3");
		vtitle4 = pi.properties.getProperty("vtitle4");
		vtitle5 = pi.properties.getProperty("vtitle5");
		
		//设置标题
//		this.setTitle("贝克街42号－威廉古堡之狼人");
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setTypeface(Typeface.createFromAsset(getAssets(), "fzzy.ttf"));
		
		//初始化代理数据
		if(listdata==null)
			listdata = new ArrayList<Map<String,Object>>();
		
        //开启接收消息的服务
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
		
		initPreferences();						//初始化持久化数据
//		initNfcHelper();						//初始化nfc得帮助类
		initMessage();							//初始化消息
		initResetGameDialog();					//初始化重置数据窗口
		
		

	}

	/**
	 * 初始化持久数据
	 */
	private void initPreferences(){
		settings = this.getSharedPreferences("ListDatas", MODE_PRIVATE);
		
		//调试数据
		Editor editor = settings.edit();
		editor.putBoolean("bk42-xz001", true);
		editor.putBoolean("bk42-xz002", true);
		editor.putBoolean("bk42-xz003", true);
		editor.putBoolean("bk42-xz004", true);
		editor.putBoolean("bk42-xz005", true);
		editor.commit();
		
		listdata.clear();
		//初始化数据并将已扫描的数据加入到列表中
		makeListDataInitPreByID("bk42-xz001");
		makeListDataInitPreByID("bk42-xz002");
		makeListDataInitPreByID("bk42-xz003");
		makeListDataInitPreByID("bk42-xz004");
		makeListDataInitPreByID("bk42-xz005");
//		makeListDataInitPreByID("bk42-xz006");
//		makeListDataInitPreByID("bk42-xz007");
	}
	/**
	 * 如果id对应的数据在持久数据中存在，则增加到列表中
	 * 如果id对应的数据在持久数据中不存在，则在持久数据中初始化该数据
	 * 用于程序启动初始化持久数据
	 * @param id	对应的资源id
	 */
	private void makeListDataInitPreByID(String id){
		if(settings.contains(id) && settings.getBoolean(id, false)){
			listdata.add(makeListDataByID(id));
		}else{
			Editor editor = settings.edit();
			editor.putBoolean(id, false);
			editor.commit();
		}
	}
	
	/**
	 * 根据数据项的id生成一条列表数据
	 * @param id	列表的id
	 * @return		返回该id对应的资源数据
	 */
	private Map<String,Object> makeListDataByID(String id){
//		String s = R.string.nid001;
		if(id.equals("bk42-xz001")){
			return Utils.makeListItemData("bk42-xz001",vtitle1,R.raw.v001,R.drawable.i001);
		}else if(id.equals("bk42-xz002")){
			return Utils.makeListItemData("bk42-xz002",vtitle2,R.raw.v002,R.drawable.i002);
		}else if(id.equals("bk42-xz003")){
			return Utils.makeListItemData("bk42-xz003",vtitle3,R.raw.v003,R.drawable.i003);
		}else if(id.equals("bk42-xz004")){
			return Utils.makeListItemData("bk42-xz004", vtitle4,  R.raw.v004,R.drawable.i004);
		}else if(id.equals("bk42-xz005")){
			return Utils.makeListItemData("bk42-xz005", vtitle5,  R.raw.v005,R.drawable.i005);
//		}else if(id.equals("bk42-xz006")){
//			return Utils.makeListItemData("bk42-xz006", "凶宅视频6", R.raw.v006,R.drawable.i006);
//		}else if(id.equals("bk42-xz007")){
//			return Utils.makeListItemData("bk42-xz007", "凶宅视频7",  R.raw.v007,R.drawable.i007);
		}
		return null;
	}
	/**
	 * 将一个id增加到持久数据中
	 * @param id	id编号
	 */
	private void addListDataToPrefences(String id){
		Editor editor = settings.edit();
		editor.putBoolean(id, true);
		editor.commit();
	}
	
//	/*
//	 * 初始化NfcHelper
//	 */
//	private void initNfcHelper(){
//		//初始化nfc数据帮助类
//		nfchelper = new NfcActivityHelper(this);
//		nfchelper.onCreate();
//		//当读取到nfc数据时的操作
//		nfchelper.setOnNFCReadListener(new OnNfcReadListener(){
//			@Override
//			public void read(String data) {
//				if(data.equals("preferencesClear")){
//					clearPreferences();						//清理数据
//					sa.notifyDataSetChanged();				//通知控件刷新数据
//					return;
//				}
//				
//				//如果列表中包含当前的数据，则不增加该数据到列表中
//				for(Map<String,Object> map:listdata){
//					if(map.get("id").equals(data)){
//						startRadioActivity(map.get("title").toString(),(Integer)map.get("lrcpath"),
//								vpath+((Integer)map.get("oggpath")),(Integer)map.get("imgpath"));
//						return;
//					}
//				}
//				//根据数据id增加数据
//				if(data.equals("bk42-lr001")){
//					addListDataAndStartRadioActivity(data,"凯瑟琳公主",R.raw.l001,R.raw.r001,R.drawable.i001);
//				}else if(data.equals("bk42-lr002")){
//					addListDataAndStartRadioActivity(data,"米洛陶洛斯",R.raw.l002,R.raw.r002,R.drawable.i002);
//				}else if(data.equals("bk42-lr003")){
//					addListDataAndStartRadioActivity(data,"法拉奥",R.raw.l003,R.raw.r003,R.drawable.i003);
//				}else if(data.equals("bk42-lr004")){
//					addListDataAndStartRadioActivity(data,"阿喀琉斯",R.raw.l004,R.raw.r004,R.drawable.i004);
//				}else if(data.equals("bk42-lr005")){
//					addListDataAndStartRadioActivity(data,"塔纳托斯",R.raw.l005,R.raw.r005,R.drawable.i005);
//				}else if(data.equals("bk42-lr006")){
//					addListDataAndStartRadioActivity(data,"美杜莎",R.raw.l006,R.raw.r006,R.drawable.i006);
//				}else if(data.equals("bk42-lr007")){
//					addListDataAndStartRadioActivity(data,"奥丁",R.raw.l007,R.raw.r007,R.drawable.i007);
//				}
//				//通知控件刷新数据代理
//				sa.notifyDataSetChanged();						
//			}});	
//	}
	/**
	 * 增加ListView的数据并跳转到播放界面
	 * @param id		消息的id
	 * @param title		消息的标题
	 * @param lid		歌词文件的id
	 * @param rid		声音文件的id
	 * @param iid		背景图片的id
	 */
	private void addListDataAndStartRadioActivity(String id,String title,int lid,int rid,int iid){
		listdata.add(Utils.makeListItemData(id, title,  rid, iid));			//增加listview得代理数据
		addListDataToPrefences(id);											//增加到持久化数据
		startVideoActivity(title,vpath+rid);								//开始播放声音
	}
	/**
	 * 传入必要参数，开始播放视频
	 * @param title		声音标题
	 * @param lid		歌词id
	 * @param opath		声音文件路径
	 * @param iid		背景图片id
	 */
//	private void startRadioActivity(String title,int lid,String opath,int iid){
//		//根据当前得数据跳转到下一界面自动播放
//		Intent intent = new Intent(MainActivity.this,VideoActivity.class);
//		intent.putExtra("title", title);					//传送标题到下一个界面
//		intent.putExtra("lrcpath", lid);					//歌词路径 
//		intent.putExtra("oggpath", opath);					//传送播放路径到下一个界面
//		intent.putExtra("imgpath", iid);					//传送背景图片到下一个界面
//		MainActivity.this.startActivity(intent);		
//	}
	/**
	 * 传入必要参数,开始播放视频
	 * @param title		视频标题
	 * @param vpath		视频文件路径
	 */
	private void startVideoActivity(String title,String vpath){
		//根据当前获得的数据跳转到下一界面自动播放
		Intent intent = new Intent(MainActivity.this,VideoActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("vpath", vpath);
		MainActivity.this.startActivity(intent);
	}
	
	/**
	 * 初始化收件箱消息部分
	 */
	private void initMessage(){
		lv_message = (ListView) findViewById(R.id.lv_message);
		
		sa = new ListViewSimpleAdapter(this,listdata,R.layout.listitem
				,new String[]{"title"}
				,new int[]{R.id.item_title});
		
		lv_message.setAdapter(sa);
		lv_message.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,long id) {
				Map<String,Object> mapitem = listdata.get(position);
				startVideoActivity(mapitem.get("title").toString(),
						vpath+((Integer)mapitem.get("oggpath")));
			}});
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
//		nfchelper.onNewIntent(intent);
	}
	@Override
	protected void onPause() {
		super.onPause();
//		nfchelper.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
//		nfchelper.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//截获back键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}

		if (KeyEvent.KEYCODE_HOME == keyCode)
			return true;
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);  
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		 int id = item.getItemId();
		if(id==R.id.action_settings){
			Toast.makeText(this, "action_setting", Toast.LENGTH_SHORT).show();
			dlg_rstgame.show();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	//对话框对象
	private AlertDialog dlg_rstgame;
	/**
	 * 初始化输入密码对话框
	 */
	private void initResetGameDialog(){
		//获得密码编辑框的view
		LayoutInflater li = LayoutInflater.from(this);
		View gameResetDialogView = li.inflate(R.layout.game_reset_dialog, null);
		
		//获得密码编辑框
		final EditText et = (EditText) gameResetDialogView.findViewById(R.id.et_password);
		et.setText("");
		
		//定义输入密码的对话框
		dlg_rstgame = new AlertDialog.Builder(this)
			.setTitle("游戏重置密码")
			.setView(gameResetDialogView)
			.setPositiveButton("确定", new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					if(et.getText().toString().equals(PREFERENCES_CLEAR_PASSWORD)){
						Toast.makeText(MainActivity.this, "重置游戏成功", Toast.LENGTH_SHORT).show();
						//密码正确则清除数据
						clearPreferences();
					}
					else
						Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
					dialog.dismiss();	
				}})
			.setNeutralButton("取消", null)
			.create();
	}
	/**
	 * 清除持久数据
	 */
	private void clearPreferences(){
		Editor editor = MainActivity.this.settings.edit();
		editor.clear();
		editor.commit();
		this.initPreferences();
	}

	public String getVpath() {
		return vpath;
	}

	public String getVtitle1() {
		return vtitle1;
	}

	public String getVtitle2() {
		return vtitle2;
	}

	public String getVtitle3() {
		return vtitle3;
	}

	public String getVtitle4() {
		return vtitle4;
	}

	public String getVtitle5() {
		return vtitle5;
	}

}
