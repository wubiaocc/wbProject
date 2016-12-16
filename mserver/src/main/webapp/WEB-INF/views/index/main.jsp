<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>
<style type="text/css">
	.commitopacity{position:absolute; width:100%; height:100px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.8; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
</style>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
<script src="${ctx}/static2/ace/js/bootbox.js"></script>
<script>
//TOCMAT重启、sesion超时 跳转登录首页 
if (window != top) {
	top.location.href = location.href;
}
</script>
<script type="text/javascript">
	$(function() {
		var reloadFlag="${reloadFlag}";
		if (reloadFlag=="reloadFlag") {
			window.location.reload();
		}
		var setRole="${setRole}";
		if (setRole=="setRole") {
			bootbox.alert("菜单无法显示，请登录管理员帐号重新设置角色权限！",function(){});
		}
	})
</script>
</head>
	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		
		<!-- 页面顶部¨ -->
		<%@ include file="head.jsp"%>
		<div id="websocket_button"></div><!-- 少了此处，聊天窗口就无法关闭 -->
		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>

			<!-- #section:basics/sidebar -->
			<!-- 左侧菜单 -->
			<%@ include file="left.jsp"%>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- #section:settings.box -->
						<div class="row">	
							<div id="jzts" style="display:none; width:100%; position:fixed; z-index:99999999;">
								<div class="commitopacity" id="bkbgjz"></div>
								<div style="padding-left: 70%;padding-top: 1px;">
									<div style="float: left;margin-top: 3px;"><img src="${ctx}/static2/images/loadingi.gif" /> </div>
									<div style="margin-top: 6px;"><h4 class="lighter block red" style="margin-top: 7px;font-size: 14px;">&nbsp;加载中 ...</h4></div>
								</div>
							</div>
							<div>
								<iframe name="mainFrame" id="mainFrame" frameborder="0" src="login/tab" style="margin:0 auto;width:100%;height:100%;"></iframe>
							</div>
						</div><!-- /.row -->	
					</div><!-- /.page-content -->
					
				</div>
			</div><!-- /.main-content -->

		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<!-- 页面底部js¨ -->
		<%@ include file="foot.jsp"%>
		
		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="${ctx}/static2/ace/js/ace/elements.scroller.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.colorpicker.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.fileinput.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.typeahead.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.wysiwyg.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.spinner.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.treeview.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.wizard.js"></script>
		<script src="${ctx}/static2/ace/js/ace/elements.aside.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.ajax-content.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.touch-drag.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.sidebar.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.submenu-hover.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.widget-box.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.settings.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.settings-rtl.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.settings-skin.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.widget-on-reload.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.searchbox-autocomplete.js"></script>
		<!-- inline scripts related to this page -->

		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="${ctx}/static2/ace/css/ace.onpage-help.css" />

		<script type="text/javascript"> ace.vars['base'] = '..'; </script>
		<script src="${ctx}/static2/ace/js/ace/elements.onpage-help.js"></script>
		<script src="${ctx}/static2/ace/js/ace/ace.onpage-help.js"></script>
	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="${ctx}/static2/js/myjs/head.js"></script>
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="${ctx}/static2/js/myjs/index.js"></script>
		<!--引入弹窗组件start-->
		<script type="text/javascript" src="plugins/attention/zDialog/zDrag.js"></script>
		<script type="text/javascript" src="plugins/attention/zDialog/zDialog.js"></script>
		<!--引入弹窗组件end-->
		<!--提示框-->
	</body>
</html>