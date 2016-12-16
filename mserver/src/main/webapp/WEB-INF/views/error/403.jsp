<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>403 - 缺少权限</title>
<script type="text/javascript">
function gotoDefault() {
	parent.location.reload();
}
</script>
</head>

<body>
<div>
	<div><h1>你没有访问该页面的权限.</h1></div>
	<div><a href="javascript:void(0);" onclick="gotoDefault();">返回首页</a></div>
</div>
</body>
</html>