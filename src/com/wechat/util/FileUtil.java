package com.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * @Description 文件工具类
 * @ClassName FileUtil.java
 * @author Administrator
 * @date 2016年6月16日上午10:38:03
 */
public class FileUtil {

	
	/**
     * 保存文件流到指定路径
     * @param filePath
     * @param fileName
     * @param inputStream
     * @throws Exception
     */
    public static void saveInputStreamToFile(String filePath, String fileName,
            InputStream inputStream) throws Exception {
        OutputStream os = null;
        try {
            File forder = new File(filePath);
            if (!forder.exists()) {
                forder.mkdir();
            }
            File file = new File(filePath + fileName);
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (os != null) {
                os.close();
            }
        }
    }
}
