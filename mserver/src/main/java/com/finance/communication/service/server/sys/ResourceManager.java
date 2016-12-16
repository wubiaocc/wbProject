package com.finance.communication.service.server.sys;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.communication.common.SecurityNullObjects;
import com.finance.communication.dao.ResourceDao;
import com.finance.communication.entity.Resource;
import com.finance.communication.entity.dto.Menu;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * 
 * @author zyy
 *
 */
@Service
public class ResourceManager {
	@Autowired
	private ResourceDao resourceDao;

	/**
	 * 更据资源串获取资源集合
	 * 
	 * @param Ids
	 * @return 资源集合
	 */
	public List<Resource> getResourceByIds(String Ids) {
		if (Ids == null || Ids.equals("")) {
			return null;
		}
		List<Resource> resourceList = new ArrayList<Resource>();
		String ids[] = Ids.split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			// System.out.println("resource_id"+id);
			resourceList.add(resourceDao.getResourceById(id));

		}
		return resourceList;
	}

	public Resource gerResourceById(String id) {
		return resourceDao.get(id);
	}

	/**
	 * 获取Menu
	 * 
	 * @param menus
	 * @return
	 */
	public Menu menuTreeGenerator(final List<Resource> menus) {
		final Resource rootMenu = getRootMenu(menus, 0L);
		Menu rootMenuVO = new Menu();
		if (rootMenu != null) {
			final List<Resource> firstMenus = getChildren(menus,
					rootMenu.getId());

			rootMenuVO = entityMappingValueObject(rootMenu,
					!firstMenus.isEmpty());
			final List<Menu> firstMenusVO = new ArrayList<Menu>();

			for (Resource resource : firstMenus) {

				List<Resource> children = getChildren(menus, resource.getId());
				Menu mVO = entityMappingValueObject(resource,
						!children.isEmpty());

				List<Menu> secondMenuVOList = new ArrayList<Menu>();
				for (Resource second : children) {
					Menu secondVO = entityMappingValueObject(second, false);

					secondMenuVOList.add(secondVO);
				}

				mVO.setChildren(secondMenuVOList);
				firstMenusVO.add(mVO);
			}

			rootMenuVO.setChildren(firstMenusVO);
		}

		return rootMenuVO;
	}

	/**
	 * 根据父节点生成相应的菜单
	 * 
	 * @param menus
	 * @param rootId
	 * @return
	 */
	public Menu menuTreeGenerator(final List<Resource> menus, Long rootId) {
		final Resource rootMenu = getRootMenu(menus, rootId);
		Menu rootMenuVO = new Menu();
		if (rootMenu != null) {
			final List<Resource> firstMenus = getChildren(menus,
					rootMenu.getId());

			rootMenuVO = entityMappingValueObject(rootMenu,
					!firstMenus.isEmpty());
			final List<Menu> firstMenusVO = new ArrayList<Menu>();

			for (Resource resource : firstMenus) {

				List<Resource> children = getChildren(menus, resource.getId());
				Menu mVO = entityMappingValueObject(resource,
						!children.isEmpty());

				List<Menu> secondMenuVOList = new ArrayList<Menu>();
				for (Resource second : children) {
					Menu secondVO = entityMappingValueObject(second, false);

					secondMenuVOList.add(secondVO);
				}

				mVO.setChildren(secondMenuVOList);
				firstMenusVO.add(mVO);
			}

			rootMenuVO.setChildren(firstMenusVO);
		}

		return rootMenuVO;
	}

	/**
	 * 根据树根获取资源
	 * 
	 * @param sourceList
	 * @return
	 */
	private Resource getRootMenu(final List<Resource> sourceList,
			final Long rootId) {
		// final Long rootId = Long.valueOf(0);
		final Collection<Resource> transform = Collections2.filter(sourceList,
				new Predicate<Resource>() {
					public boolean apply(Resource input) {
						return rootId.equals(input.getParentId());
					}
				});
		Resource root;
		if (transform.isEmpty()) {
			root = SecurityNullObjects.nullMenu();
		} else {
			root = (Resource) transform.toArray()[0];
		}

		return root;
	}

	private List<Resource> getChildren(final List<Resource> sourceList,
			final String parentId) {
		Collection<Resource> transform = Collections2.filter(

		sourceList, new Predicate<Resource>() {
			public boolean apply(Resource input) {
				return parentId.equals(input.getParentId());
			}
		});

		return Lists.newArrayList(transform);
	}

	private Menu entityMappingValueObject(Resource menu, boolean hasChild) {
		Menu mVO = new Menu();

		if (menu != null) {
			mVO.setId(menu.getId());
			mVO.setName(menu.getName());
			mVO.setEnname(menu.getEnname());
			mVO.setLink(menu.getLink());
			mVO.setParentId(menu.getParentId());
			mVO.setOrderid(menu.getOrderid());
			mVO.setIcon(menu.getIcon());
			mVO.setMemo(menu.getMemo());
		}

		return mVO;
	}

	/**
	 * 获取parentId级菜单的id集合
	 * 
	 * @return
	 */
	public List<BigInteger> getSecondMenuIds(Long parentId) {
		return resourceDao.getSecondMenuIds(parentId);
	}

	/**
	 * 获取资源集合 更据父节点id
	 * 
	 * @param parentId
	 *            父节点id
	 * @return
	 */
	public List<Resource> getResourceListByParentId(Long parentId) {
		return resourceDao.getResourceByParentId(parentId);
	}

	/**
	 * 获取资源名称
	 * 
	 * @param id
	 * @return
	 */
	public String getResourceNameById(Long id) {
		return resourceDao.getResourceNameById(id);
	}

	/**
	 * 获取资源英文名称
	 * 
	 * @param id
	 * @return
	 */
	public String getResourceEnmeById(Long id) {
		return resourceDao.getResourceEnameById(id);
	}

	/**
	 * 获取资源英文名称
	 * 
	 * @param id
	 * @return
	 */
	public Resource getResourceById(String id) {
		return resourceDao.get(id);
	}

	public List<Resource> getAllResource() {
		return resourceDao.getAll();
	}
	
	/** 
	 * 根据角色权限处理权限状态(递归处理)
	 * 
	 * @param menuList：传入的总菜单
	 * @param roleRights：已有权限
	 * @return
	 */
	public List<Resource> readMenu(List<Resource> allResourceList, Map<String, Resource> roleRightsMap){
		for(Resource res : allResourceList){
			if (roleRightsMap.containsKey(res.getId())) {
				res.setChecked(true);
			}
			this.readMenu(res.getChildren(), roleRightsMap); //继续排查其子菜单
		}
		
		return allResourceList;
	}

	/**
	 * 更据代理商等级级是否开通代收付过滤资源
	 * 
	 * @author bin.cheng
	 * @param menuList
	 * @param agentLevel
	 * @param replacePayment
	 * @return
	 */
	public List<Resource> removeResource(List<Resource> menuList) {
		List<Resource> newMenuList=new ArrayList<Resource>();
		for (int i = 0; i < menuList.size(); i++) {
			Resource res = menuList.get(i);
			newMenuList.add(res);
		}
		return newMenuList;
	}

	/**
	 * 为默认管理员角色授权
	 * 
	 * @author bin.cheng
	 * @param roleid
	 */
	public void insertResources(Long roleid) {
		resourceDao.insertResources(roleid);		
	}

	/**
	 * 检查是否有新增的菜单
	 * 
	 * @author bin.cheng
	 * @param roleid 
	 * @return
	 */
	public boolean hasAddSource(Long roleid) {
		return resourceDao.hasAddSource(roleid);
	}

}
