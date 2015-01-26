package com.bsu.bakerstreet42_ghost.tools;

import com.bsu.bakerstreet42_ghost.listener.OnNfcReadListener;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
/**
 * 公共类，用来帮助一个Activity快速构建成一个支持Ndef的Activity
 * @author fengchong
 *
 */
public class NfcActivityHelper {
	private Activity activity;				//要操作的Activity
	private NfcAdapter adapter;				//Nfc设备代理
	private PendingIntent pintent;			//意图对象
	private OnNfcReadListener listener;		//读取操作的监听器

	public NfcActivityHelper(Activity a){
		activity = a;
		//初始化ndef设备
		adapter = NfcAdapter.getDefaultAdapter(activity);
		//截获Intent,使用当前的Activity
		pintent = PendingIntent.getActivity(activity, 0, new Intent(activity,activity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
	}
	
	/**
	 * 用于Activity的onCreate执行
	 */
	public void onCreate(){
		
	}
	/**
	 * 用于在Activity中的onResume里执行
	 */
	public void onResume(){
		if(adapter != null)
			adapter.enableForegroundDispatch(activity, pintent, null, null);
	}
	/**
	 * 用于在Activity中的onPause里的执行
	 */
	public void onPause(){
		if(adapter != null)
			adapter.disableForegroundDispatch(activity);
	}
	/**
	 * 设置读取nfc数据的监听器
	 * @param l
	 */
	public void setOnNFCReadListener(OnNfcReadListener l){
		listener = l;
	}
	
	/**
	 * 用在Activity中的onNewIntent里的执行
	 * @param intent	传入的意图对象，其中包含标签的数据
	 */
	public void onNewIntent(Intent intent){
		//当读取到一个ACTION_TAG_DISCOVERED标签
		if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String tagtype = NFCDataUtils.witchMifareType(tag);
			if(tagtype.equals("MifareClassic")){
				if(listener!=null)
					listener.read(NFCDataUtils.readMifareClassicData(tag));
			}
			else if(tagtype.equals("MifareUltralight")){
				if(listener!=null)
					listener.read(NFCDataUtils.readMifareUltralightDataByPage(tag, 8));
			}

		//当读到一个ACTION_NDEF_DISCOVERED数据,返回ndef数据
		}else if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
			if(listener!=null)
				listener.read(NFCDataUtils.readNdefData(intent));
		}
	}
}
