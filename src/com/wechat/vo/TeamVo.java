package com.wechat.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TeamVo implements Serializable {

	private static final long serialVersionUID = 7965572402734602507L;

	private Integer id;
	private String teamName;
	private String teamInfo;
	private String teamPic;
	private Timestamp createTime;
	private String userName;
	private Integer isDel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamInfo() {
		return teamInfo;
	}
	public void setTeamInfo(String teamInfo) {
		this.teamInfo = teamInfo;
	}
	public String getTeamPic() {
		return teamPic;
	}
	public void setTeamPic(String teamPic) {
		this.teamPic = teamPic;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	
	
}
