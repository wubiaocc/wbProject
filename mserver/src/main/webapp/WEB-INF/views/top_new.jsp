<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>商户门户</title>
<link rel="Bookmark" type="image/x-icon"
	href="${ctx}/static/icon/favicon.ico" />
<link rel="icon" href="${ctx}/static/icon/favicon.ico"
	type="image/x-icon" />
<link href="${ctx}/static/css/style_hy.css" type="text/css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/common.css">
<script src="${ctx}/static/js/Chart.js"></script>

<!-- cheng -->
	 <link rel="Bookmark" type="image/x-icon" href="${ctx}/static/icon/favicon.ico" />
	 <link rel="icon" href="${ctx}/static/icon/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${ctx}/static/icon/favicon.ico"	type="image/x-icon" />
			<link rel="stylesheet" type="text/css" href="${ctx}/static/css/index.css" >
			<link rel="stylesheet" type="text/css" href="${ctx}/static/css/listGrid.css" >
			<link rel="stylesheet" type="text/css" href="${ctx}/static/css/formGrid.css" >
			<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
			<script src="${ctx}/static/js/popup.js" type="text/javascript"></script>
			<script src="${ctx}/static/js/validate.js" type="text/javascript"></script>
		<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
		<script src="${ctx}/static/js/ichart.1.2.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctx}/static/js/jquery-ui-1.10.2.custom.min.js"></script>
		<%-- <script type="text/javascript" src="${ctx}/widgets/lhgdialog/lhgdialog.min.js"></script> --%>
<%-- 		<link rel="stylesheet" type="text/css" href="${ctx}/widgets/jq-ui/themes/base/jquery.ui.all.css" > --%>
		<link href="${ctx}/static/css/style_hy.css" type="text/css" rel="stylesheet">
		<script src="${ctx}/static/js/Chart.js"></script> 

		<link href="${ctx}/static/css/style_hy.css" type="text/css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css" >
		<script src="${ctx}/static/js/Chart.js"></script> 

	
    <!-- bootstrap -->
    <link href="${ctx}/static/css/bootstrap/bootstrap.css" rel="stylesheet" />
    <link href="${ctx}/static/css/bootstrap/bootstrap-overrides.css" type="text/css" rel="stylesheet" />

    <!-- libraries -->
    <link href="${ctx}/static/css/lib/jquery-ui-1.10.2.custom.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/css/lib/font-awesome.css" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/compiled/layout.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/compiled/elements.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static/css/compiled/icons.css">

    <!-- this page specific styles -->
   <link rel="stylesheet" href="${ctx}/static/css/compiled/index.css" type="text/css" media="screen" /> 

	<script> 
	$(function(){
		var logoUrl = '<shiro:principal property="logoUrl"/>';
		if (logoUrl==null||logoUrl=="") {
			$("#logoUrl").attr("src","${ctx}/static/images/noPicture.jpg");
		} else {
			$("#logoUrl").attr("src",logoUrl);
		}
		
		$(".subNav").click(function(){
			$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd")
			$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt")
			$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		});
		$(".dropdown a").click(function(){
			var obj = document.getElementById("dropdown");
			var clzz = obj.className;
			if ("dropdown" == clzz) {
				obj.className="dropdown open";
			} else {
				obj.className="dropdown";
			}
		})
	});
	function Toptop(){ 
		$("#sidebar-nav a").click(function(){ 
		  	$('body,html').animate({scrollTop:"0px"},1000);  
		 		 return false;
		 	 }) 
		} 
		$(function(){
			var activs=document.getElementById("activs").value;
			  
			if(activs==""){
		    	 return;
		     }
		     var s=activs.split("_");
			   
		     var id=s[0];
		     var oD=document.getElementById(id);
		     var ids="#"+id;
			 
		     if(oD!=null){
		    	$(ids).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
				$(ids).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
				$(ids).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		     }
	  });
		
		//修改密码
		function changePassword() {
			$.ajax({
				url:"${ctx}/mUser/changePassword",
				success:function(data) {
					MessageBox.Msg({title:"修改密码",content:data,mark:1,muli:1});
				}
			})
		}
		//保存修改密码
		function saveChangePassword() {
			if(checkFormValidate(document.getElementById("changePasswordForm"))) {
				$.ajax({
					url:'${ctx}/mUser/changePassword',
					type:'POST',
					data:$("#changePasswordForm").serialize(),
					success:function(data) {
						if(data.code == 0) {
							MessageBox.hid(0);
							var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/success.png" />'+data.message+'</div><div class="pop_up_content_button"><a class="ok button" href="${ctx}/logout">确定</a></div></div>';
							MessageBox.Msg({title:"提示",content:c,mark:1,muli:1});
							//setTimeout(function(){MessageBox.hid(0);},2000);
						}else {
							$("#serverChangePasswordErrorMsg").html(data.message);
						} 
					}
				}) 
			}
		}
	</script>


<!-- cheng -->


</head>
<body>
	<header class="navbar navbar-inverse" role="banner">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><img style="width:47px;height:37px;margin-left: -19px;  margin-top: 3px;z-index:999px;" id="logoUrl" alt="" src=""></a>
		</div>
		<ul class="nav navbar-nav pull-right hidden-xs">
            <li id="dropdown" class="dropdown">
                <a href="#" class="dropdown-toggle hidden-xs hidden-sm" data-toggle="dropdown">
                   	<shiro:principal property="userName"/>,你好！<b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <!-- <li><a onclick="">修改密码</a></li> -->
                    <li><a onclick="changePassword()">修改密码</a></li>
                    <li id="userManagerLink" style="display: none;"><a href="${ctx}/mUser/list">用户管理</a></li>
                </ul>
            </li>
           <!--  <li class="settings hidden-xs hidden-sm">
                <a href="#" role="button">
                    <i class="icon-cog"></i>
                </a>
            </li> -->
            <li class="settings hidden-xs hidden-sm">
                <a href="javascript:if(confirm('确认要退出吗?'))location='${ctx}/logout'" role="button">
                    <i class="icon-share-alt"></i>
                </a>
            </li>
        </ul>
	</header>
	<input type="hidden" name="actives" id="activs" value="${active}">
</body>
</html>
