package com.wechat.service;

import java.util.Map;

import com.wechat.entity.History;
import com.wechat.util.PageQueryUtil;

public interface HistoryService extends BaseService {
	
	//分页查询
	public Map<String, Object> findHistoryInfoByPage(History history, PageQueryUtil page);
	
	//删除
	public Integer deleteHistoryInfo(Integer id);
	
}
