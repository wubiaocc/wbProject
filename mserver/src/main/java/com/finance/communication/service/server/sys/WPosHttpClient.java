package com.finance.communication.service.server.sys;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

@Component
@Transactional
public class WPosHttpClient {
	private final Log log = LogFactory.getLog(getClass());
	private static final int DEFAULT_TIMEOUT = 10000;
	private static final String WPCharset = "UTF-8";
	private static final String JsonContentType = "application/json";
	private static String url = "";

	@PostConstruct
	public void initialize() {
		// url = messageSource.getMessage("pos.service.url", null,
		// Locale.ENGLISH);
		// log.debug("url ="+url);
	}

	public String post(String json) {
		try {
			return post(url, json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String post(String url, String json) throws Exception {
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(DEFAULT_TIMEOUT)
				.setConnectTimeout(DEFAULT_TIMEOUT)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(url);
		/* json.put("signature", ""); */

		CloseableHttpResponse response = null;
		try {
			StringEntity postEntity = new StringEntity(json,
					ContentType.create(JsonContentType, WPCharset));
			httpPost.setEntity(postEntity);
			response = httpClient.execute(httpPost);
			log.debug("StatusCode:" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("Access failed");
			}
			byte[] result = EntityUtils.toByteArray(response.getEntity());
			System.out.print(response.getEntity().getContent());
			return parseResult(result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源  
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return json;
	}

//	public JSONObject get(String url) throws Exception {
//		
//		RequestConfig requestConfig = RequestConfig.custom()
//				.setSocketTimeout(DEFAULT_TIMEOUT)
//				.setConnectTimeout(DEFAULT_TIMEOUT)
//				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
//		CloseableHttpClient httpClient = HttpClients.custom()
//				.setDefaultRequestConfig(requestConfig).build();
//		HttpGet httpGet = new HttpGet(url);
//		JSONObject json =new JSONObject();
//		HttpResponse response = null;
//		try {
//			response = httpClient.execute(httpGet);
//			log.debug("StatusCode:" + response.getStatusLine().getStatusCode());
//			if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
//				throw new Exception("Access failed");
//			}
//			byte[] result = EntityUtils.toByteArray(response.getEntity());
//			System.out.print(response.getEntity().getContent());
//			return parseResult(result);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			// 关闭连接,释放资源  
//			try {
//				httpClient.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return json;
//	}

	private String parseResult(byte[] response) throws Exception {
//		JSONObject json = null;
		if ((response == null) || (response.length == 0)) {
			throw new Exception("无响应报文");
		}
		String content = toString(response, WPCharset);
		log.debug("response json:" + content);
//		json = JSONObject.parseObject(content);
//		return json;
		return content;
	}

	private String toString(byte[] content, String charsetName)
			throws Exception {
		try {
			return new String(content, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Unsupported encoding");
		}
	}

}
