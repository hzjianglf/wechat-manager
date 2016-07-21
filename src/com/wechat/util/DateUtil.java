package com.wechat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * date类型转string
	 * @param date
	 * @param type
	 * @return
	 */
	public static String dateToString(Date date, String type) {  
        String str = null;  
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        if (type.equals("SHORT")) {  
            // 07-1-18  
            format = DateFormat.getDateInstance(DateFormat.SHORT);  
            str = format.format(date);  
        } else if (type.equals("MEDIUM")) {  
            // 2007-1-18  
            format = DateFormat.getDateInstance(DateFormat.MEDIUM);  
            str = format.format(date);  
        } else if (type.equals("FULL")) {  
            // 2007年1月18日 星期四  
            format = DateFormat.getDateInstance(DateFormat.FULL);  
            str = format.format(date);  
        }  
        return str;  
    }
	
	/**
	 * string 转date
	 * @param str
	 * @return
	 */
    public static Date stringToDate(String str) {  
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = null;  
        try {  
            // Fri Feb 24 00:00:00 CST 2012  
            date = format.parse(str);   
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        // 2012-02-24  
        //date = java.sql.Date.valueOf(str);  
                                              
        return date;  
    }  
    /**
     * 
     * @param year
     * @return
     * @author Administrator-zhur
     * @date 2016年7月15日 下午3:54:42
     */
    public static Date StringYearToDate(String year){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
    	Date date=null;
    	try {
			date=sdf.parse(year);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;
    }
    /**
     * 直接转成java.sql.Date
     * @param str
     * @return
     */
    public static java.sql.Date stringToSQLDate(String str) {  
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
    	Date date = null;  
    	try {  
    		// Fri Feb 24 00:00:00 CST 2012  
    		date = format.parse(str);   
    	} catch (ParseException e) {  
    		e.printStackTrace();  
    	}  
    	// 2012-02-24  
    	return java.sql.Date.valueOf(str);  
    }  
    public static void main(String[] args) throws ParseException {  
        Date date = new Date();  
        System.out.println(dateToString(date, "MEDIUM"));  
        String str = "2012-2-24";  
        System.out.println(stringToDate(str));  
        System.out.println(stringToSQLDate(str));  
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
        String dstr="2008-4-24";  
        java.util.Date date1=sdf.parse(dstr);
        //Thu Apr 24 00:00:00 CST 2008
        System.err.println(date1);
        
        System.err.println(StringYearToDate("2017"));
    }
}
