package com.wechat.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 1,生成时间目录
 * 2,根据文件获取目录
 * @Description 
 * @ClassName PictureUtil.java
 * @author Administrator-zhur
 * @date 2016年7月14日下午4:15:11
 */
public class PictureUtil {

    public static String IMAGE_UPLOAD_FOLDER = "uploads"; 

    /**
     * 获取图片磁盘地址
     * 
     * @param path
     * @param imgName
     * @return
     */
    public static String getImgFilePath(String path, String imgName) {
        if (StringUtils.isBlank(imgName)) {
            return "";
        }
        return getFolderPath(path, imgName) + imgName;
    }

    /**
     * 获取文件磁盘目录
     * 
     * @param path
     * @param imgName
     * @return
     */
    public static String getFolderPath(String path, String imgName) {
        String[] pic = imgName.split("\\.");
        if (pic.length < 2 || !StringUtils.isNumeric(pic[0])) {
            return "";
        }
        return path + IMAGE_UPLOAD_FOLDER + generateFolderPathByTime(Long.valueOf(pic[0]));
    }

    /**
     * ds /** 根据时间生成目录
     * 
     * @param time
     * @return
     */
    public static String generateFolderPathByTime(long time) {
        if (time <= 0) {
            return "";
        }
        return "/" + getYear(time) + "/" + getMonth(time) + "/" + getDate(time) + "/"
                + getHour(time) + "/";
    }

    /**
     * 获取年份
     * 
     * @param millis long
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
     * @param millis long
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
     * @param millis long
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
     * @param millis long
     * @return int
     */
    public static final int getHour(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
    
    public static void main(String[] args) {
    	String str=generateFolderPathByTime(new Date().getTime());
    	System.err.println(str);
    	String stt=getFolderPath("D:\\opt\\pic","1468484591054.png");
    	System.err.println(stt);
	}
}
