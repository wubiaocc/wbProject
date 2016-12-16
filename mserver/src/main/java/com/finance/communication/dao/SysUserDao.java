package com.finance.communication.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Component;

import com.finance.communication.common.Function;
import com.finance.communication.entity.SysUser;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.hibernate.HibernateDao;

@Component
public class SysUserDao extends HibernateDao<SysUser, String> {
	public List<SysUser> login(SysUser mUser) {
		String hql = "from SysUser where username = ? and password = ?";

		return this.find(hql, mUser.getName(), mUser.getPassword());

	}

	/**
	 * 获取用户表的mid--YangLiuqing
	 * 
	 * @param mUser
	 * @return
	 */
	public Boolean getName(String loginName) {
		String hql = "from SysUser where loginName ='" + loginName + "'";
		Query query = this.getSession().createQuery(hql);
		if (query.list().size() == 0) {
			return false;
		}
		return true;
	}

	public SysUser getByMid(String mid) {
		String hql = "from SysUser where loginName=? and merchantDef.mid=?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, mid);
		query.setString(1, mid);
		return (SysUser) query.uniqueResult();
	}

	public Boolean updateUser(Long id) {
		String sql = "update sys_m_user set role_ids='' where role_ids=?";
		Query query = this.getSession().createSQLQuery(sql);
		this.getSession().setFlushMode(FlushMode.MANUAL);
		query.setLong(0, id);
		return query.executeUpdate() > 0;
	}

	/**
	 * DRDS--删除MUser 实体
	 * 
	 * @param id
	 * @param mid
	 */
	public void deleteMUser(String id, String mid) {
		String sql = "delete from  sys_m_user where mid='" + mid + "' and id='" + id + "'";
		this.getSession().setFlushMode(FlushMode.MANUAL);
		Query query = this.getSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public Boolean update(SysUser mUser) {
		String sql = "update sys_m_user set name=?,email=?  where id=?";
		Query query = this.getSession().createSQLQuery(sql);
		query.setString(0, mUser.getName());
		query.setString(1, mUser.getEmail());
		query.setString(2, mUser.getId());
		return query.executeUpdate() > 0;
	}

	/**
	 * 用户列表
	 * 
	 * @author bin.cheng
	 * @param page
	 * @param loginAgentId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<SysUser> serchMUser(Page<SysUser> page, int pageNo, Integer pageSize) {
		// String ids = "";
		// if (StringUtils.isNotEmpty(loginAgentId)) {
		// ids=getAgentIds(loginAgentId);
		// }
		String hql = "";
		hql = "from SysUser";
		Query query = this.getSession().createQuery(hql);
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNo - 1) * pageSize);
		List<SysUser> list = query.list();
		String countSql = "";
		countSql = "select count(*) from sys_m_user";
		Query q = this.getSession().createSQLQuery(countSql);
		BigInteger total = (BigInteger) q.uniqueResult();
		page.setTotalCount(total.longValue());
		page.setResult(list);
		page.pageNo(pageNo);
		page.pageSize(pageSize);
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
	 * 保存验证码
	 * 
	 * @author bin.cheng
	 * @param loginName
	 * @param authCode
	 * @param toMail
	 * @param agentId
	 */
	public void saveAuthCode(String loginName, String authCode, String toMail, String agentId) {
		String uid = Function.getUid();
		String sql = "insert into mrt_mail_log (id,mid,mail_type,mail,create_time,mail_agent_auth_code,mail_agent_login_name,merchant_type) values (?,?,?,?,?,?,?,?)";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setString(0, uid);
		query.setString(1, agentId);
		query.setString(2, "4");
		query.setString(3, toMail);
		query.setTimestamp(4, new Date());
		query.setString(5, authCode);
		query.setString(6, loginName);
		query.setString(7, "2");
		query.executeUpdate();
	}

	/**
	 * 获取最后一次发送的验证码
	 * 
	 * @author bin.cheng
	 * @param loginName
	 * @param agentId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getLastAuthCode(String loginName, String agentId) {
		String sql = "select id id, mail_agent_auth_code code from mrt_mail_log where mid='" + agentId
				+ "' and merchant_type='2' and mail_type='4' and mail_agent_login_name='" + loginName
				+ "' and create_time>=? order by create_time DESC";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		Date now = new Date();
		String nowStr = Function.formatDate(Function.addMinute(now, -60), "yyyy-MM-dd HH:mm:ss");
		query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		query.setString(0, nowStr);
		List<Map<String, Object>> list = query.list();
		Map<String, Object> map = new HashMap<String, Object>();
		if (list.size() != 0) {
			map = list.get(0);
		}
		return map;
	}

	/**
	 * 验证码输入错误，使验证码失效
	 * 
	 * @author bin.cheng
	 * @param codeMailId
	 * @param exutAuthCode
	 * @param agentId
	 */
	public void invalidateLastAuthCode(String codeMailId, String exutAuthCode, String agentId) {
		String sql = "update mrt_mail_log set mail_agent_auth_code=? where mid=? and id=?";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setString(0, exutAuthCode + "-f");
		query.setString(1, agentId);
		query.setString(2, codeMailId);
		query.executeUpdate();
	}

	/**
	 * 忘记密码step4-重置密码
	 * 
	 * @author bin.cheng
	 * @param loginName
	 * @param newPass
	 */
	public void resetPassword(String loginName, String newPass) {
		SysUser mUser = findUniqueBy("loginName", loginName);
		if (mUser != null) {
			mUser.setPassword(Function.makeMD5(newPass));
			save(mUser);
		}
	}

	/**
	 * @author bin.cheng
	 * @param id
	 * @return
	 */
	public SysUser findById(String id) {
		String hql = "from SysUser where id=?";
		Query query = this.getSession().createQuery(hql);
		query.setString(0, id);
		return (SysUser) query.uniqueResult();
	}
}
