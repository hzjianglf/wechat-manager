package com.wechat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wechat.validate.NotNull;

@Entity
@Table(name="portal_us_history",catalog="wechat")
public class History implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	
	private Date historyYear;
	@NotNull("名称")
	private String historyName;
	
	private String historyInfo;
	
	private String historyInfo1;
	private String historyInfo2;
	private String historyInfo3;
	private String historyPic;
			
	private Integer isDel;
	
	public History() {
	}
	
	public History(Integer id, Date historyYear, String historyName,
			String historyInfo, String historyInfo1, String historyInfo2,
			String historyInfo3, String historyPic, Integer isDel) {
		this.id = id;
		this.historyYear = historyYear;
		this.historyName = historyName;
		this.historyInfo = historyInfo;
		this.historyInfo1 = historyInfo1;
		this.historyInfo2 = historyInfo2;
		this.historyInfo3 = historyInfo3;
		this.historyPic = historyPic;
		this.isDel = isDel;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id",unique=true,nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="history_time")
	public Date getHistoryYear() {
		return historyYear;
	}

	public void setHistoryYear(Date historyYear) {
		this.historyYear = historyYear;
	}
	@Column(name="history_name")
	public String getHistoryName() {
		return historyName;
	}

	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}
	@Column(name="history_info")
	public String getHistoryInfo() {
		return historyInfo;
	}

	public void setHistoryInfo(String historyInfo) {
		this.historyInfo = historyInfo;
	}
	@Column(name="history_info1")
	public String getHistoryInfo1() {
		return historyInfo1;
	}

	public void setHistoryInfo1(String historyInfo1) {
		this.historyInfo1 = historyInfo1;
	}
	@Column(name="history_info2")
	public String getHistoryInfo2() {
		return historyInfo2;
	}

	public void setHistoryInfo2(String historyInfo2) {
		this.historyInfo2 = historyInfo2;
	}
	@Column(name="history_info3")
	public String getHistoryInfo3() {
		return historyInfo3;
	}

	public void setHistoryInfo3(String historyInfo3) {
		this.historyInfo3 = historyInfo3;
	}
	@Column(name="history_pic")
	public String getHistoryPic() {
		return historyPic;
	}

	public void setHistoryPic(String historyPic) {
		this.historyPic = historyPic;
	}

	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	

}
