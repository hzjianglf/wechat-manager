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

@Entity
@Table(name="portal_laboratory",catalog="wechat")
public class Laboratory implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	@NotNull("实验室名称")
	@Str(maxLength=40,mesName="实验室名称")
	private String laboratoryName;
	@NotNull("内容")
	private String laboratoryInfo;
	@NotNull("图片")
	private String laboratoryPic;
	@NotNull("类型")
	private int laboratoryType;
	private Date createTime;	
	private Date updateTime;
			
	private Integer isDel;
	
	public Laboratory() {
	}

	public Laboratory(Integer id, String laboratoryName, String laboratoryInfo,
			String laboratoryPic, int laboratoryType, Date createTime,
			Date updateTime, Integer isDel) {
		this.id = id;
		this.laboratoryName = laboratoryName;
		this.laboratoryInfo = laboratoryInfo;
		this.laboratoryPic = laboratoryPic;
		this.laboratoryType = laboratoryType;
		this.createTime = createTime;
		this.updateTime = updateTime;
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
	
	@Column(name="laboratory_name")
	public String getLaboratoryName() {
		return laboratoryName;
	}

	public void setLaboratoryName(String laboratoryName) {
		this.laboratoryName = laboratoryName;
	}
	@Column(name="laboratory_info")
	public String getLaboratoryInfo() {
		return laboratoryInfo;
	}

	public void setLaboratoryInfo(String laboratoryInfo) {
		this.laboratoryInfo = laboratoryInfo;
	}
	@Column(name="laboratory_pic")
	public String getLaboratoryPic() {
		return laboratoryPic;
	}

	public void setLaboratoryPic(String laboratoryPic) {
		this.laboratoryPic = laboratoryPic;
	}
	@Column(name="laboratory_type")
	public int getLaboratoryType() {
		return laboratoryType;
	}

	public void setLaboratoryType(int laboratoryType) {
		this.laboratoryType = laboratoryType;
	}
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	

}
