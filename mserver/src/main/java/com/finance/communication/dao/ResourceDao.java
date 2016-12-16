package com.finance.communication.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Component;

import com.finance.communication.entity.Resource;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;
@Component
public class ResourceDao extends  HibernateDao<Resource, String>
{
	public Resource getResourceById(Long id){
		String hql="from Resource where id="+id;
		Query  query=this.getSession().createQuery(hql);
		return (Resource)query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<BigInteger> getSecondMenuIds(Long parentId) {
		String sql="select id  from  agent_sys_resource where parent_id= "+parentId;
		Query query=this.getSession().createSQLQuery(sql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Resource> getResourceByParentId(Long parentId) {
	      String hql="from Resource where parentId="+parentId;
	      Query query=this.createQuery(hql); 
		return query.list();
	}
	public String getResourceNameById(Long id) {
		String sql="select name from sys_resource where id= "+id;
		Query query=this.getSession().createSQLQuery(sql);
		return (String) query.uniqueResult();
	}

	public String getResourceEnameById(Long id) {
		String sql="select enname from sys_resource where id= "+id;
		Query query=this.getSession().createSQLQuery(sql);
		return (String) query.uniqueResult();
	}

	/**
	 * 为默认管理员角色授权
	 * 
	 * @author bin.cheng
	 * @param roleid
	 */
	@SuppressWarnings("unchecked")
	public void insertResources(Long roleid) {
		String sql = "select id from agent_sys_resource";
		Query query = this.getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		this.getSession().setFlushMode(FlushMode.MANUAL);
		for (int i = 0; i < list.size(); i++) {
			String insert = "insert agent_role_auth (role_id,resource_id) values(?,?)";
			SQLQuery insertQuery=this.getSession().createSQLQuery(insert);
			insertQuery.setInteger(0, Integer.valueOf(roleid.toString()));
			insertQuery.setInteger(1, Integer.valueOf(list.get(i).toString()));
			insertQuery.executeUpdate();
		}
	}

	/**
	 * 检查是否有新增的菜单
	 * 
	 * @author bin.cheng
	 * @param roleid 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean hasAddSource(Long roleid) {
		String sql = "select id from agent_sys_resource order by id asc";
		Query query = this.getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		String sql2 = "select resource_id from agent_role_auth where role_id=? order by resource_id asc";
		Query query2 = this.getSession().createSQLQuery(sql2);
		query2.setInteger(0, Integer.valueOf(roleid.toString()));
		List<Object> list2 = query2.list();
		String listStr = list.toString();
		String listStr2 = list2.toString();
		return !listStr.equals(listStr2);
	}
}
