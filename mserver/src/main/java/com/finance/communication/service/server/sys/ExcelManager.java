package com.finance.communication.service.server.sys;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.finance.communication.common.Constant;
import com.finance.communication.common.MqttUtil;
import com.finance.communication.common.ReadExcel;
import com.finance.communication.common.RedisUtil;
import com.finance.communication.common.Tools;
import com.finance.communication.common.excle.xls.SheetDataToTMrtProTask;
import com.finance.communication.common.excle.xls.SheetDataToThirdTranLogTask;
import com.finance.communication.dao.MerchantDefDao;
import com.finance.communication.dao.ThirdTranLogDao;
import com.finance.communication.dao.ThirdTranLogTask;
import com.finance.communication.entity.MerchantDef;
import com.finance.communication.entity.ProductPic;
import com.finance.communication.entity.ThirdTranLog;
import com.finance.communication.entity.ThirdTranLogDataInput;
import com.finance.communication.entity.ThirdTranLogInput;
import com.finance.communication.entity.ThirdTranLogMrtInput;
import com.finance.communication.entity.dto.MerchantDefData;
import com.finance.communication.entity.dto.MrtProductsInfoData;
import com.finance.communication.service.server.realm.ShiroDbRealm.ShiroUser;

import redis.clients.jedis.Jedis;

@Component
@Transactional
public class ExcelManager {
	
	private static final Logger logger = Logger.getLogger(ExcelManager.class);
	
	@Autowired
	private ThirdTranLogDao thirdTranLogDao;
	@Autowired
	private MerchantDefDao merchantDefDao;
	@Autowired
	private WPosHttpClient wPosHttpClient;

	/**
	 * 批量导入产品
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	@Async
	public List<Integer> batchImportProduct(ProductPic pic) throws Exception {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String loginName = shiroUser.getLoginName();
		
		String fileName = "";
		String filePath = "";
		if(pic != null){
			fileName = pic.getOriginalName();
			String s[] = fileName.split("\\.");
			fileName = s[0];
			filePath = pic.getAddress();
		}
		
		Jedis jedis = RedisUtil.getJedis();

		jedis.del(loginName + "_portal");
		jedis.del(loginName + "_code");

		try {
			Long sheetDataMapbeginTime = System.currentTimeMillis();
			ReadExcel readExcel = new ReadExcel(new File(filePath));

			List<Integer> re1 = new ArrayList<Integer>();
			int success = 0;
			int fail = 0;
			List<Map<String, String>> sheetDataList = readExcel.getSheetDataMap(filePath, readExcel.getTitle());
			if (sheetDataList == null | sheetDataList.isEmpty()) {

				jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
				jedis.set(loginName + "_code", "4");

				re1.add(0, success);
				re1.add(1, fail);
				return re1;
			}

			Long sheetDataEndTime = System.currentTimeMillis();
			String msgEnd = "";
			String msgResult = "";
			String msg = "解析Excel数据处理时间：" + (sheetDataEndTime - sheetDataMapbeginTime) / 1000 + "秒,数据量："
					+ sheetDataList.size() + "条";
			logger.warn(msg);
			jedis.set(loginName + "_portal", msg);
			
			Long startTime1 = System.currentTimeMillis();
			
			// 处理数据
			List<SheetDataToThirdTranLogTask> lists = createList(sheetDataList, 5000, fileName);
			// // 多线程计算
			ExecutorService executor = Executors.newFixedThreadPool(lists.size());
			// // 执行
			List<Future<List<ThirdTranLog>>> results = executor.invokeAll(lists);
			executor.shutdown();
			// // 合并多线程计算数据
			List<ThirdTranLog> thirdTranLogs = new ArrayList<ThirdTranLog>();
			if (!results.isEmpty()) {
				for (Future<List<ThirdTranLog>> result : results) {
					thirdTranLogs.addAll(result.get());
				}
			}
			success = thirdTranLogs.size();
			fail = sheetDataList.size() - thirdTranLogs.size();
			if (thirdTranLogs.isEmpty()) {

				msg = "Excel未解析到有效数据，请确认数据或导入模板";
				jedis.set(loginName + "_portal", msg);
				jedis.set(loginName + "_code", "4");
				re1.add(0, success);
				re1.add(1, fail);
				return re1;
			}
			
			//根据批次号(fileName)删除数据
			thirdTranLogDao.delByBillNo(fileName);
			
			//保存
			// 数据入库任务
			List<ThirdTranLogTask> saveLists = createListToDatabase(thirdTranLogs, 5000);

			ExecutorService saveExecutor = Executors.newFixedThreadPool(saveLists.size());

			List<Future<List<ThirdTranLog>>> saveResults = saveExecutor.invokeAll(saveLists);
			saveExecutor.shutdown();
			if (!saveResults.isEmpty()) {
				for (Future<List<ThirdTranLog>> result2 : saveResults) {
					result2.get();
				}
			}
			
			
			Long endTime1 = System.currentTimeMillis();

			msgResult = "Excel数据处理时间：" + (endTime1 - startTime1) / 1000 + "秒<br/>";
			logger.warn(msg);
			
			//根据`mgt_loct`='1616' and city_loct='6666' 查询 最大商户号
			String maxMid = merchantDefDao.getByLoct();
			
			//调用存储过程
			thirdTranLogDao.produre();
			
			//根据maxMid 查询大于maxMid的新生成的mid list
			List<MerchantDefData> mList = merchantDefDao.getByMaxMid(maxMid);
			if(mList != null && !mList.isEmpty()){
				logger.info("商户数量： " + mList.size());
			}
			
			//批次号billno  查新增流水
			List<ThirdTranLog> tList = thirdTranLogDao.getByBillNo(fileName);
			if(tList != null && !tList.isEmpty()){
				logger.info("流水数量： " + tList.size());
			}
			
			endTime1 = System.currentTimeMillis();
			if(mList != null && !mList.isEmpty()){
				msgEnd += "处理完成，处理时间，" + (endTime1 - sheetDataMapbeginTime) / 1000 + "秒，共有：" + sheetDataList.size()
						+ "条记录，成功导入：" + success + "条记录，失败：" + fail + "条记录，新增商户共" + mList.size() + "个";
			}else{
				msgEnd += "处理完成，处理时间，" + (endTime1 - sheetDataMapbeginTime) / 1000 + "秒，共有：" + sheetDataList.size()
						+ "条记录，成功导入：" + success + "条记录，失败：" + fail + "条记录";
			}
			jedis.set(loginName + "_portal", msgEnd);
			jedis.set(loginName + "_code", "0");
			RedisUtil.returnResource(jedis);
			logger.warn(msgEnd);
			
			//发送mqtt -->把调用mqtt的方法放到接口里面
			sendMqtt(mList, tList);
			
			re1.add(0, success);
			re1.add(1, fail);
			return re1;

		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.interrupted();
			jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
			jedis.set(loginName + "_code", "4");
			List<Integer> re1 = new ArrayList<Integer>();
			re1.add(0, 0);
			re1.add(1, 0);
			return re1;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
			jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
			jedis.set(loginName + "_code", "4");
			List<Integer> re1 = new ArrayList<Integer>();
			re1.add(0, 0);
			re1.add(1, 0);
			return re1;
		}

		return null;

	}
	
	/**
	 * Excel数据解析为Bean任务
	 * 
	 * @param targe
	 * @param size
	 * @param merchantDef
	 * @return
	 */
	public static List<SheetDataToThirdTranLogTask> createList(List<Map<String, String>> targe, int size, String fileName) {
		List<SheetDataToThirdTranLogTask> listArr = new ArrayList<SheetDataToThirdTranLogTask>();
		// 获取被拆分的数组个数
		int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
		for (int i = 0; i < arrSize; i++) {
			List<Map<String, String>> sub = new ArrayList<Map<String, String>>();
			// 把指定索引数据放入到list中
			for (int j = i * size; j <= size * (i + 1) - 1; j++) {
				if (j <= targe.size() - 1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(new SheetDataToThirdTranLogTask(sub, fileName));
		}

		return listArr;
	}
	
	/**
	 * Excel数据解析为Bean任务
	 * 
	 * @param targe
	 * @param size
	 * @param merchantDef
	 * @return
	 */
	public static List<SheetDataToTMrtProTask> createMrtList(List<Map<String, String>> targe, int size, String fileName) {
		List<SheetDataToTMrtProTask> listArr = new ArrayList<SheetDataToTMrtProTask>();
		// 获取被拆分的数组个数
		int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
		for (int i = 0; i < arrSize; i++) {
			List<Map<String, String>> sub = new ArrayList<Map<String, String>>();
			// 把指定索引数据放入到list中
			for (int j = i * size; j <= size * (i + 1) - 1; j++) {
				if (j <= targe.size() - 1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(new SheetDataToTMrtProTask(sub, fileName));
		}

		return listArr;
	}
	
	/**
	 * 数据保存数据库
	 * 
	 * @param targe
	 * @param size
	 * @param productDao
	 * @param productAttsDao
	 * @param storageDao
	 * @return
	 */
	public static List<ThirdTranLogTask> createListToDatabase(List<ThirdTranLog> targe, int size) {
		List<ThirdTranLogTask> listArr = new ArrayList<ThirdTranLogTask>();
		// 获取被拆分的数组个数
		int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
		for (int i = 0; i < arrSize; i++) {
			List<ThirdTranLog> sub = new ArrayList<ThirdTranLog>();
			// 把指定索引数据放入到list中
			for (int j = i * size; j <= size * (i + 1) - 1; j++) {
				if (j <= targe.size() - 1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(new ThirdTranLogTask(sub));
		}

		return listArr;
	}
	
	/**
	 * 发送mqtt消息
	 * 2016年11月17日
	 * @param mList
	 * @param tList
	 * @throws InterruptedException 
	 */
	public void sendMqtt(List<MerchantDefData> mList, List<ThirdTranLog> tList) throws InterruptedException{
		//新开通商户数量
		int num = 0;
		if(mList != null && !mList.isEmpty()){
			num = mList.size();
		}
		
		//发送mqtt消息
		try{
		    logger.info("开始准备发送数据");
		    SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    
		    logger.info("--准备发送交易流水--");
		    
		    if(!tList.isEmpty() && tList.size() > 0){
		    	
		    	ThirdTranLogDataInput thirdTranLogDataInput = new ThirdTranLogDataInput();
		    	List<ThirdTranLogInput> logList = new ArrayList<ThirdTranLogInput>();
		    	
		    	for(ThirdTranLog t : tList){
		    		//第三方公司
		    		String provider = t.getTranLogProvider();
		    		String model = t.getTranLogModel();
		    		if("卡头".equals(model)){
		    			model = "手刷";
		    		}
		    		//服务器
		    		String tranServer = provider + model;
		    		
		    		//交易时间
		    		String tranTime = sdf.format(t.getTranTime());
		    		
		    		//交易金额
		    		DecimalFormat df = new DecimalFormat("######0.00");
		    		Double amount = t.getTranAmount();
		    		
		    		//交易类型
		    		String tranType = t.getTranType();
		    		
		    		//交易码
		    		
		    		//商户名称
		    		String merchantName = t.getMerchantName();
		    		
		    		//代理商名称
		    		String agentName = provider;
		    		if("微付".equals(provider)){
		    			agentName = "上海微付网络技术有限公司";
		    		}
		    		
		    		//大商户/渠道
		    		String conType = "普通支付方式";
		    		
		    		//是否销售单
		    		String salesSlip = "无销售单";
		    		
		    		//是否来自接口
		    		String connection = provider + "导入";
		    		
		    		ThirdTranLogInput log = new ThirdTranLogInput();
		    		log.setServer(tranServer);
		    		log.setTranCode("null");
		    		log.setTranDesc(tranType);
		    		log.setTranTime(tranTime);
		    		log.setMid(t.getMid());
		    		log.setMerchantName(merchantName);
		    		log.setAgentName(agentName);
		    		log.setSuperMerchantName(conType);
		    		log.setSaleOrderNo(salesSlip);
		    		log.setComeFrom(connection);
		    		log.setType("trade");
		    		log.setTranAmount(df.format(amount/100.00d));
		    		log.setTerminalType("null");
		    		log.setCardType("null");
		    		log.setCardSource("null");
		    		logList.add(log);
//		    		logger.info("交易流水 : " + log.toString());
		    	}
		    	thirdTranLogDataInput.setServiceType("1");
		    	thirdTranLogDataInput.setLogList(logList);
		    	String jsonObject= JSONObject.toJSONString(thirdTranLogDataInput);
		    	
		    	try {
					String result = wPosHttpClient.post("http://train.wizarpos.com/wizarposOpen-server/v1_0/terminal/thirdMrt/sendMqtt", jsonObject);
					logger.info(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	logger.info("交易流水信息结束");
		    }
		    
		    String server="";
		    switch (Constant.CURRENT_VERSION) {
		     case "CASHIER2":
		         server="beta";
		         break;
		     case "91HUISHANG":
		         server="91cashier";
		         break;
		     case "TRAIN":
		         server="alpha";
		         break;
		     case "XINLAN":
		         server="alpha_xinlan";
		         break;
		     case "ZYWT":
		         server="beta_zywt";
		         break;
		     default:
		         server="beta";
		         break;
		    }
		    
		    if(num != 0){
		    	ThirdTranLogDataInput thirdTranLogDataInput = new ThirdTranLogDataInput();
		    	
				Date time = new Date();
				String today = sd.format(time);
				
				List<ThirdTranLogMrtInput> mrtList = new ArrayList<ThirdTranLogMrtInput>();
				ThirdTranLogMrtInput mrt = new ThirdTranLogMrtInput();
				
				mrt.setServer(server);
				mrt.setTime(today);
				mrt.setType("trade");
				mrt.setOpenNum(Integer.toString(num));
				
				mrtList.add(mrt);
				thirdTranLogDataInput.setServiceType("2");
				thirdTranLogDataInput.setMrtList(mrtList);
				
				String jsonObject= JSONObject.toJSONString(thirdTranLogDataInput);
				
				logger.info("发送内容（新增商户数量：openNum）：" + num);
				try {
					String result = wPosHttpClient.post("http://train.wizarpos.com/wizarposOpen-server/v1_0/terminal/thirdMrt/sendMqtt", jsonObject);
					logger.info(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		   
		}catch (Exception e) {
		    logger.info("发送失败："+e);
        }

	}
	
	/**
	 * 批量导入商户设置产品
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	@Async
	public List<Integer> batchImportMrt(ProductPic pic) throws Exception {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String loginName = shiroUser.getLoginName();
		
		String fileName = "";
		String filePath = "";
		if(pic != null){
			fileName = pic.getOriginalName();
			String s[] = fileName.split("\\.");
			fileName = s[0];
			filePath = pic.getAddress();
		}
		
		Jedis jedis = RedisUtil.getJedis();

		jedis.del(loginName + "_portal");
		jedis.del(loginName + "_code");

		try {
			Long sheetDataMapbeginTime = System.currentTimeMillis();
			ReadExcel readExcel = new ReadExcel(new File(filePath));

			List<Integer> re1 = new ArrayList<Integer>();
			int success = 0;
			int fail = 0;
			List<Map<String, String>> sheetDataList = readExcel.getSheetDataMap(filePath, readExcel.getTitle());
			if (sheetDataList == null | sheetDataList.isEmpty()) {

				jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
				jedis.set(loginName + "_code", "4");

				re1.add(0, success);
				re1.add(1, fail);
				return re1;
			}

			Long sheetDataEndTime = System.currentTimeMillis();
			String msgEnd = "";
			String msgResult = "";
			String msg = "解析Excel数据处理时间：" + (sheetDataEndTime - sheetDataMapbeginTime) / 1000 + "秒,数据量："
					+ sheetDataList.size() + "条";
			logger.warn(msg);
			jedis.set(loginName + "_portal", msg);
			
			Long startTime1 = System.currentTimeMillis();
			
			// 处理表格数据
			List<SheetDataToTMrtProTask> lists = createMrtList(sheetDataList, 5000, fileName);
			// // 多线程计算
			ExecutorService executor = Executors.newFixedThreadPool(lists.size());
			// // 执行
			List<Future<List<MrtProductsInfoData>>> results = executor.invokeAll(lists);
			executor.shutdown();
			// // 合并多线程计算数据
			List<MrtProductsInfoData> mrtProInfos = new ArrayList<MrtProductsInfoData>();
			if (!results.isEmpty()) {
				for (Future<List<MrtProductsInfoData>> result : results) {
					mrtProInfos.addAll(result.get());
				}
			}
			
			//检验商户号是否有效
			
			
			
			success = mrtProInfos.size();
			fail = sheetDataList.size() - mrtProInfos.size();
			if (mrtProInfos.isEmpty()) {
				msg = "Excel未解析到有效数据，请确认数据或导入模板";
				jedis.set(loginName + "_portal", msg);
				jedis.set(loginName + "_code", "4");
				re1.add(0, success);
				re1.add(1, fail);
				return re1;
			}
			
			//删除商户已有菜单、删除商户已有角色模板，写入文件执行
			StringBuffer buffer = new StringBuffer("");
			for(MrtProductsInfoData m : mrtProInfos){
				String lineSeparator = System.getProperty("line.separator", "\n"); 
				buffer.append("delete from sys_products_resource where mid = '" + m.getMid() + "';");
				buffer.append(lineSeparator + "delete from sys_sprole_auth where role_id in (select id from sys_m_user where mid = '" + m.getMid() + "')");
				buffer.append(lineSeparator + "commit;");
				buffer.append(lineSeparator);
			}
			List<File> files = new ArrayList<>();
			FileOutputStream fileOutputStream = null;
			File file = null;
			try {
				file = new File(new Date().getTime() + ".sql");
				if (file.exists()) {
					file.createNewFile();
				}
				fileOutputStream = new FileOutputStream(file);
				// 写入文件
				IOUtils.write(buffer.toString(), fileOutputStream);
				files.add(file);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(fileOutputStream);
			}
			Tools.antSQLExec(files);
			
			
			Long endTime1 = System.currentTimeMillis();

			msgResult = "Excel数据处理时间：" + (endTime1 - startTime1) / 1000 + "秒<br/>";
			logger.warn(msg);
			
			endTime1 = System.currentTimeMillis();
			msgEnd += "处理完成，处理时间，" + (endTime1 - sheetDataMapbeginTime) / 1000 + "秒，共有：" + sheetDataList.size()
					+ "条记录，成功导入：" + success + "条记录，失败：" + fail + "条记录";
			jedis.set(loginName + "_portal", msgEnd);
			jedis.set(loginName + "_code", "0");
			RedisUtil.returnResource(jedis);
			logger.warn(msgEnd);
			
			re1.add(0, success);
			re1.add(1, fail);
			return re1;

		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.interrupted();
			jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
			jedis.set(loginName + "_code", "4");
			List<Integer> re1 = new ArrayList<Integer>();
			re1.add(0, 0);
			re1.add(1, 0);
			return re1;
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
			jedis.set(loginName + "_portal", "Excel未解析到有效数据，请确认数据或导入模板");
			jedis.set(loginName + "_code", "4");
			List<Integer> re1 = new ArrayList<Integer>();
			re1.add(0, 0);
			re1.add(1, 0);
			return re1;
		}

		return null;

	}
}
