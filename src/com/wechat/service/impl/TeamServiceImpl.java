package com.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wechat.entity.Team;
import com.wechat.service.TeamService;
import com.wechat.util.Constrants;
import com.wechat.util.PageQueryUtil;
import com.wechat.util.StringTools;
import com.wechat.vo.TeamVo;

@Service("teamService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TeamServiceImpl extends BaseServiceImpl implements
		TeamService {

	/*
	 * 技术团队分页列表带字段搜索
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#findHonorInfoByPage(com.oa.entity.Honor, com.oa.util.PageQueryUtil)
	 */
	public Map<String, Object> findTeamInfoByPage(Team team,
			PageQueryUtil page) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.id,t.team_name teamName,t.team_info teamInfo,t.team_pic teamPic,u.user_name userName,t.create_time createTime,t.is_del isDel  from portal_team t left join sys_user u")
		.append(" on t.create_user = u.id where t.is_del=?");
		List<Object> list = new ArrayList<Object>();
		list.add(Constrants.DATA_NOT_DEL);
		if(!StringTools.isEmpty(team.getTeamName())){
			sql.append(" and t.team_name like ?");
			list.add("%" + team.getTeamName().trim() + "%");
		}

		sql.append(" order by t.create_time desc");
		//当子查询中含有from字句时请大写FROM，不然会出错
		return backPageSql(sql.toString(), TeamVo.class, page, list.toArray());
	}

	/*
	 * 根据id删除单条记录
	 * (non-Javadoc)
	 * @see com.oa.service.HonorService#deleteHonorInfo(java.lang.Long)
	 */
	@Override
	public Integer deleteTeamInfo(Integer id) {
		String hql="update Team set isDel=? where id=? ";
		return getBaseDao().executeHql(hql, Constrants.DATA_DEL,id);
	}
	
}
