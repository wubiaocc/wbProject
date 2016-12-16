package com.finance.communication.common;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.finance.communication.common.PicUploadUtil;
import com.finance.communication.common.Tools;

/**
 * @author YangLiuqing
 * 文件上传
 */
public class FileUpload {

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @throws IOException
	 */
	public static void uploadPicture(			
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();
		String result="";
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		List<String> listFilePaths = new ArrayList<String>();
		SimpleDateFormat sDateFormat;
		Random r = new Random();
		//Date date = new Date();
		String savePath = request.getRealPath(""); // 获取项目根路径
		savePath = savePath +"/logoImage"
				+ "/";
		Tools.createDir(savePath);
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码

		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		while (iter.hasNext()) {
			String temp_name = (String) iter.next();
			System.out.println("temp_name:"+temp_name);
			MultipartFile file = multipartRequest.getFile(temp_name);
			//判断 是否有上传
			if(file.getOriginalFilename()!=null&&!file.getOriginalFilename().equals("")){
				// 获取拓展名
				if (file.getOriginalFilename().lastIndexOf(".") >= 0) {
					
					extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
				}
				// 获取文件大小
				//Long size = file.getSize();
				newFileName = nowTimeStr + rannum +"bg"+extName.toLowerCase(); // 文件重命名后的名字
	           // System.out.println(newFileName);       
				// 上传文件到服务器
				File uploadFile = new File(savePath + "/" + newFileName);
				FileCopyUtils.copy(file.getBytes(), uploadFile);
				try{
					 result = PicUploadUtil.upload(savePath + "/" + newFileName);
					 listFilePaths.add(result);
				}
				catch(Exception e){
					 uploadFile.delete();//删除本地服务器临时文件
				}			
		         	uploadFile.delete();//删除本地服务器临时文件		     
				}
			else{
				 listFilePaths.add("-1");
			}
		}
	}
}

