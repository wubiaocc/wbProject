package com.finance.communication.web.controller.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.finance.communication.common.RedisUtil;
import com.finance.communication.common.RespMessage;
import com.finance.communication.entity.ProductPic;
import com.finance.communication.service.server.realm.ShiroDbRealm.ShiroUser;
import com.finance.communication.service.server.sys.ExcelManager;
import com.finance.communication.service.server.sys.MerchantProductsInfoManager;
import com.finance.communication.service.server.sys.ProductPicManager;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping(value = "/mrtProInfo")
public class MerchantProductsInfoController {
	
	@Autowired
	private MerchantProductsInfoManager merchantProductsInfoManager;
	@Autowired
	private ProductPicManager productPicManager;
	@Autowired
	private ExcelManager excelManager;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String merchantList(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "order", defaultValue = "ASC", required = false) String order,
			@RequestParam(value = "orderBy", required = false) String orderBy,
			HttpServletRequest request){
		
		return "mrtProducts/list";
	}
	
	/**
	 * 导入
	 * 2016年12月13日
	 * @return
	 */
	@RequestMapping(value = "/batchImport", method = RequestMethod.GET)
	public ModelAndView backUp(){
		
		return new ModelAndView("mrtProducts/batchImport");
	}
	
	/**
	 * 模板下载
	 * 2016年12月13日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request, HttpServletResponse response) {
		merchantProductsInfoManager.download(request, response);
	}
	
	/**
	 * 导入结果返回
	 * 
	 * @param mid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchImportMrtResult", method = RequestMethod.POST)
	public Object batchImportProductResult() {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String loginName = shiroUser.getLoginName();
		RespMessage respMessage = new RespMessage();
		Jedis jedis = RedisUtil.getJedis();
		try {
			String result = jedis.get(loginName + "_portal");
			// 执行的过程
			if (StringUtils.isNotEmpty(result)) {
				respMessage.setObj(result);
				// 移除值
				jedis.del(loginName + "_portal");
				String code = jedis.get(loginName + "_code");
				if ("0".equals(code)) {
					// 导入结束，前台轮训结束
					respMessage.setCode(3);
					jedis.del(loginName + "_code");
				}else if("4".equals(code)){
					// 导入结束，前台轮训结束
					respMessage.setCode(1);
					jedis.del(loginName + "_code");
				}
			} else {
				respMessage.setCode(2);
			}

		} catch (Exception e) {

			e.printStackTrace();
			respMessage.setCode(1);
			respMessage.setMessage("处理失败，请重试");
			
		} finally {
			jedis.del(loginName + "_portal");
			jedis.del(loginName + "_code");
			RedisUtil.returnResource(jedis);
		}
		return respMessage;
	}
	
	/**
	 * 批量导入
	 * 
	 * @param merchantId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/batchImportMrt", method = RequestMethod.POST)
	public Object batchImportProduct(@RequestParam(value = "productFile") String fileId,
			 HttpServletRequest request) {

		RespMessage respMessage = new RespMessage();
		try {

			ProductPic pic = productPicManager.getPicById(fileId);
			excelManager.batchImportMrt(pic);
			respMessage.setCode(0);

		} catch (Exception e) {

			e.printStackTrace();
			respMessage.setCode(1);
			respMessage.setMessage("导入失败");
		}

		return respMessage;
	}
}
