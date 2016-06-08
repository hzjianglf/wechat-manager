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

import com.wechat.entity.Honor;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.HonorService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/honor")
@Module("HonorManager")
public class HonorController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(HonorController.class);
	
	@Resource
	private HonorService honorService;

	@Resource
	private UserService userService;

	/**
	 * 荣誉列表页
	 * @param honor
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/honorList")
	@Prev(module="honorList",oprator="all")
	public ModelAndView honorList(Honor honor,PageQueryUtil page) throws Exception {
		return backView("honor/honorList", honorService.findHonorInfoByPage(honor, page));
	}

	/**
	 * 修改
	 * @param honor
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateHonorInfo")
	@Prev(module = "HonorManager", oprator = "update")
	public ModelAndView updateHonorInfo(Honor honor,
			PageQueryUtil page, Long id) throws Exception {
		try {
			if (honor.getTitle() != null) {
				honor.setTitle(new String(honor.getTitle().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("honor/addHonorInfo", "info",
					honorService.get(Honor.class, id)).addObject(
					"honorInfo", honor).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateEmployeeInfo error", e);
			throw e;
		}
	}
	
	@RequestMapping("/ajaxFileUpload")
	@Prev(module="HonorManager",oprator="add")
	public void ajaxFileUpload(@RequestParam MultipartFile[] picFile, HttpServletRequest request, HttpServletResponse response) throws Exception{
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
		//
		FileOutputStream out=new FileOutputStream(new File(path+"/upload/"+time+fileName));
		out.write(fileToByte);
		
		out.flush();
		out.close();
		
		//ajax返回数据
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.println("0`" + request.getContextPath() + "/upload/" + time+fileName);
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
	@RequestMapping(value = "/addHonorInfoCommit", method = RequestMethod.POST)
	@Prev(module = "HonorManager", oprator = "add")
	@ResponseBody
	public ModelMap addHonorInfoCommit(Honor honor, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(honor);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(honor);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				honor.setCreateUser(user.getId());
				honor.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				honor.setIsDel(Constrants.DATA_NOT_DEL);
				if (honor.getId() == null) {
					honorService.save(honor);
				} else {
					honorService.update(honor);
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
	@RequestMapping("/addHonorInfo")
	@Prev(module = "HonorManager", oprator = "add")
	public String addEmployeeInfo() {
		return "honor/addHonorInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteHonorInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="HonorManager",oprator="delete")
	public ModelMap deleteHonorInfo(Long id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && honorService.deleteHonorInfo(id) == 1) {
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
		//return null;
	}
}
