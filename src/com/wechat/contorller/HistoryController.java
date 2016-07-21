package com.wechat.contorller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.History;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.HistoryService;
import com.wechat.util.Constrants;
import com.wechat.util.DateUtil;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/about_us/history")
@Module("AboutUs")
public class HistoryController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(HistoryController.class);
	
	@Resource
	private HistoryService historyService;

	
	@RequestMapping("/historyList")
	@Prev(module="historyInfoList",oprator="all")
	public ModelAndView historyList(History history,PageQueryUtil page) throws Exception {
		return backView("history/historyList", historyService.findHistoryInfoByPage(history, page));
	}

	@RequestMapping("/updateHistoryInfo")
	@Prev(module = "HistoryManager", oprator = "update")
	public ModelAndView updateHistoryInfo(History history,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (history.getHistoryName()!= null) {
				history.setHistoryName(new String(history.getHistoryName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("history/addHistoryInfo", "info",
					historyService.get(History.class, id)).addObject(
					"historyInfo", history).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/addHistoryInfoCommit", method = RequestMethod.POST)
	@Prev(module = "HistoryManager", oprator = "add")
	@ResponseBody
	public ModelMap addHistoryInfoCommit(History history,String year)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(history);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}
			
			if (year==null || ("").equals(year)) {
				String historyYearMessage = "";
				if (!map.containsKey("historyYearMessage")) {
					flag = true;
					historyYearMessage = "历史沿革年份不能为空！！@@@";
					map.put("historyYearMessage", historyYearMessage);
				}
			}
			if (("").equals(history.getHistoryInfo()) && ("").equals(history.getHistoryInfo1())&&("").equals(history.getHistoryInfo2())&&("").equals(history.getHistoryInfo3())) {
				String historyInfoMessage = "";
				if (!map.containsKey("historyInfoMessage")) {
					flag = true;
					historyInfoMessage = "历史沿革信息不能为空，请至少填写一条！！@@@";
					map.put("historyInfoMessage", historyInfoMessage);
				}
			}
			
			if (flag == false) {
				StringTools.trim(history);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				history.setIsDel(Constrants.DATA_NOT_DEL);
				history.setHistoryYear(DateUtil.StringYearToDate(year));
				if (history.getId() == null) {
					historyService.save(history);
				} else {
					historyService.update(history);
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
	
	
	@RequestMapping("/addHistoryInfo")
	@Prev(module = "HistoryManager", oprator = "add")
	public String addOrganizeInfo() {
		return "history/addHistoryInfo";
	}
	
	
	@RequestMapping(value="/deleteHistoryInfo",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="HistoryManager",oprator="delete")
	public ModelMap deleteHistoryInfo(Integer id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && historyService.deleteHistoryInfo(id) == 1) {
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
