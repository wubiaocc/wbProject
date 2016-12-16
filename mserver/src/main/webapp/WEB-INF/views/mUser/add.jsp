<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
<script src="${ctx}/static2/js/validate.js" type="text/javascript"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<script type="text/javascript">

alert(document.cookie);
//检查该用户名(登录名)是否存在
function checkLoginName(){
	var loginName = $("#loginName").val();
	if(loginName==''){
		$("#loginName").tips({
			side:3,
			msg:'登录名不能为空',
			bg:'#FF0000',
			time:3
		});
		 return false;
	}
	var reg = /^[^\u4e00-\u9fa5]+$/;//不含汉字 
	var reg1 = /^[A-Za-z0-9\u4E00-\u9FA5_-]+$/;
	if (!reg.test(loginName)||!reg1.test(loginName)) {
		$("#loginName").tips({
			side:3,
			msg:'不能有特殊字符或中文',
			bg:'#FF0000',
			time:3
		});
		return false;
	} else {
		var res =  nameIsChecked(loginName);
		if(res==0){
			return true;
		} else {
			$("#loginName").tips({
				side:3,
				msg:'登录名已存在',
				bg:'#FF0000',
				time:3
			});
			return false;
		}
	}
}

/**
	  * 检查该用户名(登录名)是否存在--Yangliuqiing
 */
function nameIsChecked(loginName){
	var res=-1;
	$.ajax({
		url:'${ctx}/mUser/checkLoginName',
		type:'post',
		async: false,
		data:{'loginName':loginName},
		success:function(data){
		  res=data.code;		
		},
		error:function(data){
			$("#name").tips({
				side:3,
				msg:'校验失败,稍后再试',
				bg:'#FF0000',
				time:3
			});
		}
	});
	return res;
}
//验证确认密码
function checkPassword1(){
	 var password = $("#password").val();
	 var password1 = $("#password1").val();
	 if (password1=='') {
		 $("#password1").tips({
			side:3,
			msg:'密码确认不能为空',
			bg:'#FF0000',
			time:3
		 });
		 return false;
	}	
	if (password!= password1) {
		$("#password1").tips({
				side:3,
				msg:'两次输入的密码不一致',
				bg:'#FF0000',
				time:3
			 });
		return false;
	} else {
		return true;
	}
}
//验证密码
function checkPassword(){
	 var password = $("#password").val();
	 var oNameInfo=document.getElementById("passwordErrorMsg");
	 if (password=='') {
		 $("#password").tips({
				side:3,
				msg:'密码不能为空',
				bg:'#FF0000',
				time:3
			 });
		 return false;
	}
	 if (password.length<4) {
		 $("#password").tips({
				side:3,
				msg:'密码长度必须大于4位',
				bg:'#FF0000',
				time:3
			 });
		 return false;
	} else {
		return true;
	}
}
</script>
</head>
<body class="no-skin">

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div id="dialog" class="col-xs-12">
						<form action="${ctx}/mUser/save" name="addmUserForm" id="addmUserForm"  method="post">
							<div id="zhongxin" style="padding-top:13px;">
							<table class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">用户名：</td>
									<td class="span10 date-picker">
										<input type="text" id="name" name="name"  
										validates="required,noSpecial" label="角色名称" maxlength="20" 
										value="" style="width:95%;" />
									</td>
								</tr>
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">登录名：</td>
									<td class="span10 date-picker">
										<input type="text" id="loginName" name="loginName" validates="required,noSpecial" 
										onblur="checkLoginName()"  maxlength="10" 
										label="角色备注" value="" style="width:95%;" />
									</td>
		  						 </tr>
		  						 <tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">密码：</td>
									<td class="span10 date-picker">
										<input type="text" id="password" name="password" onfocus="changeType(this)" onblur="checkPassword(this)" 
										maxlength="20" validates="required" label="密码" value="" style="width:95%;" />
									</td>
		  						 </tr>
		  						 <tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">确认密码：</td>
									<td class="span10 date-picker">
										<input type="text" id="password1" name="password1" onfocus="changeType(this)" onblur="checkPassword1(this)" 
										validates="required" label="再次输入密码" value="" style="width:95%;" maxlength="20" />
									</td>
		  						 </tr>
		  						  <tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">邮箱：</td>
									<td class="span10 date-picker">
										<input type="text" id="email" name="email" validates="required,email"  
										label="邮箱" value="" style="width:95%;"  maxlength="50" />
									</td>
		  						 </tr>
		  						 <tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">角色名称：</td>
									<td class="span10 date-picker">
										<select id="roleIds" name="roles" style="width:98%;">
											<option value="">---请选择角色---</option>
											<c:forEach items="${roleList}" var="role" varStatus="status">
											    <option value="${role.id}">${role.name}</option>
										    </c:forEach> 
			     						</select>
									</td>
		  						 </tr>
							</table>
							</div>
						</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->

<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp"%>
<!--提示框-->
<script type="text/javascript">
$(top.hangge());
function changeType(obj) {
	obj.type="password";
}
//保存
function save(){
	if(checkLoginName()&&checkPassword()&&checkPassword1()){
		if (checkFormValidate(document.getElementById("addmUserForm"))) {
			$("#addmUserForm").submit();
		}
	}
}
</script>
</body>
</html>

