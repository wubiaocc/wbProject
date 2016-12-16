package com.finance.communication.entity;


/**
 * 第三方交易导入，交易流水
 * @author DaiRongliang
 * @2016年11月21日
 */
public class ThirdTranLogInput {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 服务器
	 */
	private String server;
	
	/**
	 * 交易码
	 */
	private String tranCode;
	
	/**
	 * 交易描述/交易类型
	 */
	private String tranDesc;
	
	/**
	 * 交易时间
	 */
	private String tranTime;
	
	/**
	 * 商户号
	 */
	private String mid;
	
	/**
	 * 商户名
	 */
	private String merchantName;
	
	/**
	 * 代理商名称
	 */
	private String agentName;
	
	/**
	 * 大商户/渠道
	 */
	private String superMerchantName;
	
	/**
	 * 是否销售单
	 */
	private String saleOrderNo;
	
	/**
	 * 是否来自接口
	 */
	private String comeFrom;
	
	/**
	 * mqtt消息标签 trade productOpen..
	 */
	private String type;
	
	/**
	 * 交易金额
	 */
	private String tranAmount;
	
	/**
	 * 终端类型
	 */
	private String terminalType;
	
	/**
	 * 会员卡类型
	 */
	private String cardType;
	
	/**
	 * 会员识别
	 */
	private String cardSource;
	
	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getTranDesc() {
		return tranDesc;
	}

	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getSuperMerchantName() {
		return superMerchantName;
	}

	public void setSuperMerchantName(String superMerchantName) {
		this.superMerchantName = superMerchantName;
	}

	public String getSaleOrderNo() {
		return saleOrderNo;
	}

	public void setSaleOrderNo(String saleOrderNo) {
		this.saleOrderNo = saleOrderNo;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(String tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardSource() {
		return cardSource;
	}

	public void setCardSource(String cardSource) {
		this.cardSource = cardSource;
	}

}
