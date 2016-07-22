package com.wechat.db.config;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.druid.util.DruidPasswordCallback;
import com.alibaba.druid.util.StringUtils;
import com.wechat.util.DESPlus;

@SuppressWarnings("serial")
public class DbPasswordCallback extends DruidPasswordCallback {

	private Logger log=Logger.getLogger(DbPasswordCallback.class);
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		String pwd = properties.getProperty("db.password");
		log.info(pwd);
		if (!StringUtils.isEmpty(pwd)) {
			try {
				setPassword(new DESPlus().decrypt(pwd).toCharArray());
				log.info(getPassword());
			} catch (Exception e) {
				setPassword(pwd.toCharArray());
				e.printStackTrace();
			}
		}
	}
}
