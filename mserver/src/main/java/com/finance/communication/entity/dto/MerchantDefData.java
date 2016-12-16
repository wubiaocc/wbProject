package com.finance.communication.entity.dto;

/**
 * 商户表dto
 * @author DaiRongliang
 * @2016年11月17日
 */
public class MerchantDefData {
	
	/**
	 * 商户号
	 */
	private String mid;
	
	/**
	 * 收单终端号
	 */
	private String merchantTerminalNo;
	
	/**
	 * 收单商户号
	 */
	private String merchantId;
	
	/**
	 * 管理区域位
	 */
	private String mgtLoct;
	
	/**
	 * 城市区域位
	 */
	private String cityLoct;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMerchantTerminalNo() {
		return merchantTerminalNo;
	}

	public void setMerchantTerminalNo(String merchantTerminalNo) {
		this.merchantTerminalNo = merchantTerminalNo;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMgtLoct() {
		return mgtLoct;
	}

	public void setMgtLoct(String mgtLoct) {
		this.mgtLoct = mgtLoct;
	}

	public String getCityLoct() {
		return cityLoct;
	}

	public void setCityLoct(String cityLoct) {
		this.cityLoct = cityLoct;
	}
	
	
}
