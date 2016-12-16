package com.finance.communication.common.excle.xls;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;

import com.finance.communication.common.DoubleUtil;
import com.finance.communication.entity.dto.MrtProductsInfoData;


/**
 * 多线程excel数据转换实体信息
 * 
 * @author LiXiaoPeng
 *
 */
public class SheetDataToTMrtProTask implements Callable<List<MrtProductsInfoData>> {

	private List<Map<String, String>> dataMap;
	private String fileName;

	public SheetDataToTMrtProTask(List<Map<String, String>> dataMap, String fileName) {
		this.dataMap = dataMap;
		this.fileName = fileName;
	}

	private List<MrtProductsInfoData> sheetDataToMrtPro() {
		List<MrtProductsInfoData> mrtProList = new ArrayList<MrtProductsInfoData>();

		MrtProductsInfoData m = null;

		for (Map<String, String> dataMap : dataMap) {
			
			m = new MrtProductsInfoData();
			
			//商户号
			String mid = dataMap.get("商户号");
			if(StringUtils.isNotEmpty(mid)){
				try {
					m.setMid(mid);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//手机号
			String phone = dataMap.get("手机号");
			if(StringUtils.isNotEmpty(phone)){
				try {
					m.setPhone(phone);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//邮箱
			String email = dataMap.get("邮箱");
			if(StringUtils.isNotEmpty(email)){
				try {
					m.setEmail(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//默认密码
			String pwd = dataMap.get("默认密码");
			if(StringUtils.isNotEmpty(pwd)){
				try {
					m.setPwd(pwd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//产品类型
			String proType = dataMap.get("产品类型（零售版、会员、收款）");
			if(StringUtils.isNotEmpty(proType)){
				try {
					m.setProType(proType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			mrtProList.add(m);
		}

		return mrtProList;
	}


	@Override
	public List<MrtProductsInfoData> call() throws Exception {

		return sheetDataToMrtPro();
	}

}
