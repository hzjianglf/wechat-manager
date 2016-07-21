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
import com.wechat.validate.Str;

/**
 * 企业文化
 * @Description 
 * @ClassName Culture.java
 * @author Administrator-zhur
 * @date 2016年7月11日下午6:23:47
 */
@Entity
@Table(name="portal_us_ent_culture",catalog="wechat")
public class Culture implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	@NotNull("名称")
	private String cultureName;
	@NotNull("描述")
	@Str(mesName="描述",maxLength=40)
	private String cultureDescription;
	@NotNull("内容")
	private String cultureInfo;
	@NotNull("图片")
	private String culturePic;
	
	private Date cultureDate;
		
	private Integer isDel;
	
	public Culture() {
	}
	
	
	public Culture(Integer id, String cultureName, String cultureDescription,
			String cultureInfo, String culturePic, Date cultureDate,
			Integer isDel) {
		this.id = id;
		this.cultureName = cultureName;
		this.cultureDescription = cultureDescription;
		this.cultureInfo = cultureInfo;
		this.culturePic = culturePic;
		this.cultureDate = cultureDate;
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
	@Column(name="enterprise_culture_name")
	public String getCultureName() {
		return cultureName;
	}

	public void setCultureName(String cultureName) {
		this.cultureName = cultureName;
	}
	@Column(name="culture_description")
	public String getCultureDescription() {
		return cultureDescription;
	}

	public void setCultureDescription(String cultureDescription) {
		this.cultureDescription = cultureDescription;
	}
	@Column(name="en_culture_info")
	public String getCultureInfo() {
		return cultureInfo;
	}

	public void setCultureInfo(String cultureInfo) {
		this.cultureInfo = cultureInfo;
	}
	@Column(name="en_culture_pic")
	public String getCulturePic() {
		return culturePic;
	}

	public void setCulturePic(String culturePic) {
		this.culturePic = culturePic;
	}
	@Column(name="culture_date")
	public Date getCultureDate() {
		return cultureDate;
	}

	public void setCultureDate(Date cultureDate) {
		this.cultureDate = cultureDate;
	}
	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	

}
