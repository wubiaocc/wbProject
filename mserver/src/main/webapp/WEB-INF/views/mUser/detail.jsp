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
							<div id="zhongxin" style="padding-top:13px;">
							<table class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">用户名称</td>
									<td style="width:100px; text-align:left; padding-top:13px;">${mUser.name}</td>
								</tr>
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">Email</td>
									<td style="width:100px; text-align:left; padding-top:13px;">${mUser.email}</td>
								</tr>
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">创建时间</td>
									<td style="width:100px; text-align:left; padding-top:13px;">
									<fmt:formatDate value="${mUser.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
									</td>
								</tr>
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">登录名称</td>
									<td style="width:100px; text-align:left; padding-top:13px;">${mUser.loginName}</td>
								</tr>
								<tr>
									<td style="width:100px; text-align:right; padding-top:13px;">是否可用</td>
									<td style="width:100px; text-align:left; padding-top:13px;">
										<c:if test="${mUser.enable=='1'}">不可用</c:if>
										<c:if test="${mUser.enable=='0'}">可用</c:if>
									</td>
								</tr>
							</table>
							</div>
						<!-- /.col -->
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
</body>
<!--提示框-->
<script type="text/javascript">
$(top.hangge());
</script>
</html>