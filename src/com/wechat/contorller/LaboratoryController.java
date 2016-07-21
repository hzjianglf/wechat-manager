package com.wechat.contorller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Laboratory;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.LaboratoryService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/laboratory")
@Module("Laboratory")
public class LaboratoryController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(LaboratoryController.class);
	
	@Resource
	private LaboratoryService laboratoryService;

	
	@RequestMapping("/laboratoryList")
	@Prev(module="laboratoryInfoList",oprator="all")
	public ModelAndView laboratoryList(Laboratory laboratory,PageQueryUtil page) throws Exception {
		return backView("laboratory/laboratoryList", laboratoryService.findLaboratoryInfoByPage(laboratory, page));
	}

	@RequestMapping("/updateLaboratoryInfo")
	@Prev(module = "LaboratoryManager", oprator = "update")
	public ModelAndView updateLaboratoryInfo(Laboratory laboratory,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (laboratory.getLaboratoryName()!= null) {
				laboratory.setLaboratoryName(new String(laboratory.getLaboratoryName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("laboratory/addLaboratoryInfo", "info",
					laboratoryService.get(Laboratory.class, id)).addObject(
					"laboratoryInfo", laboratory).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/addLaboratoryInfoCommit", method = RequestMethod.POST)
	@Prev(module = "LaboratoryManager", oprator = "add")
	@ResponseBody
	public ModelMap addLaboratoryInfoCommit(Laboratory laboratory)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(laboratory);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(laboratory);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				laboratory.setIsDel(Constrants.DATA_NOT_DEL);
				if (laboratory.getId() == null) {
					laboratoryService.save(laboratory);
				} else {
					laboratoryService.update(laboratory);
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
	
	
	@RequestMapping("/addLaboratoryInfo")
	@Prev(module = "LaboratoryManager", oprator = "add")
	public String addOrganizeInfo() {
		return "laboratory/addLaboratoryInfo";
	}
	
	
	@RequestMapping(value="/deleteLaboratoryInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="LaboratoryManager",oprator="delete")
	public ModelMap deleteLaboratoryInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && laboratoryService.deleteLaboratoryInfo(id) == 1) {
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
