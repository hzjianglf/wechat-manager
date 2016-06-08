package com.wechat.service;

import java.util.List;
import java.util.Map;

import com.wechat.bean.DateEntity;
import com.wechat.bean.EmployeeInfoBean;
import com.wechat.util.PageQueryUtil;

public interface EmployeeService extends BaseService {

	public EmployeeInfoBean findMyInfo(Integer id) throws Exception;
	
	public Map<String, Object> findAddressByPage(EmployeeInfoBean employeeInfo, PageQueryUtil page) throws Exception;
	
	public List<EmployeeInfoBean> findAddressAll() throws Exception;
	
	public Map<String, Object> findMySalaryInfoByPage(DateEntity date,  PageQueryUtil page, Integer accountId) throws Exception;

	public Map<String, Object> findMyLeaveInfoByPage(DateEntity date, PageQueryUtil page, Integer id) throws Exception;
	
	public boolean isOnlyEmail(String email, Long id) throws Exception;
	
	public boolean isOnlyMobileNumber(String mobileNumber, Long id) throws Exception;
	
}
