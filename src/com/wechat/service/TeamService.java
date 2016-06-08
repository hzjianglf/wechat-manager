package com.wechat.service;

import java.util.Map;

import com.wechat.entity.Team;
import com.wechat.util.PageQueryUtil;

/**
 * 技术团队接口
 * @Description:
 * @author zhur
 * @date 2016年6月6日 下午4:45:25
 */
public interface TeamService extends BaseService {
	
	//分页查询
	public Map<String, Object> findTeamInfoByPage(Team team, PageQueryUtil page);
	
	//删除
	public Integer deleteTeamInfo(Integer id);
	
}
