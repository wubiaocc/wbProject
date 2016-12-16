package com.finance.communication.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.MerchantDef;
import com.finance.communication.entity.dto.MerchantDefData;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

@Component
public class MerchantDefDao extends HibernateDao<MerchantDef, String> {
	
	/**
	 * 根据`mgt_loct`='1616' and city_loct='6666' 查询 最大商户号
	 * 2016年11月17日
	 * @return
	 */
	public String getByLoct(){
		String maxMid = "";
		String sql = "select max(mid) from sys_merchant_def where mgt_loct='1616' and city_loct='6666' ";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createSQLQuery(sql);
		List<String> list = query.list();
		if(!list.isEmpty() && list.size() > 0){
			maxMid = list.get(0);
		}
		return maxMid;
	}
	
	/**
	 * 查询新生成的mid list
	 * 2016年11月17日
	 * @param maxMid
	 * @return
	 */
	public List<MerchantDefData> getByMaxMid(String maxMid){
		String sql = "";
		if(StringUtils.isEmpty(maxMid)){
			sql = "select mid as mid, merchant_terminal_no as merchantTerminalNo, merchant_id as merchantId, mgt_loct as mgtLoct, city_loct as cityLoct "
					+ " from sys_merchant_def where mgt_loct = '1616' and city_loct = '6666' ";
		}else{
			sql = "select mid as mid, merchant_terminal_no as merchantTerminalNo, merchant_id as merchantId, mgt_loct as mgtLoct, city_loct as cityLoct "
					+ " from sys_merchant_def where mgt_loct = '1616' and city_loct = '6666' and mid > ?";
		}
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createSQLQuery(sql);
		if(StringUtils.isNotEmpty(maxMid)){
			query.setString(0, maxMid);
		}
		query.setResultTransformer(Transformers.aliasToBean(MerchantDefData.class));
		List<MerchantDefData> list = query.list();
		if(!list.isEmpty()){
			return list;
		}else{
			return null;
		}
	}
}
