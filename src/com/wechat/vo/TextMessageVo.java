package com.wechat.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

public class TextMessageVo implements Serializable {

	private static final long serialVersionUID = 7965572402734602507L;

	private Long id;
	private String keyword;
	private String text;
	private BigInteger click;
	private Timestamp createTime;
	private String userName;
	private Integer isDel;
	public Long getId() {
		return id;
	}
	//java.lang.Long, actual value: java.math.BigInteger
	public void setId(BigInteger id) {
		this.id = id.longValue();
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public BigInteger getClick() {
		return click;
	}
	public void setClick(BigInteger click) {
		this.click = click;
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
