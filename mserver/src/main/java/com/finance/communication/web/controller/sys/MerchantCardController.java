package com.finance.communication.web.controller.sys;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.finance.communication.entity.MerchantCard;
import com.finance.communication.service.server.sys.MerchantCardManager;
import com.wizarpos.wx.core.orm.Page;

@Controller
@RequestMapping(value = "/merchantCard")
public class MerchantCardController {

	@Autowired
	private MerchantCardManager merchantCardManager;


	// 每页显示的条数
	private static final Integer PAGE_SIZE = 10;
	private Logger logger= Logger.getLogger(MerchantCardController.class);
	private Page<MerchantCard> page = new Page<MerchantCard>(10);


	/**
	 * 新增
	 * 
	 * @param moel
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String createMerchantCard(Model moel) {
		moel.addAttribute("action", "save");
		return "/merchantCard/add";
	}

	/**
	 * 用户管理列表
	 * 
	 * @param pageNo
	 * @param order
	 * @param orderBy
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public String findList(
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) String order,
			@RequestParam(value = "orderBy",required = false) String orderBy,
			HttpServletRequest request,
			Model model) 
	{  

		String mid = "";
		Map<String,Object> params = new HashMap<String,Object>();
	    String cardNo = request.getParameter("cardNo");
	    if(StringUtils.isNotEmpty(cardNo)) {
	    	params.put("cardNo", cardNo);
	    }
	    String username = request.getParameter("username");
	    if(StringUtils.isNotEmpty(username)) {
	    	params.put("username", username);
	    }
	    String mobileNo =  request.getParameter("mobileNo");
	    if(StringUtils.isNotEmpty(mobileNo)) {
	    	params.put("mobileNo", mobileNo);
	    }
	    String canceled = request.getParameter("canceled");
	    if(StringUtils.isNotEmpty(canceled)) {
	    	params.put("canceled", canceled);
	    }
	    String freeze= request.getParameter("freeze");
	    if(StringUtils.isNotEmpty(freeze)) {
	    	params.put("freeze", freeze);
	    }
	    String startTime = request.getParameter("startTime");
	    if(StringUtils.isNotEmpty(startTime)) {
	    	params.put("startTime", startTime);
	    }
	    String endTime = request.getParameter("endTime");
	    if(StringUtils.isNotEmpty(endTime)) {
	    	params.put("endTime", endTime);
	    }
	  
	   
		page.setPageNo(pageNo);
		page.setPageSize(PAGE_SIZE);
		List<MerchantCard> result = merchantCardManager.findList(pageNo, PAGE_SIZE, mid, params);
		BigInteger totalCount = merchantCardManager.findTotalCount(params,mid);
		
		page.setResult(result);
		page.setTotalCount(Integer.parseInt(totalCount.toString()));
		
		model.addAttribute("page", page);
		model.addAttribute("active", "member_merchantCard");
		model.addAttribute("searchParams", params);
		model.addAttribute("mid", mid);
		return "merchantCard/queryList";
	}
	
}
