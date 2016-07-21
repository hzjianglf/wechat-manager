package com.wechat.service;

import java.util.List;
import java.util.Map;

import com.wechat.entity.Case;
import com.wechat.entity.ServiceItem;
import com.wechat.util.PageQueryUtil;

/**
 * 
 * @Description 服务项目接口
 * @ClassName ServiceItemService.java
 * @author Administrator
 * @date 2016年6月15日下午4:02:22
 */
public interface ServiceItemService extends BaseService {
	
	//分页查询
	public Map<String, Object> findServiceItemByPage(ServiceItem item, PageQueryUtil page);
	
	//删除
	public Integer deleteServiceItem(Integer id);
	
	//案例
	public List<Case> queryCaseByServiceId(int serviceId);
	
	public List<Case> queryNotBindCaseByServiceId(int serviceId);
		
}
