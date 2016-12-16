package com.finance.communication.service.server.sys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.communication.dao.ThirdTranLogDao;
import com.finance.communication.entity.ThirdTranLog;
import com.wizarpos.wx.core.orm.Page;

/**
 * 第三方交易查询manager
 * @author DaiRongliang
 * @2016年11月14日
 */
@Service
public class ThirdTranLogManager {
	
	@Autowired
	private ThirdTranLogDao thirdTranLogDao;
	
	/**
	 * 第三方交易查询
	 * 2016年11月14日
	 * @param searchParams
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<ThirdTranLog> queryForPage(Map<String, Object> searchParams,Integer pageNo,Integer pageSize){
		return thirdTranLogDao.queryForPage(searchParams, pageNo, pageSize);
	}
	
	/**
	 * 模板下载
	 * 2016年11月19日
	 * @param request
	 * @param response
	 * @param type
	 */
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String filename = "第三方交易模板.xls";
		response.setContentType("application/octet-stream;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("utf-8"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String path = request.getSession().getServletContext()
				.getRealPath("/static/excel/第三方交易模板.xls");
		InputStream in;
		OutputStream out;
		try {
			in = new FileInputStream(path);
			out = response.getOutputStream();
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
