<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<link rel="stylesheet" href="${ctx}/static2/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<link rel="stylesheet" href="${ctx}/static2/ace/css/datepicker.css" />
<!-- 百度echarts -->
<script src="${ctx}/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
//删除
function deleteObj(id,name){
	bootbox.confirm("确定要 <label style='color: red;font-weight: bold;'>删除</label> 用户["+name+"]吗?", function(result) {
		if(result) {
			var url = "${ctx}/mUser/delete?id="+id;
			$.post(url,function(data){
				nextPage("${page.pageNo}");
			});
		}
	});
}
//修改
function update(id){
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改用户";
	 diag.URL = "${ctx}/mUser/edit?id="+id;
	 diag.Width = 500;
	 diag.Height = 300;
	 diag.OKEvent = function(){
		 var reloadFlag = diag.innerFrame.contentWindow.document.getElementById('reload_flag');
		 if(reloadFlag!=null&&reloadFlag.value){
			 top.Dialog.close();
		 } else {
			 var bool = diag.innerFrame.contentWindow.save();
			 if (bool) {
			 	diag.setSize(500,300);
			}
		 }
	 };
	 diag.CancelEvent = function(){ //关闭事件
		var reloadFlag = diag.innerFrame.contentWindow.document.getElementById('reload_flag');
		if(reloadFlag!=null&&reloadFlag.value){
			setTimeout("self.location.reload()",100);
		}
		diag.close();
	 };
	 diag.show();
	 diag.okButton.className="btn btn-mini btn-primary";
	 diag.cancelButton.className="btn btn-mini btn-danger";
}
//新增角色
function toAdd(){
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="新增用户";
	 diag.URL = '${ctx}/mUser/add';
	 diag.Width = 500;
	 diag.Height = 400;
	 diag.OKEvent = function(){
		 var reloadFlag = diag.innerFrame.contentWindow.document.getElementById('reload_flag');
		 if(reloadFlag!=null&&reloadFlag.value){
			 top.Dialog.close();
		 } else {
		 	var bool = diag.innerFrame.contentWindow.save();
		 	if (bool) {
		 		diag.setSize(500,300);
			}
		 }
	 };
	 diag.CancelEvent = function(){ //关闭事件
		var reloadFlag = diag.innerFrame.contentWindow.document.getElementById('reload_flag');
		if(reloadFlag!=null&&reloadFlag.value){
			setTimeout("self.location.reload()",100);
		} 
		diag.close();
	 };
	 diag.show();
	 diag.okButton.className="btn btn-mini btn-primary";
	 diag.cancelButton.className="btn btn-mini btn-danger";
}

//新增角色
function detail(id){
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="用户详情";
	 diag.URL = '${ctx}/mUser/mUserdetail/'+id;
	 diag.Width = 500;
	 diag.Height = 350;
	 diag.show();
	 diag.okButton.className="btn btn-mini btn-primary";
	 diag.cancelButton.className="btn btn-mini btn-danger";
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
						<div class="col-xs-12">
							<table style="margin-top: 8px;">
								<tr height="35">
									<td style="width:69px;"><a href="javascript:toAdd();" class="btn btn-sm btn-success">新增用户</a></td>
								</tr>
							</table>
							<table id="dynamic-table" class="table table-striped table-bordered table-hover" style="margin-top:7px;">
								<thead>
									<tr>
										<th class="center" style="width:25%;">登录名</th>
										<th class="center" style="width:20%;">用户名</th>
										<th class="center" style="width:25%;">电子邮件</th>
										<th class="center" style="width:30%;">操作</th>
									</tr>
								</thead>
								<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty page.result}">
										<c:forEach items="${page.result}" var="mUser" varStatus="status">
											<tr>
												<td class='center'>${mUser.loginName}</td>
												<td class="center">${mUser.name}</td>
												<td class="center">${mUser.email}</td>
												<td class="center">
													<c:if test="${fn:replace(mUser.loginName,'','') eq 'admin'}">
														<a class="btn btn-mini btn-danger" title="用户详情">默认用户不可修改</a>
														<a class="btn btn-mini btn-info" title="用户详情" onclick="detail('${mUser.id}');"><i class='ace-icon fa fa-file-text-o bigger-120'></i>详情</a>
													</c:if>
													<c:if test="${fn:replace(mUser.loginName,'','') ne 'admin'}">
														<a class='btn btn-mini btn-purple' title="编辑用户" onclick="update('${mUser.id}');"><i class='ace-icon fa fa-wrench bigger-120'></i>修改</a>
														<a class="btn btn-mini btn-danger" title="删除此用户" onclick="deleteObj('${mUser.id}','${mUser.name}');"><i class='ace-icon fa fa-trash-o bigger-120'></i>删除</a>
														<a class="btn btn-mini btn-info" title="用户详情" onclick="detail('${mUser.id}');"><i class='ace-icon fa fa-file-text-o bigger-120'></i>详情</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
							<div class="page-header position-relative">
								<table style="width:100%;">
									<tr>
										<td style="vertical-align:top;">
											<div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">
												<tags:pagination page="${page}" paginationSize="${page.pageSize}"/>
											</div>
										</td>
									</tr>
								</table>
							</div>
 						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
			<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="${ctx}/static2/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="${ctx}/static2/ace/js/ace/ace.js"></script>
	<script src="${ctx}/static2/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="${ctx}/static2/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>
