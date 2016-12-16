package com.finance.communication.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_in_ticket")
public class SysInTicket  implements Serializable  {
	private static final long serialVersionUID = -4309994218963920437L;
	// 慧商户号
	@Column(name = "mid")
	private String mid;

	// ticket的方法名
	@Column(name = "api_symbol")
	private String apiSymbol;

	// ticket的值
	@Id
	@Column(name = "wx_ticket")
	private String wxTicket;

	// ticket的过期时间
	@Column(name = "expiry_time")
	private long expiryTime;

	public String getApiSymbol() {
		return apiSymbol;
	}

	public void setApiSymbol(String apiSymbol) {
		this.apiSymbol = apiSymbol;
	}

	public String getWxTicket() {
		return wxTicket;
	}

	public void setWxTicket(String wxTicket) {
		this.wxTicket = wxTicket;
	}

	public long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
