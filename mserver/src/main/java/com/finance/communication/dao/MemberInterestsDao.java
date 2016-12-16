package com.finance.communication.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.MemberInterests;
import com.finance.communication.entity.ThirdTranLog;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

/**
 * 
 * 
 */
@Component
public class MemberInterestsDao extends HibernateDao<MemberInterests, Long> {
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<MemberInterests> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		Page<MemberInterests> page = new Page<MemberInterests>();
		String sql = "from MemberInterests where '1' = '1' ";

		
		sql += " order by createTime desc";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = this.getSession().createQuery(sql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<MemberInterests> list = query.list();
		
		String countSql = "select count(*) " + sql + " ";
		Query countQuery = this.getSession().createQuery(countSql);
	
		
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
