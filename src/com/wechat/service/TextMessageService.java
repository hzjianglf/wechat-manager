package com.wechat.service;

import java.util.Map;

import com.wechat.entity.TextMessage;
import com.wechat.util.PageQueryUtil;

/**
 * 文本消息接口
 * @Description:
 * @author zhur
 * @date 2016年6月8日 上午10:34:53
 */
public interface TextMessageService extends BaseService {
	
	//分页查询
	public Map<String, Object> findTextMessageByPage(TextMessage text, PageQueryUtil page);
	
	//删除
	public Integer deleteTextMessage(Long id);
	
}
