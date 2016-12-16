<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>错误页面</title>
<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css" >

</head>
<body>
	<div class="error_page"><img src="${ctx}/static/images/error_page.png" />系统出现异常，请联系管理员！</div>
</body>
</html>