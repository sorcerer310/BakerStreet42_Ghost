package com.bsu.bakerstreet42_ghost.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * 工具类，提供一些方便的工具函数
 * @author fengchong
 *
 */
public class Utils {
	/**
	 * 从一个输入流对象中获得文本数据
	 * @param inputStream	输入流对象
	 * @return				返回utf-8编码的文本
	 */
	public static String getString(InputStream inputStream) {  
	    InputStreamReader inputStreamReader = null;  
	    try {  
	        inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
	    } catch (UnsupportedEncodingException e1) {  
	        e1.printStackTrace();  
	    }  
	    BufferedReader reader = new BufferedReader(inputStreamReader);  
	    StringBuffer sb = new StringBuffer("");  
	    String line;  
	    try {  
	        while ((line = reader.readLine()) != null) {  
	            sb.append(line);  
	            sb.append("\n");  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return sb.toString();  
	}  
	/**
	 * 创建一个List的项目
	 * @param id		项目的id，对应标签离的数据bk42表示店名，lr表示主题名001表示该主题的第几个标签
	 * @param title		表示该项目播放时的标题
	 * @param lrcp		表示歌词路径
	 * @param oggp		表示声音文件路径
	 * @return			返回一个Map<String,Object>类型对象
	 */
	public static Map<String,Object> makeListItemData(String id,String title,int lrcp,int oggp,int imgp){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);							//
		map.put("title",title);						//			
		map.put("lrcpath", lrcp);				//
		map.put("oggpath", oggp);				//
		map.put("imgpath", imgp);						//表示播放声音时显示的npc的图片路径
		return map;
	}
}
