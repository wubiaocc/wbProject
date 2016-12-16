package com.finance.communication.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 基本工具类
 * 
 */
public class Tools {

	/** 由id做累加，防止重复 */
	private static int idNum = 0;
	private static Properties prop = null;
	private static Properties cons_prop = null;

	public static String getEnv(String name) {
		if (prop == null) {
			prop = new Properties();
			try {
				ConfigUtil configUtil = SpringContextHolder.getBean("configUtil");

				prop.load(new InputStreamReader(
						Tools.class.getClassLoader().getResourceAsStream(configUtil.getResourceAsStreamPath()),
						"UTF-8"));
			} catch (IOException e) {
			}
		}
		return prop.getProperty(name);
	}

	public static String getConstargs(String name) {
		if (cons_prop == null) {
			cons_prop = new Properties();
			try {
				cons_prop.load(Tools.class.getResourceAsStream("/constargs.properties"));
			} catch (IOException e) {
			}
		}
		return cons_prop.getProperty(name);
	}

	public static boolean eq(String a, String b, boolean isTrim) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (isTrim) {
			if (a.trim().equals(b.trim())) {
				return true;
			}
		}
		if (a.equals(b)) {
			return true;
		}
		return false;
	}

	public static boolean eq(String a, String b) {
		return eq(a, b, false);
	}

	public static Map<String, String> getParamMap(String str, String division) {
		Map<String, String> map = new HashMap<String, String>();
		String[] params = str.split(division);
		for (String param : params) {
			int i = param.indexOf("=");
			if (i == -1) {
				map.put(param, "");
			} else {
				map.put(param.substring(0, i), param.substring(i + 1));
			}
		}
		return map;
	}

	public static void bufferedWriteAndCloseStream(OutputStream outStream, InputStream inStream) throws IOException {
		BufferedOutputStream boutStream = new BufferedOutputStream(outStream);
		BufferedInputStream binStream = new BufferedInputStream(inStream);

		int length = -1;
		byte[] cache = new byte[1024 * 8];
		while ((length = binStream.read(cache)) != -1) {
			boutStream.write(cache, 0, length);
			boutStream.flush();
		}
		boutStream.close();
		binStream.close();
	}

	public static void bufferedWrite(OutputStream outStream, InputStream inStream) throws IOException {
		BufferedOutputStream boutStream = new BufferedOutputStream(outStream);
		BufferedInputStream binStream = new BufferedInputStream(inStream);

		int length = -1;
		byte[] cache = new byte[1024 * 8];
		while ((length = binStream.read(cache)) != -1) {
			boutStream.write(cache, 0, length);
			boutStream.flush();
		}
	}

	public static void bufferedWriteAndCloseStream(OutputStream outStream, Reader reader) throws IOException {
		BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(outStream));
		BufferedReader breader = new BufferedReader(reader);

		int length = -1;
		char[] cache = new char[1024 * 8];
		while ((length = breader.read(cache)) != -1) {
			bwriter.write(cache, 0, length);
			bwriter.flush();
		}
		bwriter.close();
		breader.close();
	}

	public static void bufferedWrite(OutputStream outStream, Reader reader) throws IOException {
		BufferedWriter bwriter = new BufferedWriter(new OutputStreamWriter(outStream));
		BufferedReader breader = new BufferedReader(reader);

		int length = -1;
		char[] cache = new char[1024 * 8];
		while ((length = breader.read(cache)) != -1) {
			bwriter.write(cache, 0, length);
			bwriter.flush();
		}
	}

	public static void write(OutputStream outStream, byte[] data) throws IOException {
		outStream.write(data);
		outStream.flush();
	}

	public static void bufferedWriteAndCloseStream(OutputStream outStream, String path, String charset)
			throws IOException {
		bufferedWriteAndCloseStream(outStream, new InputStreamReader(new FileInputStream(path), charset));
	}

	/**
	 * 获取时间戳(yyyyMMddHHmmssSSS)
	 * 
	 * @return 时间戳
	 */
	public static String getId() {
		if (idNum > 999) {
			idNum = 0;
		}
		return getFormatDate("yyyyMMddHHmmssSSS" + (idNum++));
	}

	/**
	 * 格式化指定日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return 格式化的日期字符串
	 */
	public static String getFormatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 格式化当天日期
	 * 
	 * @param pattern
	 *            指定格式
	 * @return 格式化的日期字符串
	 */
	public static String getFormatDate(String pattern) {
		return getFormatDate(new Date(), pattern);
	}

	public static String getID() {
		return getFormatDate("yyyyMMddHHmmssSSS" + (num++));
	}

	/**
	 * url编码
	 * 
	 * @param url
	 *            资源定位符
	 * @param encoding
	 *            编码格式
	 * @param urlEncoded
	 *            是否是URLEncoded
	 * @return 编码后的串。
	 */
	public static String encodeUrl(String url, String encoding, Boolean urlEncoded) {
		if (url == null || "".equals(url)) {
			return "";
		}
		if (encoding == null || "".equals(encoding)) {
			return url;
		}
		if (urlEncoded == null) {
			return url;
		}
		if (url.indexOf("?") == -1) {
			StringBuilder s = new StringBuilder();
			try {
				char[] cs = url.toCharArray();
				for (char c : cs) {
					if (c > 0x80) { // 对非ASCII，进行编码
						s.append(URLEncoder.encode(c + "", encoding));
					} else {
						s.append(c);
					}
				}
			} catch (UnsupportedEncodingException e1) {
				throw new RuntimeException("不支持的编码格式:" + encoding);
			}
			return s.toString();
		}
		String baseUrl = url.substring(0, url.indexOf("?") + 1);
		String paramStr = url.substring(url.indexOf("?") + 1);
		StringBuilder sb = new StringBuilder(baseUrl);

		String[] params = paramStr.split("&");
		for (String param : params) {
			int eqIndex = param.indexOf("=");
			String proStr = param.substring(eqIndex + 1);
			String newStr = "";
			if (urlEncoded == true) {
				try {
					newStr = URLEncoder.encode(proStr, encoding);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("不支持的编码格式:" + encoding);
				}
			} else {
				try {
					newStr = new String(proStr.getBytes(), encoding);
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException("不支持的编码格式:" + encoding);
				}
			}
			sb.append(param.substring(0, eqIndex + 1)).append(newStr).append("&");
		}
		if (params.length > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public static String hexString(byte[] b) {
		StringBuffer d = new StringBuffer();
		for (byte element : b) {
			char hi = Character.forDigit((element >> 4) & 0x0F, 16);
			char lo = Character.forDigit(element & 0x0F, 16);
			d.append(Character.toUpperCase(hi));
			d.append(Character.toUpperCase(lo));
		}
		return d.toString();
	}

	/**
	 * 格式化指定日期
	 * 
	 * @param pattern
	 *            指定格式
	 * @return 格式化的日期字符串
	 * @throws ParseException
	 */
	public static Date getOrderFormatDate(String date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		Date nowDate = format.parse(date);

		return nowDate;
	}

	/**
	 * 格式化指定日期
	 * 
	 * @param pattern
	 *            指定格式
	 * @return 格式化的日期字符串
	 * @throws ParseException
	 */
	public static Date getOrderFormatDate(Date date, String pattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);

		String formatDate = format.format(date);

		Date nowDate = format.parse(formatDate);

		return nowDate;
	}

	/**
	 * IO的流转操作。可能涉及到网络流、文件流等
	 * 
	 * @param inStream
	 *            输入流
	 * @param outStream
	 *            输出流
	 */
	public static void writeFile(InputStream inStream, OutputStream outStream) {
		try {
			byte[] cache = new byte[1024 * 4];
			int length = -1;
			while ((length = inStream.read(cache)) != -1) {
				outStream.write(cache, 0, length);
				outStream.flush();
			}
		} catch (Exception e) {
			throw new RuntimeException("M-Adaptor write file error:", e);
		}
	}

	public static String base64Encoder(String content) {
		BASE64Encoder base64 = new BASE64Encoder();
		try {
			return base64.encode(content.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	public static String base64Encoder(byte[] data) {
		BASE64Encoder base64 = new BASE64Encoder();
		return base64.encode(data);
	}

	public static String base64Decoder(String content) {
		return new String(base64DecoderToBytes(content));
	}

	public static byte[] base64DecoderToBytes(String content) {
		BASE64Decoder base64 = new BASE64Decoder();
		try {
			return base64.decodeBuffer(content);
		} catch (IOException e) {
			throw new RuntimeException("Base64解码错误", e);
		}
	}

	public static String base64Decoder(String content, String charset) {
		BASE64Decoder base64 = new BASE64Decoder();
		try {
			return new String(base64.decodeBuffer(content), charset);
		} catch (IOException e) {
			throw new RuntimeException("Base64解码错误", e);
		}
	}

	public static String getFileName(String fileLocation) {
		if (fileLocation.indexOf("/") == -1) {
			return fileLocation;
		}
		if (fileLocation.endsWith("/")) {
			throw new RuntimeException("the file location don't contain file.");
		}
		int index = fileLocation.lastIndexOf("/");
		return fileLocation.substring(index + 1);
	}

	// HTML字符转换表
	public final static Map<String, String> HTML_CHAR = new HashMap<String, String>();

	static {
		HTML_CHAR.put("&", "&#38;");
		HTML_CHAR.put("\"", "&#34;");
		HTML_CHAR.put("<", "&#60;");
		HTML_CHAR.put(">", "&#62;");
		HTML_CHAR.put("'", "&#39;");
	}

	public static String dealStrHtml(String html) {
		String str = html.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "")
				.replace("\r", "").replace("\n", "").replace("\t", "").replace("&nbsp;", "");
		try {
			str = full2HalfChange(str);
		} catch (UnsupportedEncodingException e) {
			System.out.println("格式化摘要异常：：" + e);
		}
		str = str.replace(" ", "");
		return str;
	}

	// 全角转半角的 转换函数
	public static final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {

		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);

			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}

			b = Tstr.getBytes("unicode"); // 得到 unicode 字节数据

			if (b[3] == -1) { // 表示全角？
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;

				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}

		} // end for.

		return outStrBuf.toString();
	}

	public static final StringBuilder toHTMLChar(String str) {
		if (str == null) {
			return new StringBuilder();
		}
		StringBuilder sb = new StringBuilder(str);

		char tempChar;
		String tempStr;
		for (int i = 0; i < sb.length(); i++) {
			tempChar = sb.charAt(i);
			if (HTML_CHAR.containsKey(Character.toString(tempChar))) {
				tempStr = HTML_CHAR.get(Character.toString(tempChar));
				sb.replace(i, i + 1, tempStr);
				i += tempStr.length() - 1;
			}
		}
		return sb;
	}

	// 应用程序id计数使用
	private static int num = 0;

	public static int getNum() {
		return num++;
	}

	/**
	 * 换算两点之间的距离
	 * 
	 */
	static double DEF_PI = 3.14159265359; // PI

	static double DEF_2PI = 6.28318530712; // 2*PI

	static double DEF_PI180 = 0.01745329252; // PI/180.0

	static double DEF_R = 6370693.5; // radius of earth

	public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2)

	{

		double ew1, ns1, ew2, ns2;

		double dx, dy, dew;

		double distance;

		// 角度转换为弧度

		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;

		ew2 = lon2 * DEF_PI180;

		ns2 = lat2 * DEF_PI180;

		// 经度差

		dew = ew1 - ew2;

		// 若跨东经和西经180 度，进行调整

		if (dew > DEF_PI) {
			dew = DEF_2PI - dew;
		} else if (dew < -DEF_PI) {
			dew = DEF_2PI + dew;
		}

		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)

		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)

		// 勾股定理求斜边长

		distance = Math.sqrt(dx * dx + dy * dy);

		return distance;

	}

	public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2)

	{

		double ew1, ns1, ew2, ns2;

		double distance;

		// 角度转换为弧度

		ew1 = lon1 * DEF_PI180;

		ns1 = lat1 * DEF_PI180;

		ew2 = lon2 * DEF_PI180;

		ns2 = lat2 * DEF_PI180;

		// 求大圆劣弧与球心所夹的角(弧度)

		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);

		// 调整到[-1..1]范围内，避免溢出

		if (distance > 1.0) {
			distance = 1.0;
		} else if (distance < -1.0) {
			distance = -1.0;
		}

		// 求大圆劣弧长度

		distance = DEF_R * Math.acos(distance);

		return distance;

	}

	public static void createDir(String filePath) {
		if (!new File(filePath).mkdirs())
			System.out.println("The folder exists");
		else
			System.out.println("The folder create success");
	}

	public static int NextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());

		return tmp % (max - min + 1) + min;
	}

	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	private static Logger logger = LoggerFactory.getLogger(Tools.class);
	
	/**
	 * Java 执行mysql 脚本
	 * 
	 * @param buffer
	 */
	public static void antSQLExec(List<File> files) {

		try {
			ConfigUtil configUtil = SpringContextHolder.getBean("configUtil");

			if (!files.isEmpty()) {

				// String jdbcUrl = configUtil.getJdbcUrl();
				// if (StringUtils.isNotEmpty(jdbcUrl)) {
				//
				// String username =
				// ConfigTools.decrypt(configUtil.getJdbcUserName());// 用户名
				// String password =
				// ConfigTools.decrypt(configUtil.getJdbcPassword());// 密码
				// String host = jdbcUrl.substring(jdbcUrl.indexOf("//") + 2,
				// jdbcUrl.indexOf("3306") - 1);// 导入的目标数据库所在的主机
				// String port = "3306";// 使用的端口号
				// String importDatabaseName =
				// jdbcUrl.substring(jdbcUrl.indexOf("3306") + 5,
				// jdbcUrl.indexOf("?"));// 导入的目标数据库的名称
				// String importPath = file.getAbsolutePath();// 导入的目标文件所在的位置
				//
				// // 第一步，获取登录命令语句
				// String loginCommand = new StringBuffer()
				// .append("/usr/local/mysql-5.6.28-osx10.8-x86_64/bin/mysql
				// -u").append(username)
				// .append(" -p").append(password).append("
				// --default-character-set=utf8 ").append(" -h")
				// .append(host).append(" -P").append(port).toString();
				// // 第二步，获取切换数据库到目标数据库的命令语句
				// String switchCommand = new StringBuffer("use
				// ").append(importDatabaseName).toString();
				// // 第三步，获取导入的命令语句
				// String importCommand = new StringBuffer("source
				// ").append(importPath).toString();
				// // 需要返回的命令语句数组
				// String[] commands = new String[] { loginCommand,
				// switchCommand, importCommand };
				// // runtime.exec(cmdarray);//这里也是简单的直接抛出异常
				//
				// Runtime runtime = Runtime.getRuntime();
				// process = runtime.exec(commands[0]);
				// // 执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口
				// // 进程执行后面的代码
				// OutputStream os = process.getOutputStream();
				// OutputStreamWriter writer = new OutputStreamWriter(os);
				// // 命令1和命令2要放在一起执行
				// writer.write(commands[1] + "\r\n" + commands[2]);
				// writer.flush();
				// writer.close();
				// os.close();
				//
				//// new ProcessClearStream(process.getInputStream(),
				// "INFO").start();
				//// new ProcessClearStream(process.getErrorStream(),
				// "ERROR").start();
				//
				// int status = process.waitFor();
				// System.out.println("Process exitValue:" + status);
				// // 执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口
				// // 进程执行后面的代码
				//
				// // OutputStreamWriter writer = new OutputStreamWriter(os);
				// // // 命令1和命令2要放在一起执行
				// // writer.write(commands[1] + "\r\n" + commands[2]);
				// // writer.flush();
				// // writer.close();
				// // os.close();
				// }
				SQLExec sqlExec = new SQLExec();
				// 设置数据库参数
				sqlExec.setDriver(configUtil.getJdbcDriver());
				sqlExec.setUrl(configUtil.getJdbcUrl());
				sqlExec.setUserid(ConfigTools.decrypt(configUtil.getJdbcUserName()));
				sqlExec.setPassword(ConfigTools.decrypt(configUtil.getJdbcPassword()));
				sqlExec.setPrint(false); // 设置是否输出
				// 有出错的语句该如何处理
				sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute.getInstance(SQLExec.OnError.class, "abort")));
				// 输出到文件 sql.out 中；不设置该属性，默认输出到控制台
				sqlExec.setOutput(new File("importOut.log"));
				sqlExec.setProject(new Project()); // 要指定这个属性，不然会出错
				for (File file : files) {
					long lastTime = System.currentTimeMillis();
					// 要执行的脚本
					sqlExec.setSrc(file);
					sqlExec.execute();
					long lastTime1 = System.currentTimeMillis();
					logger.warn((lastTime1 - lastTime) / 1000 + "秒,执行文件");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!files.isEmpty()) {
				for (File file : files) {
					file.delete();
				}
			}

		}

	}
}
