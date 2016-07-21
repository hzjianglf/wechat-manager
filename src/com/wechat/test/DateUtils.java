package com.wechat.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

/**
 * 提供日期的加减转换等功能 包含多数常用的日期格式
 */
public class DateUtils {
	// private static final Logger logger =
	// LoggerFactory.getLogger(DateUtils.class);

	/** new a Calendar instance */
	static GregorianCalendar cldr = new GregorianCalendar();

	/** the milli second of a day */
	public static final long DAYMILLI = 24 * 60 * 60 * 1000;

	/** the milli seconds of an hour */
	public static final long HOURMILLI = 60 * 60 * 1000;

	/** the milli seconds of a minute */
	public static final long MINUTEMILLI = 60 * 1000;

	/** the milli seconds of a second */
	public static final long SECONDMILLI = 1000;

	/** added time */
	public static final String TIMETO = " 23:59:59";

	/**
	 * set the default time zone
	 */
	static {
		cldr.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9:00"));
	}

	/** flag before */
	public static final transient int BEFORE = 1;

	/** flag after */
	public static final transient int AFTER = 2;

	/** flag equal */
	public static final transient int EQUAL = 3;

	/** date format dd/MMM/yyyy:HH:mm:ss +0900 */
	public static final String TIME_PATTERN_LONG = "dd/MMM/yyyy:HH:mm:ss +0900";

	/** date format dd/MM/yyyy:HH:mm:ss +0900 */
	public static final String TIME_PATTERN_LONG2 = "dd/MM/yyyy:HH:mm:ss +0900";

	/** date format yyyy-MM-dd HH:mm:ss */
	public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/** date format YYYY-MM-DD HH24:MI:SS */
	public static final String DB_TIME_PATTERN = "YYYY-MM-DD HH24:MI:SS";

	/** date format dd/MM/yy HH:mm:ss */
	public static final String TIME_PATTERN_SHORT = "dd/MM/yy HH:mm:ss";

	/** date format dd/MM/yy HH24:mm */
	public static final String TIME_PATTERN_SHORT_1 = "yyyy/MM/dd HH:mm";

	/** date format yyyyMMddHHmmss */
	public static final String TIME_PATTERN_SESSION = "yyyyMMddHHmmss";

	/** date format yyyyMMdd */
	public static final String DATE_FMT_0 = "yyyyMMdd";

	/** date format yyyy/MM/dd */
	public static final String DATE_FMT_1 = "yyyy/MM/dd";

	/** date format yyyy/MM/dd hh:mm:ss */
	public static final String DATE_FMT_2 = "yyyy/MM/dd hh:mm:ss";
	
	public static final String DATE_FMT_3 = "yyMMddHHmmss";

	/** date format yyyy-MM-dd */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String SIMPLE_FORMAT = "yyyy-MM-dd HH:mm:ss";


	public static String format(Date date) {
        if (date != null) {
            SimpleDateFormat sf = new SimpleDateFormat(SIMPLE_FORMAT);
            return sf.format(date);
        }
        return null;
    }
	public static String format(Date date,String format) {
        if (date != null) {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            return sf.format(date);
        }
        return null;
    }
	public static long nowMillis(){
		return System.currentTimeMillis();
	};
	public static Timestamp now(){
		return new Timestamp(nowMillis());
	};
	/**
	 * change string to Timestamp 如果传入的String是数字格式的则优先会转化成Long构造Date
	 * 
	 * @deprecated plz use
	 *             <code>Calendar.toDate(String sDate, String sFmt)</code>
	 * @param str
	 *            formatted timestamp string
	 * @param sFmt
	 *            string format
	 * @return timestamp
	 */
	public static Timestamp parseString(String str, String sFmt) {
		if ((str == null) || str.equals("")) {
			return null;
		}

		try {
			long time = Long.parseLong(str);

			return new Timestamp(time);
		} catch (Exception ex) {
			try {
				DateFormat df = new SimpleDateFormat(sFmt);
				java.util.Date dt = df.parse(str);

				return new Timestamp(dt.getTime());
			} catch (Exception pe) {
				try {
					return Timestamp.valueOf(str);
				} catch (Exception e) {
					return null;
				}
			}
		}
	}

	/**
	 * return current date
	 * 
	 * @return current date
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * return current calendar instance
	 * 
	 * @return Calendar
	 */
	public static Calendar getCurrentCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * return current time
	 * 
	 * @return current time
	 */
	public static Timestamp getCurrentDateTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static final int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取年份
	 * 
	 * @param millis
	 *            long
	 * @return int
	 */
	public static final int getYear(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static final int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取月份
	 * 
	 * @param millis
	 *            long
	 * @return int
	 */
	public static final int getMonth(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取日期
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static final int getDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取日期
	 * 
	 * @param millis
	 *            long
	 * @return int
	 */
	public static final int getDate(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获取小时
	 * 
	 * @param date
	 *            Date
	 * @return int
	 */
	public static final int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取小时
	 * 
	 * @param millis
	 *            long
	 * @return int
	 */
	public static final int getHour(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 把日期后的时间归0 变成(yyyy-MM-dd 00:00:00:000)
	 * 
	 * @param date
	 *            Date
	 * @return Date
	 */
	public static final Date zerolizedTime(Date fullDate) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(fullDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date convertDate(String adateStrteStr, String format) {
		java.util.Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(adateStrteStr);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return date;
	}
	
	public static String convertString(Date d,String format){
		SimpleDateFormat f = new SimpleDateFormat(format);
		
		String code = null;
		try{
			code = f.format(d);
		}catch(Exception e){
			
		}
		
		return code;
	}

	/**
     * 获得卡截止时间
     * @param effectivePoint
     * @return
     */
    public static Date getCardEndDate(Date cardStartDate){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(cardStartDate);
    	calendar.add(Calendar.YEAR, 1);
    	int y=calendar.get(Calendar.YEAR);
		int m=calendar.get(Calendar.MONTH);
		if(m==0||m==2||m==4||m==6||m==7||m==9||m==11) {
			calendar.set(Calendar.DATE, 31);
		}else if(m==3||m==5||m==8||m==10){
			calendar.set(Calendar.DATE, 30);
		}else{
			if((y%4==0&&y%100!=0)||(y%400==0)){
				calendar.set(Calendar.DATE, 29);
			}else{
				calendar.set(Calendar.DATE, 28);
			}
		}
		Date cardEndDate=calendar.getTime();
		String cardEndDateTime=DateUtils.formatDate(cardEndDate)+" 23:59:59";
		cardEndDate=DateUtils.parse(cardEndDateTime);
		return cardEndDate;
    }
    
    public static String formatDate(Date date) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            return df.format(date);
        }
        return null;
    }
    
    /**
     * 把字符串转成时间 ：yyyy-MM-dd HH:mm:ss 如果格式不正确返回NULL
     * 
     * @param date
     * @return
     */
    public static Date parse(String date) {
        if (StringUtils.isBlank(date))
            return null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(SIMPLE_FORMAT);
            return sf.parse(date);
        } catch (ParseException e) {
            return parseDate(date);
        }
    }
    
    /**
     * 把字符串转成时间 ：yyyy-MM-dd 如果格式不正确返回NULL
     * 
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        if (StringUtils.isBlank(date))
            return null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
            return df.parse(date);
        } catch (ParseException e) {
        }
        return null;
    }
}
