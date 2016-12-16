package com.finance.communication.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.MemberGrade;
import com.finance.communication.entity.MemberInterests;
import com.finance.communication.entity.ThirdTranLog;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

/**
 * 
 * 
 */
@Component
public class MemberGradeDao extends HibernateDao<MemberGrade, Long> {
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<MemberGrade> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		Page<MemberGrade> page = new Page<MemberGrade>();
		String sql = "from MemberGrade where '1' = '1' ";

		
		sql += " order by createTime desc";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = this.getSession().createQuery(sql);
		query.setFirstResult((pageNo - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		List<MemberGrade> list = query.list();
		
		String countSql = "select count(*) " + sql + " ";
		Query countQuery = this.getSession().createQuery(countSql);
	
		
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setResult(list);
		Object totalCount = countQuery.uniqueResult();
		page.setTotalCount(Long.parseLong(totalCount==null?"0":totalCount.toString()));
		
		return page;
	}
	
	
	
}
