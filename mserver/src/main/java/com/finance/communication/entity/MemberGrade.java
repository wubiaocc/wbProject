package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mrt_member_grade")
public class MemberGrade implements Serializable {
	private static final long serialVersionUID = -4309994218963920437L;
	/*
	 * 会员等级
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;// 编号

	private String memberType;//会员类型
	private String gradeName;//会员等级名称
	private String memberCode;//会员代码
	private String memberCondition;//入会条件
	private String memberInterests;//会员权益集
	private String annualFee;//年费
	private String annualIntegral;//积分

	private Date createTime;
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberCondition() {
		return memberCondition;
	}

	public void setMemberCondition(String memberCondition) {
		this.memberCondition = memberCondition;
	}

	public String getMemberInterests() {
		return memberInterests;
	}

	public void setMemberInterests(String memberInterests) {
		this.memberInterests = memberInterests;
	}

	public String getAnnualFee() {
		return annualFee;
	}

	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}

	public String getAnnualIntegral() {
		return annualIntegral;
	}

	public void setAnnualIntegral(String annualIntegral) {
		this.annualIntegral = annualIntegral;
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

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

}
