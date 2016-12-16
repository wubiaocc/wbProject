package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/***
 * 门户用户表 .
 * 
 */
@Entity
@Table(name = "sys_m_user")
public class SysUser extends IdEntity implements Serializable {

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建时间
	 */
	private Date updateTime;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 代理商名称
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;

	/**
	 * 登陆名
	 */
	private String loginName;
	/**
	 * 0 禁止登陆 1 允许登陆
	 */
	private String enable;

	private Set<Role> roles;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}