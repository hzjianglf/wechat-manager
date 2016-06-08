package com.wechat.contorller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.support.json.JSONParser;
import com.wechat.entity.Team;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.TeamService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/team")
@Module("TeamManager")
public class TeamController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(TeamController.class);
	
	@Resource
	private TeamService teamService;

	@Resource
	private UserService userService;

	/**
	 * 团队信息列表页
	 * @param honor
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/teamList")
	@Prev(module="teamInfoList",oprator="all")
	public ModelAndView honorList(Team team,PageQueryUtil page) throws Exception {
		return backView("team/teamList", teamService.findTeamInfoByPage(team, page));
	}

	/**
	 * 修改
	 * @param honor
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTeamInfo")
	@Prev(module = "TeamManager", oprator = "update")
	public ModelAndView updateTeamInfo(Team team,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (team.getTeamName() != null) {
				team.setTeamName(new String(team.getTeamName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("team/addTeamInfo", "info",teamService.get(Team.class, id)).addObject("teamInfo", team).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateTeamInfo error", e);
			throw e;
		}
	}
	
	/**
	 * 多文件上传
	 * @param pucFiles
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ajaxFileUpload")
	@Prev(module="teamManager",oprator="add")
	@ResponseBody
	public void ajaxFileUpload(@RequestParam("picFiles") MultipartFile[] picFiles,HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		
		//服务端文件命名
		String path=request.getSession().getServletContext().getRealPath("/");
				
		String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		String urls="";
		if(picFiles!=null&&picFiles.length>0){
			for(int i=0;i<picFiles.length;i++){
				MultipartFile file=picFiles[i];
				String nameFile=file.getOriginalFilename();
				file.transferTo(new File(path+"/upload",time+nameFile));
				urls+= request.getContextPath() + "/upload/" + time+nameFile;
			}
		}
		
		//ajax返回数据
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.println("0`" + urls);
		pw.flush();
		pw.close();
		
		
	}
	/**
	 * 多文件上传
	 * @param pucFiles
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/ajaxFileUpload1")
	@Prev(module="teamManager",oprator="add")
	public void ajaxFileUpload1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest mt=(MultipartHttpServletRequest) request;
		List<MultipartFile> list=mt.getFiles("picFiles");
		
		
		
		//服务端文件命名
		String path=request.getSession().getServletContext().getRealPath("/");
				
		String time=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		Iterator<MultipartFile> iter=list.iterator();
		while(iter.hasNext()){
			MultipartFile file=iter.next();
			
			String fileName=file.getOriginalFilename();
			if(!file.isEmpty()){
				
				file.transferTo(new File(path+"/upload",time+fileName));
				
				
				//将图片url存放在数组中
				Map<String,String> map=new HashMap<String,String>();
				map.put("url", path+"/upload/"+time+fileName);
				//ajax返回数据
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter pw=response.getWriter();
				pw.println("0`" + request.getContextPath() + "/upload/" + time+fileName);
				pw.flush();
				pw.close();
			}
			
		}
		
		
	}
	/**
	 * 通过id判断是否修改或新增
	 * @param honor
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTeamInfoCommit", method = RequestMethod.POST)
	@Prev(module = "TeamManager", oprator = "add")
	@ResponseBody
	public ModelMap addTeamInfoCommit(Team team, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(team);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(team);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				team.setCreateUser(user.getId());
				team.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				team.setIsDel(Constrants.DATA_NOT_DEL);
				if (team.getId() == null) {
					teamService.save(team);
				} else {
					teamService.update(team);
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
	@RequestMapping("/addTeamInfo")
	@Prev(module = "TeamManager", oprator = "add")
	public String addTeamInfo() {
		return "team/addTeamInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteTeamInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="TeamManager",oprator="delete")
	public ModelMap deleteTeamInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && teamService.deleteTeamInfo(id) == 1) {
				map.put(Constrants.MESSAGE_TIP, "删除成功");
				map.put(Constrants.MESSAGE_TIP_FLAGS, true);
			} else {
				map.put(Constrants.MESSAGE_TIP, "删除失败");
				map.put(Constrants.MESSAGE_TIP_FLAGS, false);
			}
			return map;
		} catch (Exception e) {
			log.error("deleteTeamInfo error", e);
			throw e;
		}
	}
}
