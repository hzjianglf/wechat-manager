package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Laboratory;
import com.wechat.service.LaboratoryService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;

@Service("laboratoryService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LaboratoryServiceImpl extends BaseServiceImpl implements
		LaboratoryService {

	public Map<String, Object> findLaboratoryInfoByPage(Laboratory lab,
			PageQueryUtil page) {
		String chql = " where 1 = 1";
		String hql = "select new com.wechat.entity.Laboratory(id, laboratoryName,laboratoryInfo,laboratoryPic,laboratoryType,updateTime,createTime,isDel) from Laboratory";
		List<Object> conditions = new ArrayList<Object>();
		if (lab.getLaboratoryName()!= null
				&& !"".equals(lab.getLaboratoryName().trim())) {
			chql += " and laboratoryName like ?";
			conditions.add("%" + lab.getLaboratoryName().trim() + "%");
		}
		return backPage(hql + chql, page, conditions.toArray());
	}

	@Override
	public Integer deleteLaboratoryInfo(Integer id) {
		String hql = "update Laboratory set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL, id);
	}

}
