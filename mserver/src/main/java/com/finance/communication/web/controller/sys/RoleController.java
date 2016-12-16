package com.finance.communication.web.controller.sys;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.finance.communication.common.Function;
import com.finance.communication.common.RespMessage;
import com.finance.communication.entity.Resource;
import com.finance.communication.entity.Role;
import com.finance.communication.entity.SysUser;
import com.finance.communication.service.server.realm.ShiroDbRealm.ShiroUser;
import com.finance.communication.service.server.sys.ResourceManager;
import com.finance.communication.service.server.sys.RoleManager;
import com.finance.communication.service.server.sys.UserManager;
import com.wizarpos.wx.core.orm.Page;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/memberRole")
public class RoleController {
	private Logger logger = Logger.getLogger(RoleController.class);
	@Autowired
	private RoleManager roleManager;
	@Autowired
	private ResourceManager resourceManager;
	@Autowired
	private UserManager userManager;
	
	// 每页显示的条数
	private static final Integer PAGE_SIZE = 10;
	private Page<Role> page = new Page<Role>(10);

	@RequestMapping("/rolelist")
	public String roleList(
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) String order,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			HttpServletRequest request, Model model) {
		//登录用户角色id
		if (!page.isOrderBySetted()) {
			page.setOrderBy("id");
			page.setOrder(Page.ASC);
		}
		if (pageNo<=0) {
			pageNo=1;
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		//page = roleManager.searchForPage(page, filters);
		page = roleManager.agentRoleList(page, pageNo, pageSize);

//		model.addAttribute("active", "system_roleManagement");
		model.addAttribute("page", page);
//		model.addAttribute("roleId", roleId);
		
		return "/role2/rolelist";
	}

	/**
	 * 新增角色页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String toAddPage(HttpServletRequest request, Model model) {
//		model.addAttribute("active", "member_RoleSet");
		return "role2/add";
	}

	/**
	 * 编辑角色页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateRole")
	public String updateRolePage(String id,HttpServletRequest request, Model model) {
		Role role = roleManager.getRoleById(id);
		model.addAttribute("role",role);
		return "role2/update";
	}
	
	/**
	 * 检查名称是否重复
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage checkName(String name,
			@RequestParam(value="id", required=false, defaultValue="") String id) {
		RespMessage resp = new RespMessage();
		if (roleManager.checkNameExist(name, id)) {
			resp.setCode(0);
			resp.setMessage("重复了");
			return resp;
		} else {
			resp.setCode(1);
			resp.setMessage("没有重复");
			return resp;
		}
	}

	@RequestMapping(value = "/checkenName", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage checkEnName(String enname) {
		RespMessage resp = new RespMessage();
		if (roleManager.checkenNameExist(enname)) {
			resp.setCode(0);
			resp.setMessage("重复了");
			return resp;
		} else {
			resp.setCode(1);
			resp.setMessage("没有重复");
			return resp;
		}
	}

	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public String saveRole(String name, String enName, String memo, Model model) {
		RespMessage resp = new RespMessage();
		final Role role = new Role();
		role.setName(name);
		role.setEnname("enName");
		role.setMemo(memo);
		role.setCreateTime(new Date());
		role.setStatus("0");
		role.setOrderid(1);
		try {
			roleManager.saveRole(role);
			resp.setCode(0);
			resp.setMessage("<label style='color: green;'><h3>角色【"+name+"】创建成功！</h3></label>");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(1);
			resp.setMessage("<label style='color: red;'><h3>操作失败！</h3></label>");
		}

		model.addAttribute("respMessage", resp);
		return "/result";
	}
	
	@RequestMapping(value = "/saveUpdateRole", method = RequestMethod.POST)
	public String updateRole(String id, String name, String memo, Model model) {
		RespMessage resp = new RespMessage();
		Role role = roleManager.getRoleById(id);
		role.setName(name);
		role.setMemo(StringUtils.isEmpty(memo)?null:memo);
		try {
			roleManager.updateRole(role);
			resp.setCode(0);
			resp.setMessage("<label style='color: green;'><h3>角色【"+name+"】修改成功！</h3></label>");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(1);
			resp.setMessage("<label style='color: red;'><h3>操作失败！</h3></label>");
		}

		model.addAttribute("respMessage", resp);
		return "/result";

	}

	/**
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/roleSet")
	public String toResource(final String id, Model model) {
        Role role=roleManager.getRoleById(id);
        // 已有资源
        Set<Resource> roleRights = role.getResources();
		// 角色已有资源
		Map<String, Resource> roleRightsMap = new HashMap<String, Resource>();
		for(Resource right : roleRights){
			roleRightsMap.put(right.getId(), right);
		}
		// 根资源开始的所以资源
		List<Resource> menuList=resourceManager.getResourceListByParentId(0l);
		// 根据角色权限处理菜单权限状态(递归处理)
		List<Resource> menu = resourceManager.readMenu(menuList, roleRightsMap);
		menu = menuList.get(0).getChildren();
		// 更据代理商等级和是否开通代收付过滤资源
		menu=resourceManager.removeResource(menu);
		for (Resource resource : menu) {
			if (resource.getChildren().size()>0) {
				Collections.sort(resource.getChildren(),new Comparator<Resource>(){  
					public int compare(Resource arg0, Resource arg1) {  
						return arg0.getOrderid().compareTo(arg1.getOrderid());  
					}  
				});
			}
		}
		Collections.sort(menu,new Comparator<Resource>(){  
            public int compare(Resource arg0, Resource arg1) {  
                return arg0.getOrderid().compareTo(arg1.getOrderid());  
            }  
        });
		JSONArray arr = JSONArray.fromObject(menu);
		String json = arr.toString();
		json = json.replaceAll("parentId", "pId").replaceAll("children", "nodes").replace("根", "全部");
		model.addAttribute("zTreeNodes", json);
		model.addAttribute("id", id);
		return "role2/detail";
	}

	/**
	 * 保存资源
	 */
	@RequestMapping(value = "/saveResource", method = RequestMethod.POST)
	public String saveResoursce(String resourceIds, String roleId, Model model) {
		RespMessage resp = new RespMessage();
		logger.debug("roleId = " + roleId + ", resourceIds = "+resourceIds);
		try {
			roleManager.updateRoleAuth(roleId, resourceIds);
			resp.setCode(0);
			resp.setMessage("<label style='color: green;'><h3>权限修改成功！</h3></label>");
		} catch (Exception e) {
			logger.error(e.getMessage());
			resp.setCode(1);
			resp.setMessage("<label style='color: red;'><h3>操作失败！</h3></label>");
		}
		model.addAttribute("respMessage", resp);
		return "/result";
	}
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) throws ServletException
//	{
//		// @see
//		binder.registerCustomEditor(Resource.class, new ResourceEditor(
//				resourceManager));
//	}

	/**
	 * 删除角色
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage delRole(String id) {
		RespMessage resp = new RespMessage();
		try {

			//authorizeManager.delAuth(id, mid);// DRDS--mid
			roleManager.delById(id);// DRDS--mid
			// 更新 持有用户role id 为“”;
			//userManager.updateUser(id);// DRDS--mid

			resp.setCode(0);
			resp.setMessage("操作成功！");
			return resp;
		} catch (Exception e) {
			logger.error(e);
			resp.setCode(1);
			resp.setMessage("操作失败！");
			return resp;
		}
	}
	
	/**
	 * 获取角色资源
	 * 
	 * @author bin.cheng
	 * @param id
	 * @return
	 */
//	@RequestMapping(value="/getResources")
//	@ResponseBody
//	public RespMessage getResources(String id) {
//		RespMessage resp = new RespMessage();
//		Role role = roleManager.getRoleById(Long.parseLong(id));
//		if (role!=null) {
//			if (StringUtils.isEmpty(role.getResourceIds())) {
//				resp.setObj(1);
//				return resp;
//			}
//		}
//		String[] resources = role.getResourceIds().split(",");
//		resp.setObj(resources);
//		return resp;
//	}
}
