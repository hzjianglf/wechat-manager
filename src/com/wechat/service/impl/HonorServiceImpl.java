package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.bean.DateEntity;
import com.wechat.bean.EmployeeInfoBean;
import com.wechat.entity.Honor;
import com.wechat.service.HonorService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.util.TimeUtil;
import com.wechat.vo.HonorVo;

@Service("honorService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HonorServiceImpl extends BaseServiceImpl implements
		HonorService {

	public Honor findHonor(Long id) throws Exception {
		String hql = "select * FROM Honor id=? "
				+ "and p.isDel=?";
		return getBaseDao().getOne(hql,
				new Object[] { (long) id, Constrants.DATA_NOT_DEL });
	}

	/*
	 * 荣誉分页列表带字段搜索
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#findHonorInfoByPage(com.oa.entity.Honor, com.oa.util.PageQueryUtil)
	 */
	public Map<String, Object> findHonorInfoByPage(Honor honor,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select h.id,title,content,h.pic,u.user_name userName,h.create_time createTime,h.is_del isDel  from weixin_honor h left join sys_user u")
		.append(" on h.create_user = u.id where h.is_del=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(honor.getTitle())){
			sql.append(" and h.title like ?");
			list.add("%" + honor.getTitle().trim() + "%");
		}

		sql.append(" order by h.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), HonorVo.class, page, list.toArray());
	}

	/*
	 * 根据id删除单条记录
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#deleteHonorInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteHonorInfo(Long id) {
		String hql="update Honor set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}
