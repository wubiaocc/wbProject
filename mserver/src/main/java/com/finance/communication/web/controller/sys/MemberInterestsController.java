package com.finance.communication.web.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.communication.entity.MemberInterests;
import com.finance.communication.service.server.sys.MemberInterestsManager;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.web.Servlets;

/**
 *  会员权益集维护
 */
@Controller
@RequestMapping(value = "/memberInterests")
public class MemberInterestsController {
	
	// 每页显示的条数
	private static final Integer PAGE_SIZE = 10;
	private Page<MemberInterests> page = new Page<MemberInterests>(10);
	
	@Autowired
	private MemberInterestsManager memberInterestsManager;

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String merchantList(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) String order,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			HttpServletRequest request){
		
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "filter_");
		// 设置默认排序方式
		if (!page.isOrderBySetted())
		{
			page.setOrderBy("tranTime");
			page.setOrder(Page.DESC);
		}
		page.setPageSize(PAGE_SIZE);
		page = memberInterestsManager.queryForPage(searchParams, pageNo, page.getPageSize());
		model.addAttribute("page", page);
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "filter_"));
		return "memberInterests/list";
	}
	
	
	
	
	
	
}
