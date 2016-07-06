package com.wechat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wechat.validate.NotNull;
import com.wechat.validate.Str;

/**
 * 技术团队表
 * @Description:
 * @author zhur
 * @date 2016年6月6日 上午10:18:47
 */
@Entity
@Table(name="weixin_team",catalog="wechat")
public class Team implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	
	@NotNull("团队名称")
	@Str(minLength = 2, maxLength = 25, mesName = "团队名称")
	private String teamName;
	
	private String teamInfo;
	
	private String teamPic;
	
	private Integer createUser;
	
	private Timestamp createTime;
	
	private Integer isDel;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id",unique=true,nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="team_name",length=255)
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Column(name="team_info")
	public String getTeamInfo() {
		return teamInfo;
	}

	public void setTeamInfo(String teamInfo) {
		this.teamInfo = teamInfo;
	}

	@Column(name="create_user")
	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Column(name="team_pic")
	public String getTeamPic() {
		return teamPic;
	}

	public void setTeamPic(String teamPic) {
		this.teamPic = teamPic;
	}
	
	

}
