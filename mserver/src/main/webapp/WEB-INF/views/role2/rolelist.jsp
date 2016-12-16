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
function delRole(id,name){
	bootbox.confirm("确定要 <label style='color: red;font-weight: bold;'>删除</label> 角色["+name+"]吗?", function(result) {
		if(result) {
			var url = "${ctx}/memberRole/del?id="+id;
			$.post(url,function(data){
				nextPage("${page.pageNo}");
			});
		}
	});
}
//修改
function toEdit(id){
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="修改角色";
	 diag.URL = "${ctx}/memberRole/updateRole/?id="+id;
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
	 diag.Title ="新增角色";
	 diag.URL = '${ctx}/memberRole/add';
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
//角色授权
function toAuth(id){
	top.jzts();
	 var diag = new top.Dialog();
	 diag.Drag=true;
	 diag.Title ="角色授权";
	 diag.URL = '${ctx}/memberRole/roleSet?id='+id;
	 diag.Width = 400;
	 diag.Height = 500;
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
									<td style="width:69px;"><a href="javascript:toAdd();" class="btn btn-sm btn-success">新增角色</a></td>
								</tr>
							</table>
							<table id="dynamic-table" class="table table-striped table-bordered table-hover" style="margin-top:7px;">
								<thead>
									<tr>
										<th class="center" style="width:10%;">角色编号</th>
										<th class="center" style="width:30%;">角色名称</th>
										<th class="center" style="width:30%;">描述</th>
										<th class="center" style="width:30%;">操作</th>
									</tr>
								</thead>
								<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty page.result}">
										<c:forEach items="${page.result}" var="role" varStatus="status">
											<tr>
												<td class='center'>${role.id}</td>
												<td class="center">${role.name}</td>
												<td class="center">${role.memo}</td>
												<td class="center">
													<c:if test="${role.name eq '系统管理员'}">系统管理员，不可修改</c:if>
													<c:if test="${role.name ne '系统管理员'}">
														<a class='btn btn-mini btn-info' title="编辑角色描述" onclick="toEdit('${role.id}');"><i class='ace-icon fa fa-wrench bigger-120'></i>编辑</a>
														<a class="btn btn-mini btn-danger" title="删除此角色" onclick="delRole('${role.id}','${role.name}');"><i class='ace-icon fa fa-trash-o bigger-120'></i>删除</a>
														<c:if test="${role.id ne roleId}">
															<a class="btn btn-mini btn-purple" title="菜单资源授权" onclick="toAuth('${role.id}');"><i class='ace-icon fa fa-pencil-square-o bigger-120'></i>授权</a>
														</c:if>
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
								<table style="width: 100%;">
									<tr>
										<td style="vertical-align: top;"></td>
										<td style="vertical-align: top;">
											<div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">
												<tags:pagination page="${page}" paginationSize="${page.pageSize}" />
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
