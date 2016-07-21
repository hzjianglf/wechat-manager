package com.wechat.service;

import java.util.Map;

import com.wechat.entity.Laboratory;
import com.wechat.util.PageQueryUtil;

public interface LaboratoryService extends BaseService {
	
	//分页查询
	public Map<String, Object> findLaboratoryInfoByPage(Laboratory lab, PageQueryUtil page);
	
	//删除
	public Integer deleteLaboratoryInfo(Integer id);
	
}
