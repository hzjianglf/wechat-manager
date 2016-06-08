package com.wechat.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class HonorVo implements Serializable {

	private static final long serialVersionUID = 7965572402734602507L;

	private Long id;
	private String title;
	private String content;
	private String pic;
	private Timestamp createTime;
	private String userName;
	private Integer isDel;
	
	public Long getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id.longValue();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
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
