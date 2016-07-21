package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Culture;
import com.wechat.service.CultureService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;

@Service("cultureService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CultureServiceImpl extends BaseServiceImpl implements
		CultureService {

	public Map<String, Object> findCultureInfoByPage(Culture culture,
			PageQueryUtil page) {
		String chql = " where 1 = 1";
		String hql = "select new com.wechat.entity.Culture(c.id,c.cultureName, c.cultureDescription, c.cultureInfo,c.culturePic,c.cultureDate, c.isDel) from Culture c";
		List<Object> conditions = new ArrayList<Object>();
		if (culture.getCultureName()!= null
				&& !"".equals(culture.getCultureName().trim())) {
			chql += " and c.cultureName like ?";
			conditions.add("%" + culture.getCultureName().trim() + "%");
		}
		return backPage(hql + chql, page, conditions.toArray());
	}

	@Override
	public Integer deleteCultureInfo(Integer id) {
		String hql = "update Culture set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL, id);
	}

}
