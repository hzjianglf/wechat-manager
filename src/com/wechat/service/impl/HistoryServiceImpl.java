package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.History;
import com.wechat.service.HistoryService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;

@Service("historyService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HistoryServiceImpl extends BaseServiceImpl implements
		HistoryService {

	public Map<String, Object> findHistoryInfoByPage(History history,
			PageQueryUtil page) {
		String chql = " where 1 = 1";
		String hql = "select new com.wechat.entity.History(id,historyYear, historyName,historyInfo,historyInfo1,historyInfo2,historyInfo3,historyPic,isDel) from History";
		List<Object> conditions = new ArrayList<Object>();
		if (history.getHistoryName()!= null
				&& !"".equals(history.getHistoryName().trim())) {
			chql += " and historyName like ?";
			conditions.add("%" + history.getHistoryName().trim() + "%");
		}
		return backPage(hql + chql, page, conditions.toArray());
	}

	@Override
	public Integer deleteHistoryInfo(Integer id) {
		String hql = "update History set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL, id);
	}

}
