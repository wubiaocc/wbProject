package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mrt_member_interests")
public class MemberInterests implements Serializable {
	private static final long serialVersionUID = -4309994218963920437L;
	/*
	 * 会员权益
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;// 编号

	private String interestsCode;//权益编码 

	private String memberType;//会员类型

	private String interestsContent;//权益内容

	private Date createTime;
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInterestsCode() {
		return interestsCode;
	}

	public void setInterestsCode(String interestsCode) {
		this.interestsCode = interestsCode;
	}

	public String getInterestsContent() {
		return interestsContent;
	}

	public void setInterestsContent(String interestsContent) {
		this.interestsContent = interestsContent;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

}
