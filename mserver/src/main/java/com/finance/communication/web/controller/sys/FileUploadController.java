package com.finance.communication.web.controller.sys;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.finance.communication.common.Tools;
import com.finance.communication.entity.ProductPic;
import com.finance.communication.service.server.sys.ProductPicManager;
import com.wizarpos.wx.core.mapper.JsonMapper;

//package com.wizarpos.agent.portal.web.controller.sys;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Random;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.shiro.SecurityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.aliyun.oss.ClientException;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSException;
//import com.aliyun.oss.model.ObjectMetadata;
//import com.wizarpos.agent.portal.common.Constant;
//import com.wizarpos.agent.portal.common.Tools;
//import com.wizarpos.agent.portal.entity.MerchantIntoApplication;
//import com.wizarpos.agent.portal.service.server.realm.ShiroDbRealm.ShiroUser;
//import com.wizarpos.wx.core.mapper.JsonMapper;
//
///**
// * 上传文件
// * @author YangLiuqing
// */
//@Controller
////@RequestMapping(value = "/file")
//public class FileUploadController {
//
//	private static final String ACCESS_ID = "vzapvhMeb7Rrlome";
//	private static final String ACCESS_KEY = "vlhQKpSqtI92hQSe22BDEXaOeBSrBe";
//	private static Logger logger = LoggerFactory
//			.getLogger(FileUploadController.class);
//	
//
//	@RequestMapping(value = "/file/uploadPictures/{tranId}/{type}")
//	@ResponseBody
//	public String uploadBanner(@PathVariable("tranId") String tranId,@PathVariable("type") String type,
//			Model model,HttpServletRequest request, 
//			HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		Iterator<String> iter = multipartRequest.getFileNames();
//		MerchantIntoApplication mer = new MerchantIntoApplication();
//		mer.setTranId(tranId);
//		/*try {
//			mer = merIntoAppManager.getMerchantIntoApplication(tranId);
//		} catch (Exception e) {
//			String jsonStr = "";
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("error", 1);
//			result.put("message", "商户不存在！");
//			logger.error("查询商户异常");
//			jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//			return jsonStr;
//		}*/
//		OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//		String bucketName = "wizarpos";
//		String jsonStr = "";
//		String extName = ""; // 保存文件拓展名
//		String newFileName = ""; // 保存新的文件名
//		String nowTimeStr = ""; // 保存当前时间
//		SimpleDateFormat sDateFormat;
//		Random r = new Random();
//		Date date = new Date();
//		String savePath = request.getRealPath(""); // 获取项目根路径
//		savePath = savePath + "/logoImage" + "/";
//		Tools.createDir(savePath);
//		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//
//		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//		String[] fileTypeExts = new String[]{".jpg",".jpeg",".png",".bmp"};
//		while (iter.hasNext()) {
//			MultipartFile file = multipartRequest.getFile((String) iter.next());
//
//			Map<String, Object> result = new HashMap<String, Object>();
//			// 获取拓展名
//			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//				extName = file.getOriginalFilename().substring(
//						file.getOriginalFilename().lastIndexOf("."));
//			}
//				if(!extName.equals(".jpg")&&!extName.equals(".bmp")&&!extName.equals(".png")&&!extName.equals(".jpeg")){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//			// 获取文件大小
//			Long size = file.getSize();
//			if(size > 1048576){
//				result.put("error", 1);
//				result.put("message", "上传图片过大，请重新上传");
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//				return jsonStr;
//			}
//			if(type.equals("1")){
//				//newFileName = mer.getTranId() + nowTimeStr + rannum + "businessLicense"
//						//+ extName.toLowerCase(); // 文件重命名后的名字
//				newFileName = tranId + nowTimeStr + rannum + "businessLicense"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			if(type.equals("2")){
//				newFileName = tranId + nowTimeStr + rannum + "oranConstruCodeCertificate"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			
//			if(type.equals("3")){
//				newFileName = tranId + nowTimeStr + rannum + "taxRegisterCertificate"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			if(type.equals("4")){
//				newFileName = tranId + nowTimeStr + rannum + "openPermissionCertificate"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			if(type.equals("5")){
//				newFileName = tranId + nowTimeStr + rannum + "legalPersonCardIdPositive"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			if(type.equals("6")){
//				newFileName = tranId + nowTimeStr + rannum + "legalPersonCardIdReverse"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			}
//			// 上传文件
//			File uploadFile = new File(savePath + "/" + newFileName);
//			FileCopyUtils.copy(file.getBytes(), uploadFile);
//			try {
//				//System.out.println("正在上传...");
//				uploadFile(client, bucketName, newFileName, savePath + "/"
//						+ newFileName);// 上传到阿里oss
//			} catch (Exception e) {
//				uploadFile.delete();
//				System.out.println("图片上传失败！");
//				result.put("error", 1);
//				result.put("message", "服务器异常！");
//				e.printStackTrace();
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//				return jsonStr;
//			}
//			if(type.equals("1")){
//				mer.setBusinessLicense("http://image.wizarpos.com/"
//						+ newFileName);
//				String path1 = mer.getBusinessLicense();
//				model.addAttribute("path1", path1);
//			}
//			if(type.equals("2")){
//				mer.setOranConstruCodeCertificate("http://image.wizarpos.com/"
//						+ newFileName);
//				String path2 = mer.getOranConstruCodeCertificate();
//				model.addAttribute("path2", path2);
//			}
//			if(type.equals("3")){
//				mer.setTaxRegisterCertificate("http://image.wizarpos.com/"
//						+ newFileName);
//				String path3 = mer.getTaxRegisterCertificate();
//				model.addAttribute("path3", path3);
//			}
//			if(type.equals("4")){
//				mer.setOpenPermissionCertificate("http://image.wizarpos.com/"
//						+ newFileName);
//				String path4 = mer.getOpenPermissionCertificate();
//				model.addAttribute("path4", path4);
//			}
//			if(type.equals("5")){
//				mer.setLegalPersonCardIdPositive("http://image.wizarpos.com/"
//						+ newFileName);
//				String path5 = mer.getLegalPersonCardIdPositive();
//				model.addAttribute("path5", path5);
//			}
//			if(type.equals("6")){
//				mer.setLegalPersonCardIdReverse("http://image.wizarpos.com/"
//						+ newFileName);
//				String path6 = mer.getLegalPersonCardIdReverse();
//				model.addAttribute("path6", path6);
//			}
//			//merIntoAppManager.save(mer);
//			uploadFile.delete();// 删除本地服务器临时文件}
//			result.put("error", 0);
//			result.put("url", "http://image.wizarpos.com/" + newFileName
//					+ "@100h_90Q" + extName.toLowerCase());
//			jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//		}
//		logger.debug("jsonStr:" + jsonStr);
//		return jsonStr;
//	}
//	@RequestMapping(value = "/file/uploadAgentPictures/{agentId}/{type}")
//	@ResponseBody
//	public String uploadAgentPictures(@PathVariable("agentId") String agentId,@PathVariable("type") String type,
//			Model model,HttpServletRequest request, 
//			HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		Iterator<String> iter = multipartRequest.getFileNames();
//		
//		OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//		String bucketName = "wizarpos";
//		String jsonStr = "";
//		String extName = ""; // 保存文件拓展名
//		String newFileName = ""; // 保存新的文件名
//		String nowTimeStr = ""; // 保存当前时间
//		SimpleDateFormat sDateFormat;
//		Random r = new Random();
//		Date date = new Date();
//		String savePath = request.getRealPath(""); // 获取项目根路径
//		savePath = savePath + "/logoImage" + "/";
//		Tools.createDir(savePath);
//		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//		
//		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//		String[] fileTypeExts = new String[]{".jpg",".jpeg",".png",".bmp"};
//		while (iter.hasNext()) {
//			MultipartFile file = multipartRequest.getFile((String) iter.next());
//			
//			Map<String, Object> result = new HashMap<String, Object>();
//			// 获取拓展名
//			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//				extName = file.getOriginalFilename().substring(
//						file.getOriginalFilename().lastIndexOf("."));
//			}
//			if(!extName.equals(".jpg")&&!extName.equals(".bmp")&&!extName.equals(".png")&&!extName.equals(".jpeg")){
//				result.put("error", 1);
//				result.put("message", "上传文件格式不正确，请重新上传");
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//				return jsonStr;
//			}
//			// 获取文件大小
//			Long size = file.getSize();
//			if(size > 1048576){
//				result.put("error", 1);
//				result.put("message", "上传图片过大，请重新上传");
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//				return jsonStr;
//			}
//			newFileName = agentId + nowTimeStr + rannum + "picture"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//			// 上传文件
//			File uploadFile = new File(savePath + "/" + newFileName);
//			FileCopyUtils.copy(file.getBytes(), uploadFile);
//			try {
//				//System.out.println("正在上传...");
//				uploadFile(client, bucketName, newFileName, savePath + "/"
//						+ newFileName);// 上传到阿里oss
//			} catch (Exception e) {
//				uploadFile.delete();
//				System.out.println("图片上传失败！");
//				result.put("error", 1);
//				result.put("message", "服务器异常！");
//				e.printStackTrace();
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//				return jsonStr;
//			}
//			String path1 = "http://image.wizarpos.com/"+newFileName;
//			model.addAttribute(type, path1);
//			uploadFile.delete();// 删除本地服务器临时文件}
//			result.put("error", 0);
//			String pigAfter = "";
//			if ("agent_path1".equals(type)) {
//				pigAfter="@0-0-40-40a";
//			}
//			if ("agent_path3".equals(type)) {
//				pigAfter="@0-0-160-40a";
//			}
//			result.put("url", "http://image.wizarpos.com/" + newFileName
//					+ pigAfter);
//			jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//		}
//		logger.debug("jsonStr:" + jsonStr);
//		return jsonStr;
//	}
//		// 上传文件
//		private static void uploadFile(OSSClient client, String bucketName,
//				String key, String filename) throws OSSException, ClientException,
//				FileNotFoundException {
//			File file = new File(filename);
//
//			ObjectMetadata objectMeta = new ObjectMetadata();
//			objectMeta.setContentLength(file.length());
//			// 可以在metadata中标记文件类型
//			objectMeta.setContentType("image/jpeg");
//
//			InputStream input = new FileInputStream(file);
//			client.putObject(bucketName, key, input, objectMeta);
//
//		}
//		
//		@RequestMapping(value = "/file/uploadFiles/{tranId}")
//		@ResponseBody
//		public String uploadFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "otherHelpMaterial"
//							+ extName.toLowerCase(); // 文件重命名后的名字
//
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//					mer.setOtherHelpMaterial("http://image.wizarpos.com/"
//							+ newFileName);
//					String path = mer.getOtherHelpMaterial();
//					model.addAttribute("path7", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		
//		@RequestMapping(value = "/file/alipyFiles/{tranId}")
//		@ResponseBody
//		public String alipyFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "alipyMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path8", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		@RequestMapping(value = "/file/weixinFiles/{tranId}")
//		@ResponseBody
//		public String weixinFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "weixinMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path9", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		@RequestMapping(value = "/file/baiduFiles/{tranId}")
//		@ResponseBody
//		public String baiduFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "baiduFilesMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path10", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		@RequestMapping(value = "/file/qqFiles/{tranId}")
//		@ResponseBody
//		public String qqFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "qqMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path11", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		@RequestMapping(value = "/file/companyBankFiles/{tranId}")
//		@ResponseBody
//		public String companyBankFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "companyBankMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path12", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		@RequestMapping(value = "/file/personalBankFiles/{tranId}")
//		@ResponseBody
//		public String personalBankFiles(@PathVariable("tranId") String tranId,
//				Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			MerchantIntoApplication mer = new MerchantIntoApplication();
//			mer.setTranId(tranId);
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = tranId + nowTimeStr + rannum + "personalBankMaterial"
//						+ extName.toLowerCase(); // 文件重命名后的名字
//				
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				
//				mer.setOtherHelpMaterial("http://wizarpos.oss-cn-hangzhou.aliyuncs.com/"
//						+ newFileName);
//				String path = mer.getOtherHelpMaterial();
//				model.addAttribute("path13", path);
//				
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		
//		
//		private static void uploadFile1(OSSClient client, String bucketName,
//				String key, String filename) throws OSSException, ClientException,
//				FileNotFoundException {
//			File file = new File(filename);
//			ObjectMetadata objectMeta = new ObjectMetadata();
//			objectMeta.setContentLength(file.length());
//			// 可以在metadata中标记文件类型
//			objectMeta.setContentType("application/zip");
//			InputStream input = new FileInputStream(file);
//			client.putObject(bucketName, key, input, objectMeta);
//
//		}
//		
//		/**
//		 * 上传转账凭据
//		 * @param model
//		 * @param request
//		 * @param response
//		 * @param redirectAttributes
//		 * @return
//		 * @throws IOException
//		 */
//		@RequestMapping(value = "/file/uploadTransCredentials/{id}/{mid}")
//		@ResponseBody
//		public String uploadTransCredentials(@PathVariable("id") String id,@PathVariable("mid") String mid,
//				Model model, HttpServletRequest request, HttpServletResponse response,
//				RedirectAttributes redirectAttributes) throws IOException {
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath +"/logoImage"
//					+ "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				// 获取文件大小
//				Long size = file.getSize();
//				
//	            if(!extName.equals(".png")&&!extName.equals(".jpeg")&&!extName.equals(".jpg")){
//	            	Map<String, Object> result = new HashMap<String, Object>();
//					System.out.println("图片上传失败！");
//					result.put("error", 1);
//					result.put("message", "请选择图片文件");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//	            	
//	            }
//				
//				newFileName = mid+nowTimeStr + rannum +extName.toLowerCase(); // 文件重命名后的名字
//	            
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try{
//				
//		           uploadFile(client, bucketName, newFileName, savePath + "/" + newFileName);//上传到阿里oss
//		           
//				} catch(Exception e){
//					Map<String, Object> result = new HashMap<String, Object>();
//					uploadFile.delete();
//					System.out.println("图片上传失败！");
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					 e.printStackTrace();
//					 jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					 return jsonStr;
//				}
//				
//				uploadFile.delete();//删除本地服务器临时文件
//				
//		         
//		        //virtPayLogManager.addTransCredentials("http://image.wizarpos.com/"+ newFileName,mid,id);
//				
//		        Map<String, Object> result = new HashMap<String, Object>();
//				result.put("error", 0);
//				result.put("url",Constant.IMAGE_SERVER_URL+newFileName+"@100h_90Q"+extName.toLowerCase());
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		
//		/**
//		 * 上传图片
//		 * 
//		 * @author bin.cheng
//		 * @param agentId
//		 * @param model
//		 * @param request
//		 * @param response
//		 * @param redirectAttributes
//		 * @return
//		 * @throws IOException
//		 */
//		@RequestMapping(value = "/file/uploadPicture/{agentId}")
//		@ResponseBody
//		public String uploadMerAgreement(
//				@PathVariable("agentId") String agentId,
//				Model model,HttpServletRequest request,HttpServletResponse response,
//				RedirectAttributes redirectAttributes) throws IOException {
//			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//			agentId=shiroUser.agentId;
//			
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				if(!extName.equals(".jpg")&&!extName.equals(".bmp")&&!extName.equals(".png")&&!extName.equals(".jpeg")){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				//if(size > 1048576){//1M
//				if(size > 2097152){//2M
//					result.put("error", 1);
//					result.put("message", "上传图片过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = agentId + nowTimeStr + rannum + "picture"
//							+ extName.toLowerCase(); // 文件重命名后的名字
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					//System.out.println("正在上传...");
//					uploadFile(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					System.out.println("图片上传失败！");
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		
//		/**
//		 * 批量上传图片
//		 * 
//		 * @author bin.cheng
//		 * @param agentId
//		 * @param model
//		 * @param request
//		 * @param response
//		 * @param redirectAttributes
//		 * @return
//		 * @throws IOException
//		 */
//		@RequestMapping(value = "/file/batchUploadPicture")
//		@ResponseBody
//		public String batchUploadPicture(Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//			String agentId = shiroUser.agentId;
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//			
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				if(!extName.equals(".jpg")&&!extName.equals(".bmp")&&!extName.equals(".png")&&!extName.equals(".jpeg")){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 1048576){
//					result.put("error", 1);
//					result.put("message", "上传图片过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = agentId + nowTimeStr + rannum + "picture"
//							+ extName.toLowerCase(); // 文件重命名后的名字
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					//System.out.println("正在上传...");
//					uploadFile(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					System.out.println("图片上传失败！");
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//		
//		/**
//		 * 上传压缩包
//		 * 
//		 * @author bin.cheng
//		 * @param model
//		 * @param request
//		 * @param response
//		 * @param redirectAttributes
//		 * @return
//		 * @throws IOException
//		 */
//		@RequestMapping(value = "/file/uploadZips")
//		@ResponseBody
//		public String uploadZips(Model model,HttpServletRequest request, 
//				HttpServletResponse response,RedirectAttributes redirectAttributes)throws IOException{
//			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//			Iterator<String> iter = multipartRequest.getFileNames();
//			OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
//			String bucketName = "wizarpos";
//			String jsonStr = "";
//			String extName = ""; // 保存文件拓展名
//			String newFileName = ""; // 保存新的文件名
//			String nowTimeStr = ""; // 保存当前时间
//			SimpleDateFormat sDateFormat;
//			Random r = new Random();
//			Date date = new Date();
//			String savePath = request.getRealPath(""); // 获取项目根路径
//			savePath = savePath + "/logoImage" + "/";
//			Tools.createDir(savePath);
//			response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码
//
//			// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
//			int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
//			sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
//			nowTimeStr = sDateFormat.format(new Date()); // 当前时间
//			while (iter.hasNext()) {
//				MultipartFile file = multipartRequest.getFile((String) iter.next());
//				Map<String, Object> result = new HashMap<String, Object>();
//				// 获取拓展名
//				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = file.getOriginalFilename().substring(
//							file.getOriginalFilename().lastIndexOf("."));
//				}
//				
//				if(!".zip".equals(extName)){
//					result.put("error", 1);
//					result.put("message", "上传文件格式不正确，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				// 获取文件大小
//				Long size = file.getSize();
//				if(size > 5242880){//5M
//					result.put("error", 1);
//					result.put("message", "上传文件过大，请重新上传");
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				newFileName = nowTimeStr + rannum + extName.toLowerCase(); // 文件重命名后的名字
//
//				System.out.println(newFileName);
//				// 上传文件
//				File uploadFile = new File(savePath + "/" + newFileName);
//				FileCopyUtils.copy(file.getBytes(), uploadFile);
//				try {
//					System.out.println("正在上传...");
//					uploadFile1(client, bucketName, newFileName, savePath + "/"
//							+ newFileName);// 上传到阿里oss
//				} catch (Exception e) {
//					uploadFile.delete();
//					result.put("error", 1);
//					result.put("message", "服务器异常！");
//					e.printStackTrace();
//					jsonStr = JsonMapper.nonEmptyMapper().toJson(result);
//					return jsonStr;
//				}
//				//merIntoAppManager.save(mer);
//				uploadFile.delete();// 删除本地服务器临时文件}
//				result.put("error", 0);
//				result.put("url", "http://image.wizarpos.com/" + newFileName);
//				jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
//			}
//			logger.debug("jsonStr:" + jsonStr);
//			return jsonStr;
//		}
//}
//
@Controller
public class FileUploadController {
	
	@Autowired
	private ProductPicManager productPicManager;
		
	@RequestMapping(value = "/file/upload")
	@ResponseBody
	@Transactional
	public String uploadFile(Model model,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();
		String jsonStr = "";
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		Date date = new Date();
		String savePath = request.getRealPath(""); // 获取项目根路径
		savePath = savePath + Tools.getEnv("attachment_path")
				+ new SimpleDateFormat("yyyyMMdd").format(date) + "/";
		Tools.createDir(savePath);
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码

		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		while (iter.hasNext()) {
			MultipartFile file = multipartRequest.getFile((String) iter.next());

			// 获取拓展名
			if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
				extName = file.getOriginalFilename().substring(
						file.getOriginalFilename().lastIndexOf("."));
			}
			// 获取文件大小
			Long size = file.getSize();
			newFileName = nowTimeStr + rannum + extName; // 文件重命名后的名字

			// 上传文件
			File uploadFile = new File(savePath + "/" + newFileName);
			FileCopyUtils.copy(file.getBytes(), uploadFile);

			// 入库操作
			String originalName = file.getOriginalFilename();
			String name = newFileName;// 名称
			String address = Tools.getEnv("attachment_path")
					+ new SimpleDateFormat("yyyyMMdd").format(date) + "/"
					+ newFileName;// 地址
			String fileExt = extName;// 附件后缀
			Date createDate = date;// 创建时间
			String descn = "";// 备注
			String type = "2";// 分类

			// 保存文件
			ProductPic attac = new ProductPic(originalName, name,
					uploadFile.getAbsolutePath(), fileExt, createDate, descn,
					type, size);
			attac.setId(UUID.randomUUID().toString());
			productPicManager.addProductPic(attac);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("file_id", attac.getId());
			result.put("file_originalName", attac.getOriginalName());
			result.put("file_url", attac.getAddress());
			result.put("code", 0);
			jsonStr = JsonMapper.nonEmptyMapper().toJson(result);// 转化成json字符串
		}

		return jsonStr;
	}
}
