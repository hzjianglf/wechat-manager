package com.wechat.db.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.wechat.util.DESPlus;

public class DecryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		String val=null;
		if(isEncryptPropertyVal(propertyName)){
			try {
				val= new DESPlus().decrypt(propertyValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			val= propertyValue;
		}
		return val;
	}
	
	@SuppressWarnings("unused")
	private boolean isEncryptPropertyVal(String propertyName){
		if(propertyName.startsWith("encrypt")){
			return true;
		}else{
			return false;
		}
	}
}
