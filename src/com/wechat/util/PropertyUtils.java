package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties文件信息
 * @Description 
 * @ClassName PropertyUtils.java
 * @author Administrator-zhur
 * @date 2016年7月14日下午4:15:43
 */
public class PropertyUtils {
	
	public static String getProperty(String key) {
		//InputStream in = PropertyUtils.class.getResourceAsStream("/properties/image.properties");
		InputStream in=StringUtils.class.getResourceAsStream("/properties/image.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			new RuntimeException(e.getMessage());
		}
		return p.getProperty(key);
	}
	
	public static void main(String[] args) {
		String p=PropertyUtils.getProperty("photo.path");
		System.err.println(p);
	}
}
