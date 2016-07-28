package com.wechat.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Case;
import com.wechat.entity.ServiceItem;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.CaseService;
import com.wechat.service.ServiceItemService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/service")
@Module("Services")
public class ServiceItemController extends BaseController {
    
	private static final Log4jLogger log = Log4jLogger
			.getLogger(ServiceItemController.class);
	
	@Resource
	private ServiceItemService serviceItemService;

	@Resource
	private UserService userService;

	@Resource
	private CaseService caseService;
	
	@RequestMapping("/serviceItemList")
	@Prev(module="serviceItemList",oprator="all")
	public ModelAndView serviceItemList(ServiceItem item,PageQueryUtil page) throws Exception {
		return backView("serviceItem/serviceItemList", serviceItemService.findServiceItemByPage(item, page));
	}

	
	@RequestMapping("/updateServiceItem")
	@Prev(module = "ServiceItemManager", oprator = "update")
	public ModelAndView updateServiceItem(ServiceItem item,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (item.getServiceName()!= null) {
				item.setServiceName(new String(item.getServiceName().getBytes("iso-8859-1"),"UTF-8"));
			}
			return new ModelAndView("serviceItem/addServiceItemInfo", "item",
					serviceItemService.get(ServiceItem.class, id)).addObject(
					"itemInfo", item).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 通过id判断是否修改或新增
	 * @param honor
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addServiceItemCommit", method = RequestMethod.POST)
	@Prev(module = "ServiceItemManager", oprator = "add")
	@ResponseBody
	public ModelMap addServiceItemCommit(ServiceItem item, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(item);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(item);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				item.setCreateUser(user.getId());
				item.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				item.setIsDel(Constrants.DATA_NOT_DEL);
				if (item.getId() == null) {
					serviceItemService.save(item);
				} else {
					serviceItemService.update(item);
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
	@RequestMapping("/addServiceItem")
	@Prev(module = "ServiceItemManager", oprator = "add")
	public String addArticle() {
		return "serviceItem/addServiceItemInfo";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteServiceItem",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="ServiceItemManager",oprator="delete")
	public ModelMap deleteServiceItemInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && serviceItemService.deleteServiceItem(id) == 1) {
				map.put(Constrants.MESSAGE_TIP, "删除成功");
				map.put(Constrants.MESSAGE_TIP_FLAGS, true);
			} else {
				map.put(Constrants.MESSAGE_TIP, "删除失败");
				map.put(Constrants.MESSAGE_TIP_FLAGS, false);
			}
			return map;
		} catch (Exception e) {
			log.error("deleteArticle error", e);
			throw e;
		}
	}
	
	@RequestMapping(value="/bindCases/{serviceId}")
	@Prev(module = "ServiceItemManager", oprator = "bind")
	public ModelAndView bindCases(@PathVariable int serviceId,PageQueryUtil page){
		
		List<Case> notBindCase=serviceItemService.queryNotBindCaseByServiceId(serviceId);
		
		List<Case> bindCase=serviceItemService.queryCaseByServiceId(serviceId);

		return new ModelAndView("serviceItem/bindServiceCases","notBindCase",notBindCase).addObject("bindCase", bindCase)
				.addObject("serviceId", serviceId).addObject("pageQueryUtil", page);
	}
	
	//@RequestBody String[] arrays,@RequestParam("arrays[]") List<String> ar
	@RequestMapping(value="/bindServiceCaseCommit/{serviceId}")
	@Prev(module = "ServiceItemManager", oprator = "bind")
	@ResponseBody
	public ModelMap bindServiceCaseCommit(@PathVariable int serviceId,@RequestParam("arrays[]") String[] arr){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("serviceId", serviceId);
		map.put("arrays", arr);
		boolean result=caseService.updateBindCaseByServiceId(map);
		
		ModelMap m=new ModelMap();
		if(result){
			
			m.put(Constrants.MESSAGE_TIP, "操作成功！");
		}else{
		}
		return m;
	}
	
}
