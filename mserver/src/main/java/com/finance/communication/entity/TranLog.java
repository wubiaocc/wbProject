package com.finance.communication.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "pay_tran_log")
public class TranLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 交易流水号 主键
	 */
	private String id;
	/**
	 * 慧商户号
	 * 
	 */

	private MerchantDef merchantDef;

	/**
	 * 发行(卡)商户号
	 */
	private String fid;
	/**
	 * 交易时间
	 */
	private Date tranTime;
	/**
	 * 交易码
	 */
	private String tranCode;
	/**
	 * 其它收银类型码
	 */
	private String cashType;
	/**
	 * 交易金额
	 */
	private Integer tranAmount;
	/**
	 * 附加金额．充值，送的金额．消费，折扣扣减金额
	 */
	private Integer extraAmount;
	/**
	 * 输入金额
	 */
	private Integer inputAmount;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 关联交易流水号
	 */
	private String relateTranLogId;
	/**
	 * 是否有效
	 */
	private Boolean canceledFlag;
	/**
	 * 操作员ID
	 */
	private String operatorId;
	/**
	 * 简单标记
	 */
	private String tranMark;
	/**
	 * 交易描述
	 */

	/**
	 * 交易描述
	 */
    private String mixedTranFlag;
    /**
     * 关联交易流水号
     */
    private String masterTranLogId;
    /**
     * 冲正标识 (0未冲正  1已冲正)  
     */
     private String  reversFlag;
    
	/**
	 * 第三方交易单号
	 */
	private String thirdTradeNo;
    /**
     * 终端号
     */
    private String sn;
    
    
    private String refno;
    
    private String voucher;
    
    private String merchantName;
    
    private String tranDesc;
    
    private String pid;
    
    private String storeName;
    
    private String cardNo;
    

	public String getMixedTranFlag() {
		return mixedTranFlag;
	}

	public void setMixedTranFlag(String mixedTranFlag) {
		this.mixedTranFlag = mixedTranFlag;
	}

	@Transient
	public float getFtranAmount() {
		if (tranAmount == null) {
			tranAmount = 0;
		}
		return tranAmount / 100f;
	}

	public TranLog() {
	}

	public TranLog(String id) {
		this.id = id;
	}

	public TranLog(String id, String sn, String merchantId, Date tranTime,
			String tranCode, Integer tranAmount, Integer cardType, 
			String relateTranLogId, Boolean canceledFlag,
			String operatorId, String posCode, String cardName,
			Integer inputAmount, Integer extraAmount, Integer bindMid) {
		this.id = id;
		this.tranTime = tranTime;
		this.tranCode = tranCode;
		this.tranAmount = tranAmount;
		this.relateTranLogId = relateTranLogId;
		this.canceledFlag = canceledFlag;
		this.operatorId = operatorId;
		this.inputAmount = inputAmount;
		this.extraAmount = extraAmount;

	}



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mid")
	@NotFound(action = NotFoundAction.IGNORE)
	public MerchantDef getMerchantDef() {
		return merchantDef;
	}

	public void setMerchantDef(MerchantDef merchantDef) {
		this.merchantDef = merchantDef;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 40)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tran_time", length = 19)
	public Date getTranTime() {
		return this.tranTime;
	}

	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	@Column(name = "tran_code", length = 10)
	public String getTranCode() {
		return this.tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
     @Transient
	//@Column(name = "cash_type", length = 2)
	public String getCashType() {
		return this.cashType;
	}

	public void setCashType(String cashType) {
		this.cashType = cashType;
	}

	@Column(name = "tran_amount")
	public Integer getTranAmount() {
		return this.tranAmount;
	}

	public void setTranAmount(Integer tranAmount) {
		this.tranAmount = tranAmount;
	}

	@Column(name = "relate_tran_log_id", length = 40)
	public String getRelateTranLogId() {
		return this.relateTranLogId;
	}

	public void setRelateTranLogId(String relateTranLogId) {
		this.relateTranLogId = relateTranLogId;
	}
   @Transient
	//@Column(name = "canceled_flag")
	public Boolean getCanceledFlag() {
		return this.canceledFlag;
	}

	public void setCanceledFlag(Boolean canceledFlag) {
		this.canceledFlag = canceledFlag;
	}

	@Column(name = "operator_id", length = 2)
	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "tran_mark")
	public String getTranMark() {
		return tranMark;
	}

	public void setTranMark(String tranMark) {
		this.tranMark = tranMark;
	}

	@Column(name = "extra_amount")
	public Integer getExtraAmount() {
		return extraAmount;
	}

	public void setExtraAmount(Integer extraAmount) {
		this.extraAmount = extraAmount;
	}

	@Column(name = "input_amount")
	public Integer getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Integer inputAmount) {
		this.inputAmount = inputAmount;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getMasterTranLogId() {
		return masterTranLogId;
	}

	public void setMasterTranLogId(String masterTranLogId) {
		this.masterTranLogId = masterTranLogId;
	}

	public String getReversFlag() {
		return reversFlag;
	}

	public void setReversFlag(String reversFlag) {
		this.reversFlag = reversFlag;
	}

	public String getThirdTradeNo() {
		return thirdTradeNo;
	}

	public void setThirdTradeNo(String thirdTradeNo) {
		this.thirdTradeNo = thirdTradeNo;
	}
	
	@Transient
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	
	@Transient
	public String getTranDesc() {
		return tranDesc;
	}

	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}
	
	@Transient
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@Transient
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@Transient
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	
	
	
}
