package com.finance.communication.service.server.sys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.finance.communication.dao.MerchantCardDao;
import com.finance.communication.entity.MerchantCard;
import com.wizarpos.wx.core.orm.Page;
import com.wizarpos.wx.core.orm.PropertyFilter;
import com.wizarpos.wx.core.orm.PropertyFilter.MatchType;

@Component
@Transactional
public class MerchantCardManager {
	@Autowired
	private MerchantCardDao merchantCardDao;
	

	
	private static final String WPCharset = "UTF-8";
	private static final String JsonContentType = "application/json";
	private  final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 使用属性过滤条件查询任务
	 */
	@Transactional(readOnly = true)
	public Page<MerchantCard> search(final Page<MerchantCard> page,
			final List<PropertyFilter> filters) {
		return merchantCardDao.findPage(page, filters);
	}

	/**
	 * 保存MerchantCard实体
	 * 
	 * @param entity
	 */
	public void save(MerchantCard entity) {
		try {
			merchantCardDao.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除MerchantCard实体
	 * 
	 * @param id
	 */
	public void deleteMerchantCard(String id) {
		merchantCardDao.delete(id);
	}

	

	/**
	 * 获得TranLog实体
	 * 
	 * @param id
	 * @return
	 */
	public MerchantCard getMerchantCard(String id) {
		return merchantCardDao.get(id);
	}

	/**
	 * 获得TranLog实体
	 * 
	 * @param id
	 * @return
	 */
	public MerchantCard getMerchantCardById(String id) {
		List<MerchantCard> merchantCards = merchantCardDao.findBy("id", id, MatchType.EQ);
		if(merchantCards == null){
			return null;
		}else{
			if(merchantCards.size() == 0){
				return null;
			}else{
				return merchantCards.get(0);
			}
		}
	}
	
	/**
	 * 获取会员数两
	 * 
	 * @param mid
	 * @return
	 */
	public String getMemberNumber(String mid) {
		String num = merchantCardDao.queryMemberNumber(mid);

		return num;
	}

	/**
	 * 获得今天，本周七天，本周总共，本月总共，本年总共，以及当前总共会员数量
	 * 
	 * @param mid
	 * @return
	 */
	public String getMenberNumber(String mid) {
		String day = merchantCardDao.queryTodayNumber(mid);
		String week = merchantCardDao.queryWeekNumber(mid);
		String month = merchantCardDao.queryMonthNumber(mid);
		String sum = merchantCardDao.querySumNumber(mid);
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String now = df.format(d);
		String dayBefor0 = df.format(getBeforeAfterDate(now, -1));
		String dayBefor1 = df.format(getBeforeAfterDate(now, -2));
		String dayBefor2 = df.format(getBeforeAfterDate(now, -3));
		String dayBefor3 = df.format(getBeforeAfterDate(now, -4));
		String dayBefor4 = df.format(getBeforeAfterDate(now, -5));
		String dayBefor5 = df.format(getBeforeAfterDate(now, -6));
		String dayBefor6 = df.format(getBeforeAfterDate(now, -7));

		List<Object[]> tmpYear = merchantCardDao.queryYearNumber(mid);

		// 填充七天中的空余天的会员数为：0
		Object[] seven = new Object[7];
		seven[6] = merchantCardDao.getThisDateNum(mid, dayBefor0);
		seven[5] = merchantCardDao.getThisDateNum(mid, dayBefor1);
		seven[4] = merchantCardDao.getThisDateNum(mid, dayBefor2);
		seven[3] = merchantCardDao.getThisDateNum(mid, dayBefor3);
		seven[2] = merchantCardDao.getThisDateNum(mid, dayBefor4);
		seven[1] = merchantCardDao.getThisDateNum(mid, dayBefor5);
		seven[0] = merchantCardDao.getThisDateNum(mid, dayBefor6);

		// 填充空余月份会员数为：0
		Object[] year = new Object[12];
		for (Object[] item : tmpYear) {
			int index = Integer.parseInt(item[0].toString());
			year[index - 1] = item[1];
		}
		for (int i = 0; i < 12; i++) {
			if (year[i] == null) {
				year[i] = 0;
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"day\":");
		sb.append("\"" + day + "\"");
		sb.append(",");
		sb.append("\"week\":");
		sb.append("\"" + week + "\"");
		sb.append(",");
		sb.append("\"month\":");
		sb.append("\"" + month + "\"");
		sb.append(",");
		sb.append("\"sum\":");
		sb.append("\"" + sum + "\"");
		sb.append(",");
		sb.append("\"seven\":");
		sb.append(JSON.toJSONString(seven));
		sb.append(",");
		sb.append("\"year\":");
		sb.append(JSON.toJSONString(year));
		sb.append("}");

		return sb.toString();
	}

	private static Date getBeforeAfterDate(String datestr, int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date olddate = null;
		try {
			df.setLenient(false);
			olddate = new Date(df.parse(datestr).getTime());
		} catch (ParseException e) {
			throw new RuntimeException("日期转换错误");
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(olddate);

		int Year = cal.get(Calendar.YEAR);
		int Month = cal.get(Calendar.MONTH);
		int Day = cal.get(Calendar.DAY_OF_MONTH);

		int NewDay = Day + day;

		cal.set(Calendar.YEAR, Year);
		cal.set(Calendar.MONTH, Month);
		cal.set(Calendar.DAY_OF_MONTH, NewDay);

		return new Date(cal.getTimeInMillis());
	}
	 
	 

	 
	 public List<MerchantCard> findListNew(Integer pageNo,Integer pageSize,String gid,Map<String,Object> params) {
		 List<MerchantCard> lists = merchantCardDao.findListNew(pageNo, pageSize,gid, params);
			List<MerchantCard> newLists = new ArrayList<MerchantCard>();
			for(int i=0;i<lists.size();i++) {
				String labelNames = "";
				MerchantCard m = lists.get(i);
				/*if(StringUtils.isNotEmpty(m.getLabelIds())) {
					String[] strId = m.getLabelIds().split(",");
					for(int j=0;j<strId.length;j++) {
						if(StringUtils.isEmpty(strId[j])||"{}".equals(strId[j].toString())){
							continue;
						}
						MerchantLabelDef label = merchantLabelDefDao.findMerchantLabelDefById(Integer.valueOf(strId[j]),mid);
						if(label != null) {
							if(j == 0) {
								labelNames += label.getLabelName();
							}else {
								labelNames += ";"+label.getLabelName();
							}
						}
					}
				}*/
				String labelJson = m.getLabels();
				if(StringUtils.isNotEmpty(labelJson)){
					JSONObject labelJsonObject = JSONObject.parseObject(labelJson);
					/*labelNames = labelJsonObject.values().toString();*/
					labelNames = labelJsonObject.toJSONString();
				}
				m.setLabelNames(labelNames.replace("{", "").replace("}", "").replace(",", ";&nbsp;&nbsp;").replace("\"", ""));
				newLists.add(m);
			}
		 
		 return newLists;
	 }
	 
	 
	 public List<MerchantCard> findList(Integer pageNo,Integer pageSize,String gid,Map<String,Object> params) {
		 List<MerchantCard> lists = merchantCardDao.findList(pageNo, pageSize,gid, params);
			List<MerchantCard> newLists = new ArrayList<MerchantCard>();
			for(int i=0;i<lists.size();i++) {
				String labelNames = "";
				MerchantCard m = lists.get(i);
				if(StringUtils.isNotEmpty(m.getLabelIds())) {
					String[] strId = m.getLabelIds().split(",");
					for(int j=0;j<strId.length;j++) {
						if(StringUtils.isEmpty(strId[j])||"{}".equals(strId[j].toString())){
							continue;
						}
					}
				}
				m.setLabelNames(labelNames);
				newLists.add(m);
			}
		 
		 return newLists;
	 }
	 
		public BigInteger findTotalCount(Map<String,Object> params,String mid) {
			return merchantCardDao.findTotalCount(params,mid);
		}
		
		public BigInteger findTotalCountNew(Map<String,Object> params,String gid) {
			return merchantCardDao.findTotalCountNew(params,gid);
		}
        /**
         * 导出会与卡数据
         * @param mid
         * @param result
         * @param response
         * @param params 
         */
		public void export(String mid, List<MerchantCard> result,
				HttpServletResponse response, Map<String, Object> params) {
			String startTime=params.get("startTime")==null?"":params.get("startTime").toString();
	        String endTime=params.get("endTime")==null?"":params.get("endTime").toString();
			String excelName="会员资料";
//			if (StringUtils.isEmpty(startTime)&&StringUtils.isEmpty(endTime)) {
//				excelName+="全部";
//			}
			if (StringUtils.isNotEmpty(startTime)&&StringUtils.isEmpty(endTime)) {
				excelName+=startTime+"之后";
			}
			if (StringUtils.isEmpty(startTime)&&StringUtils.isNotEmpty(endTime)) {
				excelName+=endTime+"之前";
			}
			if (StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)) {
				excelName+=startTime+"至"+endTime;
			}
			
			HSSFWorkbook wb = generateWork(mid,result);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			try {
				wb.write(outputStream);
				outputStream.flush();
			} catch (IOException e) {
			
			}
			byte[] byteArray = outputStream.toByteArray();

			try {
				responseDownloader(response, byteArray, excelName + ".xls");
			} catch (Exception e) {
				
				System.out.println("faile");
			}
		}
		
		private HSSFWorkbook generateWork(String mid, List<MerchantCard> result) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet 1");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sheet.setDefaultColumnWidth(17);
			 HSSFRow headRow0 = sheet.createRow(0);
			 headRow0.createCell(0).setCellValue("商场号");
			 headRow0.createCell(1).setCellValue("会员卡号");
			 headRow0.createCell(2).setCellValue("会员姓名");
			 headRow0.createCell(3).setCellValue("手机号");
			 headRow0.createCell(4).setCellValue("激活时间");
			 headRow0.createCell(5).setCellValue("余额");
			 headRow0.createCell(6).setCellValue("过期时间");
			 headRow0.createCell(7).setCellValue("积分点数");
			 headRow0.createCell(8).setCellValue("卡类型");
			 headRow0.createCell(9).setCellValue("冻结标识");
			 headRow0.createCell(10).setCellValue("注销标识");
			// headRow0.createCell(9).setCellValue("微信openId");
			 int size=result.size();
			 for(int i=0;i<size;i++){
				 MerchantCard c=result.get(i);
				 HSSFRow row = sheet.createRow(i+1);
				 row.createCell(0).setCellValue(mid);
				 row.createCell(1).setCellValue(c.getCardNo()==null?"":c.getCardNo());
				 row.createCell(2).setCellValue(c.getUsername()==null?"":c.getUsername());
				 row.createCell(3).setCellValue(c.getMobileNo()==null?"":c.getMobileNo());
				 row.createCell(4).setCellValue(c.getActiveTime()==null?"":df.format(c.getActiveTime()));
				 row.createCell(5).setCellValue(c.getBalance()/100.00);
				 row.createCell(6).setCellValue(c.getExpriyTime()==null?"":df.format(c.getExpriyTime()));
				 row.createCell(7).setCellValue(c.getPoints());
			
				 if(StringUtils.isNotEmpty(c.getCardType())){
					 row.createCell(8).setCellValue(c.getCardType().equals("99")?"微信会员卡":"实体卡");
				 }
				 else{
					 row.createCell(8).setCellValue("");
				 }
				
				 row.createCell(9).setCellValue("1".equals(c.getFreeze())?"是":"否");
				 row.createCell(10).setCellValue("1".equals(c.getCanceled())?"是":"否");
				 
				// row.createCell(9).setCellValue(c.getOpenId());
			 }
			return wb;
		}

		private void responseDownloader(HttpServletResponse response,
				byte[] byteArray, String fileName) throws IOException {
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));
			response.setHeader("Content-Length", "" + byteArray.length);
			response.setHeader("Cache-Control", "Cache");
			response.setHeader("Pragma", "Cache");
			response.getOutputStream().write(byteArray);
			response.flushBuffer();
		}

		public Page<MerchantCard> findForPage(Page<MerchantCard> page,
				List<PropertyFilter> filters) {
			
			return merchantCardDao.findPage(page, filters);
		}

		public Boolean updatePoint(MerchantCard card) {
			
			return merchantCardDao.update(card);
		}


		
		public Page<MerchantCard> getUserList(String mid,Map<String,Object> params,Integer pageNo,Integer pageSize){
			return merchantCardDao.getUserList(mid, params, pageNo, pageSize);
		}
		public List<MerchantCard> queryList(Integer pageNo,Integer pageSize,String mid,Map<String,Object> params) {
			List<MerchantCard> lists = merchantCardDao.queryList(pageNo, pageSize,mid, params);
			List<MerchantCard> newLists = new ArrayList<MerchantCard>();
			for(int i=0;i<lists.size();i++) {
				String labelNames = "";
				MerchantCard m = lists.get(i);
				String labelJson = m.getLabels();
				if(StringUtils.isNotEmpty(labelJson)){
					JSONObject labelJsonObject = JSONObject.parseObject(labelJson);
					/*labelNames = labelJsonObject.values().toString();*/
					labelNames = labelJsonObject.toJSONString();
				}
				m.setLabelNames(labelNames.replace("{", "").replace("}", "").replace(",", ";&nbsp;&nbsp;").replace("\"", ""));
				newLists.add(m);
			}
		 
		 return newLists;
		}
		public BigInteger findTotal(Map<String,Object> params,String mid) {
			return merchantCardDao.findTotal(params, mid);
		}
		


		@SuppressWarnings("deprecation")
		public  JSONObject post(String url,JSONObject json) throws Exception{
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(10000)
					.setConnectTimeout(10000)
					.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
			CloseableHttpClient httpClient = HttpClients.custom()
					.setDefaultRequestConfig(requestConfig).build();
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpResponse response = null;
			// 请求报文需要签名
			log.debug("request json:" + json.toJSONString());
			try {
				StringEntity postEntity = new StringEntity(json.toJSONString(),
				                ContentType.create(JsonContentType, WPCharset));
				httpPost.setEntity(postEntity);
		        response = httpClient.execute(httpPost);
		        log.debug("StatusCode:"+response.getStatusLine().getStatusCode());
					if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
						throw new Exception("Access failed");
					}
					byte[] result  =  EntityUtils.toByteArray(response.getEntity());
//					System.out.print(response.getEntity().getContent());
					return parseResult(result);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 关闭连接,释放资源  
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return json;
		}
		
		private  JSONObject parseResult(byte[] response) throws Exception {
			JSONObject json =null;
			if ((response == null) || (response.length == 0)) {
				throw new Exception("无响应报文");
			}
			String content = toString(response, WPCharset);
			log.debug("response json:"+content);
			 json  = JSONObject.parseObject(content);
			// 响应报文需要验证签名
			return json; 
		}
		
		private  String toString(byte[] content, String charsetName) throws Exception {
			try {
				return new String(content, charsetName);
			} catch (UnsupportedEncodingException e) {
				throw new Exception("Unsupported encoding");
			}
		}


        public Boolean  updateCard(MerchantCard card, String mid) {
                  return   merchantCardDao.updateCard(card,mid);
        }

		
		public boolean setLabel(String id,String mid,String labelIds) throws Exception{
			return merchantCardDao.setLabel(id, mid, labelIds);
		}
		
		public boolean batchSetLabel(String[] ids,String mid,String labelIds) throws Exception{
			return merchantCardDao.batchSetLabel(ids, mid, labelIds);
		}
		
		
		/**
		 * 根据卡号获取
		 * @param cardNo
		 * @param mid
		 * @return
		 */
		public List<MerchantCard> getCardByCardNo(String cardNo,String mid){
			
			List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
			PropertyFilter midFilter = new PropertyFilter("EQS_merchantDef.mid", mid);
			filters.add(midFilter);
			PropertyFilter cardNoFilter = new PropertyFilter("EQS_cardNo", cardNo);
			filters.add(cardNoFilter);
			
			List<MerchantCard> cards = merchantCardDao.find(filters);
			if(cards == null){
				return new ArrayList<MerchantCard>();
			}else{
				return cards;
			}
		}
		
		public List<MerchantCard> queryList2(Integer pageNo,Integer pageSize,String mid,Map<String,Object> params) {
			List<MerchantCard> lists = merchantCardDao.queryList2(pageNo, pageSize,mid, params);
			List<MerchantCard> newLists = new ArrayList<MerchantCard>();
			for(int i=0;i<lists.size();i++) {
				String labelNames = "";
				MerchantCard m = lists.get(i);
				String labelJson = m.getLabels();
				if(StringUtils.isNotEmpty(labelJson)){
					JSONObject labelJsonObject = JSONObject.parseObject(labelJson);
					/*labelNames = labelJsonObject.values().toString();*/
					labelNames = labelJsonObject.toJSONString();
				}
				m.setLabelNames(labelNames.replace("{", "").replace("}", "").replace(",", ";&nbsp;&nbsp;").replace("\"", ""));
				newLists.add(m);
			}
		 
		 return newLists;
		}
		public BigInteger findTotal2(Map<String,Object> params,String mid) {
			return merchantCardDao.findTotal2(params, mid);
		}
	
		/**
		 * 获取新增会员数
		 * 
		 * @author bin.cheng
		 * @param mid
		 * @param timeStart
		 * @param timeEnd
		 * @return
		 */
		public BigInteger getAddCardCount(String mid, String timeStart, String timeEnd) {
			return merchantCardDao.getAddCardCount(mid, timeStart, timeEnd);
		}

		/**
		 * 获取会员充值金额
		 * 
		 * @author bin.cheng
		 * @param mid
		 * @param dayStart
		 * @param dayEnd
		 * @return
		 */
		public BigDecimal getAddCardRecharge(String mid, String timeStart, String timeEnd) {
			return merchantCardDao.getAddCardRecharge(mid, timeStart, timeEnd);
		}

		/**
		 * 获取首页会员卡图表数据
		 * 
		 * @author bin.cheng
		 * @param mid
		 * @param timeStart
		 * @param timeEnd
		 * @return
		 */
		public List<Map<String, Object>> drawCardMonthData(String mid,
				String timeStart, String timeEnd) {
			return merchantCardDao.drawCardMonthData(mid, timeStart, timeEnd);
		}

		/**
		 * 会员卡饼图数据查询
		 * 
		 * @author bin.cheng
		 * @param mid
		 * @param timeStart
		 * @param timeEnd
		 * @return
		 */
		public List<Map<String, Object>> drawCardMonthPercentData(String mid,
				String timeStart, String timeEnd) {
			return merchantCardDao.drawCardMonthPercentData(mid, timeStart, timeEnd);
		}
}
