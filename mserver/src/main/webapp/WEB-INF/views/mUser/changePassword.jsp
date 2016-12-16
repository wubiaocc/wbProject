<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/validate.js" ></script>
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="row">
					<div id="dialog" class="col-xs-12">
						<label></label>
						<form id="changePasswordForm" action="${ctx}/mUser/changePassword" method="post">
							<input type="hidden" value="10" name="agentHeader">
							<div id="zhongxin" style="padding-top:13px;">
							<table class="table table-striped table-bordered table-hover">
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">原密码：</td>
									<td class="span10 date-picker">
										<input type="password" id="oldPassword" name="oldPassword"  label="原密码" validates="required," 
											value="" style="width:98%;" placeholder="请输入原密码"/>
									</td>
		  						</tr>
		  						<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">新密码：</td>
									<td class="span10 date-picker">
										<input type="password" id="newPassword" name="newPassword"  validates="required" label="新密码"
											value=""  style="width:98%;" placeholder="请输入新密码"/>
									</td>
								</tr>
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">确认密码：</td>
									<td class="span10 date-picker">
										<input type="password" id="confirmPassword" name="confirmPassword" label="确定密码" validates="required,equals[newPassword]"
											value="" style="width:98%;" placeholder="请输入确认密码"/>
									</td>
								</tr>
		  						
							</table>
							</div>
							<div id="zhongxin2" class="center" style="display:none;margin-top: 100px;">
								<img src="${ctx}/static2/images/jiazai.gif" />
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
function save(){
	if(checkFormValidate(document.getElementById("changePasswordForm"))){
		$("#changePasswordForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		return true;
	} else {
		return false;
	}
}
</script>

</body>
</html>