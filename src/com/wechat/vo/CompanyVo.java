package com.wechat.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class CompanyVo implements Serializable {

	private static final long serialVersionUID = 7965572402734602507L;

	private Integer id;
	private String companyName;
	private String companyInfo;
	private String companyPic;
	private Timestamp createTime;
	private String userName;
	private Integer isDel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}
	public String getCompanyPic() {
		return companyPic;
	}
	public void setCompanyPic(String companyPic) {
		this.companyPic = companyPic;
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
