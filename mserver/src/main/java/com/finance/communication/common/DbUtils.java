package com.finance.communication.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理sql，获取count
 * 
 * @author lixiaopeng
 * @date 2012-9-19
 * 
 */
public class DbUtils
{
	public static String prepareCountHql(String orgHql)
	{
		String countHql = "select count(*) "
				+ removeSelect(removeOrders(orgHql));
		return countHql;
	}

	private static String removeSelect(String hql)
	{
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	private static String removeOrders(String hql)
	{
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
