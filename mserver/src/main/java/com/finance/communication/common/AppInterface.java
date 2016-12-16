package com.finance.communication.common;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
/**
 * 第三方授权 accessToken 获取工具异常
 * 
 * @author Yangyang.zhang
 *
 */ 
public class AppInterface {
	  public static String postMsg2(String msg, String url) {
	        try {
	            // 使用安全的https 协议
	            CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
	            
	            HttpPost post = new HttpPost(url);
	            post.setHeader("Content-Type", "application/json");
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            StringEntity entity = new StringEntity(msg, "utf-8");// 防止中文乱码
	            
	            post.setEntity(entity);// 设置消息实体
	            String response = new String(httpclient .execute(post, responseHandler).toString() .getBytes("ISO_8859_1"), "utf-8");// 执行请求获取微信服务器返回的消息
	            return response;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	    }
}
