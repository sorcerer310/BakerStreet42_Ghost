package com.bsu.bakerstreet42_ghost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bsu.bakerstreet42_ghost.tools.PropertiesInstance;
import com.bsu.bakerstreet42_ghost.widget.adapter.ListViewSimpleAdapter;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ListFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ListFragment#newInstance} factory method to create an instance of this
 * fragment.
 *
 */
public class ListFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	//列表控件使用的对象
	private ListView lv_message;
	private List<Map<String,Object>> listdata;
	private ListViewSimpleAdapter sa;
	
	//视频音频资源路径
//	private String vpath,vtitle1,vtitle2,vtitle3,vtitle4,vtitle5;
	//属性实例
	private PropertiesInstance pi = null;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment FragmentList.
	 */
	// TODO: Rename and change types and number of parameters
	public static ListFragment newInstance(String param1, String param2) {
		ListFragment fragment = new ListFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ListFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		//获得配置数据
//		pi = PropertiesInstance.getInstance();
//		vpath = pi.properties.getProperty("vpath");
//		vtitle1 = pi.properties.getProperty("vtitle1");
//		vtitle2 = pi.properties.getProperty("vtitle2");
//		vtitle3 = pi.properties.getProperty("vtitle3");
//		vtitle4 = pi.properties.getProperty("vtitle4");
//		vtitle5 = pi.properties.getProperty("vtitle5");

		
		//设置标题
		View view = inflater.inflate(R.layout.fragment_list, container, false); 
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fzzy.ttf"));

		//设置列控件
		lv_message = (ListView) view.findViewById(R.id.lv_message);
		
		
		//初始化列表代理数据
		if(listdata==null)
			listdata = new ArrayList<Map<String,Object>>();
		
		return inflater.inflate(R.layout.fragment_list, container,false);
	}

	/**
	 * 初始化收件箱消息部分
	 */
	public void setListViewSimpleAdapter(Context c,ListViewSimpleAdapter sa,String vp){
		
		lv_message.setAdapter(sa);
		final String vpath = vp;
		final Context context = c;
		lv_message.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position,long id) {
				Map<String,Object> mapitem = listdata.get(position);
				startVideoActivity(context,mapitem.get("title").toString(),
						vpath+((Integer)mapitem.get("oggpath")));
			}});
	}
	
	/**
	 * 传入必要参数,开始播放视频
	 * @param title		视频标题
	 * @param vpath		视频文件路径
	 */
	public void startVideoActivity(Context context,String title,String vpath){
		//根据当前获得的数据跳转到下一界面自动播放
		Intent intent = new Intent(context,VideoActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("vpath", vpath);
		context.startActivity(intent);
	}
	
	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * 清理列表的数据
	 */
	public void clear(){
		listdata.clear();
	}
	/**
	 * 获得列表控件数据集
	 * @return
	 */
	public List<Map<String,Object>> getListdata(){
		return listdata;
	}
	
	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
