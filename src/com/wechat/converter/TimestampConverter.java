package com.wechat.converter;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.wechat.util.Log4jLogger;

public class TimestampConverter implements Converter<String, Timestamp> {

	private static final Log4jLogger log = Log4jLogger.getLogger(TimestampConverter.class);
	@Override
	public Timestamp convert(String arg0) {
		Timestamp tt=null;
		if(!StringUtils.isEmpty(arg0)){
			//long time=Long.valueOf(arg0);
			//tt=new Timestamp(time);
			tt=Timestamp.valueOf(arg0);
		}
		return tt;
	}

	public static void main(String[] args) {
		System.err.println(new TimestampConverter().convert("2016-06-30 12:56:51"));
		
		log.error(System.currentTimeMillis());
		log.error(Long.valueOf(System.currentTimeMillis()));
		log.error(new Timestamp(1468812853567L));
	}
}
