package com.wechat.contorller;

import java.util.Map;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wechat.entity.TextMessage;
import com.wechat.entity.User;
import com.wechat.menu.Module;
import com.wechat.prev.Prev;
import com.wechat.service.TextMessageService;
import com.wechat.service.UserService;
import com.wechat.util.Constrants;
import com.wechat.util.Log4jLogger;
import com.wechat.util.MyDateUtil;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.validate.Validate;

@Controller
@RequestMapping("/message/text")
@Module("MessageManager")
public class TextMessageController extends BaseController {

	private static final Log4jLogger log = Log4jLogger
			.getLogger(TextMessageController.class);
	
	@Resource
	private TextMessageService textMessageService;

	@Resource
	private UserService userService;

	/**
	 * 回复文本消息列表
	 * @param text
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/textMessageList")
	@Prev(module="textMessageList",oprator="all")
	public ModelAndView textMessageList(TextMessage text,PageQueryUtil page) throws Exception {
		return backView("text/textMessageList", textMessageService.findTextMessageByPage(text, page));
	}

	/**
	 * 跳转修改页
	 * @param text
	 * @param page
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTextMessage")
	@Prev(module = "MessageManager", oprator = "update")
	public ModelAndView updateTextMessage(TextMessage text,
			PageQueryUtil page, Long id) throws Exception {
		try {
			if (text.getKeyword() != null) {
				text.setKeyword(new String(text.getKeyword().getBytes("iso-8859-1"),
						"UTF-8"));
			}
			return new ModelAndView("text/addTextMessage", "info",
					textMessageService.get(TextMessage.class, id)).addObject(
					"textMessage", text).addObject("pageQueryUtil", page);
		} catch (Exception e) {
			log.error("updateTextMessage error", e);
			throw e;
		}
	}
	
	/**
	 * 通过id判断是否修改或新增
	 * @param text
	 * @param createTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addTextMessageCommit", method = RequestMethod.POST)
	@Prev(module = "MessageManager", oprator = "add")
	@ResponseBody
	public ModelMap addTextMessageCommit(TextMessage text, String createTime)
			throws Exception {
		try {
			ModelMap map = new ModelMap();
			boolean flag = false;
			Map<String, Object> m = Validate.valid(text);
			map.addAllAttributes(m);
			if (m.size() != 0) {
				flag = true;
			}

			if (flag == false) {
				StringTools.trim(text);
				User user=(User) getSession().getAttribute(Constrants.USER_KEY);
				text.setCreateUser(user.getId());
				text.setCreateTime(MyDateUtil.stringToTimestamp(createTime));
				text.setIsDel(Constrants.DATA_NOT_DEL);
				if (text.getId() == null) {
					textMessageService.save(text);
				} else {
					textMessageService.update(text);
				}
				map.put(Constrants.MESSAGE_TIP, "操作成功！");
			}
			map.put(Constrants.MESSAGE_TIP_FLAGS, flag);
			return map;
		} catch (Exception e) {
			log.error("addTextMessageCommit error", e);
			throw e;
		}
	}
	
	/**
	 * 跳转到新增页
	 * @return
	 */
	@RequestMapping("/addTextMessage")
	@Prev(module = "MessageManager", oprator = "add")
	public String addTextMessage() {
		return "text/addTextMessage";
	}
	
	/**
	 * 删除记录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/deleteTextMessage",method=RequestMethod.POST)
	@ResponseBody
	@Prev(module="TextMessageManager",oprator="delete")
	public ModelMap deleteTextMessage(Long id) throws Exception{
		try {
			ModelMap map = new ModelMap();
			if (id != null && textMessageService.deleteTextMessage(id) == 1) {
				map.put(Constrants.MESSAGE_TIP, "删除成功");
				map.put(Constrants.MESSAGE_TIP_FLAGS, true);
			} else {
				map.put(Constrants.MESSAGE_TIP, "删除失败");
				map.put(Constrants.MESSAGE_TIP_FLAGS, false);
			}
			return map;
		} catch (Exception e) {
			log.error("deleteTextMessage error", e);
			throw e;
		}
	}
}
