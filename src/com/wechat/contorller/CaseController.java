package com.wechat.contorller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.Case;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.CaseService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/case")
@Module("Services")
public class CaseController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(CaseController.class);
	@Autowired
	private CaseService caseService;

	
	@RequestMapping("/caseList")
	@Prev(module="caseyInfoList",oprator="all")
	public ModelAndView honorList(Case cases,PageQueryUtil page) throws Exception {
		return backView("case/caseList", caseService.findCaseInfoByPage(cases, page));
	}

	
	@RequestMapping("/updateCaseInfo")
	@Prev(module = "CaseManager", oprator = "update")
	public ModelAndView updateCompanyInfo(Case cases,
			PageQueryUtil page, Integer id) throws Exception {
		try {
			if (cases.getCaseName() != null) {
				cases.setCaseName(new String(cases.getCaseName().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("case/addCaseInfo", "info",
					caseService.get(Case.class, id)).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@RequestMapping(value = "/addCaseInfoCommit", method = RequestMethod.POST)
	@Prev(module = "CaseManager", oprator = "add")
	@ResponseBody
	public ModelMap addCompanyInfoCommit(Case cases,String id_int)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(cases);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(cases);
				if(id_int!=null&&id_int!=""){
					cases.setId(Integer.parseInt(id_int));
				}
				if (cases.getId()==0) {
					caseService.save(cases);
				} else {
					caseService.update(cases);
				}
				map.put(Constrants.MESSAGE_TIP, "操作成功！");
			}
			map.put(Constrants.MESSAGE_TIP_FLAGS, flag);
			return map;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 跳转到新增页
	 * @return
	 */
	@RequestMapping("/addCaseInfo")
	@Prev(module = "CaseManager", oprator = "add")
	public String addCompanyInfo() {
		return "case/addCaseInfo";
	}
	
}
