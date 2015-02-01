package com.bsu.bakerstreet42_ghost.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesInstance {
	private static PropertiesInstance instance = null;
	public static PropertiesInstance getInstance(){
		if(instance==null)
			instance = new PropertiesInstance();
		return instance;
	}
	private PropertiesInstance(){
		InputStream is = getClass().getResourceAsStream("/assets/config.properties");
		try {
			properties.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Properties properties = new Properties(); 
}
