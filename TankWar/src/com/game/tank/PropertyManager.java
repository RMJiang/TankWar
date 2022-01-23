package com.game.tank;

import java.util.Properties;
import java.io.IOException;

/**
 * @author ruomengjiang
 * @date 2022年1月22日
 */


public class PropertyManager {
	static Properties props = new Properties();
	
	static {
		try {
			props.load(PropertyManager.class.getClassLoader().getResourceAsStream("config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(String key) {
		if(props == null) {
			return null;
		}
		return props.get(key);
	}
	
	//int getInt(key)
	//getString(key)
	
	public static void main(String[] args) {
		System.out.println(PropertyManager.get("initTankCount"));
	}
}

