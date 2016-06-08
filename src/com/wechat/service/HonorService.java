package com.wechat.service;

import java.util.List;
import java.util.Map;

import com.wechat.bean.DateEntity;
import com.wechat.bean.EmployeeInfoBean;
import com.wechat.entity.EmployeeInfo;
import com.wechat.entity.Honor;
import com.wechat.util.PageQueryUtil;

public interface HonorService extends BaseService {

	public Honor findHonor(Long id) throws Exception;
	
	//分页查询
	public Map<String, Object> findHonorInfoByPage(Honor honor, PageQueryUtil page);
	
	//删除
	public Integer deleteHonorInfo(Long id);
	
}
