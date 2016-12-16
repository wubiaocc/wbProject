package com.finance.communication.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.communication.entity.Resource;
import com.finance.communication.service.server.sys.ResourceManager;

/**
 * 功能 Controller
 * 
 * @author lixiaopeng
 * @date 2013-8-8
 * 
 */
@Controller
@RequestMapping("/resource")
public class ResourceController
{
	@Autowired
	private ResourceManager resourceManager;

	/**
	 * 功能资源分页列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("getResourceList")
	public String getResourceList(
			@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
			Model model)
	{

//		Page<Resource> resources = resourceManager.getResourcePages(pageNumber,
//				pageSize);
//		model.addAttribute("resources", resources);

		return "/resource/resourceList";
	}

	/**
	 * 功能点状态修改 flag 正常0 暂停1 注销2
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "changeResourceStatusById/{flag}/{id}")
	public String changeResourceStatusById(@PathVariable String flag,
			@PathVariable Long id)
	{

//		resourceManager.updateResourceStatus(flag, id);
		return "redirect:/resource/getResourceList";
	}

	/**
	 * 用户新增表单
	 * 
	 * @return
	 */
	@RequestMapping("getResourceForm")
	public String getResourceForm(Model model)
	{
		return "/resource/resourceSaveForm";
	}

	/**
	 * 编辑表单
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "edit/{id}")
	public String editResourceById(@PathVariable String id, Model model)
	{

		Resource resource = resourceManager.getResourceById(id);
		model.addAttribute("resource", resource);
		return "/resource/resourceUpdateForm";
	}

	/**
	 * 保存
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String saveBranches(@ModelAttribute Resource resource, Model model)
	{
		// 状态默认为0，表示正常
//		resource.setResourceStatus("0");
//		resource.setCreateDate(new Date());
//		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject()
//				.getPrincipal();
		// 设定保存人员姓名
//		resource.setCreateUser(shiroUser.getUserName());
//		resourceService.save(resource);
		return "redirect:/resource/getResourceList";

	}

	/**
	 * 更新
	 * 
	 * @return
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateResource(
			@ModelAttribute("preloadResource") Resource branches)
	{
//		resourceManager.save(branches);
		return "redirect:/resource/getResourceList";
	}

	@ModelAttribute("preloadResource")
	public Resource getResource(
			@RequestParam(value = "id", required = false) String id)
	{
		if (id != null)
		{
			return resourceManager.getResourceById(id);
		}
		return null;
	}

}
