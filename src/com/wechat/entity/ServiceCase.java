package com.wechat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @Description 
 * @ClassName ServiceItem.java
 * @author Administrator
 * @date 2016年6月15日下午3:55:36
 */
@Entity
@Table(name="portal_service_case",catalog="wechat")
public class ServiceCase implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	
	private Integer serviceId;
	
	private Integer caseId;
	
	
	public ServiceCase() {
	}

	public ServiceCase(Integer id, Integer serviceId, Integer caseId) {
		this.id = id;
		this.serviceId = serviceId;
		this.caseId = caseId;
	}

	public ServiceCase(Integer serviceId, Integer caseId) {
		this.serviceId = serviceId;
		this.caseId = caseId;
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
	@Column(name="service_id")
	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	@Column(name="case_id")
	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	
	
}
