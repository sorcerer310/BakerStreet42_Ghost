package com.bsu.bakerstreet42_ghost.listener;
/**
 * 用来监听读取ndef数据操作
 * @author fengchong
 *
 */
public interface OnNfcReadListener {
	public void read(String data);
}
