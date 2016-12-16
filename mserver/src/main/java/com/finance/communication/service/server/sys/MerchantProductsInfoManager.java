package com.finance.communication.service.server.sys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public class MerchantProductsInfoManager {
	
	
	/**
	 * 模板下载
	 * 2016年12月13日
	 * @param request
	 * @param response
	 * @param type
	 */
	public void download(HttpServletRequest request, HttpServletResponse response) {
		String filename = "商户模板.xls";
		response.setContentType("application/octet-stream;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("utf-8"), "iso-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String path = request.getSession().getServletContext()
				.getRealPath("/static/excel/商户模板.xls");
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
