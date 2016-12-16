package com.finance.communication.service.server.sys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finance.communication.common.Function;
import com.finance.communication.dao.SysUserDao;
import com.finance.communication.entity.SysUser;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.PropertyFilter;

@Component
public class UserManager {
	@Autowired
	private SysUserDao mUserDao;

	/**
	 * 保存MUser实体
	 * 
	 * @param entity
	 * @throws FileNotFoundException
	 */
	@Transactional
	public void saveMUser(SysUser entity) throws FileNotFoundException {
		mUserDao.save(entity);

		FileInputStream fis=new FileInputStream(new File(""));
	}

	/**
	 * 获得Muser实体
	 * 
	 * @param id
	 * @return
	 */
	public SysUser getMUser(String id) {
		return mUserDao.get(id);
	}

	/**
	 * 使用属性过滤条件查询任务
	 */
	@Transactional(readOnly = true)
	public Page<SysUser> searchMUser(final Page<SysUser> page, final List<PropertyFilter> filters) {
		return mUserDao.findPage(page, filters);
	}

	/**
	 * 删除MUser 实体
	 * 
	 * @param id
	 */
	public void deleteMUser(String id) {
		mUserDao.delete(id);
	}

	public SysUser login(SysUser mUser) {

		List<SysUser> mUserList = mUserDao.login(mUser);
		if (mUserList.size() == 1) {

			return mUserList.get(0);
		} else {
			return null;
		}
	}

	public SysUser findUserByloginName(String loginName) {
		return mUserDao.findUniqueBy("loginName", loginName);
	}

	public String changePassword(String loginName, String oldPassword, String newPassword) {
		// MUser mUser = mUserDao.get(id);
		SysUser mUser = mUserDao.findUniqueBy("loginName", loginName);
		if (mUser == null) {
			// 用户不存在
			return "1";
		}
		if (!Function.makeMD5(oldPassword).equals(mUser.getPassword())) {

			// 原密码不正确
			return "2";
		}
		mUser.setPassword(Function.makeMD5(newPassword));
		mUserDao.save(mUser);

		return "0";
	}

	/**
	 * 获取用户表的mid--YangLiuqing
	 */
	public Boolean getName(String loginName) {
		return mUserDao.getName(loginName);
	}

	public SysUser getMuserByMid(String mid) {
		return mUserDao.getByMid(mid);
	}

	/**
	 * DRDS--角色管理--删除后更新用户表
	 * 
	 * @param roleId
	 * @param mid
	 * @return
	 */
	public Boolean updateUser(Long roleId) {
		return mUserDao.updateUser(roleId);
	}

	public Boolean update(SysUser mUser) {
		return mUserDao.update(mUser);

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
	public Page<SysUser> searchMUser(Page<SysUser> page, int pageNo, Integer pageSize) {
		return mUserDao.serchMUser(page, pageNo, pageSize);
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
		mUserDao.saveAuthCode(loginName, authCode, toMail, agentId);
	}

	/**
	 * 获取最后一次发送的验证码
	 * 
	 * @author bin.cheng
	 * @param loginName
	 * @param agentId
	 * @return
	 */
	public Map<String, Object> getLastAuthCode(String loginName, String agentId) {
		return mUserDao.getLastAuthCode(loginName, agentId);
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
		mUserDao.invalidateLastAuthCode(codeMailId, exutAuthCode, agentId);
	}

	/**
	 * 忘记密码step4-重置密码
	 * 
	 * @author bin.cheng
	 * @param loginName
	 * @param newPass
	 */
	public void resetPassword(String loginName, String newPass) {
		mUserDao.resetPassword(loginName, newPass);
	}

	/**
	 * @author bin.cheng
	 * @param id
	 * @return
	 */
	public SysUser findById(String id) {
		return mUserDao.findById(id);
	}
}
