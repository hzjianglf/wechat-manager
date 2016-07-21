package com.wechat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wechat.validate.NotNull;
import com.wechat.validate.Str;

/**
 * 组织机构表
 * @Description:
 * @author zhur
 * @date 2016年6月6日 上午10:18:47
 */
@Entity
@Table(name="portal_us_organize",catalog="wechat")
public class Organize implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private Integer id;
	
	@NotNull("组织机构名称")
	@Str(mesName="名称",maxLength=40)
	private String organizeName;
	
	@NotNull("机构内容")
	private String organizeInfo;
	@NotNull("机构图片")
	private String organizePic;
		
	private Integer isDel;

	public Organize() {
	}
	
	public Organize(Integer id,String organizeName, String organizeInfo,
			String organizePic, Integer isDel) {
		this.id=id;
		this.organizeName = organizeName;
		this.organizeInfo = organizeInfo;
		this.organizePic = organizePic;
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
	@Column(name="organize_name")
	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}
	@Column(name="organize_info")
	public String getOrganizeInfo() {
		return organizeInfo;
	}

	public void setOrganizeInfo(String organizeInfo) {
		this.organizeInfo = organizeInfo;
	}
	@Column(name="organize_pic")
	public String getOrganizePic() {
		return organizePic;
	}

	public void setOrganizePic(String organizePic) {
		this.organizePic = organizePic;
	}

	@Column(name="is_del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}


}
