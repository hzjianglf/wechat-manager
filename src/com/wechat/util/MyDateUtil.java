package com.wechat.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {

	/**
	 *  string 转 timestamp
	 * @param dateStr
	 * @return
	 */
	public static Timestamp stringToTimestamp(String dateStr){
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		
		ts=Timestamp.valueOf(dateStr);
		
		//DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//date=sdf.format(dateStr);
		
		return ts;
	}
	
	public static String dateToString(Date date ,String format){
		DateFormat sdf=new SimpleDateFormat(format);
		String dateStr=sdf.format(date);
		return dateStr;
	}
}
