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
<script type="text/javascript">
//修改时检验名称
function checkName(obj){
	   var name = obj.value;
	   var reg = /^[A-Za-z0-9\u4E00-\u9FA5_-]+$/;
	   if(name==''){
			$("#name").tips({
				side:3,
				msg:'角色名称不能为空!',
				bg:'#FF0000',
				time:3
			})
		  return false;
	   } else if(!reg.test(name)){
		   $("#name").tips({
				side:3,
				msg:'角色名称不能有特殊字符!',
				bg:'#FF0000',
				time:3
			})
			return false;
	   } else {
		  var res=nameIsExit(name);
		  if(res==1){
			   return true;
		  }else if(res==0){
			  $("#name").tips({
					side:3,
					msg:'角色名称不能重复!',
					bg:'#FF0000',
					time:3
				})
				return false;
		     }else{
			  	$("#name").tips({
					side:3,
					msg:'检验失败!',
					bg:'#FF0000',
					time:3
				})
				return false;
		     }
	   }
}
//校验名字是否存在
function nameIsExit(name){
	var res=-1;
	var id = $("#id").val();
	$.ajax({
		url:'${ctx}/memberRole/checkName',
		type:'post',
		async: false,
		data:{'name':name,'id':id},
		success:function(data){
			res=data.code;		
		},
		error:function(data){
			 $("#name").tips({
				side:3,
				msg:'角色名称不能重复!',
				bg:'#FF0000',
				time:3
			})
		}
	});
	return res;
}
</script>
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
						<form action="${ctx}/memberRole/saveUpdateRole" name="roleUpdateForm" id="roleUpdateForm"  method="post">
							<div id="zhongxin" style="padding-top:13px;">
							<input type="hidden" name="id" id="id" value="${role.id}"/>
							<table class="table table-striped table-bordered table-hover" style="width:100%;">
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">角色名称：</td>
									<td class="span10 date-picker">
										<input type="text" id="name" name="name" onBlur="checkName(this)" validates="required,noSpecial"  label="角色名称" maxlength="20"
											value="${role.name}" style="width:95%;" />
									</td>
								</tr>
								<tr>
									<td style="width:100px;text-align: right;padding-top: 13px;">角色备注：</td>
									<td class="span10 date-picker">
										<input type="text" id="memo" name="memo" label="角色备注" maxlength="20"
											value="${role.memo}" style="width:95%;" />
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
	if (checkName(document.getElementById("name"))) {
		if(checkFormValidate(document.getElementById("roleUpdateForm"))){
			$("#roleUpdateForm").submit();
			return true;
		} else {
			return false;
		}
	}
}
</script>
</body>
</html>

