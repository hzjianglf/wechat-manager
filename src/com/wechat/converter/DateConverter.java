package com.wechat.converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// dateFormat.setLenient(false);
		try {
			return dateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public Timestamp convert1(String arg0){
		
		Timestamp tt=new Timestamp(System.currentTimeMillis());
		
		tt=Timestamp.valueOf(arg0);
		
		return tt;
	}

	public static void main(String[] args) {
		System.err.println(new DateConverter().convert1("2016-06-30 12:56:51"));
	}
}