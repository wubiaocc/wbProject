package com.finance.communication.entity;

// Generated 2013-12-16 10:43:18 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 会员表
 */
@Entity
@Table(name = "mrt_merchant_card")
@Immutable
public class MerchantCard implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private String id;

	/**
	 * 激活时间
	 */
	private Date activeTime;
	/**
	 * 余额
	 */
	private Integer balance;

	/**
	 * 注销时间
	 */
	private Date cancelTime;
	/**
	 * 是否注销（0.正常 1.注销）
	 */
	private String canceled;

	/**
	 * 卡号 25位(慧商户号15+卡号10)
	 */
	private String cardNo;

	/**
	 * 卡类型 99:微信会员卡 1:实体卡
	 */
	private String cardType;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 过期时间
	 */
	private Date expriyTime;
	/**
	 * 是否被冻结（0.正常 1.冻结）
	 */
	private String freeze;

	/**
	 * 冻结时间
	 */
	private Date freezeTime;

	/**
	 * 慧商户号
	 */
	private MerchantDef merchantDef;
	
	/**
	 * 手机号
	 */
	private String mobileNo;

	/**
	 * 微信号
	 */
	private String openId;

	/**
	 * 卡用户
	 */
	private String username;

	private String points;
	
	private String labelIds;
	
	private String labelNames;
	
	private String wxCallbackTicketno;
	/**
	 * 生日
	 */
	private Date birthday;
	/**
	 * U未知 F女 M男
	 */
	private String  sex;
	/**
	 * 标签json串
	 */
	private String labels;
	
	private String fid;
	
	private String gid;
	
	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public MerchantCard() {
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
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 80)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "card_no", nullable = false, length = 50)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "username", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "mobile_no", length = 20)
	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "active_time", length = 19)
	public Date getActiveTime() {
		return this.activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	@Column(name = "balance")
	public Integer getBalance() {
		if (this.balance == null) {
			return 0;
		}
		return this.balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "expriy_time", length = 19)
	public Date getExpriyTime() {
		return this.expriyTime;
	}

	public void setExpriyTime(Date expriyTime) {
		this.expriyTime = expriyTime;
	}

	public String getCanceled() {
		return canceled;
	}

	public void setCanceled(String canceled) {
		this.canceled = canceled;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cancel_time", length = 19)
	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/*@Column(name = "open_id", length = 50)*/
	@Transient
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	@Column(name = "freeze_time")
	public Date getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Transient
	public String getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String labelIds) {
		this.labelIds = labelIds;
	}
	@Transient
	public String getLabelNames() {
		return labelNames;
	}

	public void setLabelNames(String labelNames) {
		this.labelNames = labelNames;
	}
	
	@Transient
	public String getWxCallbackTicketno() {
		return wxCallbackTicketno;
	}

	public void setWxCallbackTicketno(String wxCallbackTicketno) {
		this.wxCallbackTicketno = wxCallbackTicketno;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

    
    public String getSex() {
        return sex;
    }

    
    public void setSex(String sex) {
        this.sex = sex;
    }

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}
	
}
