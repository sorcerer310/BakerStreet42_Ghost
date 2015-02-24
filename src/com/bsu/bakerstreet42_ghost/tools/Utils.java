package com.bsu.bakerstreet42_ghost.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bsu.bakerstreet42_ghost.MainActivity;
import com.bsu.bakerstreet42_ghost.R;


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
	 * @param oggp		表示声音文件路径
	 * @return			返回一个Map<String,Object>类型对象
	 */
	public static Map<String,Object> makeListItemData(String id,String title,int oggp,int imgp){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);							//
		map.put("title",title);						//			
		map.put("oggpath", oggp);				//
		map.put("imgpath", imgp);						//表示播放声音时显示的npc的图片路径
		return map;
	}
	
	public static Map<String,String> parseVideoData(String d){
		Map<String,String> ret = null;
        if(d.contains(":")){
            String[] ss= d.split(":");
            String vpath = "";
            String vtitle = "";
            if(ss[0].equals("vpath")){
            	ret = new HashMap<String,String>();
            	PropertiesInstance pi = PropertiesInstance.getInstance();
            	vpath = pi.properties.getProperty("vpath");
            	if(ss[1].equals("v001")){
            		vpath+=R.raw.v001;
            		vtitle = pi.properties.getProperty("vtitle1");
            	}else if(ss[1].equals("v002")){
            		vpath+=R.raw.v002;
            		vtitle = pi.properties.getProperty("vtitle2");
            	}else if(ss[1].equals("v003")){
            		vpath+=R.raw.v003;
            		vtitle = pi.properties.getProperty("vtitle3");
            	}else if(ss[1].equals("v004")){
            		vpath+=R.raw.v004;
            		vtitle = pi.properties.getProperty("vtitle4");
            	}else if(ss[1].equals("v005")){
            		vpath+=R.raw.v005;
            		vtitle = pi.properties.getProperty("vtitle5");
            	}
            	ret.put("vtitle", vtitle);
            	ret.put("vpath", vpath);
            }
        }
        return ret;
	}
	/**
	 * 设置持久数据,以供主activity显示已收到的信息
	 * @param c		Context对象
	 * @param uri	androidpn传过来的uri数据,格式为vpath:v001
	 */
	public static void saveSharedPreferences(Context c,String uri){
		SharedPreferences settings = c.getSharedPreferences("ListDatas", Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		
		if(uri.contains(":")){
            String[] ss= uri.split(":");
            if(ss[0].equals("vpath")){
            	if(ss[1].equals("v001")){
            		editor.putBoolean("bk42-xz001", true);
            	}else if(ss[1].equals("v002")){
            		editor.putBoolean("bk42-xz002", true);
            	}else if(ss[1].equals("v003")){
            		editor.putBoolean("bk42-xz003", true);
            	}else if(ss[1].equals("v004")){
            		editor.putBoolean("bk42-xz004", true);
            	}else if(ss[1].equals("v005")){
            		editor.putBoolean("bk42-xz005", true);
            	}
            }
        }
		editor.commit();
	}
	
	/**
	 * 判断视频是否已经发送到手机中
	 * @param c		环境变量
	 * @param uri	视频的uri数据
	 */
	public static boolean isVideoExistSharedPreferences(Context c,String uri){
		SharedPreferences settings = c.getSharedPreferences("ListDatas", Context.MODE_PRIVATE);
//		Editor editor = settings.edit();

		if(uri.contains(":")){
            String[] ss= uri.split(":");
            if(ss[0].equals("vpath")){
            	if(ss[1].equals("v001") && settings.getBoolean("bk42-xz001", false)){
            		return true;
            	}else if(ss[1].equals("v002") && settings.getBoolean("bk42-xz002", false)){
            		return true;
            	}else if(ss[1].equals("v003") && settings.getBoolean("bk42-xz003", false)){
            		return true;
            	}else if(ss[1].equals("v004") && settings.getBoolean("bk42-xz004", false)){
            		return true;
            	}else if(ss[1].equals("v005") && settings.getBoolean("bk42-xz005", false)){
            		return true;
            	}
            }
        }
		return false;
	}
	
	
	/**
	 * 模拟post方式发送表单数据
	 * @param path			url路径
	 * @param params		url参数
	 * @return				返回服务器响应的数据
	 * @throws Exception
	 */
    public static byte[] sendPostRequestByForm(String path, String params) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        return readInputStream(inStream);
    }
    
    /**
     * 从数据流中读取数据
     * @param inStream		输入数据流
     * @return				返回数据流数据
     * @throws Exception
     */
    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }
}
