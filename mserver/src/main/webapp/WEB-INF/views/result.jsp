<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="./index/top.jsp"%>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">
						<div id="dialog" class="center">
							<input type="hidden" id="reload_flag" value="true" >
							<div style="height: 100px;">
								<%-- <img src="${ctx}/static2/images/jiazai.gif" /> --%>
							</div>
							<table class="table table-striped table-bordered table-hover" style="width:100%;">
								<tr>
									<td>
										<h3>${respMessage.message}</h3>
									</td>
								</tr>
							</table>
						</div>
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
<%@ include file="./index/foot.jsp"%>
<!--提示框-->
</body>
</html>
