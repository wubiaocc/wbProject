package com.finance.communication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

import com.finance.communication.common.DbUtils;
import com.finance.communication.common.Function;
import com.finance.communication.entity.Role;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

@Component
public class RoleDao extends HibernateDao<Role, String> {
	
	/**
	 * 新增
	 * 2016年10月17日
	 */
//	public void insertRole(Role role){
//		String sql = "insert into sys_m_role (id, enname, memo, name, orderid, status, create_time, update_time)"
//				+ " values (?,?,?,?,?,?,?,?)";
//		this.getSession().setFlushMode(FlushMode.MANUAL);
//		Query query = getSession().createSQLQuery(sql);
//		query.setString(0, Function.getUid());
//		query.setString(1, role.getEnname());
//		query.setString(2, role.getMemo());
//		query.setString(3, role.getName());
//		query.setInteger(4, role.getOrderid());
//		query.setString(5, role.getStatus());
//		query.setTimestamp(6, new Date());
//		query.setTimestamp(7, new Date());
//		
//		query.executeUpdate();
//	}
	
	public Boolean checkNameExist(String name, String id) {
		String hql = "from Role where name='" + name + "' ";
		if (StringUtils.isNotEmpty(id)) {
			hql += " and id<>'" + id + "'";
		}
		Query query = this.createQuery(hql);
		if (query.list().size() == 0 || query.list() == null) {
			return false;
		}
		return true;
	}

	public Boolean checkenNameExist(String enname) {
		String hql = "from Role where enname='" + enname;
		Query query = this.createQuery(hql);
		if (query.list().size() == 0) {
			return false;
		}
		return true;
	}

	public Boolean saveRole(String name, String enName, String memo) {
		String sql = "insert into  agent_sys_role(name,enname,memo,status) values(?,?,?,'0')";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, name);
		query.setString(1, enName);
		query.setString(2, memo);
		return query.executeUpdate() > 0;
	}

	public Boolean setResource(Long id, String resourceIds) {
		String sql = "update agent_sys_role set resource_ids='" + resourceIds + "' where id=" + id;
		Query query = this.getSession().createSQLQuery(sql);
		return query.executeUpdate() > 0;
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRoles() {
		String hql = "from Role";
		Query query = this.createQuery(hql);
		return query.list();
	}

	/**
	 * DRDS--角色管理--删除
	 * 
	 * @param id
	 * @param mid
	 */
	public void deleteById(String id) {
		String sql = "delete from sys_m_role where id=?";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, id);
		query.executeUpdate();
	}

	/**
	 * 代理商角色列表
	 * 
	 * @author bin.cheng
	 * @param page
	 * @param loginAgentId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<Role> agentRoleList(Page<Role> page, int pageNo, Integer pageSize) {
		// String ids = "";
		// if (StringUtils.isNotEmpty(agentId)) {
		// ids=getAgentIds(agentId);
		// }
		StringBuffer hql = new StringBuffer("from Role where status='0'");
		// if (StringUtils.isNotEmpty(agentId)) {
		// hql.append(" where id in("+agentId+")");
		// }
		Query query = this.createQuery(hql.toString());
		page.setPageNo(pageNo);
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		page.setResult(query.list());
		Query countQuery = this.getSession().createQuery(DbUtils.prepareCountHql(hql.toString()));
		Long totalCount = (Long) countQuery.uniqueResult();
		page.setTotalCount(totalCount.intValue());
		return page;
	}

	/**
	 * 根据代理商agentId获取自己以及所有下级代理商agentId
	 * 
	 * @author bin.cheng
	 * @param agentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getAgentIds(String agentId) {
		String sql = "select agent_id from sys_agent_merchant_def where agent_id =? or parent_agent_id in (select agent_id from sys_agent_merchant_def where parent_agent_id =? or agent_id =?)";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, agentId);
		query.setString(1, agentId);
		query.setString(2, agentId);
		List<String> str = query.list();
		String agentIds = "";
		if (str.size() > 0) {
			agentIds = str.toString().replace("[", "").replace("]", "");
		}
		return agentIds;
	}

	/**
	 * 获取代理商角色
	 * 
	 * @author bin.cheng
	 * @param ids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Role> getAgentRoles(String agentId) {
		StringBuffer hql = new StringBuffer("from Role where agentId ='" + agentId + "' and status='0'");
		Query query = this.createQuery(hql.toString());
		List<Role> list = query.list();
		return list;
	}

	/**
	 * 修改角色
	 * 
	 * @author bin.cheng
	 * @param role
	 */
	public void updateRole(Role role) {
		String sql = "update sys_m_role set name=?, enname=?, memo=? where id=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, role.getName());
		query.setString(1, role.getEnname());
		query.setString(2, role.getMemo());
		query.setString(3, role.getId());
		query.executeUpdate();
	}

	/**
	 * 默认角色resources_ids
	 * 
	 * @author bin.cheng
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getResourses() {
		String sql = "select id, parent_id from agent_sys_resource";
		Query query = this.getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		String resourcesIds = "1";
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[]) list.get(i);
			if (!"0".equals(obj[1].toString())) {
				if ("1".equals(obj[1].toString())) {
					resourcesIds += ",t" + obj[0].toString();
				} else {
					resourcesIds += "," + obj[1].toString() + "_" + obj[0].toString();
				}
			}
		}
		return resourcesIds;
	}

	/**
	 * 保存默认角色
	 * 
	 * @author bin.cheng
	 * @param recourseIds
	 * @param loginAgentId
	 * @return
	 */
	public String saveAdminRole(String recourseIds, String agentId) {
		Role role = new Role();
		// role.setId((long)roleId);
		role.setCreateTime(new Timestamp(new Date().getTime()));
		role.setEnname("admin");
		role.setName("系统管理员");
		role.setStatus("0");
		save(role);
		return null;
	}

	/**
	 * 删除角色已有资源
	 * 
	 * @param roleId
	 * @return
	 */
	public Integer deleteRoleAuthByRoleId(String roleId) {
		String sql = "delete from sys_role_auth where role_id = ? ";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, roleId);
		return query.executeUpdate();
	}

	/**
	 * 角色批量授权资源
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void insertRoleAuth(final String roleId, final String resourceId) {
		this.getSession().setFlushMode(FlushMode.MANUAL);
		this.getSession().doWork(new Work() {
			String sql = "insert into sys_role_auth(role_id, resource_id) value(?,?)";

			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
				String[] rs = resourceId.split(",");
				if (rs != null && rs.length > 0) {
					for (int i = 0; i < rs.length; i++) {
						ps.setString(1, roleId);
						ps.setLong(2, Long.valueOf(rs[i]));
						ps.addBatch();
						if ((i % 50 == 0) && (i != 0)) {
							ps.executeBatch();
							ps.clearBatch();
							connection.commit();
						}
					}
				}
				ps.executeBatch();
				ps.clearBatch();
				connection.commit();
			}
		});
	}
}
