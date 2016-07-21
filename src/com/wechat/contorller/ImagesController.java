package com.wechat.contorller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.wechat.prev.Prev;
import com.wechat.util.FileUtil;
import com.wechat.util.PictureUtil;
import com.wechat.util.PropertyUtils;

@Controller
@RequestMapping("/image")
public class ImagesController {

	private static final Logger log = Logger.getLogger(ImagesController.class);

	@RequestMapping(value = "photo", method = RequestMethod.GET)
	public static void photo(HttpServletResponse rep, String imgName)
			throws IOException {
		byte[] imgs = PictureUtil.getBytes(PictureUtil.getFolderPath(
				PropertyUtils.getProperty("photo.path"), imgName) + imgName);
		if (imgs != null) {
			rep.setContentType("image/x-png");
			rep.setHeader("content-disposition", "attachment; filename="
					+ imgName);
			OutputStream os = rep.getOutputStream();
			os.write(imgs);
			os.flush();
			os.close();
		}
	}

	// return new ModelAndView(new MappingJacksonJsonView(),map);
	// ,produces="application/json;charset=UTF-8"
	@RequestMapping(value = "uploadify", method = RequestMethod.POST)
	@Prev(module = "FileUploadManager", oprator = "upload")
	@ResponseBody
	public void uploadify(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
			MultipartFile file = mrequest.getFile("upFile");

			// String data = Base64.encodeBase64String(file.getBytes());
			byte[] bytes = file.getBytes();

			long time = new Date().getTime();
			String imageName = time + ".png";
			String fullImageName = PropertyUtils.getProperty("photo.path")
					+ PictureUtil.IMAGE_UPLOAD_FOLDER
					+ PictureUtil.generateFolderPathByTime(time) + imageName;
			java.io.File folder = new java.io.File(
					PropertyUtils.getProperty("photo.path")
							+ PictureUtil.IMAGE_UPLOAD_FOLDER
							+ PictureUtil.generateFolderPathByTime(time));
			folder.mkdirs();
			FileUtil.mkdir(fullImageName);
			java.io.File f = new java.io.File(fullImageName);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.close();

			response.setContentType("text/plain; charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(imageName);
			pw.flush();
			pw.close();

		} catch (Exception e) {
			log.error("imageUp error", e);

		}

	}

	@RequestMapping(value = "ajax", method = RequestMethod.GET)
	public void ajax(@ModelAttribute Person person, PrintWriter printWriter) {
		String jsonString = JSONObject.fromObject(person).toString();
		printWriter.write(jsonString);//输出到页面
		printWriter.flush();
		printWriter.close();
	}
	
	@RequestMapping(value = "uploadify2Map", method = RequestMethod.GET)
	public ModelMap uploadify2Map(HttpServletRequest request,HttpServletResponse response) {
		ModelMap m = new ModelMap();
		try {
			MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
			MultipartFile file = mrequest.getFile("upFile");

			byte[] bytes = file.getBytes();

			long time = new Date().getTime();
			String imageName = time + ".png";
			String fullImageName = PropertyUtils.getProperty("photo.path")
					+ PictureUtil.IMAGE_UPLOAD_FOLDER
					+ PictureUtil.generateFolderPathByTime(time) + imageName;
			java.io.File folder = new java.io.File(
					PropertyUtils.getProperty("photo.path")
							+ PictureUtil.IMAGE_UPLOAD_FOLDER
							+ PictureUtil.generateFolderPathByTime(time));
			folder.mkdirs();
			FileUtil.mkdir(fullImageName);
			java.io.File f = new java.io.File(fullImageName);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.close();
			
			m.put("imgName", imageName);
			m.put("url", request.getContextPath() + "/image/photo.html?imgName=" + imageName);
			m.put("status", 1);
		} catch (Exception e) {
			m.put("status", 0);
			m.put("message", "上传失败");
			e.printStackTrace();
		}
		return m;
	}

	@RequestMapping(value = "uploadify2String", method = RequestMethod.POST)
	@ResponseBody
	public String uploadify2String(HttpServletRequest request,
			HttpServletResponse response) {
		Map m = new HashMap();
		try {
			MultipartHttpServletRequest mrequest = (MultipartHttpServletRequest) request;
			MultipartFile file = mrequest.getFile("upFile");

			// String data = Base64.encodeBase64String(file.getBytes());
			byte[] bytes = file.getBytes();

			long time = new Date().getTime();
			String imageName = time + ".png";
			String fullImageName = PropertyUtils.getProperty("photo.path")
					+ PictureUtil.IMAGE_UPLOAD_FOLDER
					+ PictureUtil.generateFolderPathByTime(time) + imageName;
			java.io.File folder = new java.io.File(
					PropertyUtils.getProperty("photo.path")
							+ PictureUtil.IMAGE_UPLOAD_FOLDER
							+ PictureUtil.generateFolderPathByTime(time));
			folder.mkdirs();
			FileUtil.mkdir(fullImageName);
			java.io.File f = new java.io.File(fullImageName);
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.close();
			
			m.put("imgName", imageName);
			m.put("url", request.getContextPath() + "/image/photo.html?imgName=" + imageName);
			m.put("status", 1);
		} catch (Exception e) {
			m.put("status", 0);
			m.put("message", "上传失败");
			e.printStackTrace();
		}
		return JSONObject.fromObject(m).toString();
	}
}
