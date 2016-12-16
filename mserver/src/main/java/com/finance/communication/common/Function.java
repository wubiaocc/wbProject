package com.finance.communication.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

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
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class Function {
	private static Logger logger = Logger.getLogger(Function.class);
	private static final String WPCharset = "UTF-8";
	private static final String JsonContentType = "application/json";
	// 资源文件
	private static Properties props = null;

	// static {
	// Resource resource = new ClassPathResource("/application.properties");
	// try {
	// props = PropertiesLoaderUtils.loadProperties(resource);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 判断是否为纯数字
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number)
	{
		for (int i = 0; i < number.length(); i++)
		{
			if (!Character.isDigit(number.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	public static <T> T getBean(Class<T> clazz)
	{
		return ContextLoaderListener.getCurrentWebApplicationContext().getBean(
				clazz);
	}

	public static <T> T getBean(ServletContext sc, Class<T> clazz)
	{
		return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(
				clazz);
	}

	public static String addOne(String num)
	{
		if (num == null || "null".equals(num))
		{
			num = "0";
		}
		int r = Integer.parseInt(num) + 1;
		if (String.valueOf(r).length() < 2)
		{
			return "0" + r;
		}
		return r + "";
	}

	public static String getMerchantCardId(String merchantId, String cardType,
			String cardNo)
	{
		return merchantId + cardType + cardNo;
	}

	/** 获取资源文件的 */
	public static String getValue(String name)
	{
		return props.getProperty(name);
	}

	public static String getValue(String name, String defaultValue)
	{
		return props.getProperty(name, defaultValue);
	}

	public static String getUid()
	{
		return UUID.randomUUID().toString().replace("", "");
	}

	/**
	 * 加 天
	 * 
	 * @param currentTime
	 * @param day
	 * @return
	 */
	public static Date addDay(Date currentTime, int day)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}

	/**
	 * 加 分
	 * 
	 * @param currentTime
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date currentTime, int minute)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 天 开始
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getDayStart(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	/**
	 * 天 结束
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getDayEnd(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 今天开始
	 * 
	 * @return
	 */
	public static Date getDayStart()
	{
		return getDayStart(new Date());
	}

	/**
	 * 今天结束
	 * 
	 * @return
	 */
	public static Date getDayEnd()
	{
		return getDayEnd(new Date());
	}

	public static Date addWeek(Date currentTime, int amount)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.WEEK_OF_YEAR, amount);
		return calendar.getTime();
	}

	/**
	 * 一周中，一天的索引
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public static int getIndexForDayOfWeek(int dayOfWeek)
	{
		if (dayOfWeek == 1)
		{
			return 6;
		} else
		{
			return dayOfWeek - 2;
		}
	}

	/**
	 * 一周的开始
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getWeekStart(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_MONTH, -getIndexForDayOfWeek(dayOfWeek));
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	/**
	 * 一周的结束
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getWeekEnd(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.add(Calendar.DAY_OF_MONTH, 6 - getIndexForDayOfWeek(dayOfWeek));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * 月 加
	 * 
	 * @param currentTime
	 * @param amount
	 * @return
	 */
	public static Date addMonth(Date currentTime, int amount)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		calendar.add(Calendar.MONTH, amount);
		return calendar.getTime();
	}

	/**
	 * 月开始
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getMonthStart(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		calendar.add(Calendar.DAY_OF_MONTH, -dayOfMonth);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	/**
	 * 月结束
	 * 
	 * @param currentTime
	 * @return
	 */
	public static Date getMonthEnd(Date currentTime)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTime);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH) - 1;
		int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DAY_OF_MONTH, count - dayOfMonth - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	public static String formatDate(Date date, String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String formatDate(String pattern)
	{
		return formatDate(new Date(), pattern);
	}

	public static boolean isToday(Date date)
	{
		Date currentTime = new Date();
		Date startDate = getDayStart(currentTime);
		Date endDate = getDayEnd(currentTime);
		if (date.getTime() >= startDate.getTime()
				&& date.getTime() <= endDate.getTime())
		{
			return true;
		}
		return false;
	}

	public static <T> T mapToJavaBean(Map<String, Object> map, Class<T> clazz)
	{
		return JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(map)),
				clazz);
	}

	public static Map<String, Object> javaBeanToMap(Object javaBean)
	{
		return JSON.parseObject(JSON.toJSONString(javaBean),
				new TypeReference<Map<String, Object>>()
				{
				});
	}

	public static Map<String, String> httpParamToMap(String httpParam)
	{
		Map<String, String> m = new HashMap<String, String>();
		if (httpParam == null)
		{
			return m;
		}
		String[] ss = httpParam.split("&");
		for (String s : ss)
		{
			String[] kv = s.split("=");
			if (kv == null || "".equals(kv) || kv.length == 0)
			{
				continue;
			} else if (kv.length == 1)
			{
				m.put(kv[0], null);
			} else
			{
				m.put(kv[0], kv[1]);
			}
		}
		return m;
	}

	public static String byteToHex(byte[] bs)
	{
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < bs.length; n++)
		{
			stmp = Integer.toHexString(bs[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
		}
		return sb.toString().toUpperCase().trim();
	}

	public static String makeMD5(String s)
	{
		try
		{
			byte[] bs = s.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] d = md.digest(bs);
			return byte2String(d);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static String byte2String(byte[] bs)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bs.length; i++)
		{
			sb.append(String.format("%02X", bs[i]));
		}
		return sb.toString();
	}

	/**
	 * 判断2个日期是否在同一天
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date, Date date2)
	{
		String str = formatDate(date, "yyyyMMdd");
		String str2 = formatDate(date2, "yyyyMMdd");
		return str.equals(str2);
	}

	public static Long strDateToLong(String date) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sdf.parse(date);
		return dt.getTime();
	}

	public static Long strDateTimeToLong(String date) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = sdf.parse(date);
		return dt.getTime();
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException
	{
		if (!file.exists())
		{
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}

	public static void makeDir(File dir)
	{
		if (!dir.getParentFile().exists())
		{
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] importExcel(MultipartFile multipartFile,
			int ignoreRows) throws FileNotFoundException, IOException
	{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(
				multipartFile.getInputStream());
		// 打开HSSFWorkbook
		HSSFWorkbook wb = null;
		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(in);
			wb = new HSSFWorkbook(fs);
		} catch (Exception e)
		{

			e.printStackTrace();
		}

		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++)
		{
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++)
			{
				HSSFRow row = st.getRow(rowIndex);
				if (row == null)
				{
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize)
				{
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++)
				{
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null)
					{
						// 注意：一定要设成这个，否则可能会出现乱码
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType())
						{
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
						case HSSFCell.CELL_TYPE_FORMULA:
							if (HSSFDateUtil.isCellDateFormatted(cell))
							{
								Date date = cell.getDateCellValue();
								if (date != null)
								{
									value = new SimpleDateFormat("yyyy-MM-dd")
											.format(date);
								} else
								{
									value = "";
								}
							} else
							{
								value = String.valueOf(cell
										.getNumericCellValue());
								if (value.equals("NaN"))
								{// 如果获取的数据值为非法值,则转换为获取字符串
									value = cell.getRichStringCellValue()
											.toString();
								}
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
									: "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals(""))
					{
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}

				if (hasValue)
				{
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++)
		{
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str)
	{
		if (str == null)
		{
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--)
		{
			if (str.charAt(i) != 0x20)
			{
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	/**
	 * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * 
	 * @param sourceDate
	 * @param formatLength
	 * @return 重组后的数据
	 */
	public static String frontCompWithZore(int sourceDate, int formatLength)
	{
		/*
		 * 0 指前面补充零 formatLength 字符总长度为 formatLength d 代表为正数。
		 */
		String newString = String.format("%0" + formatLength + "d", sourceDate);
		return newString;
	}
	/**
	 * double四舍五入
	 * @param data
	 * @return
	 */
	public static Double getRoundDouble(double data)
	{

		DecimalFormat df = new DecimalFormat("#.##");    
		double get_double = Double.parseDouble(df.format(data));  

		return get_double;
	}

	/**
	 * 发送邮件
	 *
	 * @author Yangyang.zhang
	 * @param toMail
	 * @param mailTitle
	 * @param mailContent
	 * @param email
	 * @param password
	 * @param File
	 *            附件
	 * @return
	 */
	public static String sendMail(String toMail, String mailTitle,
			String mailContent, String email, String password, File file) {
		String mess = "";
		String[] str = email.split("@");
		String userName = str[0];
		String host = "";
		if ("wizarpos.com".equals(str[1]) || "91huishang.com".equals(str[1])) {
			host = "smtp.exmail.qq.com";
			userName = email;
		} else {
			host = "smtp." + str[1];
		}

		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		// 设定mail server
		senderImpl.setHost(host);
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, "GBK");
			// 设置收件人，寄件人
			messageHelper.setTo(toMail);
			messageHelper.setFrom(email);
			messageHelper.setSubject(mailTitle);
			// true 表示启动HTML格式的邮件
			messageHelper.setText(mailContent, true);
			// 添加附件的内容
			if (file != null) {
				FileSystemResource sfile = new FileSystemResource(file);
				// 这里的方法调用和插入图片是不同的。
				messageHelper.addAttachment("密钥.zip", file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		senderImpl.setUsername(userName); // 根据自己的情况,设置username
		senderImpl.setPassword(password); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		prop.put("mail.transport.protocol", "smtp"); // 设置邮件的传输协议是smtp
		senderImpl.setJavaMailProperties(prop);
		try {
			// 发送邮件
			senderImpl.send(mailMessage);
			mess = "0";// 邮件发送成功！
		} catch (Exception e) {
			e.printStackTrace();
			mess = "1";// 邮件发送失败！（邮箱用户名，密码错误或寄件人邮箱是未开通POP3/SMTP服务！）
		}
		return mess;
	}

	@SuppressWarnings("unused")
	@Async
	public static JSONObject post(JSONObject dataJson) {
		JSONObject resJson = null;
		try {
			resJson = postMember(dataJson);
			logger.info("获取结果：" + resJson.toString());
		} catch (Exception e) {
			throw new RuntimeException("请求失败");
		}
		if (resJson == null) {
			throw new RuntimeException("请求失败");
		}
		return resJson;
	}

	private static JSONObject postMember(JSONObject json) throws Exception {
		return post(Constant.MEMBER_SERVICE_URL, json);
	}

	private static JSONObject post(String url, JSONObject json)
			throws Exception {
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(10000).setConnectTimeout(10000)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();
		CloseableHttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(requestConfig).build();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		// TODO 请求报文需要签名
		logger.debug("request json:" + json.toJSONString());
		try {
			StringEntity postEntity = new StringEntity(json.toJSONString(),
					ContentType.create(JsonContentType, WPCharset));
			httpPost.setEntity(postEntity);
			response = httpClient.execute(httpPost);
			logger.debug("StatusCode:"
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("Access failed");
			}
			byte[] result = EntityUtils.toByteArray(response.getEntity());
			// System.out.print(response.getEntity().getContent());
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

	private static JSONObject parseResult(byte[] response) throws Exception {
		JSONObject json = null;
		if ((response == null) || (response.length == 0)) {
			throw new Exception("无响应报文");
		}
		String content = toString(response, WPCharset);
		logger.debug("response json:" + content);
		json = JSONObject.parseObject(content);
		// TODO 响应报文需要验证签名
		return json;
	}

	private static String toString(byte[] content, String charsetName)
			throws Exception {
		try {
			return new String(content, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Unsupported encoding");
		}
	}
}
