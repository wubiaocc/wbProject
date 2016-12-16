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
					<div id="dialog" class="col-xs-12">
						<form action="${ctx}/mUser/update" name="editUserForm" id="editUserForm"  method="post">
							<div id="zhongxin" style="padding-top:13px;">
							<input type="hidden" name="id" id="id" value="${mUser.id}"/>
							<table class="table table-striped table-bordered table-hover" style="width:100%;">
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">用户名：</td>
									<td class="span10 date-picker">
										<input type="text" id="name" name="name" label="用户名" validates="required,noSpecial"  maxlength="20"
											value="${mUser.name}" style="width:95%;" />
									</td>
								</tr>
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
									<td class="span10 date-picker">
										<input type="text" id="email" name="email" label="邮箱" maxlength="20" validates="required,email" 
											value="${mUser.email}" style="width:95%;" />
									</td>
								</tr>
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">角色名称：</td>
									<td class="span10 date-picker">
										<select id="roleIds" name="roles" style="width:98%;">
											<option value="">请选择角色</option>
											<c:forEach items="${roleList}" var="role" varStatus="status">
											    <option value="${role.id}" >${role.name}</option>
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
//保存
function save(){
	if(checkFormValidate(document.getElementById("editUserForm"))){
		$("#editUserForm").submit();
		return true;
	} else {
		return false;
	}
}
</script>
</body>
</html>

