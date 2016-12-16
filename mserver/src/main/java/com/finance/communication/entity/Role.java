package com.finance.communication.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 系统角色表
 * 
 * @author LiXiaoPeng
 *
 *         2015年4月24日 下午1:59:38
 */
@Entity
@Table(name = "sys_m_role")
public class Role extends IdEntity {
	private String name;// 名称
	private String enname;// 英文名
	private String status;// 状态
	private Integer orderid;// 排序号
	private String memo;// 简介
	private Date createTime;// 简介

	// 功能
	private Set<Resource> resources;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "sys_role_auth", joinColumns = @JoinColumn(name = "role_id") , inverseJoinColumns = @JoinColumn(name = "resource_id") )
	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	@Column(name = "name", length = 30, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "enname", length = 30)
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "memo", length = 150)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
