package com.finance.communication.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 慧银token信息表
 * 
 * @author Administrator
 */

@Entity
@Table(name = "sys_in_token")
public class SysInToken implements Serializable {

	private static final long serialVersionUID = -6386816676401989412L;
	
	@Id
	@Column(name = "token_id")
	private String tokenId;

	@Column(name = "info")
	private String info;

	@Column(name = "sub_info")
	private String subInfo;

	@Column(name = "mid")
	private String mid;

	@Column(name = "expiry_time")
	private Long expiryTime;

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSubInfo() {
		return subInfo;
	}

	public void setSubInfo(String subInfo) {
		this.subInfo = subInfo;
	}

}
