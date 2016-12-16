package com.finance.communication.common;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.jgroups.util.UUID;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;

public class MqttUtil {

	public static Logger logger = Logger.getLogger(MqttUtil.class);
	/**
	 * 设置MQTT的接入点，请根据应用所在环境选择合适的region，不支持跨Region访问
	 */
	final static String broker = Constant.MQTT_BROKER;
	/**
	 * 设置阿里云的AccessKey，用于鉴权
	 */
	final static String acessKey = Constant.MQTT_ACESSKEY;
	/**
	 * 设置阿里云的SecretKey，用于鉴权
	 */
	final static String secretKey = Constant.MQTT_SECRETKEY;
	/**
	 * 发消息使用的一级Topic，需要先在MQ控制台里申请
	 */
	final static String topic = Constant.MQTT_TOPIC;
	/**
	 * MQTT的ClientID，一般由两部分组成，ProducerID@@@DeviceID 其中ProducerID在MQ控制台里申请
	 * DeviceID由应用方设置，可能是设备编号等，需要唯一，否则服务端拒绝重复的ClientID连接
	 */
	// final static String clientId ="PID_mqttdelay3@@@ClientID_0001";
	final static String producerId = Constant.MQTT_PRODUCERID;
	final static String consumId = Constant.MQTT_CONSUMID;

	/*
	 * public static void send(String scontent) { //
	 * 如果该设备需要接收点对点的推送，那么需要订阅二级Topic，Topic/p2p/，但凡以Topic/p2p/为前缀的，都认为是点对点推送。
	 * final String p2ptopic = topic + "/p2p/"; //
	 * 同时订阅两个Topic，一个是基于标准MQTT协议的发布订阅模式，一个是扩展的点对点推送模式。 // final String[]
	 * topicFilters=new String[]{topic+"/notice/",p2ptopic};
	 * 
	 * String sign; MemoryPersistence persistence = new MemoryPersistence(); try
	 * { final MqttClient sampleClient = new MqttClient(broker, producerId,
	 * persistence); final MqttConnectOptions connOpts = new
	 * MqttConnectOptions(); logger.debug("Connecting to broker: " + broker);
	 * sign = MacSignature.macSignature(producerId.split("@@@")[0], secretKey);
	 * connOpts.setUserName(acessKey); connOpts.setServerURIs(new String[] {
	 * broker }); connOpts.setPassword(sign.toCharArray());
	 * connOpts.setCleanSession(false); connOpts.setKeepAliveInterval(100);
	 * sampleClient.setCallback(new MqttCallback() { public void
	 * connectionLost(Throwable throwable) {
	 * logger.error("mqtt connection lost"); throwable.printStackTrace(); }
	 * 
	 * public void messageArrived(String topic, MqttMessage mqttMessage) throws
	 * Exception { logger.info("messageArrived:" + topic + "------" + new
	 * String(mqttMessage.getPayload())); }
	 * 
	 * public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	 * logger.info("deliveryComplete:" + iMqttDeliveryToken.getMessageId()); }
	 * }); sampleClient.connect(connOpts);
	 * 
	 * try { final MqttMessage message = new
	 * MqttMessage(scontent.getBytes("UTF-8")); message.setQos(1);
	 * logger.info("pushed at " + new Date() + " " + scontent); //
	 * 消息发送到某个主题Topic，所有订阅这个Topic的设备都能收到这个消息。遵循MQTT的发布订阅规范，Topic也可以是多级Topic。 //
	 * 除了点对点Topic/p2p/这个前缀的，作为点对点发送的特殊Topic，不遵循发布订阅模式。 //
	 * sampleClient.publish(topic+"/notice/", message); //
	 * 发送给指定设备，格式为Topic/p2p/targetClientId, //
	 * targetClientId的格式详见user.properties文件。 sampleClient.publish(p2ptopic
	 * +consumId+ "@@@" +producerId, message);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * } catch (Exception me) { me.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * public static void receive () {
	 * 
	 * 
	 * 
	 * //如果该设备需要接收点对点的推送，那么需要订阅二级Topic，Topic/p2p/，但凡以Topic/p2p/为前缀的，都认为是点对点推送。
	 * final String p2ptopic = topic+"/p2p/";
	 * //同时订阅两个Topic，一个是基于标准MQTT协议的发布订阅模式，一个是扩展的点对点推送模式。 final String[]
	 * topicFilters=new String[]{topic+"/notice/",p2ptopic}; String clientId =
	 * consumId+ "@@@"+producerId; String sign; MemoryPersistence persistence =
	 * new MemoryPersistence(); try { final MqttClient sampleClient = new
	 * MqttClient(broker, clientId, persistence); final MqttConnectOptions
	 * connOpts = new MqttConnectOptions();
	 * System.out.println("Connecting to broker: " + broker); sign =
	 * MacSignature.macSignature(consumId, secretKey);
	 * connOpts.setUserName(acessKey); connOpts.setServerURIs(new String[] {
	 * broker }); connOpts.setPassword(sign.toCharArray());
	 * connOpts.setCleanSession(false); connOpts.setKeepAliveInterval(100);
	 * sampleClient.setCallback(new MqttCallback() { public void
	 * connectionLost(Throwable throwable) {
	 * System.out.println("mqtt connection lost"); throwable.printStackTrace();
	 * } public void messageArrived(String topic, MqttMessage mqttMessage)
	 * throws Exception { System.out.println("messageArrived:" + topic +
	 * "------" + new String(mqttMessage.getPayload())); } public void
	 * deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
	 * System.out.println("deliveryComplete:" +
	 * iMqttDeliveryToken.getMessageId()); } }); sampleClient.connect(connOpts);
	 * sampleClient.subscribe(topicFilters);
	 * System.out.println("subscribe....success");
	 * Thread.sleep(Integer.MAX_VALUE); } catch (Exception me) {
	 * me.printStackTrace(); } }
	 */
	public static void main(String args[]) throws InterruptedException {
		/*
		 * send("hello this is test for "); for(int i=0;i<10;i++){
		 * send("hello this is test "+i); }
		 * 
		 * receive ();
		 */
		// receive ();

		// sendMsgOneWay("this is test","test");
		// sendMsgOneWay("this is test","test");
		// Thread.sleep(10000);
		// getmsg();

	}

	/**
	 * tcp 单向发送
	 * 
	 * @param content
	 * @param tag
	 */
	public static void sendMsgOneWay(String content, String tag) {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.AccessKey, Constant.MQTT_ACESSKEY);// AccessKey
																			// 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.SecretKey, Constant.MQTT_SECRETKEY);// SecretKey
																			// 阿里云身份验证，在阿里云服务器管理控制台创建
		properties.put(PropertyKeyConst.ProducerId, Constant.MQTT_PRODUCERID);// 您在控制台创建的Producer
																				// ID
		properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");// 设置发送超时时间，单位毫秒
		properties.put(PropertyKeyConst.ConsumerId, Constant.MQTT_CONSUMID);

		/*
		 * properties.put(PropertyKeyConst.ONSAddr,
		 * "tcp://beijing-mqtt.ons.aliyun.com:1883");
		 */// 此处以公有云生产环境为例
		Producer producer = ONSFactory.createProducer(properties);
		// 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
		producer.start();
		// 发送消息

		Message msg = new Message(
		// Message Topic
				topic,
				// Message Tag,
				// 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在MQ服务器过滤
				tag,
				// Message Body
				// 任何二进制形式的数据，MQ不做任何干预，需要Producer与Consumer协商好一致的序列化和反序列化方式
				content.getBytes());
		// 设置代表消息的业务关键属性，请尽可能全局唯一。
		// 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发。
		// 注意：不设置也不会影响消息正常收发
		msg.setKey(UUID.randomUUID().toString());
		// oneway发送消息，只要不抛异常就是成功
		producer.sendOneway(msg);

		// 在应用退出前，销毁Producer对象
		// 注意：如果不销毁也没有问题
//		producer.shutdown();
	}

	/*
	 * public static void getmsg(){ Properties properties = new Properties();
	 * properties.put(PropertyKeyConst.ConsumerId, Constant.MQTT_CONSUMID);
	 * properties.put(PropertyKeyConst.AccessKey, Constant.MQTT_ACESSKEY);
	 * properties.put(PropertyKeyConst.SecretKey, Constant.MQTT_SECRETKEY);
	 * Consumer consumer = ONSFactory.createConsumer(properties);
	 * consumer.subscribe(topic, "*", new MessageListener() { public Action
	 * consume(Message message, ConsumeContext context) {
	 * System.out.println("Receive: " + message);
	 * 
	 * return Action.CommitMessage; } }); consumer.start();
	 * System.out.println("Consumer Started"); }
	 */
}
