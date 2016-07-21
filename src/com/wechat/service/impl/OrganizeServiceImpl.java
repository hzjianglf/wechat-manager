package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Organize;
import com.wechat.service.OrganizeService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;

@Service("organizeService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class OrganizeServiceImpl extends BaseServiceImpl implements
		OrganizeService {

	public Map<String, Object> findOrganizeInfoByPage(Organize organize,
			PageQueryUtil page) {
		String chql = " where 1 = 1";
		String hql = "select new com.wechat.entity.Organize(o.id,o.organizeName, o.organizeInfo, o.organizePic, o.isDel) from Organize o";
		List<Object> conditions = new ArrayList<Object>();
		if (organize.getOrganizeName() != null
				&& !"".equals(organize.getOrganizeName().trim())) {
			chql += " and o.organizeName like ?";
			conditions.add("%" + organize.getOrganizeName().trim() + "%");
		}
		return backPage(hql + chql, page, conditions.toArray());
	}

	@Override
	public Integer deleteOrganizeInfo(Integer id) {
		String hql = "update Organize set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL, id);
	}

}
