package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.TextMessage;
import com.wechat.service.TextMessageService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.vo.TextMessageVo;

@Service("textMessageService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TextMessageServiceImpl extends BaseServiceImpl implements
		TextMessageService {

	/*
	 * 文本消息分页列表带字段搜索
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#findHonorInfoByPage(com.oa.entity.Honor, com.oa.util.PageQueryUtil)
	 */
	public Map<String, Object> findTextMessageByPage(TextMessage text,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.id,t.keyword,t.text,t.click,u.user_name userName,t.create_time createTime,t.is_del isDel  from weixin_text t left join sys_user u")
		.append(" on t.create_user = u.id where t.is_del=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(text.getKeyword())){
			sql.append(" and t.keyword like ?");
			list.add("%" + text.getKeyword().trim() + "%");
		}

		sql.append(" order by t.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), TextMessageVo.class, page, list.toArray());
	}

	/*
	 * 根据id删除单条记录
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#deleteHonorInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteTextMessage(Long id) {
		String hql="update TextMessage set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}
