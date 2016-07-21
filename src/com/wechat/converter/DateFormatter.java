package com.wechat.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

public class DateFormatter implements Formatter<Date> {

	@Override
	public String print(Date object, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		sdf.parse(text);
		return java.sql.Date.valueOf(text);
	}

}
