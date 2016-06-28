package com.wechat.contorller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.wechat.prev.Prev;
import com.wechat.util.FileUtil;
import com.wechat.util.MyDateUtil;

/**
 * 
 * @Description 文件上传
 * @ClassName FileUploadController.java
 * @author Administrator
 * @date 2016年6月16日上午10:36:31
 */
@Controller
@RequestMapping(value="/file")
public class FileUploadController extends BaseController{

	private String path="D:/opt"+"/pic/";
	
	/**
	 * 使用xheditor插件上传
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/xheditorFileUpload" , method = RequestMethod.POST)
	@Prev(module="FileUploadManager" ,oprator="upload")
	public void xheditorFileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ServletOutputStream out = response.getOutputStream();
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 设置编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile image = multipartRequest.getFile("filedata");
            String url = "images"+File.separator+MyDateUtil.dateToString(new Date(), "yyyyMMdd")+File.separator;
            String filePath = path;
            Random r = new Random();
            if(image != null && !image.isEmpty()){
                InputStream xtins = image.getInputStream();
                String hj = new String(image.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
                hj = hj.split("\\.")[1];
                //这里用来生成文件名
                String fileName = MyDateUtil.dateToString(new Date(), "yyyyMMddHHmmss")+r.nextInt(1000)+"."+hj;
                new FileUtil().saveInputStreamToFile(filePath, fileName, xtins);
                
                out.print("{'err':'','msg':'"+ ("../../img/" +fileName).replace("\\", "/")+"'}");
                
            }
        }
	}
	
	/**
	 * 使用ajaxfileupoload上传
	 * @param picFile
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ajaxFileUpload")
	@Prev(module="FileUploadManager",oprator="upload")
	public void ajaxFileUpload(@RequestParam MultipartFile[] picFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest mt=(MultipartHttpServletRequest) request;
		MultipartFile file=mt.getFile("picFile");
		String fileName=file.getOriginalFilename();
		
		//服务端文件命名
		//String path=request.getSession().getServletContext().getRealPath("/");
		
		String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//将file转成字节
		byte[] fileToByte=file.getBytes();
				
		FileOutputStream out=new FileOutputStream(new File(path+time+fileName));
		out.write(fileToByte);
		
		out.flush();
		out.close();
		
		//ajax返回数据
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.println("0`" + "../../img/" + time+fileName);
		pw.flush();
		pw.close();
	}
}
