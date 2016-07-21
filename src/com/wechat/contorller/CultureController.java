package com.wechat.contorller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Culture;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.CultureService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/about_us/culture")
@Module("AboutUs")
public class CultureController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(CultureController.class);
	
	@Resource
	private CultureService cultureService;

	
	@RequestMapping("/cultureList")
	@Prev(module="cultureInfoList",oprator="all")
	public ModelAndView cultureList(Culture culture,PageQueryUtil page) throws Exception {
		return backView("culture/cultureList", cultureService.findCultureInfoByPage(culture, page));
	}

	@RequestMapping("/updateCultureInfo")
	@Prev(module = "CultureManager", oprator = "update")
	public ModelAndView updateCultureInfo(Culture culture,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (culture.getCultureName()!= null) {
				culture.setCultureName(new String(culture.getCultureName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("culture/addCultureInfo", "info",
					cultureService.get(Culture.class, id)).addObject(
					"cultureInfo", culture).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/addCultureInfoCommit", method = RequestMethod.POST)
	@Prev(module = "CultureManager", oprator = "add")
	@ResponseBody
	public ModelMap addCultureInfoCommit(Culture culture)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(culture);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(culture);				
				culture.setIsDel(Constrants.DATA_NOT_DEL);
				
				if (culture.getId() == null) {
					culture.setCultureDate(new Date());
					cultureService.save(culture);
				} else {
					culture=cultureService.get(Culture.class, culture.getId());
					culture.setCultureDescription(culture.getCultureDescription());
					culture.setCultureInfo(culture.getCultureInfo());
					culture.setCultureName(culture.getCultureName());
					culture.setCulturePic(culture.getCulturePic());
					cultureService.update(culture);
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
	
	
	@RequestMapping("/addCultureInfo")
	@Prev(module = "CultureManager", oprator = "add")
	public String addOrganizeInfo() {
		return "culture/addCultureInfo";
	}
	
	
	@RequestMapping(value="/deleteCultureInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="CultureManager",oprator="delete")
	public ModelMap deleteCultureInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && cultureService.deleteCultureInfo(id) == 1) {
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
