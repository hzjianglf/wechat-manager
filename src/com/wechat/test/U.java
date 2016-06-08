package com.wechat.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class U {

	public static void main(String[] args) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("md5");
		String data="123";
		byte[] bt = digest.digest(data.getBytes());
		BASE64Encoder encoder = new BASE64Encoder();
		String str = encoder.encode(bt);
		System.err.println(str);
	}
	
}
