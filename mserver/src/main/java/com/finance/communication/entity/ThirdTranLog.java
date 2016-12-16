package com.finance.communication.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 第三方交易查询实体
 * @author DaiRongliang
 * @2016年11月14日
 */
@Entity
@Table(name = "third_tran_log")
public class ThirdTranLog {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	/**
	 * 数据来源2(产品)
	 */
	private String tranLogModel;
	
	/**
	 * 数据来源1(第三方公司)
	 */
	private String tranLogProvider;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 收单商户号
	 */
	private String merchantNo;
	
	/**
	 * 终端号
	 */
	private String terminalNo;
	
	/**
	 * 交易时间
	 */
	private Date tranTime; 
	
	/**
	 * 交易金额
	 */
	private Double tranAmount;
	
	/**
	 * 交易类型
	 */
	private String tranType;
	
	/**
	 * 商户全称
	 */
	private String merchantFullName;
	
	/**
	 * 法人名称
	 */
	private String legalPerson;
	
	/**
	 * 商户结算率
	 */
	private String rate;
	
	/**
	 * 手续费金额
	 */
	private Double freeAmount;
	
	/**
	 * 终端流水号
	 */
	private String terminalLog;
	
	/**
	 * 终端批次号
	 */
	private String batchNo;
	
	/**
	 * 交易主账号
	 */
	private String accountNo;
	
	/**
	 * 银行应答流水号
	 */
	private String bankLog;
	
	/**
	 * 代理商号
	 */
	private String agentNo;
	
	/**
	 * 代理商名称
	 */
	private String agentName;
	
	/**
	 * 第三方系统支付方式
	 */
	private String tranDesc;
	
	/**
	 * 交易描述
	 */
	private String tranMark;
	
	/**
	 * 导入日期
	 */
	private Date createTime;
	
	/**
	 * 批次号
	 */
	private String billNo;
	
	/**
	 * 慧银商户号
	 */
	private String mid;
	
	@Id
  	@GeneratedValue(generator = "uuid")
  	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  	@Column(name = "id", length = 80)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTranLogModel() {
		return tranLogModel;
	}

	public void setTranLogModel(String tranLogModel) {
		this.tranLogModel = tranLogModel;
	}

	public String getTranLogProvider() {
		return tranLogProvider;
	}

	public void setTranLogProvider(String tranLogProvider) {
		this.tranLogProvider = tranLogProvider;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public Date getTranTime() {
		return tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public Double getTranAmount() {
		return tranAmount;
	}

	public void setTranAmount(Double tranAmount) {
		this.tranAmount = tranAmount;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getMerchantFullName() {
		return merchantFullName;
	}

	public void setMerchantFullName(String merchantFullName) {
		this.merchantFullName = merchantFullName;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Double getFreeAmount() {
		return freeAmount;
	}

	public void setFreeAmount(Double freeAmount) {
		this.freeAmount = freeAmount;
	}

	public String getTerminalLog() {
		return terminalLog;
	}

	public void setTerminalLog(String terminalLog) {
		this.terminalLog = terminalLog;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBankLog() {
		return bankLog;
	}

	public void setBankLog(String bankLog) {
		this.bankLog = bankLog;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getTranDesc() {
		return tranDesc;
	}

	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	public String getTranMark() {
		return tranMark;
	}

	public void setTranMark(String tranMark) {
		this.tranMark = tranMark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
}
