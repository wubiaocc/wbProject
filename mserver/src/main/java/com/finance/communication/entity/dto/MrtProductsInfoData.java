package com.finance.communication.entity.dto;

/**
 * 批量导入商户设置产品实体
 * @author DaiRongliang
 * @2016年12月13日
 */
public class MrtProductsInfoData {
	
	/**
	 * 商户号
	 */
	private String mid;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 默认密码
	 */
	private String pwd;
	
	/**
	 * 产品类型
	 */
	private String proType;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}
	
}
