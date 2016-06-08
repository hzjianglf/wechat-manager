package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Company;
import com.wechat.service.CompanyService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.vo.CompanyVo;

@Service("companyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CompanyServiceImpl extends BaseServiceImpl implements
		CompanyService {

	/*
	 * 介绍分页列表带字段搜索
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#findHonorInfoByPage(com.oa.entity.Honor, com.oa.util.PageQueryUtil)
	 */
	public Map<String, Object> findCompanyInfoByPage(Company company,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id,c.company_name companyName,c.company_info companyInfo,c.company_pic companyPic,u.user_name userName,c.create_time createTime,c.is_del isDel  from weixin_company c left join sys_user u")
		.append(" on c.create_user = u.id where c.is_del=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(company.getCompanyName())){
			sql.append(" and c.company_name like ?");
			list.add("%" + company.getCompanyName().trim() + "%");
		}

		sql.append(" order by c.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), CompanyVo.class, page, list.toArray());
	}

	/*
	 * 根据id删除单条记录
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#deleteHonorInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteCompanyInfo(Integer id) {
		String hql="update Company set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}
