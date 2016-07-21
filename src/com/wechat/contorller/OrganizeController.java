package com.wechat.contorller;

import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Organize;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.OrganizeService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/about_us/organize")
@Module("AboutUs")
public class OrganizeController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(OrganizeController.class);
	
	@Resource
	private OrganizeService organizeService;

	
	@RequestMapping("/organizeList")
	@Prev(module="organizeInfoList",oprator="all")
	public ModelAndView organizeList(Organize organize,PageQueryUtil page) throws Exception {
		return backView("organize/organizeList", organizeService.findOrganizeInfoByPage(organize, page));
	}

	@RequestMapping("/updateOrganizeInfo")
	@Prev(module = "OrganizeManager", oprator = "update")
	public ModelAndView updateOrganizeInfo(Organize organize,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (organize.getOrganizeName() != null) {
				organize.setOrganizeName(new String(organize.getOrganizeName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("organize/addOrganizeInfo", "info",
					organizeService.get(Organize.class, id)).addObject(
					"organizeInfo", organize).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/addOrganizeInfoCommit", method = RequestMethod.POST)
	@Prev(module = "OrganizeManager", oprator = "add")
	@ResponseBody
	public ModelMap addOrganizeInfoCommit(Organize organize, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(organize);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(organize);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				organize.setIsDel(Constrants.DATA_NOT_DEL);
				if (organize.getId() == null) {
					organizeService.save(organize);
				} else {
					organizeService.update(organize);
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
	
	
	@RequestMapping("/addOrganizeInfo")
	@Prev(module = "OrganizeManager", oprator = "add")
	public String addOrganizeInfo() {
		return "organize/addOrganizeInfo";
	}
	
	
	@RequestMapping(value="/deleteOrganizeInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="OrganizeManager",oprator="delete")
	public ModelMap deleteOrganizeInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && organizeService.deleteOrganizeInfo(id) == 1) {
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
