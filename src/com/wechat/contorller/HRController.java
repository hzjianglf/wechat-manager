package com.wechat.contorller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.EmployeeInfo;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.EmployeeService;
import com.wechat.service.HrService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.util.TimeUtil;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/system/hr")
@Module("HrManager")
public class HRController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(HRController.class);

	@Resource
	private HrService hrService;

	@Resource
	private EmployeeService employeeService;

	@RequestMapping("/statistics")
	@Prev(module = "statistics", oprator = "all")
	public ModelAndView statistics() throws Exception {
		try {
			return new ModelAndView("hr/statistics", "map",
					hrService.statisticsGender());
		} catch (Exception e) {
			log.error("statistics", e);
			throw e;
		}
	}

	@RequestMapping("/employeeInfoList")
	@Prev(module = "employeeInfoList", oprator = "all")
	public ModelAndView employeeInfoList(EmployeeInfo info, PageQueryUtil page)
			throws Exception {
		try {
			return backView("hr/employeeInfoList",
					hrService.findEmployeeInfoByPage(info, page));
		} catch (Exception e) {
			log.error("employeeInfoList error", e);
			throw e;
		}
	}

	@RequestMapping("/addEmployeeInfo")
	@Prev(module = "HrManager", oprator = "add")
	public String addEmployeeInfo() {
		return "hr/addEmployeeInfo";
	}

	@RequestMapping("/ajaxFileUpload") 
	@Prev(module = "HrManager", oprator = "add")
    public String addUser(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response) throws IOException{  
        //可以在上传文件的同时接收其它参数  
       // System.out.println("收到用户[" + uname + "]的文件上传请求");  
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建  
        String realPath = request.getSession().getServletContext().getRealPath("/upload");  
        //设置响应给前台内容的数据格式  
        response.setContentType("text/plain; charset=UTF-8");  
        //设置响应给前台内容的PrintWriter对象  
        PrintWriter out = response.getWriter();  
        //上传文件的原名(即上传前的文件名字)  
        String originalFilename = null;  
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解  
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解  
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件  
        for(MultipartFile myfile : myfiles){  
            if(myfile.isEmpty()){  
                out.print("1`请选择文件后上传");  
                out.flush();  
                return null;  
            }else{  
                originalFilename = myfile.getOriginalFilename();  
                System.out.println("文件原名: " + originalFilename);  
                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件长度: " + myfile.getSize());  
                System.out.println("文件类型: " + myfile.getContentType());  
                System.out.println("========================================");  
                try {  
                    //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉  
                    //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传  
                	
                	//java.lang.NoSuchMethodError: org.apache.commons.io.FileUtils.copyInputStreamToFile(Ljava/io/InputStream;Ljava/io/File;)V
                    //FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));  
                	myfile.transferTo(new File(realPath,originalFilename));
                	/*MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                	 Iterator iter = multiRequest.getFileNames();
                	   while(iter.hasNext()){
                	    MultipartFile file = multiRequest.getFile((String)iter.next());
                	    if(file != null){
                	     String fileName = file.getOriginalFilename();
                	     String path = "D:/" + fileName;
                	     File localFile = new File(path);
                	     //写文件到本地
                	     file.transferTo(localFile);
                	    }
                	   }*/
                	
                } catch (Exception e) {  
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");  
                    e.printStackTrace();  
                    out.print("1`文件上传失败，请重试！！");  
                    out.flush();  
                    return null;  
                }  
            }  
        }  
        //此时在Windows下输出的是[D:\Develop\apache-tomcat-6.0.36\webapps\AjaxFileUpload\\upload\愤怒的小鸟.jpg]  
        //System.out.println(realPath + "\\" + originalFilename);  
        //此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]  
        //System.out.println(request.getContextPath() + "/upload/" + originalFilename);  
        //不推荐返回[realPath + "\\" + originalFilename]的值  
        //因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img src="D:/aa.jpg">firefox是不认的  
        out.print("0`" + request.getContextPath() + "/upload/" + originalFilename);  
        out.flush();  
        return null;  
    }
	@RequestMapping(value = "/addEmployeeInfoCommit", method = RequestMethod.POST)
	@Prev(module = "HrManager", oprator = "add")
	@ResponseBody
	public ModelMap addEmployeeInfoCommit(EmployeeInfo info, String entryTimeStr)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(info);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			String emailMessage = "";
			if (!map.containsKey("emailMessage")
					&& !employeeService.isOnlyEmail(info.getEmail().trim(),
							info.getId())) {
				flag = true;
				emailMessage = "邮箱名已注册过！";
				map.put("emailMessage", emailMessage);
			}

			String mobileNumberMessage = "";
			if (!map.containsKey("mobileNumberMessage")
					&& !employeeService.isOnlyMobileNumber(info
							.getMobileNumber().trim(), info.getId())) {
				flag = true;
				mobileNumberMessage = "手机号已注册过！";
				map.put("mobileNumberMessage", mobileNumberMessage);
			}

			if (flag == false) {
				StringTools.trim(info);
				info.setEntryTime(TimeUtil.toDate(entryTimeStr, 1));
				info.setIsDel(Constrants.DATA_NOT_DEL);
				if (info.getId() == null) {
					hrService.save(info);
				} else {
					hrService.update(info);
				}
				map.put(Constrants.MESSAGE_TIP, "操作成功！");
			}
			map.put(Constrants.MESSAGE_TIP_FLAGS, flag);
			return map;
		} catch (Exception e) {
			log.error("addEmployeeInfoCommit error", e);
			throw e;
		}
	}

	@RequestMapping("/updateEmployeeInfo")
	@Prev(module = "HrManager", oprator = "update")
	public ModelAndView updateEmployeeInfo(EmployeeInfo info,
			PageQueryUtil page, Long id) throws Exception {
		try {
			if (info.getName() != null) {
				info.setName(new String(info.getName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("hr/addEmployeeInfo", "info",
					hrService.get(EmployeeInfo.class, id)).addObject(
					"employeeInfo", info).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateEmployeeInfo error", e);
			throw e;
		}
	}

	@RequestMapping(value = "/deleteEmployeeInfo", method = RequestMethod.POST)
	@ResponseBody
	@Prev(module = "HrManager", oprator = "delete")
	public ModelMap deleteEmployeeInfo(Long id) throws Exception {
		try {
			ModelMap map = new ModelMap();
			if (id != null && hrService.deleteEmployeeInfo(id) == 1) {
				map.put(Constrants.MESSAGE_TIP, "删除成功");
				map.put(Constrants.MESSAGE_TIP_FLAGS, true);
			} else {
				map.put(Constrants.MESSAGE_TIP, "删除失败");
				map.put(Constrants.MESSAGE_TIP_FLAGS, false);
			}
			return map;
		} catch (Exception e) {
			log.error("deleteEmployeeInfo error", e);
			throw e;
		}
	}

}