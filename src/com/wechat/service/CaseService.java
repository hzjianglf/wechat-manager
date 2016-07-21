package com.wechat.service;

import java.util.List;
import java.util.Map;

import com.wechat.entity.Case;
import com.wechat.util.PageQueryUtil;

public interface CaseService extends BaseService {
	
	//分页查询
	public Map<String, Object> findCaseInfoByPage(Case cases, PageQueryUtil page);
	
	//删除
	public Integer deleteCaseInfo(int id);
	
	public List<Case> getAllCase();
	
	public boolean updateBindCaseByServiceId(Map map);
	
}
