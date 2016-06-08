package com.wechat.service;

import java.util.Map;

import com.wechat.entity.Company;
import com.wechat.util.PageQueryUtil;

/**
 * 公司接口
 * @Description:
 * @author zhur
 * @date 2016年6月6日 下午4:45:25
 */
public interface CompanyService extends BaseService {
	
	//分页查询
	public Map<String, Object> findCompanyInfoByPage(Company company, PageQueryUtil page);
	
	//删除
	public Integer deleteCompanyInfo(Integer id);
	
}
