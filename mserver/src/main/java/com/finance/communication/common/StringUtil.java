package com.finance.communication.common;
import java.security.MessageDigest;

public class StringUtil {

    public static String makeMD5(String s) {
        try {
            byte[] bs = s.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest(bs);
            return byte2String(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byte2String(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bs.length; i++) {
            sb.append(String.format("%02X", bs[i]));
        }
        return sb.toString();
    }

}
