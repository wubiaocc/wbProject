<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--  
<link rel="Bookmark" type="image/x-icon" href="${ctx}/static/icon/favicon.ico" />
<link rel="icon" href="${ctx}/static/icon/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/icon/favicon.ico"	type="image/x-icon" />
-->
<title>上海慧银信息科技有限公司</title>
<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script>
	function changePassword() {
		$.ajax({
			url:"${ctx}/mUser/changePassword",
			success:function(data) {
				MessageBox.Msg({title:"修改密码",content:data,mark:1,muli:1});
			}
		})
	}
	
	function saveChangePassword() {
		if(checkFormValidate(document.getElementById("changePasswordForm"))) {
			$.ajax({
				url:'${ctx}/mUser/changePassword',
				type:'POST',
				data:$("#changePasswordForm").serialize(),
				success:function(data) {
					if(data.code == 0) {
						MessageBox.hid(0);
						var c = '<div class="pop_up_content" style="width:400px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/success.png" />'+data.message+'</div><div class="pop_up_content_button"><a class="ok button" onclick="MessageBox.hid(0);">确定</a></div></div>';
						MessageBox.Msg({title:"提示",content:c,mark:1,muli:1});
						setTimeout(function(){MessageBox.hid(0);},2000);
					}else {
						$("#serverChangePasswordErrorMsg").html(data.message);
					} 
				}
			}) 
		}
	}
</script>
</head>
<body>
	<div class="index_top">
		<span class="logo">商户门户-收银易</span>
		<span class="user_info">
			<img src="${ctx}/static/images/user.png" />&nbsp;&nbsp;<span>当前用户：<shiro:principal property="name" /></span>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="${ctx}/static/images/change_password.png" />&nbsp;&nbsp;<span class="change_password" onclick="changePassword()">修改密码</span>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="${ctx}/static/images/quit.png" /><a class="quit" href="https://wxhuiyi-wizarpos.xicp.net:8443/cas/logout?service=http://wxhuiyi-wizarpos.xicp.net:28080/cashier-web/logout">退出</a>
		</span>
	</div>
</body>
</html>


