package com.wechat.service;

import java.util.Map;

import com.wechat.entity.Organize;
import com.wechat.util.PageQueryUtil;

/**
 * 
 * @Description 
 * @ClassName OrganizeService.java
 * @author Administrator-zhur
 * @date 2016年7月13日下午3:04:49
 */
public interface OrganizeService extends BaseService {
	
	//分页查询
	public Map<String, Object> findOrganizeInfoByPage(Organize organize, PageQueryUtil page);
	
	//删除
	public Integer deleteOrganizeInfo(Integer id);
	
}
