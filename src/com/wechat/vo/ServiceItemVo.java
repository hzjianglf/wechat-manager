package com.wechat.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class ServiceItemVo implements Serializable {

	private static final long serialVersionUID = 7965572402734602507L;

	private Integer id;
	private String serviceName;
	private String serviceContent;
	private String servicePic;
	private Timestamp createTime;
	private String userName;
	private Integer isDel;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceContent() {
		return serviceContent;
	}
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	public String getServicePic() {
		return servicePic;
	}
	public void setServicePic(String servicePic) {
		this.servicePic = servicePic;
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
