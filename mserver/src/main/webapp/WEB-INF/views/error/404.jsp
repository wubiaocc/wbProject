<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<TITLE>您所访问的页面可能已被删除或是重命名</TITLE>
		<META content=404错误 name=keywords>
		<META http-equiv=Refresh
			content="5; url=${ctx}/">
		<META http-equiv=Content-Type content="text/html; charset=utf-8">
		<STYLE type=text/css>
BODY {
	MARGIN: 0px;
	BACKGROUND-COLOR: #000000
}

BODY {
	COLOR: #ffffff;
	FONT-FAMILY: Comic Sans MS;
	background-image: url(static/images/404bg.jpg);
	background-repeat: repeat;
	background-color: #D9D9D9;
}

TD {
	COLOR: #ffffff;
	FONT-FAMILY: Comic Sans MS
}

TH {
	COLOR: #ffffff;
	FONT-FAMILY: Comic Sans MS
}

.style6 {
	FONT-WEIGHT: bold;
	FONT-SIZE: 12px
}

.style7 {
	FONT-SIZE: 18px
}

.style8 {
	FONT-SIZE: 12px
}

.style9 {
	FONT-SIZE: 24px;
	FONT-FAMILY: "楷体_GB2312"
}

.style10 {
	COLOR: #ff0000
}

body,td,th {
	color: #666;
}

a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 18px;
	color: #666;
}

a:link {
	text-decoration: none;
	color: #06F;
}

a:visited {
	text-decoration: none;
	color: #09F;
}

a:hover {
	text-decoration: none;
	color: #09F;
}

a:active {
	text-decoration: none;
	color: #09F;
}
</STYLE>

		<META content="MSHTML 6.00.2900.3354" name=GENERATOR>
	</HEAD>
	<BODY>
		<DIV align=center>
			<P align=left>
			<P align=center>
				<IMG src="${ctx}//static/static/images/404.jpg" width=600 height=400
					tppabs="${ctx}//static/static/images/404.jpg" alt="404 ERROR">
			</P>
			<P>
				<b><font size="5" face="黑体">404 Error您所访问的网页无法显示</font>
				</b>
			</P>
		</DIV>
		<DIV class=style6 align=center>
			<P class=style7>
			</P>
			<P>
				<span id="mes" style="color:red">5</span>秒后自动跳转
			</P>
		</DIV>
	</BODY>
		<script language="javascript" type="text/javascript"> 
var i = 5; 

var intervalid; 

intervalid = setInterval("fun()", 1000); 

function fun() { 

if (i == 0) { 

//window.location.href = "{ctx}//"; 
parent.location.reload();

clearInterval(intervalid); 

} 

document.getElementById("mes").innerHTML = i; 

i--; 

} 
</script>
</HTML>
