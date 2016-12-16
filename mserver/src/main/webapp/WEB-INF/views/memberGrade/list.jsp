<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<style type="text/css">
	footer{height:50px;position:fixed;bottom:0px;left:0px;width:100%;text-align: center;}
</style>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/validate.js"></script>
<link rel="stylesheet" href="${ctx}/static2/js/common.css" />
<link rel="stylesheet" href="${ctx}/static2/ace/css/chosen.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/plugins/zTree/2.6/zTreeStyle.css"/>
<script type="text/javascript" src="${ctx}/plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<link rel="stylesheet" href="${ctx}/static2/ace/css/datepicker.css" />
<!-- 百度echarts -->
<script src="${ctx}/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
$(function(){
	//日期框
	$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
	//点击日历图标弹出日期框
	$('#startTime').datepicker().next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	$('#endTime').datepicker().next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
});
//检索
function searchs(){
	if (checkQueryTime()) {
		$("#serachFrom").submit();
	} else {
		$("#endTime").tips({
			side:3,
			msg:'结束时间要大于开始时间！',
			bg:'#FF0000',
			time:3
		});
	}
}
//导入
function importExcel() {
// 	window.location.href="${ctx}/thirdTranLog/import";
	var diag = new top.Dialog();
	diag.Drag = true;
	diag.Title = "导入";
	diag.URL = '${ctx}/thirdTranLog/batchImport';
	diag.Width = 600;
	diag.Height = 350;
	diag.OKEvent = function() {
		var reloadFlag = diag.innerFrame.contentWindow.document
				.getElementById('reload_flag');
		if (reloadFlag != null && reloadFlag.value == "true") {
			diag.close();
			self.location.reload();
		} else {
			diag.innerFrame.contentWindow.save();

		}
	};
	diag.CancelEvent = function() { //关闭事件
		var reloadFlag = diag.innerFrame.contentWindow.document
				.getElementById('reload_flag');
		if (reloadFlag != null && reloadFlag.value == "true") {
			top.jzts();
			setTimeout("self.location.reload()", 100);
		}
		diag.close();
	};
	diag.show();
	diag.okButton.className = "btn btn-primary";
	diag.cancelButton.className = "btn btn-danger";

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
								<!-- 检索  -->
								<form id="serachFrom" action="${ctx}/memberGrade/list" method="get">
									<input type="hidden" name="pageSize" value="${page.pageSize}">
									<table style="margin-top:5px;">
										<tr>
											<td style="padding-left:2px;">
												<div class="input-group">
													<input class="form-control date-picker" name="filter_GED_tranTime" id="startTime"  value="${param['filter_GED_tranTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:88px;" placeholder="开始时间" title="开始时间"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
												</div>
											</td>
											<td>
												<span class="input-group-addon" style="height: 33px;">
													<i class="fa fa-exchange"></i>
												</span>
											</td>
											<td style="padding-left:2px;">
												<div class="input-group">
													<input class="form-control date-picker" name="filter_LED_tranTime" id="endTime"  value="${param['filter_LED_tranTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:88px;" placeholder="结束时间" title="结束时间"/>
													<span class="input-group-addon">
														<i class="fa fa-calendar bigger-110"></i>
													</span>
												</div>
											</td>
											<td style="padding-left:2px;">
												<div class="nav-search">
												<span class="input-icon">
													<input class="nav-search-input" autocomplete="off" type="text" name="filter_LIKES_merchantName" value="${param['filter_LIKES_merchantName']}" placeholder="这里输入商户名称" />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
												</div>
											</td>
											<td style="padding-left:2px;">
												<div class="nav-search">
												<span class="input-icon">
													<input id="filter_EQS_merchantNo" class="nav-search-input" autocomplete="off" type="text" name="filter_EQS_merchantNo" value="${param['filter_EQS_merchantNo']}" placeholder="这里输入收单商户号" />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
												</div>
											</td>
											<td style="padding-left:2px;">
												<div class="nav-search">
												<span class="input-icon">
													<input class="nav-search-input" autocomplete="off" type="text" name="filter_EQS_terminalNo" value="${param['filter_EQS_terminalNo']}" placeholder="这里输入终端号" />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
												</div>
											</td>
											<td style="padding-left:2px;">
												<div class="nav-search">
												<span class="input-icon">
													<input class="nav-search-input" autocomplete="off" type="text" name="filter_EQS_billNo" value="${param['filter_EQS_billNo']}" placeholder="这里输入批次号" />
													<i class="ace-icon fa fa-search nav-search-icon"></i>
												</span>
												</div>
											</td>
											<td style="vertical-align:center;padding-left:2px;"><a class="btn btn-mini btn-info" onclick="searchs();" title="检索">查询</a></td>
											<td style="vertical-align:center;padding-left:2px;"><a class="btn btn-mini btn-info" onclick="importExcel();" title="导入">导入</a></td>
										</tr>
									</table>
								</form>
								<table style="margin-top: 8px;">
								<tr height="35">
									<td style="width:69px;"><a href="javascript:toAdd();" class="btn btn-sm btn-success">新增会员等级</a></td>
								</tr>
							    </table>
								<!-- 检索  -->
								<table class="table table-striped table-bordered table-hover" style="margin-top:5px;">
									<thead>
										<tr>
										    <th class="center" style="width: 8%;">会员类型</th>
										    <th class="center" style="width: 5%;">会员等级</th>
											<th class="center" style="width: 5%;">等级代码</th>
											<!-- <th class="center" style="width: 5%;">第三方公司</th> -->
											<th class="center" style="width: 5%;">年费(元)</th>
											<th class="center" style="width: 5%;">积分</th>
											<th class="center" style="width: 15%;">权益内容</th>
											<th class="center" style="width: 10%;">创建时间</th>
											<th class="center" style="width: 20%;">操作</th>
										</tr>
									</thead>
									<tbody>
									<!-- 开始循环 -->	
									<c:choose>
										<c:when test="${not empty page.result}">
											<c:forEach items="${page.result}" var="memberGrade" varStatus="vs">
												<tr>
													<td align="center">${memberGrade.memberType}</td>
													<td align="center">${memberGrade.gradeName}</td>
													<td align="center">${memberGrade.memberCode}</td>
													<td align="center">${memberGrade.annualFee}</td>
													<td align="center">${memberGrade.annualIntegral}</td>
													<%-- <td align="center">${tranLog.tranLogProvider}</td> --%>
													<td align="center">${memberGrade.memberInterests}</td>
													<td align="center"><fmt:formatDate value="${memberGrade.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
											<td style="vertical-align:top;"></td>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;"><tags:pagination page="${page}" paginationSize="${page.pageSize}"/></div></td>
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