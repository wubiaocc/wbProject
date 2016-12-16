package com.finance.communication.service.server.sys;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finance.communication.dao.RoleDao;
import com.finance.communication.entity.Role;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.PropertyFilter;

/**
 * 角色管理
 * 
 * @author test @
 *
 */
@Service
@Transactional
public class RoleManager
{
	@Autowired
	private RoleDao roleDao;

	public Role getRoleById(String id)
	{
		return roleDao.get(id);
	}

	public List<Role> getRolesByIds(String ids)
	{
		List<Role> roleList = new ArrayList<Role>();
		String[] roles = ids.split(",");
		for (int i = 0; i < roles.length; i++)
		{
			String id = roles[i];
			roleList.add(roleDao.get(id));
		}
		return roleList;
	}

	public Page<Role> searchForPage(final Page<Role> page,
			final List<PropertyFilter> filters)
	{
		return roleDao.findPage(page, filters);
	}

	public Boolean checkNameExist(final String name, String id)
	{
		return roleDao.checkNameExist(name, id);

	}

	public boolean checkenNameExist(String enname)
	{

		return roleDao.checkenNameExist(enname);
	}

	public void saveRole(Role role)
	{
		roleDao.save(role);
	}
    
	/**
     * 更新资源
     * @param id
     * @param resourceIds
     * @return
     */
	public Boolean setResource(Long id, String resourceIds) {
		return roleDao.setResource(id, resourceIds);
	}

	
	/**
	 * 查询当前商户的角色
	 * 
	 * @return
	 */
	public List<Role> searchRoles()
	{
		return roleDao.findRoles();

	}

	/**
	 * 获取所有角色
	 * 
	 * @return
	 */
	public List<Role> getAllRoles()
	{
		return roleDao.getAll();

	}

	public void delById(String id)
	{
		// roleDao.delete(id,mid);
		roleDao.deleteById(id);
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
	public Page<Role> agentRoleList(Page<Role> page, int pageNo, Integer pageSize) {
		return roleDao.agentRoleList(page,pageNo,pageSize);
	}

	/**
	 * 获取代理商角色
	 * 
	 * @author bin.cheng
	 * @param loginAgentId
	 * @return
	 */
	public List<Role> getAgentRoles() {
		List<Role> list = new ArrayList<Role>();
		list = roleDao.getAll();
		return list;
	}

	/**
	 * 修改角色
	 * 
	 * @author bin.cheng
	 * @param role
	 */
	public void updateRole(Role role) {
		roleDao.updateRole(role);
	}

	/**
	 * 创建默认角色（系统管理员）
	 * 
	 * @author bin.cheng
	 * @param loginAgentId 
	 * @return
	 */
	public String createAdminRole(String agentId) {
		String recourseIds = roleDao.getResourses();
		String roleId = roleDao.saveAdminRole(recourseIds,agentId);
		return roleId;
	}
	
	/**
	 * 更新角色资源
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void updateRoleAuth(String roleId, String resourceId) {
		roleDao.deleteRoleAuthByRoleId(roleId);
		roleDao.insertRoleAuth(roleId, resourceId);
	}

	/**
	 * 删除已有权限
	 * 
	 * @author bin.cheng
	 * @param roleid
	 */
	public void deleteAuthByRoleId(String roleid) {
		roleDao.deleteRoleAuthByRoleId(roleid);
	}
	
}
