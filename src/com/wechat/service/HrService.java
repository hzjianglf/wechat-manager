package com.wechat.service;

import java.util.Map;

import com.wechat.entity.EmployeeInfo;
import com.wechat.util.PageQueryUtil;

public interface HrService extends BaseService {

	public Map<String, Object> statisticsGender();
	
	public Map<String, Object> findEmployeeInfoByPage(EmployeeInfo info, PageQueryUtil page);
	
	public Integer deleteEmployeeInfo(Long id) throws Exception;
	
}
