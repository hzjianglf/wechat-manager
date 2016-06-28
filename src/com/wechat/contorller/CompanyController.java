package com.wechat.contorller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Company;
import com.wechat.entity.Honor;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.CompanyService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/company")
@Module("CompanyManager")
public class CompanyController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(CompanyController.class);
	
	@Resource
	private CompanyService companyService;

	@Resource
	private UserService userService;

	/**
	 * 介绍信息列表页
	 * @param honor
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/companyList")
	@Prev(module="companyInfoList",oprator="all")
	public ModelAndView honorList(Company company,PageQueryUtil page) throws Exception {
		return backView("company/companyList", companyService.findCompanyInfoByPage(company, page));
	}

	/**
	 * 修改
	 * @param honor
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCompanyInfo")
	@Prev(module = "CompanyManager", oprator = "update")
	public ModelAndView updateCompanyInfo(Company company,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (company.getCompanyName() != null) {
				company.setCompanyName(new String(company.getCompanyName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("company/addCompanyInfo", "info",
					companyService.get(Company.class, id)).addObject(
					"companyInfo", company).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateEmployeeInfo error", e);
			throw e;
		}
	}
	
	@RequestMapping("/ajaxFileUpload")
	@Prev(module="CompanyManager",oprator="add")
	public void ajaxFileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest mt=(MultipartHttpServletRequest) request;
		MultipartFile file=mt.getFile("picFile");
		String fileName=file.getOriginalFilename();
		
		//服务端文件命名
		String path=request.getSession().getServletContext().getRealPath("/");
		
		String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//后面再接三位随机数，防止文件名重复
		/*for(int i=0;i<3;i++){
			Random r=new Random();
			time+=r.nextInt();
		}*/
		
		//将file转成字节
		byte[] fileToByte=file.getBytes();
		//FileOutputStream out=new FileOutputStream(new File(path+"/upload/"+time+fileName));
		FileOutputStream out=new FileOutputStream(new File("D:/opt/pic/" +time+fileName));
		out.write(fileToByte);
		
		out.flush();
		out.close();
		
		//ajax返回数据
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		//pw.println("0`" + request.getContextPath() + "/upload/" + time+fileName);
		pw.println("0`" + time+fileName);
		pw.flush();
		pw.close();
	}
	/**
	 * 通过id判断是否修改或新增
	 * @param honor
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCompanyInfoCommit", method = RequestMethod.POST)
	@Prev(module = "CompanyManager", oprator = "add")
	@ResponseBody
	public ModelMap addCompanyInfoCommit(Company company, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(company);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(company);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				company.setCreateUser(user.getId());
				company.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				company.setIsDel(Constrants.DATA_NOT_DEL);
				if (company.getId() == null) {
					companyService.save(company);
				} else {
					companyService.update(company);
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
	
	/**
	 * 跳转到新增页
	 * @return
	 */
	@RequestMapping("/addCompanyInfo")
	@Prev(module = "CompanyManager", oprator = "add")
	public String addCompanyInfo() {
		return "company/addCompanyInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteCompanyInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="CompanyManager",oprator="delete")
	public ModelMap deleteCompanyInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && companyService.deleteCompanyInfo(id) == 1) {
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
