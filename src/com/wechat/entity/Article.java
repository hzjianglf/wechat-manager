package com.wechat.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文章表
 * @Description:
 * @author zhur
 * @date 2016年6月6日 上午10:18:47
 */
@Entity
@Table(name="weixin_hot_article",catalog="wechat")
public class Article implements Serializable{

	/**
	 * 序列化 成字节码文件
	 */
	private static final long serialVersionUID = -5522351679812829751L;
	
	private BigInteger id;
	
	private String title;
	
	private String tag;
	
	private String content;
	
	private String pic;
	
	private String author;
	
	private Timestamp createTime;
	
	private Integer isDel;

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id",unique=true,nullable=false)
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Column(name="title",length=255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="is_Del")
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Column(name="pic")
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Column(name="tag")
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	

}
