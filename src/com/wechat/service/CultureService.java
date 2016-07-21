package com.wechat.service;

import java.util.Map;

import com.wechat.entity.Culture;
import com.wechat.util.PageQueryUtil;

public interface CultureService extends BaseService {
	
	//分页查询
	public Map<String, Object> findCultureInfoByPage(Culture culture, PageQueryUtil page);
	
	//删除
	public Integer deleteCultureInfo(Integer id);
	
}
