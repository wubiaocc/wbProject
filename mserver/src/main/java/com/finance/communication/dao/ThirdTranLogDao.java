package com.finance.communication.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.ThirdTranLog;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

/**
 * 第三方交易查询dao
 * @author DaiRongliang
 * @2016年11月14日
 */
@Component
public class ThirdTranLogDao extends HibernateDao<ThirdTranLog, String> {
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<ThirdTranLog> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		Page<ThirdTranLog> page = new Page<ThirdTranLog>();
		String sql = "from ThirdTranLog where '1' = '1' ";
		String merchantName = "";//商户名字
		String merchantNo = "";//收单商户号
		String terminalNo = "";//终端号
		String billNo = "";//批次号
		String startTime = "";
		String endTime = "";
		if(searchParams.containsKey("LIKES_merchantName")){
			merchantName = ((String)searchParams.get("LIKES_merchantName")).trim();
			if(StringUtils.isNotEmpty(merchantName)){
				sql += " and merchantName like :merchant_name ";
			}
		}
		if(searchParams.containsKey("EQS_merchantNo")){
			merchantNo = ((String)searchParams.get("EQS_merchantNo")).trim();
			if(StringUtils.isNotEmpty(merchantNo)){
				sql += " and merchantNo = :merchant_no ";
			}
		}
		if(searchParams.containsKey("EQS_terminalNo")){
			terminalNo = ((String)searchParams.get("EQS_terminalNo")).trim();
			if(StringUtils.isNotEmpty(terminalNo)){
				sql += " and terminalNo = :pos_no ";
			}
		}
		if(searchParams.containsKey("EQS_billNo")){
			billNo = ((String)searchParams.get("EQS_billNo")).trim();
			if(StringUtils.isNotEmpty(billNo)){
				sql += " and billNo = :batch_no ";
			}
		}
		if(searchParams.containsKey("GED_tranTime")){
			startTime = (String) searchParams.get("GED_tranTime");
			if(StringUtils.isNotEmpty(startTime)){
				sql += " and tranTime >= :startTime";
			}
		}
		if(searchParams.containsKey("LED_tranTime")){
			endTime = (String) searchParams.get("LED_tranTime");
			if(StringUtils.isNotEmpty(endTime)){
				sql += " and tranTime <= :endTime";
			}
		}
		sql += " order by tranTime desc";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = this.getSession().createQuery(sql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		if(StringUtils.isNotEmpty(merchantName)){
			query.setParameter("merchant_name", "%" + merchantName + "%");
		}
		if(StringUtils.isNotEmpty(merchantNo)){
			query.setParameter("merchant_no", merchantNo);
		}
		if(StringUtils.isNotEmpty(terminalNo)){
			query.setParameter("pos_no", terminalNo);
		}
		if(StringUtils.isNotEmpty(billNo)){
			query.setParameter("batch_no", billNo);
		}
		if(StringUtils.isNotEmpty(startTime)){
			query.setString("startTime", startTime + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endTime)){
			query.setString("endTime", endTime + " 23:59:59");
		}
		List<ThirdTranLog> list = query.list();
		
		String countSql = "select count(*) " + sql + " ";
		Query countQuery = this.getSession().createQuery(countSql);
		if(StringUtils.isNotEmpty(merchantName)){
			countQuery.setParameter("merchant_name", merchantName);
		}
		if(StringUtils.isNotEmpty(merchantNo)){
			countQuery.setParameter("merchant_no", merchantNo);
		}
		if(StringUtils.isNotEmpty(terminalNo)){
			countQuery.setParameter("pos_no", terminalNo);
		}
		if(StringUtils.isNotEmpty(billNo)){
			countQuery.setParameter("batch_no", billNo);
		}
		if(StringUtils.isNotEmpty(startTime)){
			countQuery.setString("startTime", startTime + " 00:00:00");
		}
		if(StringUtils.isNotEmpty(endTime)){
			countQuery.setString("endTime", endTime + " 23:59:59");
		}
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResult(list);
		Object totalCount = countQuery.uniqueResult();
		page.setTotalCount(Long.parseLong(totalCount==null?"0":totalCount.toString()));
		
		return page;
	}
	
	/**
	 * 根据批次号删除
	 * 2016年11月16日
	 * @param fileName
	 */
	public void delByBillNo(String fileName){
		String sql = "delete from third_tran_log where bill_no = ?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createSQLQuery(sql);
		query.setString(0, fileName);
		query.executeUpdate();
	}
	
	//调用存储过程
	public void produre(){
		this.getSession().doReturningWork(new ReturningWork<List>() {
			@Override
			public List execute(Connection connection) throws SQLException {
				List list = new ArrayList();
				CallableStatement cStmt = connection.prepareCall("{CALL third_tran_log_process}");
				  cStmt.execute();
//				  ResultSet rs1 = cStmt.getResultSet();
//				  rs1.close();
				  cStmt.close();
				return list;
			}
		});
	}
	
	/**
	 * 根据批次号查询第三方交易流水表
	 * 2016年11月17日
	 * @param billNo
	 * @return
	 */
	public List<ThirdTranLog> getByBillNo(String billNo){
		String sql = " from ThirdTranLog where billNo = ?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = getSession().createQuery(sql);
		query.setString(0, billNo);
		List<ThirdTranLog> list = query.list();
		if(!list.isEmpty()){
			return list;
		}else{
			return null;
		}
	}
	
}
