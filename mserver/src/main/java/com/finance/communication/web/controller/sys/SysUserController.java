package com.finance.communication.web.controller.sys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finance.communication.common.Function;
import com.finance.communication.common.RespMessage;
import com.finance.communication.common.StringUtil;
import com.finance.communication.entity.Role;
import com.finance.communication.entity.SysUser;
import com.finance.communication.service.server.realm.ShiroDbRealm.ShiroUser;
import com.finance.communication.service.server.sys.ResourceManager;
import com.finance.communication.service.server.sys.RoleManager;
import com.finance.communication.service.server.sys.UserManager;
import com.finance.communication.web.controller.editor.RoleEditor;
import com.wizarpos.wx.core.orm.Page;

/**
 * 用户管理
 * 
 * @author LiXiaoPeng
 *
 *         2015年4月23日 下午2:23:05
 */
@Controller
@RequestMapping(value = "/mUser")
public class SysUserController {
	private static final Logger logger = Logger.getLogger(SysUserController.class);

	@Autowired
	private UserManager userManager;

	@Autowired
	private RoleManager roleManager;
	@Autowired
	private ResourceManager resourceManager;

	
	// 每页显示的条数
	private static final Integer PAGE_SIZE = 10;
	private Page<SysUser> page = new Page<SysUser>(10);

	/**
	 * 用户登录
	 * 
	 * @param name
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage login(@RequestParam String name, @RequestParam String password, HttpServletRequest request) {
		logger.debug("login params : name = " + name + " , password = " + Function.makeMD5(password));
		SysUser mUserBean = new SysUser();
		mUserBean.setName(name);
		mUserBean.setPassword(new SimpleHash("MD5", password).toHex());
		RespMessage respMessage = new RespMessage();
		try {
			SysUser mUser = userManager.login(mUserBean);
			if (mUser != null) {
				respMessage.setCode(1);
				return respMessage;
			} else {
				respMessage.setMessage("用户名或者密码错误");
				return respMessage;
			}
		} catch (Exception e) {
			logger.error("登录出错，出错原因：" + e.getMessage());
			respMessage.setMessage("登录出现异常,请联系管理员!");
			return respMessage;
		}

	}

	/**
	 * 代理商门户首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// String loginUser = shiroUser.loginName.split("@")[0];
		/*
		 * String roleids = shiroUser.getRoleIds(); if (roleids != null &&
		 * !roleids.trim().isEmpty()) { Long roleid =
		 * Long.valueOf(roleids.split(",")[0]); Role role =
		 * roleManager.getRoleById(roleid); if (role != null) { Set<Resource>
		 * roleRights = role.getResources(); // 检查代理商默认角色权限 if
		 * ("admin".equals(loginUser)) { if (roleRights.size() == 0) { //
		 * 为默认管理员角色授权 resourceManager.insertResources(roleid);
		 * model.addAttribute("reloadFlag", "reloadFlag"); } else { //
		 * 检查是否有新增的菜单 boolean hasAddResource =
		 * resourceManager.hasAddSource(roleid); if (hasAddResource) { // 删除已有权限
		 * roleManager.deleteAuthByRoleId(roleid); // 为默认管理员角色授权
		 * resourceManager.insertResources(roleid);
		 * model.addAttribute("reloadFlag", "reloadFlag"); } } } else if
		 * (roleRights.size() == 0) { model.addAttribute("setRole", "setRole");
		 * } if (!roleRights.isEmpty()) { // 角色已有资源 Map<String, Resource>
		 * roleRightsMap = new HashMap<String, Resource>(); for (Resource right
		 * : roleRights) { roleRightsMap.put(right.getId(), right); } //
		 * 根资源开始的所有资源 List<Resource> menuList =
		 * resourceManager.getResourceListByParentId(0l); //
		 * 根据角色权限处理菜单权限状态(递归处理) menuList = resourceManager.readMenu(menuList,
		 * roleRightsMap); menuList = menuList.get(0).getChildren(); //
		 * 更据代理商等级和是否开通代收付过滤资源 menuList =
		 * resourceManager.removeResource(menuList); for (Resource resource :
		 * menuList) { if (resource.getChildren().size() > 0) {
		 * Collections.sort(resource.getChildren(), new Comparator<Resource>() {
		 * public int compare(Resource arg0, Resource arg1) { return
		 * arg0.getOrderid().compareTo(arg1.getOrderid()); } }); } }
		 * Collections.sort(menuList, new Comparator<Resource>() { public int
		 * compare(Resource arg0, Resource arg1) { return
		 * arg0.getOrderid().compareTo(arg1.getOrderid()); } });
		 * model.addAttribute("menuList", menuList); }
		 * 
		 * } }
		 */
		userManager.searchMUser(page, 1, 10);
		
//		ListOperations<String, SysUser> listOperations=redisTemplate.opsForList();
//		listOperations.leftPushAll("users", page.getResult());
//		
//		List<SysUser> sysUsers=listOperations.range("users", 1, 6);
		
		
		
//	System.out.println(sysUsers);
		
		return "/index/main";
	}

	/**
	 * 退出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("log out");
		HttpSession session = request.getSession();
		session.removeAttribute("merchant");
		session.invalidate();
		session = null;
		try {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} catch (IOException e) {
			logger.error("退出出现异常，异常原因：" + e.getMessage());
		}
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword() {
		return "mUser/changePassword";
	}

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword1(Model model, @RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String loginName = shiroUser.getLoginName();
		RespMessage respMessage = new RespMessage();
		String returnValue = userManager.changePassword(loginName, oldPassword, newPassword);
		if ("0".equals(returnValue)) {
			respMessage.setCode(0);
			respMessage.setMessage("<label style='color: green;'><h3>修改密码成功!</h3></label>");
		} else if ("1".equals(returnValue)) {
			respMessage.setCode(1);
			respMessage.setMessage("<label style='color: red;'><h3>用户不存在!</h3></label>");
		} else if ("2".equals(returnValue)) {
			respMessage.setCode(2);
			respMessage.setMessage("<label style='color: red;'><h3>原始密码不正确!</h3></label>");
		}
		model.addAttribute("respMessage", respMessage);
		return "result";
	}

	/**
	 * 用户列表
	 * 
	 * @param pageNo
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String findMUserList(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest request,
			Model model) {
		// List<PropertyFilter> filters = PropertyFilter
		// .buildFromHttpRequest(request);
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// // 设置默认排序方式
		// if (!page.isOrderBySetted())
		// {
		// page.setOrderBy("createTime");
		// page.setOrder(Page.DESC);
		// }
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		// page = userManager.searchMUser(page, filters);
		// page.setTotalCount(page.getTotalCount() - 1);

		page = userManager.searchMUser(page, pageNo, pageSize);

		model.addAttribute("page", page);
		model.addAttribute("active", "system_userManagement");
		return "mUser/userList";
	}

	/**
	 * 新增用户页面跳转
	 * 
	 * @param model
	 * @param mid
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView createMerchantPage(Model model, String mid) {
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// 取角色
		List<Role> pageRole = new ArrayList<Role>();
		// pageRole = roleManager.getAllRoles();
		pageRole = roleManager.getAgentRoles();
		model.addAttribute("roleList", pageRole);
		model.addAttribute("action", "save");
		return new ModelAndView("/mUser/add");
	}

	/**
	 * 用户管理新增用户保存--YangLiuqing
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(SysUser newMuser, Model model) {
		newMuser.setCreateTime(new Date());
		newMuser.setUpdateTime(new Date());
		newMuser.setPassword(StringUtil.makeMD5(newMuser.getPassword()));
		newMuser.setEnable("0");

		RespMessage resp = new RespMessage();
		try {
			userManager.saveMUser(newMuser);
			resp.setCode(0);
			resp.setMessage("<label style='color: green;'><h3>保存成功!</h3></label>");
		} catch (Exception e) {
			logger.error("保存用户异常" + e);
			resp.setCode(1);
			resp.setMessage("<label style='color: red;'><h3>操作失败！</h3></label>");
		}

		model.addAttribute("respMessage", resp);

		return "/result";
	}

	/**
	 * 检查该用户名是否存在---YangLiuqing
	 */
	@RequestMapping(value = "/checkLoginName", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage checkLoginName(String loginName) {
		RespMessage resp = new RespMessage();
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		Boolean flag = userManager.getName(loginName);// 检查该用户名是否存在
		if (flag) {
			resp.setCode(1);// 该用户名(登录名)已存在
			resp.setMessage("重复");
			return resp;
		} else {
			resp.setCode(0);// 可以添加此用户
			resp.setMessage("没有重复");
			return resp;
		}

	}

	/**
	 * 删除用户---YangLiuqing
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RespMessage deleteMUser(String id, Model model) {
		RespMessage resp = new RespMessage();
		Subject currentUser = SecurityUtils.getSubject();
		PrincipalCollection principals = currentUser.getPrincipals();
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

		if (shiroUser.id.equals(id)) {
			resp.setCode(2);
			resp.setMessage("不能删除自己！");
			return resp;
		}
		try {
			userManager.deleteMUser(id);
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
	 * 用户详情--YangLiuqing
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mUserdetail/{id}")
	public String detailTicketDef(@PathVariable("id") String id, Model model) {
		SysUser muser = userManager.findById(id);
		model.addAttribute("mUser", muser);
		// model.addAttribute("active", "system_userManagement");
		return "mUser/detail";
	}

	/**
	 * 修改用户页面跳转---YangLiuqing
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(String id, Model model) {
		// ShiroUser shiroUser = (ShiroUser)
		// SecurityUtils.getSubject().getPrincipal();
		// 取角色
		List<Role> pageRole = new ArrayList<Role>();
		pageRole = roleManager.getAgentRoles();
		model.addAttribute("roleList", pageRole);

		// 用户信息
		SysUser muser = userManager.getMUser(id);
		model.addAttribute("mUser", muser);

		return "mUser/edit";
	}

	/**
	 * 修改用户成功后保存---YangLiuqing
	 * 
	 * @param role
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateMUser(@ModelAttribute("preloadUser") SysUser sysUser, Model model) {
		RespMessage resp = new RespMessage();
		try {
			userManager.saveMUser(sysUser);
			resp.setCode(0);
			resp.setMessage("<label style='color: green;'><h3>更新用户成功!</h3></label>");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setCode(1);
			resp.setMessage("<label style='color: red;'><h3>操作失败！</h3></label>");
		}

		model.addAttribute("respMessage", resp);
		return "/result";
	}

	@ModelAttribute("preloadUser")
	public SysUser getUser(@RequestParam(value = "id", required = false) String id) {
		if (id != null) {
			return userManager.findById(id);
		}
		return null;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) throws ServletException {

		// @see
		binder.registerCustomEditor(Role.class, new RoleEditor(roleManager));

	}

}
