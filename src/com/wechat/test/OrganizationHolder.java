package com.wechat.test;

public class OrganizationHolder {
	private static ThreadLocal<String> orgLocal;
	
	static{
		orgLocal = new ThreadLocal<String>(); 
	}

	public static void setOrgKey(String orgKey){
		orgLocal.set(orgKey);
	}
	public static String getOrgKey(){
		return orgLocal.get();
	}
	
	public static void remove(){
		orgLocal.remove();
	}
}
