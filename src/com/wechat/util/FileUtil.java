package com.wechat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

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
    
    public FileUtil() {
	}

	/**
	 * copy srcFile to distFile
	 * 
	 * @param distFile
	 * @param srcFile
	 * @throws IOException
	 */
	public static void copyFile(String distFile, String srcFile) throws IOException {
		File f = new File(srcFile);
		if (!f.exists())
			throw new FileNotFoundException(srcFile + " not exists");
		int i = distFile.lastIndexOf(File.separator);
		if (i > 0) {
			String path = distFile.substring(0, i);
			File p = new File(path);
			if (!p.exists())
				if (!p.mkdirs())
					throw new IOException("fail to create path " + path);
		}
		FileInputStream fis = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(distFile);
		FileChannel inputChannel = fis.getChannel();
		FileChannel outputChannel = fos.getChannel();
		outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		outputChannel.close();
		inputChannel.close();
		fis.close();
		fos.close();
	}

	/**
	 * move file from srcFile to distFile
	 * 
	 * @param distFile
	 * @param srcFile
	 * @throws IOException
	 */
	public static void moveFile(String distFile, String srcFile) throws IOException {
		File f = new File(srcFile);
		if (!f.exists())
			throw new FileNotFoundException(srcFile + " not exists");
		int i = distFile.lastIndexOf(File.separator);
		if (i > 0) {
			String path = distFile.substring(0, i);
			File p = new File(path);
			if (!p.exists())
				if (!p.mkdirs())
					throw new IOException("fail to create path " + path);
		}
		if (!f.renameTo(new File(distFile))) {
			copyFile(distFile, srcFile);
			f.delete();
		}
	}

	/**
	 * @author Jacken
	 * @param fileName
	 * @param content
	 * @param enc
	 * @return
	 * @throws IOException
	 */
	public static void writeStringToFile(String fileName, String content, String enc) throws IOException {
		FileUtil.mkdir(fileName);
		File file = new File(fileName);
		if (file.isFile()) {
			file.deleteOnExit();
			file = new File(file.getAbsolutePath());
		}
		OutputStreamWriter os = null;
		if (enc == null || enc.length() == 0) {
			os = new OutputStreamWriter(new FileOutputStream(file));
		} else {
			os = new OutputStreamWriter(new FileOutputStream(file), enc);
		}
		os.write(content);
		os.close();
	}

	public static void mkdir(String fileName) throws IOException {
		int i = fileName.lastIndexOf(File.separator);
		if (i > 0) {
			String path = fileName.substring(0, i);
			File p = new File(path);
			if (!p.exists())
				if (!p.mkdirs())
					throw new IOException("fail to create path " + path);
		}
	}

	public static void main(String[] args) {
		try {
			//FileUtil.writeStringToFile("d:\\test\\test\\sdfsd\\test.txt", "sdfd", "GBK");
			FileUtil.mkdir("D:\\opt\\pic\\uploads\\2016\\7\\14\\16\\201607141623.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
