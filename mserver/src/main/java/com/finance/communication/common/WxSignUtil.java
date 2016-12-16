package com.finance.communication.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class WxSignUtil
{
    private ArrayList<String> m_param_to_sign;
    
    public WxSignUtil()
    {
        m_param_to_sign = new ArrayList<String>();
    }

    public void AddData(String value)
    {
        m_param_to_sign.add(value);
    }

    public void AddData(Integer value)
    {
        m_param_to_sign.add(value.toString());
    }

    public String GetSignature()
    {
        Collections.sort(m_param_to_sign);
        StringBuilder string_to_sign = new StringBuilder();
        for (String str : m_param_to_sign)
        {
            string_to_sign.append(str);
        }
        System.out.println("string_to_sign:" + string_to_sign);
        try
        {
            MessageDigest hasher = MessageDigest.getInstance("SHA-1");
            byte[] digest = hasher.digest(string_to_sign.toString().getBytes());
            return ByteToHexString(digest);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String ByteToHexString(byte[] data)
    {
        StringBuilder str = new StringBuilder();
        for (byte b : data)
        {
            String hv = Integer.toHexString(b & 0xFF); 
            if( hv.length() < 2 )
                str.append("0");
            str.append(hv);
        }
        return str.toString();
    }

    public static String cardSign(String api_ticket, String card_id, String timestamp, String code, String openid) throws Exception{
        WxSignUtil signer = new WxSignUtil();
        signer.AddData(api_ticket);
        signer.AddData(card_id);
        signer.AddData(55312312);
        signer.AddData(timestamp);
        signer.AddData(code);
        signer.AddData(openid);
        return signer.GetSignature();
    }
    
 // 获取微信接口调用签名
    public static Map<String, String> commonSign(String jsapi_ticket, String url, long time) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNoncestr();
        String timestamp = String.valueOf(time);
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("noncestr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
 // 获取微信接口调用签名
    public static String signLocation(String accesstoken, String appId, String url, String timestamp, String nonce_str) {
        String string1;
        String addrSign = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "accesstoken=" + accesstoken +
        		  "&appId=" + appId +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            addrSign = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        return addrSign;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
	public static long createTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static String createNoncestr() {
		return UUID.randomUUID().toString();
	}

}
