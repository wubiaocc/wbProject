package com.finance.communication.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * 图片上传工具类 到 oss
 * 
 * @author Administrator
 * 
 */
public class PicUploadUtil {
	private static final String ACCESS_ID = "vzapvhMeb7Rrlome";
	private static final String ACCESS_KEY = "vlhQKpSqtI92hQSe22BDEXaOeBSrBe";
	public static String  upload(String filePath) {
		OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
		String bucketName = "wizarpos";
		String extName = ""; // 保存文件拓展名
		String newFileName = ""; // 保存新的文件名
		String nowTimeStr = ""; // 保存当前时间
		SimpleDateFormat sDateFormat;
		/*System.out.println(filePath);*/
	extName=filePath.substring(filePath.lastIndexOf("."), filePath.length());
	/*System.out.println(extName);*/
		Random r = new Random();
		// 生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
		int rannum = (int) (r.nextDouble() * (99999 - 10000 + 1)) + 10000; // 获取随机数
		sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"); // 时间格式化的格式
		nowTimeStr = sDateFormat.format(new Date()); // 当前时间
		newFileName = nowTimeStr + rannum + extName.toLowerCase(); // 文件重命名后的名字
      /*System.out.println(newFileName);*/
		/*System.out.println("正在上传...");*/
		try {
			uploadFile(client, bucketName, newFileName, filePath);
			return "http://image.wizarpos.com/" + newFileName;
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		// 上传到阿里oss
		return "-1";

	}

	// 上传文件
	private static void uploadFile(OSSClient client, String bucketName,
			String key, String filename) throws OSSException, ClientException,
			FileNotFoundException {
		File file = new File(filename);

		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType("image/jpeg");

		InputStream input = new FileInputStream(file);
		client.putObject(bucketName, key, input, objectMeta);

	}
	public  static void  main(String arg[]){
		upload("D:\test\1.jpg");
	} 
		
	
}
