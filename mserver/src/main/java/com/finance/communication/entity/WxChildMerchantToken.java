package com.finance.communication.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 微信子账户token 实体 字母商户授权
 * 
 * @author Yangyang.zhang
 * @since 2015年6月30日 09:33:48
 *
 */
@Entity
@Table(name = "sys_wx_child_merchant_token")
public class WxChildMerchantToken implements Serializable {
	private static final long serialVersionUID = -4309994218963920437L;
	// 唯一标识
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", length = 80)
	private String id;
	// 母商户id
	@Column(name = "primary_id")
	private String primaryId;
	// 慧商户号
	@Column(name = "mid", updatable=false)
	private String mid;
	// 公众号的gh号
	@Column(name = "ghCode")
	private String ghCode;
	// 授权商户app_id
	@Column(name = "wx_app_id")
	private String wxAppId;
	// 授权商户app_secret
	@Column(name = "wx_app_secret")
	private String wxAppSecret;
	// 授权商户mchid
	@Column(name = "wx_mchid")
	private String wxMchid;
	// 授权商户支付key
	@Column(name = "wx_app_key")
	private String wxAppKey;
	// 授权商户权限集
	@Column(name = "func_info")
	private String funcInfo;
	// 授权码
	@Column(name = "auth_code")
	private String authCode;
	// 授权码有效期
	@Column(name = "auth_code_expires_in")
	private Long authCodeExpiresIn = 0L;
	// 授权商户access_token
	@Column(name = "authorizer_access_token")
	private String authorizerAccessToken;
	// 授权商户刷新access_token
	@Column(name = "authorizer_refresh_token")
	private String authorizerRefreshToken;
	// 授权商户access_token有效时间
	@Column(name = "authorizer_access_token_expiry")
	private Long authorizerAccessTokenExpiry = 0L;
	// 公众号的全局唯一票据，调用各接口时都需使用
	@Column(name = "merchant_access_token")
	private String merchantAccessToken;
	// 公众号的全局唯一票据有效时间
	@Column(name = "merchant_access_token_expiry")
	private Long merchantAccessTokenExpiry = 0L;
	@Column(name = "authorizer_refresh_token_expiry")
	private Long authorizerRefreshTokenExpiry;
	/**
	 * 商户logo
	 */
	private String logoMediaId;
	/**
	 * 营业执照
	 */
	private String businessLicenseMediaId;
	/**
	 * 协议书
	 */
	private String agreementFileMediaId;
	/**
	 * 一级分类
	 */
	private String primaryCategoryId;
	/**
	 * 二级分类
	 */
	private String secondaryCategoryId;
	/**
	 * 商户名称
	 */
	private String name;
	/**
	 * 商户logUrl
	 */
	private String logUrl;
	/**
	 * 授权类型 0(默认) 一般授权 1 强授权 2,3
	 */
	private String authType;
	/**
	 * 协议到期日期
	 */
    private Date agreementEndTime;
    /**
     * 子商户ID
     */
    private Integer merchantId;
    /**
     * 欢迎语
     */
    private String welcomeContent;
    /**
     * 协议商户号 协助制券
     */
    private String pid;
    private String weixinName;
    /**
     * IMG 图文,TXT 文本
     */
    private String welcomeType;
    /**
     * 0:有公章授权函 1:无公章授权函
     */
    private String protocolType;
    /**
     * 营业执照media_id
     */
    private String agreementMediaId;
    /**
     * 营业执照图片地址
     */
    private String agreementMediaIdImg;
    
    /**
     * 身份证media_id
     */
    private String operatorMediaId;
    /**
     * 身份证图片地址
     */
    private String operatorMediaIdImg;
    
    private String gid;
    
    private String hymid;
	public String getWelcomeContent() {
		return welcomeContent;
	}

	public void setWelcomeContent(String welcomeContent) {
		this.welcomeContent = welcomeContent;
	}

	public String getLogoMediaId() {
		return logoMediaId;
	}

	public void setLogoMediaId(String logoMediaId) {
		this.logoMediaId = logoMediaId;
	}

	public String getBusinessLicenseMediaId() {
		return businessLicenseMediaId;
	}

	public void setBusinessLicenseMediaId(String businessLicenseMediaId) {
		this.businessLicenseMediaId = businessLicenseMediaId;
	}

	public String getAgreementFileMediaId() {
		return agreementFileMediaId;
	}

	public void setAgreementFileMediaId(String agreementFileMediaId) {
		this.agreementFileMediaId = agreementFileMediaId;
	}

	public String getPrimaryCategoryId() {
		return primaryCategoryId;
	}

	public void setPrimaryCategoryId(String primaryCategoryId) {
		this.primaryCategoryId = primaryCategoryId;
	}

	public String getSecondaryCategoryId() {
		return secondaryCategoryId;
	}

	public void setSecondaryCategoryId(String secondaryCategoryId) {
		this.secondaryCategoryId = secondaryCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAuthorizerRefreshTokenExpiry() {
		return authorizerRefreshTokenExpiry;
	}

	public void setAuthorizerRefreshTokenExpiry(Long authorizerRefreshTokenExpiry) {
		this.authorizerRefreshTokenExpiry = authorizerRefreshTokenExpiry;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getAuthorizerAccessToken() {
		return authorizerAccessToken;
	}

	public void setAuthorizerAccessToken(String authorizerAccessToken) {
		this.authorizerAccessToken = authorizerAccessToken;
	}

	public String getAuthorizerRefreshToken() {
		return authorizerRefreshToken;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}
	
	public Long getAuthorizerAccessTokenExpiry() {
		return authorizerAccessTokenExpiry;
	}

	public void setAuthorizerAccessTokenExpiry(Long authorizerAccessTokenExpiry) {
		this.authorizerAccessTokenExpiry = authorizerAccessTokenExpiry;
	}

	public String getFuncInfo() {
		return funcInfo;
	}

	public void setFuncInfo(String funcInfo) {
		this.funcInfo = funcInfo;
	}

	public Long getAuthCodeExpiresIn() {
		return authCodeExpiresIn;
	}

	public void setAuthCodeExpiresIn(Long authCodeExpiresIn) {
		this.authCodeExpiresIn = authCodeExpiresIn;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getGhCode() {
		return ghCode;
	}

	public void setGhCode(String ghCode) {
		this.ghCode = ghCode;
	}

	public String getWxAppSecret() {
		return wxAppSecret;
	}

	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}

	public String getWxMchid() {
		return wxMchid;
	}

	public void setWxMchid(String wxMchid) {
		this.wxMchid = wxMchid;
	}

	public String getWxAppKey() {
		return wxAppKey;
	}

	public void setWxAppKey(String wxAppKey) {
		this.wxAppKey = wxAppKey;
	}

	public String getMerchantAccessToken() {
		return merchantAccessToken;
	}

	public void setMerchantAccessToken(String merchantAccessToken) {
		this.merchantAccessToken = merchantAccessToken;
	}
	
	public Long getMerchantAccessTokenExpiry() {
		return merchantAccessTokenExpiry;
	}

	public void setMerchantAccessTokenExpiry(Long merchantAccessTokenExpiry) {
		this.merchantAccessTokenExpiry = merchantAccessTokenExpiry;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getLogUrl() {
		return logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}

	public Date getAgreementEndTime() {
		return agreementEndTime;
	}

	public void setAgreementEndTime(Date agreementEndTime) {
		this.agreementEndTime = agreementEndTime;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getWeixinName() {
		return weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getWelcomeType() {
		return welcomeType;
	}

	public void setWelcomeType(String welcomeType) {
		this.welcomeType = welcomeType;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getAgreementMediaId() {
		return agreementMediaId;
	}

	public void setAgreementMediaId(String agreementMediaId) {
		this.agreementMediaId = agreementMediaId;
	}

	public String getAgreementMediaIdImg() {
		return agreementMediaIdImg;
	}

	public void setAgreementMediaIdImg(String agreementMediaIdImg) {
		this.agreementMediaIdImg = agreementMediaIdImg;
	}

	public String getOperatorMediaId() {
		return operatorMediaId;
	}

	public void setOperatorMediaId(String operatorMediaId) {
		this.operatorMediaId = operatorMediaId;
	}

	public String getOperatorMediaIdImg() {
		return operatorMediaIdImg;
	}

	public void setOperatorMediaIdImg(String operatorMediaIdImg) {
		this.operatorMediaIdImg = operatorMediaIdImg;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getHymid() {
		return hymid;
	}

	public void setHymid(String hymid) {
		this.hymid = hymid;
	}
	
	
}
