package com.bsu.bakerstreet42_ghost.widget.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
/**
 * 自定义ListView数据代理，可以做很多自定义操作
 * @author fengchong
 *
 */
public class ListViewSimpleAdapter extends SimpleAdapter {
	private Context context;						//应用上下文对象，用于初始化控件
	private List<? extends Map<String, ?>> data;	//代理的数据
	public ListViewSimpleAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		this.context = context;								
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(context);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fzzy.ttf"));	//设置字体
		tv.setText(data.get(position).get("title").toString());						//设置显示的内容
		tv.setTextSize(40);															//设置字号
		
		return tv;
	}

}
