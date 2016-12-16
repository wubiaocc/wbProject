package com.finance.communication.common;
//package com.wizarpos.agent.portal.common;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.http.client.ResponseHandler;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.BasicResponseHandler;
//import org.apache.http.impl.client.CloseableHttpClient;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.wizarpos.portal.entity.WechartUserInfo;
//import com.wizarpos.portal.member.service.WechartUserInfoManager;
//
//
//public class GetUserFocusListUtil {
//	public static FocusResult getOpentIds(String token,String nextOpenId) {
//		List<String> list=new ArrayList<String>();
//		FocusResult results=new FocusResult();
//		try {
//			
//			 String url = 
//					 Constants.GET_OPENIDS.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID", nextOpenId); 
//			//使用安全的https 协议
//			CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
//			HttpGet get= new HttpGet(url);
//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			
//			String response = (String) httpclient
//					.execute(get, responseHandler);//执行请求获取微信服务器返回的消息
//			System.out.println(response);
//			JSONObject result = JSON.parseObject(response);
//			Integer total=result.getInteger("total");
//			String next=result.getString("next_openid");
//			JSONObject data=result.getJSONObject("data");
//		    JSONArray array=data.getJSONArray("openid");
//		   //Iterator<String> it=array.iterator();
////		   
////		   while(it.hasNext()){
////			   list.add(it.toString());
////		   }
//		    for(int i=0;i<array.size();i++){
//		    	list.add(array.getString(i));
//		    }
//		    results.setTotalCount(total);
//		    results.setOpenIds(list);
//		    results.setNextOpenId(next);
//			return results;
//
//		} catch (Exception e) {
//			results.setTotalCount(0);
//		    results.setOpenIds(list);
//		    results.setNextOpenId("");
//			return results;
//		}
//	}
//	
//	
//	public static WechartUserInfo getUserInfo(String token,String OpenId) {
//	
//		final WechartUserInfo userInfo=new WechartUserInfo();
//		try {
//			
//			 String url = 
//					 Constants.GET_USER_INFO.replace("ACCESS_TOKEN", token).replace("OPENID", OpenId); 
//			//使用安全的https 协议
//			CloseableHttpClient httpclient = SSLClient.createSSLClientDefault();//
//			HttpGet get= new HttpGet(url);
//			ResponseHandler<String> responseHandler = new BasicResponseHandler();
//			
//			String response =  new String(httpclient.execute(get, responseHandler).toString().getBytes("ISO_8859_1"),"utf-8");//执行请求获取微信服务器返回的消息
//			System.out.println(response);
//			JSONObject result = JSON.parseObject(response);
//			Integer subscribe=result.getInteger("subscribe");
//			String openid=result.getString("openid");
//			userInfo.setSubscribe(subscribe.toString());
//			userInfo.setOpenId(openid);
//			if(subscribe!=0){
//				String nickname=result.getString("nickname");
//				String sex=result.getString("sex");
//				String language=result.getString("language");
//				String city=result.getString("city");
//				String province=result.getString("province");
//				String country=result.getString("country");
//				String headimgurl=result.getString("headimgurl");
//				String subscribe_time=result.getString("subscribe_time");
//				String remark=result.getString("remark");
//				userInfo.setNickname(nickname);
//				userInfo.setSex(sex);
//				userInfo.setLanguage(language);
//				userInfo.setCity(city);
//				userInfo.setProvince(province);
//				userInfo.setCountry(country);
//				userInfo.setHeadimgurl(headimgurl);
//				userInfo.setSubscribeTime(subscribe_time);
//			}
//			
//			return userInfo;
//
//		} catch (Exception e) {
//			
//			return null;
//		}
//	}
//	
//	
//	
//	
//	public  static void main(String arg[]){
//	//getOpentIds("LT15lRRMhv88SY7dLtjDTrj18t5kshMp05czZJhtpxF6-vLXbhAaw_xTYq55IaS6lM6g2ep0zjrNVRdaaZlaXP4meDa9DLFCEH0eQJhUzws", "");
//		//System.out.println(list.getOpenIds());
//	WechartUserInfo	userInfo=getUserInfo("LT15lRRMhv88SY7dLtjDTrj18t5kshMp05czZJhtpxF6-vLXbhAaw_xTYq55IaS6lM6g2ep0zjrNVRdaaZlaXP4meDa9DLFCEH0eQJhUzws", "oAab_tgaL-ol7ZreVLFhuadNiGjg");
//	
//	System.out.println(JSON.toJSONString(userInfo));
//	} 
//	
//	
//}
