package com.finance.communication.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.scheduling.annotation.Async;

/**
 * 第三方授权 accessToken 获取工具异常
 * 
 * @author Yangyang.zhang
 *
 */
public class AuthTicketTokenUtil {
	public static String getToken(String msg, String url) {

		try {

			// 使用安全的https 协议
			CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
			HttpPost post = new HttpPost(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			StringEntity entity = new StringEntity(msg, "utf-8");// 防止中文乱码
			post.setEntity(entity);// 设置消息实体
			String response = new String(httpclient.execute(post,
					responseHandler).toString());// 执行请求获取微信服务器返回的消息
			System.out.println(response);
			//JSONObject result = JSON.parseObject(response);

			return response;

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	public static String postMsg(String msg, String url) {

		try {

			// 使用安全的https 协议
			CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
			
			HttpPost post = new HttpPost(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			StringEntity entity = new StringEntity(msg, "utf-8");// 防止中文乱码
			
			post.setEntity(entity);// 设置消息实体
			String response = new String(httpclient .execute(post, responseHandler).toString() .getBytes("ISO_8859_1"), "utf-8");// 执行请求获取微信服务器返回的消息
			//System.out.println(response);
			 

			return response;

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	public static String postMsg2(String msg, String url) {

        try {

            // 使用安全的https 协议
            CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
            
            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(msg, "utf-8");// 防止中文乱码
            
            post.setEntity(entity);// 设置消息实体
			String response = new String(httpclient
					.execute(post, responseHandler).toString()
					.getBytes("iso8859-1"), "utf-8");// 执行请求获取微信服务器返回的消息
            //System.out.println(response);
             

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void postForm() throws IOException{
	    CloseableHttpClient httpClient = HttpClients.createDefault();
	    HttpPost post = new HttpPost("http://localhost:8080/portal-web/stuff/init");
	    RequestConfig config = RequestConfig.custom()
	      .setConnectionRequestTimeout(10000).setConnectTimeout(10000)
	      .setSocketTimeout(10000).build();
	    CloseableHttpResponse response = null;
	    try {
	     List formparams = new ArrayList();
	     formparams.add(new BasicNameValuePair("type", "xml"));
	     formparams.add(new BasicNameValuePair("mid", "10010000000000"));
	     post.setEntity(new UrlEncodedFormEntity(formparams, "UTF-8"));
	     post.setHeader("Accept", "application/json");//可以返回json
	     post.setConfig(config);
	     response = httpClient.execute(post);
	     HttpEntity entity = response.getEntity();
	     String content = EntityUtils.toString(entity);
	     System.out.println("content:" + content);
	     EntityUtils.consume(entity);
	    } catch (ClientProtocolException e) {
	     e.printStackTrace();
	    } catch (IOException e) {
	     e.printStackTrace();
	    } finally {
	     try {
	      response.close();
	     } catch (IOException e) {
	      e.printStackTrace();
	     }
	    }
	   }

	/**
	 * 异步调用接口（发送邮件）
	 * 
	 * @author bin.cheng
	 * @param msg
	 * @param url
	 * @return
	 */
	@Async
	public static String sendMail(String msg, String url) {
	        try {
	            // 使用安全的https 协议
	            CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
	            
	            HttpPost post = new HttpPost(url);
	            post.setHeader("Content-Type", "application/json");
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            StringEntity entity = new StringEntity(msg, "utf-8");// 防止中文乱码
	            
	            post.setEntity(entity);// 设置消息实体
	            String response = new String(httpclient .execute(post, responseHandler).toString() .getBytes("utf-8"), "utf-8");// 执行请求获取微信服务器返回的消息
	            //System.out.println(response);
	             

	            return response;

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "error";
	        }
	    }
}
