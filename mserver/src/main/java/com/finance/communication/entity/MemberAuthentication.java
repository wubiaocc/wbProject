package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mrt_member_authentication")
public class MemberAuthentication implements Serializable {
	private static final long serialVersionUID = -4309994218963920437L;
	/*
	 * 会员认证
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;// 编号

	private String merchantCardId;//会员卡ID

	private String authMemberGrade;//认证会员等级
	private String authMemberType;//认证会员类型

	private String content;//备注

	private String authStatus;//认证状态 0：未认证   1：认证通过  2：认证不通过
	private Date authTime;//认证时间
	private String authUsername;//处理人
	private String authResult;//处理结果

	private Date createTime;//创建时间
	private Date updateTime;//更新时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCardId() {
		return merchantCardId;
	}

	public void setMerchantCardId(String merchantCardId) {
		this.merchantCardId = merchantCardId;
	}

	public String getAuthMemberGrade() {
		return authMemberGrade;
	}

	public void setAuthMemberGrade(String authMemberGrade) {
		this.authMemberGrade = authMemberGrade;
	}

	public String getAuthMemberType() {
		return authMemberType;
	}

	public void setAuthMemberType(String authMemberType) {
		this.authMemberType = authMemberType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public String getAuthUsername() {
		return authUsername;
	}

	public void setAuthUsername(String authUsername) {
		this.authUsername = authUsername;
	}

	public String getAuthResult() {
		return authResult;
	}

	public void setAuthResult(String authResult) {
		this.authResult = authResult;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
