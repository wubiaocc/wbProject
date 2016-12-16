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
import com.finance.communication.entity.ThirdTranLog;


/**
 * 多线程excel数据转换实体信息
 * 
 * @author LiXiaoPeng
 *
 */
public class SheetDataToThirdTranLogTask implements Callable<List<ThirdTranLog>> {

	private List<Map<String, String>> dataMap;
	private String fileName;

	public SheetDataToThirdTranLogTask(List<Map<String, String>> dataMap, String fileName) {
		this.dataMap = dataMap;
		this.fileName = fileName;
	}

	private List<ThirdTranLog> sheetDataToThirdTranLog() {
		List<ThirdTranLog> thirdTranLogs = new ArrayList<ThirdTranLog>();

		ThirdTranLog t = null;

		for (Map<String, String> dataMap : dataMap) {
			
			t = new ThirdTranLog();
			//数据来源1(第三方公司)
			String tranLogProvider = dataMap.get("数据来源1\n(第三方公司)");
			if(StringUtils.isNotEmpty(tranLogProvider)){
				try {
					t.setTranLogProvider(tranLogProvider);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//数据来源2(产品)
			String tranLogModel = dataMap.get("数据来源2\n（产品）");
			if(StringUtils.isNotEmpty(tranLogModel)){
				try {
					t.setTranLogModel(tranLogModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//商户名称
			String merchantName = dataMap.get("商户名称");
			if(StringUtils.isNotEmpty(merchantName)){
				try {
					t.setMerchantName(merchantName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//收单商户号
			String merchantNo = dataMap.get("商户号");
			if(StringUtils.isNotEmpty(merchantNo)){
				try {
					t.setMerchantNo(merchantNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				continue;
			}
			
			//终端号
			String terminalNo = dataMap.get("终端号");
			if(StringUtils.isNotEmpty(terminalNo)){
				try {
					t.setTerminalNo(terminalNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				try {
					t.setTerminalNo("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//交易时间
			String tranTime = dataMap.get("交易时间\n(yyyy-MM-dd HH:mm:ss)");
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(StringUtils.isNotEmpty(tranTime)){
				try {
					t.setTranTime(sim.parse(tranTime));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//交易金额
			String tranAmount = dataMap.get("交易金额\n(单位：元)");
			if (StringUtils.isNotEmpty(tranAmount)) {
				try {
					Double memberPriceD = new Double(tranAmount.replace(",", ""));
					Double f1 = DoubleUtil.round(memberPriceD, 2);
					t.setTranAmount(DoubleUtil.mul(f1, 100));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//交易类型
			String tranType = dataMap.get("交易类型");
			if(StringUtils.isNotEmpty(tranType)){
				try {
					t.setTranType(tranType);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//商户全称
			String merchantFullName = dataMap.get("商户全称");
			if(StringUtils.isNotEmpty(merchantFullName)){
				try {
					t.setMerchantFullName(merchantFullName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//法人名称
			String legalPerson = dataMap.get("法人名称");
			if(StringUtils.isNotEmpty(legalPerson)){
				try {
					t.setLegalPerson(legalPerson);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//商户结算率
			String rate = dataMap.get("商户结算费率");
			if(StringUtils.isNotEmpty(rate)){
				try {
					t.setRate(rate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//手续费金额
			String freeAmount = dataMap.get("手续费金额(元)");
			if (StringUtils.isNotEmpty(freeAmount)) {
				try {
					Double memberPriceD = new Double(freeAmount.replace(",", ""));
					Double f1 = DoubleUtil.round(memberPriceD, 2);
					t.setFreeAmount(DoubleUtil.mul(f1, 100));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//终端流水号
			String terminalLog = dataMap.get("终端流水号");
			if(StringUtils.isNotEmpty(terminalLog)){
				try {
					t.setTerminalLog(terminalLog);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//终端批次号
			String batchNo = dataMap.get("终端批次号");
			if(StringUtils.isNotEmpty(batchNo)){
				try {
					t.setBatchNo(batchNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//交易主账号
			String accountNo = dataMap.get("交易主账户");
			if(StringUtils.isNotEmpty(accountNo)){
				try {
					t.setAccountNo(accountNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//银行应答流水号
			String bankLog = dataMap.get("银行应答流水号");
			if(StringUtils.isNotEmpty(bankLog)){
				try {
					t.setBankLog(bankLog);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//代理商号
			String agentNo = dataMap.get("代理商号");
			if(StringUtils.isNotEmpty(agentNo)){
				try {
					t.setAgentNo(agentNo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//代理商名
			String agentName = dataMap.get("代理商名");
			if(StringUtils.isNotEmpty(agentName)){
				try {
					t.setAgentName(agentName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//第三方系统支付方式
			String tranDesc = dataMap.get("第三方系统支付方式");
			if(StringUtils.isNotEmpty(tranDesc)){
				try {
					t.setTranDesc(tranDesc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//导入日期
			t.setCreateTime(new Date());
			//批次号
			t.setBillNo(fileName);
			thirdTranLogs.add(t);
		}

		return thirdTranLogs;
	}


	@Override
	public List<ThirdTranLog> call() throws Exception {

		return sheetDataToThirdTranLog();
	}

}
