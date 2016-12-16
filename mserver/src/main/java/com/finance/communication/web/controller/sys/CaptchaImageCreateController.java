package com.finance.communication.web.controller.sys;

import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.finance.communication.service.server.sys.UserManager;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

@Controller
@RequestMapping("/")
public class CaptchaImageCreateController {
	
	private static final Logger logger = Logger.getLogger(CaptchaImageCreateController.class);

	private Producer captchaProducer = null;
	
	@Autowired
	private UserManager userManager;

	@Autowired
	public void setCaptchaProducer(Producer captchaProducer) {
		this.captchaProducer = captchaProducer;
	}

	@RequestMapping("/captcha-image")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response, Model model, HttpSession session,
			String random) throws Exception {

		response.setDateHeader("Expires", 0);
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		// return a jpeg
		response.setContentType("image/jpeg");
		// create the text for the image
		String capText = captchaProducer.createText();
		// store the text in the session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,
				capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		// write the data out
		ImageIO.write(bi, "jpg", out);
		String code = (String) session
				.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		System.out.println(code);
		model.addAttribute("code", code);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

//	/**
//	 * 忘记密码step1
//	 * 
//	 * @author bin.cheng
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/lostPassword1" ,method = RequestMethod.GET)
//	public String lostPassword1(Model model) {
//		return "lostPassword1";
//	}
//	/**
//	 * 忘记密码step2
//	 * 
//	 * @author bin.cheng
//	 * @param loginName
//	 * @param authCode
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/lostPassword2" ,method = RequestMethod.GET)
//	@ResponseBody
//	public ForgetPassword lostPassword2(String loginName, String authCode, Model model) {
//		ForgetPassword fogetPass = new ForgetPassword();
//		String exitCode = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//		SecurityUtils.getSubject().getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
//		MUser user = userManager.findUserByloginName(loginName);
//		if (user == null) {
////			model.addAttribute("loginName", loginName);
////			model.addAttribute("mesg", "用户不存在！");
//			fogetPass.setCode("0");
//			fogetPass.setMessage("用户不存在!");
//			fogetPass.setLoginName(loginName);
//			return fogetPass;
//		}
//		if (StringUtils.isEmpty(authCode)) {
////			model.addAttribute("loginName", loginName);
////			model.addAttribute("mesg", "验证码不能为空！");
//			fogetPass.setCode("-1");
//			fogetPass.setMessage("验证码不能为空！");
//			fogetPass.setLoginName(loginName);
//			return fogetPass;
//		}
//		if (StringUtils.isEmpty(exitCode)||!exitCode.equalsIgnoreCase(authCode)) {
////			model.addAttribute("loginName", loginName);
////			model.addAttribute("mesg", "验证码错误！");
//			fogetPass.setCode("-2");
//			fogetPass.setMessage("验证码错误！");
//			fogetPass.setLoginName(loginName);
//			return fogetPass;
//		}
//		String agentId = user.getAgentId();
//		fogetPass.setCode("1");
//		fogetPass.setLoginName(loginName);
//		fogetPass.setAgentId(agentId);
//		return fogetPass;
//	}
//	/**
//	 * 忘记密码_生成验证码并发送邮件
//	 * 
//	 * @author bin.cheng
//	 * @param checkType
//	 * @param loginName
//	 * @param authCode
//	 * @param model
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value="/lostPassword_sendCode" ,method = RequestMethod.POST)
//	@ResponseBody
//	public RespMessage lostPasswordSendCode(String checkType, String loginName, 
//			Model model, HttpServletRequest request) {
//		String authCode="";
//		RespMessage resp = new RespMessage();
//		MUser user = userManager.findUserByloginName(loginName);
//		String agentId = user.getAgentId();
//		try {
//			Random random = new Random();
//			for (int i = 0; i < 6; i++) {
//				int str = random.nextInt(10);
//				authCode+=str;
//			}
//			//email.properties
//			String email = Tools.getEnv("COMPANY_EMAIL");
//			String phone = Tools.getEnv("COMPANY_PHONE");
//			
//			//邮件通知
//			if ("2".equals(checkType)) {
//				String toMail = user.getEmail();
//				String mailTitle = "修改密码验证码";
//				String mailContent="<html><head></head><body><h3>尊敬的"
//						+ user.getName()
//						+ "：您好！</h3><p>您的验证码为'"
//						+ authCode
//						+ "'，请于60分钟内使用！</p><p>如有任何疑问，请发邮件至："+email+"或致电："+phone+" 。</p></body></html>";
//				logger.info("发送邮件TO：" + toMail);
//				JSONObject json = new JSONObject();
//				json.put("service_code", "1063");
//				JSONObject data = new JSONObject();
//				data.put("content", mailContent);
//				data.put("mid", agentId);
//				data.put("subject", mailTitle);
//				data.put("toMail", toMail);
//				data.put("attachFlag", "0");
//				json.put("param", data);
//				logger.info("发送邮件请求报文" + json.toJSONString());
//				AppInterface.postMsg2(json.toJSONString(), Constant.SEND_MAIL_URL);
//				//保存验证码邮件记录
//				userManager.saveAuthCode(loginName,authCode,toMail,agentId);
//			}
//			if ("1".equals(checkType)) {
//				System.out.println("尚未开通！");
//			}
//			resp.setMessage("<label style='color: green;'>验证码发送成功！</label>");
//		} catch (Exception e) {
//			resp.setMessage("<label style='color: red;'>验证码发送失败！</label>");
//			e.printStackTrace();
//		} 
//		return resp;
//	}
//	
//	/**
//	 * 忘记密码step3
//	 * 
//	 * @author bin.cheng
//	 * @param checkType
//	 * @param loginName
//	 * @param agentId
//	 * @param authCode
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/lostPassword3" ,method = RequestMethod.GET)
//	@ResponseBody
//	public ForgetPassword lostPassword3(String checkType, String loginName, 
//			String agentId, String authCode, Model model) {
//		ForgetPassword fogetPass = new ForgetPassword();
//		fogetPass.setCheckType(checkType);
//		fogetPass.setLoginName(loginName);
//		fogetPass.setAgentId(agentId);
//		//验证码发邮箱
//		if ("2".equals(checkType)) {
//			//获取最后一次发送的验证码
//			Map<String, Object> map = userManager.getLastAuthCode(loginName, agentId);
//			String exutAuthCode="";
//			String codeMailId="";
//			if (map.containsKey("id")) {
//				codeMailId=map.get("id")!=null?map.get("id").toString():"";
//			}
//			if (map.containsKey("code")) {
//				exutAuthCode=map.get("code")!=null?map.get("code").toString():"";
//			}
//			if (StringUtils.isEmpty(exutAuthCode)||exutAuthCode.length()>6) {
//				fogetPass.setCode("0");
//				fogetPass.setMessage("您尚未获取验证码！");
//				return fogetPass;
//			}
//			//使验证码失效
//			userManager.invalidateLastAuthCode(codeMailId, exutAuthCode, agentId);
//			if (!exutAuthCode.equalsIgnoreCase(authCode)) {
//				fogetPass.setCode("0");
//				fogetPass.setMessage("验证码错误，请重新获取验证码！");
//				return fogetPass;
//			}
//		}
//		//验证码发手机
//		if ("1".equals(checkType)) {
//			//获取最后一次发送的验证码
//			return fogetPass;
//		}
//		fogetPass.setCode("1");
//		return fogetPass;
//	}
//	/**
//	 * 忘记密码step4-重置密码
//	 * 
//	 * @author bin.cheng
//	 * @param loginName
//	 * @param newPass
//	 * @param model
//	 * @return
//	 */
//	@RequestMapping(value="/lostPassword4" ,method = RequestMethod.GET)
//	@ResponseBody
//	public ForgetPassword lostPassword4(String loginName, String newPass, Model model) {
//		ForgetPassword fogetPass = new ForgetPassword();
//		try {
//			userManager.resetPassword(loginName,newPass);
//			fogetPass.setCode("1");
//			fogetPass.setLoginName(loginName);
//			return fogetPass;
//		} catch (Exception e) {
//			e.printStackTrace();
//			fogetPass.setCode("0");
//			fogetPass.setLoginName(loginName);
//			return fogetPass;
//		}
//	}
	/**
	 * 发送邮件
	 * 
	 * @author bin.cheng
	 * @param toMail
	 * @param mailTitle
	 * @param mailContent
	 * @param email
	 * @param password
	 * @return
	 */
	public String sendMail(String toMail, String mailTitle, String mailContent,
			String email, String password) {
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
			mess = "1";// 邮件发送失败！（邮箱用户名，密码错误或寄件人邮箱是未开通POP3/SMTP服务！）
		}
		return mess;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value="/index2" ,method = RequestMethod.GET)
	public String gotoIndex2() {
		return "/index2";
	}
}
