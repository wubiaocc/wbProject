package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_merchant_def")
public class MerchantDef implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private String mid;

	/**
	 * 收款通知开关
	 */
	private String collectNotifyMark;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 商户LOGO
	 */
	private String logoImage;

	/**
	 * 收单商户号
	 */
	private String merchantId;

	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户地址
	 */
	private String merchantAddr;
	/**
	 * 商户电话
	 */
	private String merchantTel;

	/**
	 * 支付通道
	 */
	private Integer payId;

	/**
	 * 销售通知开关
	 */
	private String saleNotifyMark;

	/**
	 *
	 * 慧商户编号，从1开始顺序增加,每次加1。与自发行卡的卡号9-15位
	 */
	@Column(name = "sequence_no")
	private Integer sequenceNo;

	/**
	 * 门店相关说明
	 */
	private String shopDesc;

	/**
	 * 微信会员卡开始编号
	 */
	private Integer startNo;

	/**
	 * 商铺模板:1货架式模板 2全部商铺式模板
	 */
	private Integer storesTemplates;

	/**
	 * 使用慧银的支付宝配置参数
	 */
	private String useWizarposAlipayConfig;

	/**
	 * 使用慧银的微信配置参数
	 */
	private String useWizarposWeixinPayConfig;

	/**
	 * vip正面图片
	 */
	private String vipFrontImage;

	/**
	 * vip图片/微信会员卡图片
	 */
	private String vipImage;

	/**
	 * 商户公众号OpenId
	 */
	private String pOpenId;

	/**
	 * 慧商户公众号OpenId
	 */
	private String mOpenId;

	/**
	 * 微信appId
	 */
	private String appId;

	/**
	 * 微信appSecret
	 */
	private String appSecret;

	/**
	 * 公众号的全局唯一票据，调用各接口时都需使用
	 */
	private String accessToken;
	/**
	 * 唯一票据的申请时间戳
	 */
	private Long tokenTimesStamp;

	/**
	 * 管理区域位
	 */
	private String mgtLoct;

	/**
	 * 城市区域位
	 */
	private String cityLoct;
	/**
	 * 商户是否可用标记 0 正常 1 不可用
	 */
	private String validFlag;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 是否使用内嵌支付路由(0不使用 1使用)
	 */
	private String usePayRouteConfig;
	/**
	 * 商户简介
	 */
	private String merchantSummary;
	/**
	 * 最后修改时间
	 */
	private Long lastTime;
	/**
	 * 商户微信小店的上部的banner图片地址
	 */
	private String bannerImage;

	private String operatorType;

	private String dayOrderSeq;

	private String orderSeqResetTime;

	private String weixinCertFilePath;

	private String weixinMchId;

	private String weixinPartnerKey;

	private String weixinAppKey;
	/**
	 * 卡券log
	 */
	private String ticketImgUrl;

	private String lon;

	private String lat;
	/**
	 * 商户类型(1 零售 2 餐饮 3 按摩)
	 */
	private String merchantType;
	/**
	 * 销售提成模式 1:单品提成2:整单提成0:默认无
	 */
	private String saleDeductType;
	/**
	 * 商户联盟号
	 */
	private String fid;
	/**
	 * 0 非盟主 1 盟主
	 */
	private String isLeague;
	private String merchantQqCert;
	/**
	 * 交易码
	 */
	private String vcode;
	private String merchantQqCaCert;
	/**
	 * 1 允许负库存销售(默认) 0不允许负库存销售
	 */
	private String negaStockFlag;
	/**
	 * 是否协助制券（1：是，0：否），默认0（否）
	 */
	private String isAssistMakeTicket;

	private String merchantQqId;
	/**
	 * 营业时间
	 */
	private String shopHours;
	/**
	 * 是否为虚拟总店：1是,0否
	 */
	private String isVirtualMainStore;
	/**
	 * 所属总店Mid
	 */
	private String virtualMainStoreMid;
	/**
	 * 是否开启会员充值提成(1开启 0关闭默认)
	 */
	private String isOpenMemberDeduct;
	/**
	 * 结算时间
	 */
	private String settlementHour;

	/**
	 * 微信图文素材
	 */
	private String weixinImgText;
	/**
	 * 微信积分商城规则语
	 */
	private String weixinPointsRuleText;

	/**
	 * 在线充值'ON 开启,OFF 关闭';
	 */
	private String chargeOnline;
	private Long smsValidCount;
	private String smsChannelId;
	/**
	 * 是否关联有赞商城 0非有赞 1有赞商城
	 */
	private String isYouzanMall;

	/**
	 * 开通业务（1收款，2会员-基础版，3销售-零售版，4销售-轻餐饮版）
	 */
	private String applyModules;
	/**
	 * 管理员手机号
	 */
	private String adminMobileNo;
	/**
	 * 商户终端登录session时长 T 永久登录 F半小时失效
	 */
	private String sessionFlag;
	
	private String agentId;
	
	/**
	 * 营销活动是否开启 0：关闭 1：开启（默认）
	 */
	private String marketActivityFlag;
	
	public MerchantDef() {
	}

	@Column(name = "shop_hours")
	public String getShopHours() {
		return shopHours;
	}

	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}

	@Id
	public String getMid() {
		return this.mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLogoImage() {
		return this.logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getMerchantId() {
		return this.merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public int getPayId() {
		return this.payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public int getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getShopDesc() {
		return this.shopDesc;
	}

	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}

	public int getStartNo() {
		return this.startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getStoresTemplates() {
		return this.storesTemplates;
	}

	public void setStoresTemplates(int storesTemplates) {
		this.storesTemplates = storesTemplates;
	}

	public String getVipFrontImage() {
		return this.vipFrontImage;
	}

	public void setVipFrontImage(String vipFrontImage) {
		this.vipFrontImage = vipFrontImage;
	}

	public String getVipImage() {
		return this.vipImage;
	}

	public void setVipImage(String vipImage) {
		this.vipImage = vipImage;
	}

	public String getpOpenId() {
		return pOpenId;
	}

	public void setpOpenId(String pOpenId) {
		this.pOpenId = pOpenId;
	}

	public String getmOpenId() {
		return mOpenId;
	}

	public void setmOpenId(String mOpenId) {
		this.mOpenId = mOpenId;
	}

	public String getCollectNotifyMark() {
		return collectNotifyMark;
	}

	public void setCollectNotifyMark(String collectNotifyMark) {
		this.collectNotifyMark = collectNotifyMark;
	}

	public String getSaleNotifyMark() {
		return saleNotifyMark;
	}

	public void setSaleNotifyMark(String saleNotifyMark) {
		this.saleNotifyMark = saleNotifyMark;
	}

	public String getUseWizarposAlipayConfig() {
		return useWizarposAlipayConfig;
	}

	public void setUseWizarposAlipayConfig(String useWizarposAlipayConfig) {
		this.useWizarposAlipayConfig = useWizarposAlipayConfig;
	}

	public String getUseWizarposWeixinPayConfig() {
		return useWizarposWeixinPayConfig;
	}

	public void setUseWizarposWeixinPayConfig(String useWizarposWeixinPayConfig) {
		this.useWizarposWeixinPayConfig = useWizarposWeixinPayConfig;
	}

	@Column(name = "weixin_app_id")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "weixin_app_secret")
	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Column(name = "wx_access_token")
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Column(name = "wx_access_token_timestamp")
	public Long getTokenTimesStamp() {
		return tokenTimesStamp;
	}

	public void setTokenTimesStamp(Long tokenTimesStamp) {
		this.tokenTimesStamp = tokenTimesStamp;
	}

	public String getMerchantAddr() {
		return merchantAddr;
	}

	public void setMerchantAddr(String merchantAddr) {
		this.merchantAddr = merchantAddr;
	}

	public String getMerchantTel() {
		return merchantTel;
	}

	public void setMerchantTel(String merchantTel) {
		this.merchantTel = merchantTel;
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

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getUsePayRouteConfig() {
		return usePayRouteConfig;
	}

	public void setUsePayRouteConfig(String usePayRouteConfig) {
		this.usePayRouteConfig = usePayRouteConfig;
	}

	public String getMerchantSummary() {
		return merchantSummary;
	}

	public void setMerchantSummary(String merchantSummary) {
		this.merchantSummary = merchantSummary;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getDayOrderSeq() {
		return dayOrderSeq;
	}

	public void setDayOrderSeq(String dayOrderSeq) {
		this.dayOrderSeq = dayOrderSeq;
	}

	public String getOrderSeqResetTime() {
		return orderSeqResetTime;
	}

	public void setOrderSeqResetTime(String orderSeqResetTime) {
		this.orderSeqResetTime = orderSeqResetTime;
	}

	public String getWeixinCertFilePath() {
		return weixinCertFilePath;
	}

	public void setWeixinCertFilePath(String weixinCertFilePath) {
		this.weixinCertFilePath = weixinCertFilePath;
	}

	public String getWeixinMchId() {
		return weixinMchId;
	}

	public void setWeixinMchId(String weixinMchId) {
		this.weixinMchId = weixinMchId;
	}

	public String getWeixinPartnerKey() {
		return weixinPartnerKey;
	}

	public void setWeixinPartnerKey(String weixinPartnerKey) {
		this.weixinPartnerKey = weixinPartnerKey;
	}

	public String getWeixinAppKey() {
		return weixinAppKey;
	}

	public void setWeixinAppKey(String weixinAppKey) {
		this.weixinAppKey = weixinAppKey;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getTicketImgUrl() {
		return ticketImgUrl;
	}

	public void setTicketImgUrl(String ticketImgUrl) {
		this.ticketImgUrl = ticketImgUrl;
	}

	public void setStoresTemplates(Integer storesTemplates) {
		this.storesTemplates = storesTemplates;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getSaleDeductType() {
		return saleDeductType;
	}

	public void setSaleDeductType(String saleDeductType) {
		this.saleDeductType = saleDeductType;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getIsLeague() {
		return isLeague;
	}

	public void setIsLeague(String isLeague) {
		this.isLeague = isLeague;
	}

	public String getMerchantQqCert() {
		return merchantQqCert;
	}

	public void setMerchantQqCert(String merchantQqCert) {
		this.merchantQqCert = merchantQqCert;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
	@Column(name = "merchant_qq_ca_cert")
	public String getMerchantQqCaCert() {
		return merchantQqCaCert;
	}

	public void setMerchantQqCaCert(String merchantQqCaCert) {
		this.merchantQqCaCert = merchantQqCaCert;
	}

	public String getNegaStockFlag() {
		return negaStockFlag;
	}

	public void setNegaStockFlag(String negaStockFlag) {
		this.negaStockFlag = negaStockFlag;
	}

	public String getIsAssistMakeTicket() {
		return isAssistMakeTicket;
	}

	public void setIsAssistMakeTicket(String isAssistMakeTicket) {
		this.isAssistMakeTicket = isAssistMakeTicket;
	}

	@Column(name = "merchant_qq_id")
	public String getMerchantQqId() {
		return merchantQqId;
	}

	public void setMerchantQqId(String merchantQqId) {
		this.merchantQqId = merchantQqId;
	}

	@Column(name = "is_virtual_main_store")
	public String getIsVirtualMainStore() {
		return isVirtualMainStore;
	}

	public void setIsVirtualMainStore(String isVirtualMainStore) {
		this.isVirtualMainStore = isVirtualMainStore;
	}

	@Column(name = "virtual_main_store_mid")
	public String getVirtualMainStoreMid() {
		return virtualMainStoreMid;
	}

	@Column(name = "is_open_member_deduct")
	public String getIsOpenMemberDeduct() {
		return isOpenMemberDeduct;
	}

	public void setIsOpenMemberDeduct(String isOpenMemberDeduct) {
		this.isOpenMemberDeduct = isOpenMemberDeduct;
	}

	@Column(name = "settlement_hour")
	public String getSettlementHour() {
		return settlementHour;
	}

	public void setSettlementHour(String settlementHour) {
		this.settlementHour = settlementHour;
	}

	public String getWeixinImgText() {
		return weixinImgText;
	}

	public void setWeixinImgText(String weixinImgText) {
		this.weixinImgText = weixinImgText;
	}

	public void setVirtualMainStoreMid(String virtualMainStoreMid) {
		this.virtualMainStoreMid = virtualMainStoreMid;
	}

	public String getWeixinPointsRuleText() {
		return weixinPointsRuleText;
	}

	public void setWeixinPointsRuleText(String weixinPointsRuleText) {
		this.weixinPointsRuleText = weixinPointsRuleText;
	}

	public String getChargeOnline() {
		return chargeOnline;
	}

	public void setChargeOnline(String chargeOnline) {
		this.chargeOnline = chargeOnline;
	}
	public Long getSmsValidCount() {
		return smsValidCount;
	}

	public void setSmsValidCount(Long smsValidCount) {
		this.smsValidCount = smsValidCount;
	}

	public String getSmsChannelId() {
		return smsChannelId;
	}

	public void setSmsChannelId(String smsChannelId) {
		this.smsChannelId = smsChannelId;
	}

	public String getIsYouzanMall() {
		return isYouzanMall;
	}

	public void setIsYouzanMall(String isYouzanMall) {
		this.isYouzanMall = isYouzanMall;
	}

	public String getApplyModules() {
		return applyModules;
	}

	public void setApplyModules(String applyModules) {
		this.applyModules = applyModules;
	}

	public String getAdminMobileNo() {
		return adminMobileNo;
	}

	public void setAdminMobileNo(String adminMobileNo) {
		this.adminMobileNo = adminMobileNo;
	}

	public void setStartNo(Integer startNo) {
		this.startNo = startNo;
	}

	public String getSessionFlag() {
		return sessionFlag;
	}

	public void setSessionFlag(String sessionFlag) {
		this.sessionFlag = sessionFlag;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getMarketActivityFlag() {
		return marketActivityFlag;
	}

	public void setMarketActivityFlag(String marketActivityFlag) {
		this.marketActivityFlag = marketActivityFlag;
	}
	
	

}
