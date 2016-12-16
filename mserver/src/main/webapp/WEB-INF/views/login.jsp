<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>  
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.LockedAccountException "%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>登录</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link href="${ctx}/static2/ace/css/bootstrap.css" rel="stylesheet">	
		<link rel="stylesheet" href="${ctx}/static2/ace/css/font-awesome.css" />
		<!-- text fonts -->
		<link rel="stylesheet" href="${ctx}/static2/ace/css/ace-fonts.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx}/static2/ace/css/ace.css" />
		<!--[if lte IE 9]>
			<link rel="stylesheet" href="../assets/css/ace-part2.css" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx}/static2/ace/css/ace-rtl.css" />
        <script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="../assets/css/ace-ie.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="../assets/js/html5shiv.js"></script>
		<script src="../assets/js/respond.js"></script>
		<![endif]-->
		
	<script>
		//TOCMAT重启、sesion超时 跳转登录首页 
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
		
	<script type="text/javascript">
	
			function setCookie(c_name,value,expiredays){
			   var exdate=new Date()
			   exdate.setDate(exdate.getDate()+expiredays)
			   document.cookie=c_name+ "=" +escape(value)+ ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
			}
			
			function getCookie(c_name){
				if (document.cookie.length>0){
			  		c_start=document.cookie.indexOf(c_name + "=")
			  		if (c_start!=-1){ 
			    		c_start=c_start + c_name.length+1 
			    		c_end=document.cookie.indexOf(";",c_start)
			    		if (c_end==-1) c_end=document.cookie.length
			    			return unescape(document.cookie.substring(c_start,c_end))
			   		 } 
			 	 }
				return ""
			}
			function delCookie(name){ 
			    var exp = new Date(); 
			    exp.setTime(exp.getTime() - 1); 
			    var cval=getCookie(name); 
			    if(cval!=null) 
			        document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
			}
			

			$(function() {
				var loginName = getCookie("cookie_loginName");
				$("#username").val(loginName);
			})
			
					
			
			document.onkeydown = function(e) {  
				var keyCode = e.keyCode;
				if(keyCode == 13) {
					var c= document.getElementById("login-box").className;
					var islogin = c.indexOf("visible")==-1?false:true;
					if (islogin) {
						login();
					}
				}
			}
				
		
		
		function enter(e) {
			var keyCode = e.keyCode;
			if(keyCode == 13) {
				var c=document.getElementById("login-box").className;
				var islogin = c.indexOf("visible")==-1?false:true;
				if (islogin) {
					login();
				}
			}
		}
		//刷新验证码
		function updateImage() {  
			var obj=document.getElementById("kaptchaImage");
			obj.setAttribute('src',"${ctx}/captcha-image");
			}  
			
		//找回
		function lostPassword() {
			window.location.href="${ctx}/lostPassword1";
			}
	</script>	
		
	<script type="text/javascript">
		function login() {
			var res = checkForm();
			var name = $("#username").val();
			var flag =document.getElementById("remember-me").checked;	
			if (flag) {
				setCookie("cookie_loginName", name, 7);
			}
			if(res) {
				change();
				$("#loginForm").submit();
			}
		}
		function checkForm(){
			var line=document.getElementById("error");
			var username=document.getElementById("username").value;
			var password=document.getElementById("password").value;
			var kaptcha=document.getElementById("kaptcha").value;
			if(username==""){
				$("#username").tips({
       				side:3,
       				msg:'用户名不能为空!',
       				bg:'#FF0000',
       				time:3
       			});
				return false;
			}
			if(password==""){
				$("#password").tips({
   					side:3,
   					msg:'密码不能为空!',
   					bg:'#FF0000',
   					time:3
   				});
				return false;
			}
			if(kaptcha==""){
				$("#kaptcha").tips({
   					side:3,
   					msg:'验证码不能为空!',
   					bg:'#FF0000',
   					time:3
   				});
				return false;
			}
			return true;
			
		}
			function change(){
				var oS=document.getElementById("log");
				log.innerHTML="登录中...";
				return true;
			}
			
//忘记密码step1
function reSetPassword1() {
	$.ajax({
		url:"${ctx}/lostPassword2",
		data:$("#step1From").serialize(),
		method:'GET',
		success:function(data) {
			if (data.code=='0') {
				changeCode2();
				$("#from1LoginName").tips({
   					side:3,
   					msg:data.message,
   					bg:'#FF0000',
   					time:3
   				});
			} else if(data.code=='-1') {
				$("#kaptcha2").tips({
   					side:3,
   					msg:data.message,
   					bg:'#FF0000',
   					time:3
   				});
			} else if(data.code=='-2') {
				$("#kaptcha2").tips({
   					side:3,
   					msg:data.message,
   					bg:'#FF0000',
   					time:3
   				});
				changeCode2();
			} else if(data.code=='1') {
				$("#step1").css("display","none");
				$('#step2').css("display","block");
				$('#loginName').val(data.loginName);
				$('#agentId').val(data.agentId);
			}
		}
	})
}
//忘记密码step2
function reSetPassword2() {
	$.ajax({
		url:"${ctx}/lostPassword3",
		data:$("#step2From").serialize(),
		method:'GET',
		success:function(data) {
			if (data.code=='0') {
				$("#sendbtn").tips({
   					side:3,
   					msg:data.message,
   					bg:'#FF0000',
   					time:3
   				});
			} else if(data.code=='1') {
				$("#step2").css("display","none");
				$('#step3').css("display","block");
				$('#loginName2').val(data.loginName);
			}
		}
	})
}
//忘记密码step3
function reSetPassword3() {
	if (checkPass()&&checkPass2()) {
		$.ajax({
			url:"${ctx}/lostPassword4",
			data:$("#step3From").serialize(),
			method:'GET',
			success:function(data) {
				if (data.code=='0') {
					$("#sendbtn").tips({
	   					side:3,
	   					msg:data.message,
	   					bg:'#FF0000',
	   					time:3
	   				});
				} else if(data.code=='1') {
					$("#step3").css("display","none");
					$('#step1').css("display","block");
					$("#goLogin").click();
				}
			}
		})
	}
}
//更换验证码1
function changeCode() {
	$('#kaptchaImage').click();
	$("#step1").css("display","block");
	$('#step2').css("display","none");
	$('#step3').css("display","none");
}
//更换验证码2
function changeCode2() {
	$('#kaptchaImage2').click();
	$('#from1LoginName').val('');
	$('#kaptcha2').val('');
}
function sendAuthCode() {
	$("#sendbtn").attr("href","javaScript:;");
	var num = 60;
	var f=setInterval(function(){
		num=num-1;
		if(num=="0"){
		    $("#sendbtn").attr("href","javaScript:sendAuthCode();");
		    $("#sendbtn").html("发送验证码");
		    clearInterval(f);
		    return;
		}
		$("#sendbtn").html(num);
	},1000);
	$.ajax({
		url:"${ctx}/lostPassword_sendCode",
		data:$("#step2From").serialize(),
		type:'post',
		async:false,
		success:function(data) {
			$("#sendbtn").tips({
				side:3,
				msg:data.message,
				bg:'#FF0000',
				time:3
			});
		},
		error:function(data){
			$("#sendbtn").tips({
				side:3,
				msg:'验证码发送失败！',
				bg:'#FF0000',
				time:3
			});
		}
	})
}
function checkPass() {
	var password = $("#newPass").val();
	if (password==''||password==null) {
		$("#newPass").tips({
			side:3,
			msg:'密码不能为空！',
			bg:'#FF0000',
			time:3
		});
		return false;
	} else{
		return true;
	}
}
function checkPass2() {
	var password = $("#newPass").val();
	var password2 = $("#newPass2").val();
	if (password==''||password==null) {
		$("#newPass2").tips({
			side:3,
			msg:'密码不能为空！',
			bg:'#FF0000',
			time:3
		});
		return false;
	} else if (password!=password2) {
		$("#newPass2").tips({
			side:3,
			msg:'两次输入不一致！',
			bg:'#FF0000',
			time:3
		});
		return false;
	}else{
		return true;
	}
}
</script>	
	</head>
	<body class="login-layout light-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<%-- <div class="center" style="padding:20px 0 15px 0;">
								<img height="50" src="${ctx}/static2/images/login/logo.png">
								<img height="50" src="${ctx}/static2/images/login/xinlan.png">
							</div>
 --%>
							<div class="position-relative" style="margin-top: 120px;">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												登录
											</h4>
											
											<div id="error" class="error" style="color: red;text-align: center;" >
												<%
													String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
													if(error != null){
														if(error.contains("DisabledAccountException")){
															/* out.print("用户已被屏蔽,请登录其他用户."); */
															%>
															<script type="text/javascript">      
												       			$(function(){           
												       				$("#username").tips({
												       					side:3,
												       					msg:'用户已被屏蔽,请登录其他用户！',
												       					bg:'#FF0000',
												       					time:3
												       				});      
												                 });   
												      		</script>
															<%
														}
														else{
															if(error.contains("CaptchaException")){
																/* out.print("验证码错误！"); */
																%>
																<script type="text/javascript">      
													       			$(function(){           
													       				$("#kaptcha").tips({
													       					side:3,
													       					msg:'验证码错误！',
													       					bg:'#FF0000',
													       					time:3
													       				});      
													                 });   
													      		</script>
																<%
															}else{
																if(error.contains("NoSuchUserException")){
																	/* out.print("用户名不存在!"); */
																	%>
																	<script type="text/javascript">      
														       			$(function(){           
														       				$("#username").tips({
														       					side:3,
														       					msg:'用户名不存在!',
														       					bg:'#FF0000',
														       					time:3
														       				});      
														                 });   
														      		</script>
																	<%
																}
																else{
																	 /* out.print("密码错误!");  */
																	%>
																	<script type="text/javascript">      
														       			$(function(){           
														       				$("#password").tips({
														       					side:3,
														       					msg:'密码错误!',
														       					bg:'#FF0000',
														       					time:3
														       				});      
														                 });   
														      		</script>
																	<%
																}
															}
															
														}
													}
												%>
											</div>
											<div class="space-6"></div>
											<form action="${ctx}/login" method="post" onsubmit="change()" id="loginForm"> 
												<fieldset>
                                                    <label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名"  id="username" name="username" maxlength="20"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" id="password" name="password" maxlength="20"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
                                                    <label class="block clearfix">
														<span class="block width-60 pull-left">
															<input type="text" class="form-control" placeholder="验证码"  id="kaptcha" name="captcha" maxlength="4"/>
														</span>
                                                        <span class="block width-30 pull-left">
                                                        	<img src="${ctx}/captcha-image" id="kaptchaImage"  style="width:101px;height:40px;cursor:pointer" alt="点击更换" title="点击更换"/>
                                                        		<script type="text/javascript">      
												       				 $(function(){           
												        			    $('#kaptchaImage').click(function (){//生成验证码  
												           					$(this).hide().attr('src', '${ctx}/captcha-image?random='+Math.random().toString()).fadeIn();
												            			})      
												                 	 });   
												      			</script>
                                                        </span>
													</label>
													<div class="space"></div>
													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" id="remember-me" class="ace" />
															<span class="lbl"> 记住账号</span>
														</label>

														<button type="button" onclick="login()" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>   
															<span class="bigger-110" id="log">登录</span>
														</button>
													</div>
													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" onclick="changeCode2();" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													忘记密码
												</a>
											</div>

											<!-- <div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													注册
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div> -->
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<div id="step1">
												<h4 class="header red lighter bigger">
													<i class="ace-icon fa fa-key"></i>
													忘记密码--确认帐号
												</h4>
												<div class="space-6"></div>
												<form id="step1From" action="${ctx}/lostPassword2" method="post">
													<fieldset>
														<span id="step1Mess" style="color: red;"></span>
														<label class="block clearfix">
															<span class="block input-icon input-icon-right">
																<input type="text" id="from1LoginName" name="loginName" class="form-control" placeholder="用户名" />
																<i class="ace-icon fa fa-user"></i>
															</span>
														</label>
	                                                    <label class="block clearfix">
															<span class="block width-60 pull-left">
																<input type="text" class="form-control" placeholder="验证码"  id="kaptcha2" name="authCode" maxlength="4"/>
															</span>
	                                                        <span class="block width-30 pull-left">
	                                                        	<img src="${ctx}/captcha-image" id="kaptchaImage2"  style="width:101px;height:40px;cursor:pointer" alt="点击更换" title="点击更换"/>
	                                                        		<script type="text/javascript">      
													       				 $(function(){           
													        			    $('#kaptchaImage2').click(function (){//生成验证码  
													           					$(this).hide().attr('src', '${ctx}/captcha-image?random='+Math.random().toString()).fadeIn();
													            			})      
													                 	 });   
													      			</script>
	                                                        </span>
														</label>
														<div class="clearfix">
															<button type="button" onclick="reSetPassword1();" class="width-35 pull-right btn btn-sm btn-danger">
																<i class="ace-icon fa fa-lightbulb-o"></i>
																<span class="bigger-110">下一步</span>
															</button>
														</div>
													</fieldset>
												</form>
											</div>
											<div id="step2" style="display: none;">
												<h4 class="header red lighter bigger">
													<i class="ace-icon fa fa-key"></i>
													忘记密码--安全验证
												</h4>
												<div class="space-6"></div>
												<form id="step2From" action="/lostPassword3" method="post">
													<fieldset>
														<%-- <span id="sendMess" style="color: red;">${mess}</span> --%>
														<label class="block clearfix">
															<span class="block width-30 pull-left">
														    	验证方式
	                                                        </span>
															<span class="block input-icon input-icon-right">
																<select style="width: 180px;" class="ls-select psw-select" name="checkType">
												                    <option value="2" <c:if test="${checkType eq '2'}">selected="selected"</c:if> >邮箱</option>
												                	<%-- <option value="1" <c:if test="${checkType eq '1'}">selected="selected"</c:if> >手机号码</option> --%>
												                </select>
												                <input class="psw-input" id="loginName" name="loginName" type="hidden" value="${loginName}" />
												                <input class="psw-input" id="agentId" name="agentId" type="hidden" value="${agentId}" />
															</span>
														</label>
	                                                    <label class="block clearfix">
	                                                        <span class="block width-30 pull-left">
														    	验证码
	                                                        </span>
	                                                        <span class="block width-30 pull-left">
														        	<input class="psw-input" name="authCode" type="text" maxlength="6" value="" />&nbsp;&nbsp;
	                                                        </span>
														</label>
														<div class="clearfix">
															<button id="sendCodeButton" type="button" onclick="sendAuthCode();" class="width-40 pull-left btn btn-sm btn-primary">
																<i class="ace-icon fa fa-key"></i>   
																<span class="bigger-110" id="sendbtn">发送验证码</span>
															</button>
															<button type="button" onclick="reSetPassword2();" class="width-35 pull-right btn btn-sm btn-danger">
																<i class="ace-icon fa fa-lightbulb-o"></i>
																<span class="bigger-110">下一步</span>
															</button>
														</div>
													</fieldset>
												</form>
											</div>
											<div id="step3" style="display: none;">
												<h4 class="header red lighter bigger">
													<i class="ace-icon fa fa-key"></i>
													忘记密码--重置密码
												</h4>
												<div class="space-6"></div>
												<form id="step3From" action="/lostPassword4" method="post">
													<fieldset>
														<label class="block clearfix">
															<span class="block input-icon input-icon-right">
															<input class="psw-input" id="loginName2" name="loginName" type="hidden" value="${loginName}" />
																<input type="text" id="newPass" name="newPass" onblur="checkPass()" class="form-control" placeholder="新密码" />
																<i class="ace-icon fa fa-lock"></i>
															</span>
														</label>
	                                                    <label class="block clearfix">
															<span class="block input-icon input-icon-right">
																<input type="text" id="newPass2" name="newPass2" onblur="checkPass2()" class="form-control" placeholder="密码确认" />
																<i class="ace-icon fa fa-lock"></i>
															</span>
														</label>
														<div class="clearfix">
															<button type="button" onclick="reSetPassword3();" class="width-35 pull-right btn btn-sm btn-danger">
																<i class="ace-icon fa fa-lightbulb-o"></i>
																<span class="bigger-110">确定</span>
															</button>
														</div>
													</fieldset>
												</form>
											</div>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a id="goLogin" onclick="changeCode();" href="#" data-target="#login-box" class="back-to-login-link">
												返回登录页面
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">Reset</span>
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															<span class="bigger-110">Register</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												Back to login
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

							<!-- <div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; 
							</div> -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='../assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});
			
			
			
			//you don't need this, just used for changing background
			jQuery(function($) {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			 
			});
		</script>
	</body>
</html>