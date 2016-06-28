package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.ServiceItem;
import com.wechat.service.ServiceItemService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.vo.ServiceItemVo;

@Service("serviceItemService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ServiceItemServiceImpl extends BaseServiceImpl implements
		ServiceItemService {

	public Map<String, Object> findServiceItemByPage(ServiceItem item,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select s.id,s.service_name serviceName,u.user_name userName,s.service_content serviceContent,s.service_pic servicePic,s.create_time createTime,s.is_show isShow  from weixin_service_item s left join sys_user u")
		.append(" on u.id=s.create_user")
		.append(" where s.is_show=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(item.getServiceName())){
			sql.append(" and s.service_name like ?");
			list.add("%" + item.getServiceName().trim() + "%");
		}

		sql.append(" order by s.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), ServiceItemVo.class, page, list.toArray());
	}

	@Override
	public Integer deleteServiceItem(Integer id) {
		String hql="update ServiceItem set isShow=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}