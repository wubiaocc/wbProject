package com.finance.communication.entity;

/**
 * 产品图片表
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the erp_stock_in database table.
 * 
 */
@Entity
@Table(name ="erp_product_pic")
public class ProductPic implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * id
	 */
	private String id;
	/*
	 * 图片原名称
	 */
	private String originalName;

	/*
	 * 图片系统级变量名
	 */
	private String name;
	/*
	 * 地址(相对)
	 */
	private String address;

	/*
	 * 文件扩展名
	 */
	private String fileExt;

	/*
	 * 尺寸大小
	 */
	private Long size;

	/*
	 * 描述
	 */
	private String descn;
	/*
	 * 类型（1 图片格式）
	 */
	private String type;

	/*
	 * 创建时间
	 */

	private Date createTime;

	public ProductPic() {
	}

	public ProductPic(String originalName, String name, String address,
			String fileExt, Date createTime, String descn, String type,
			Long size) {
		super();
		this.originalName = originalName;
		this.name = name;
		this.address = address;
		this.fileExt = fileExt;
		this.size = size;
		this.descn = descn;
		this.type = type;
		this.createTime = createTime;

	}

	@Id
	@GeneratedValue(generator = "uuid")
	// 指定生成器名称
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 40)
	public String getId() {
		return this.id;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

}