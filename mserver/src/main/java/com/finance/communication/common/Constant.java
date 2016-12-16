package com.finance.communication.common;


public class Constant {

	/**
	 * 邮件标题 Train慧商代理商进件申请 C2慧商代理商进件申请 91慧商代理商进件申请
	 */
	public static final String MAIL_TITLE = Tools.getEnv("MAIL_TITLE");
	public static final String MAIL_TITLE_O = Tools.getEnv("MAIL_TITLE_O");

	/**
	 * train环境-流水号生成地址
	 */
	public static final String MEMBER_SERVICE_URL = Tools.getEnv("BASE_URL") + "/member-server/WeixinServiceV1_1";
	public static final String SEND_MAIL_URL = Tools.getEnv("BASE_URL") + "/member-server/WeixinServiceV1_1";

	public static final String DEFAULT_VALUE = "0";

	public static final int DEFAULT_TERMINAL_STATE = 0;

	public static final String QQ_BILL_GET_URL = "http://mch.tenpay.com/cgi-bin/mchdown_real_new.cgi";
	/**
	 * QQ钱包费率
	 */
	public static final Float QQ_BILL_RATE = 0.006F;
	/**
	 * 微信支付费率
	 */
	public static final Float WX_BILL_RATE = 0.006F;
	/**
	 * 百度支付费率
	 */
	public static final Float BD_BILL_RATE = 0.006F;
	/**
	 * 支付宝支付费率
	 */
	public static final Float AL_BILL_RATE = 0.006F;
	/**
	 * qq支付码
	 */
	public static final String TRAN_CODE_QQ_PAY = "841";
	/**
	 * qq退款码
	 */
	public static final String TRAN_CODE_QQ_PAY_BACK = "842";
	/**
	 * 支付宝交易码
	 */
	public static final String TRAIN_CODE_ALIPAY = "813";
	/**
	 * 支付宝退款码
	 */
	public static final String TRAN_CODE_ALIPAY_BACK = "809";
	/**
	 * 微信支付交易码
	 */
	public static final String TRAN_CODE_WX_PAY = "814";
	/**
	 * 微信支付退款码
	 */
	public static final String TRAN_CODE_WX_PAY_BACK = "820";
	/**
	 * 百度支付交易码
	 */
	public static final String TRAN_CODE_BD_PAY = "850";
	/**
	 * 百度支付退款交易码
	 */
	public static final String TRAN_CODE_BD_PAY_BACK = "851";

	// 阿里oSS 参数配置默认
	public static final String IMAGE_SERVER_URL = "http://image.wizarpos.com/";
	public static final String OSS_ACCESS_ID = "vzapvhMeb7Rrlome";
	public static final String OSS_ACCESS_KEY = "vlhQKpSqtI92hQSe22BDEXaOeBSrBe";
	public static final String OSS_BUCKET_NAME = "wizarpos";
	
	public static final String MQTT_ACESSKEY="vzapvhMeb7Rrlome";
	public static final String MQTT_SECRETKEY="vlhQKpSqtI92hQSe22BDEXaOeBSrBe";
	
	/**
	 * TRAIN\CASHIER2\91HUISHANG   \XINLAN\ZYWT
	 */
	public static final String CURRENT_VERSION = Tools.getEnv("CURRENT_VERSION");
	
	/**
	 *  发消息使用的一级Topic，需要先在MQ控制台里申请
	 */
	public static final String MQTT_TOPIC="wizar_trade_msg";
	/**
	 * 生产者ID
	 */
    public static final String MQTT_PRODUCERID="PID_wizarpos_trade";
    /**
     * 消费者
     */
    public static final String MQTT_CONSUMID="CID_wizarpos_trade";
	/**
     * mqtt  设置MQTT的接入点，请根据应用所在环境选择合适的region，不支持跨Region访问
     */
    public static final String MQTT_BROKER="tcp://beijing-mqtt.ons.aliyun.com:1883";
}
