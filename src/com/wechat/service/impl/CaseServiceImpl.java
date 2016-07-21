package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Case;
import com.wechat.entity.ServiceCase;
import com.wechat.service.CaseService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;

@Service("caseService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CaseServiceImpl extends BaseServiceImpl implements
		CaseService {

	
	public Map<String, Object> findCaseInfoByPage(Case cases,
			PageQueryUtil page) {
		StringBuilder hql = new StringBuilder();
		hql.append("from Case where 1=1");
		List<Object> list = new ArrayList<Object>();
		if(!StringTools.isEmpty(cases.getCaseName())){
			hql.append(" and caseName like ?");
			list.add("%" + cases.getCaseName().trim() + "%");
		}
		return backPage(hql.toString(), page, list.toArray());
	}

	
	@Override
	public Integer deleteCaseInfo(int id) {
		String hql="update Case set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}


	@Override
	public List<Case> getAllCase() {
		String hql="from Case";
		return getBaseDao().getAll(Case.class);
	}


	public boolean updateBindCaseByServiceId0(Map map) {
		Integer serviceId=(Integer) map.get("serviceId");
		String [] arrays=new String[]{};
				arrays=(String[]) map.get("arrays");
		String sql="update service_case set case_id=? where service_id=?";
		//return getBaseDao().executeHql(sql, map);
		for(int i=0;i<arrays.length;i++){
			Query q=getBaseDao().sqlQuery(sql);
			q.setParameter(0, arrays[i]);
			q.setParameter(1, serviceId);
			q.executeUpdate();
		}
		return true;
	}
	@Override
	public boolean updateBindCaseByServiceId(Map map) {
		Integer serviceId=(Integer) map.get("serviceId");
		String [] arrays=new String[]{};
		arrays=(String[]) map.get("arrays");
		String hql="from ServiceCase where serviceId=?";
		Query q=getBaseDao().getCurrentSession().createQuery(hql).setParameter(0, serviceId);
		List<ServiceCase> casess=q.list();
		for(ServiceCase c:casess){
			getBaseDao().getCurrentSession().delete(c);
		}
		for(int i=0;i<arrays.length;i++){
			ServiceCase sc=new ServiceCase(serviceId,Integer.parseInt(arrays[i]));
			getBaseDao().save(sc);
			//getBaseDao().saveOrUpdate(sc);
		}
		return true;
	}
	
}
