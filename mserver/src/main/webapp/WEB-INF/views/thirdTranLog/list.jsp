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
								<form id="serachFrom" action="${ctx}/thirdTranLog/list" method="get">
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
								<!-- 检索  -->
								<table class="table table-striped table-bordered table-hover" style="margin-top:5px;">
									<thead>
										<tr>
											<th class="center" style="width: 10%;">来源</th>
											<!-- <th class="center" style="width: 5%;">第三方公司</th> -->
											<th class="center" style="width: 15%;">商户名称</th>
											<th class="center" style="width: 10%;">商户号</th>
											<th class="center" style="width: 10%;">收单商户号</th>
											<th class="center" style="width: 10%;">终端号</th>
											<th class="center" style="width: 15%;">批次号</th>
											<th class="center" style="width: 10%;">交易金额</th>
											<th class="center" style="width: 10%;">交易类型</th>
											<th class="center" style="width: 10%;">交易日期</th>
										</tr>
									</thead>
									<tbody>
									<!-- 开始循环 -->	
									<c:choose>
										<c:when test="${not empty page.result}">
											<c:forEach items="${page.result}" var="tranLog" varStatus="vs">
												<tr>
													<td align="center">${tranLog.tranLogProvider}(${tranLog.tranLogModel})</td>
													<%-- <td align="center">${tranLog.tranLogProvider}</td> --%>
													<td align="center">${tranLog.merchantName}</td>
													<td align="center">${tranLog.mid}</td>
													<td align="center">${tranLog.merchantNo}</td>
													<td align="center">${tranLog.terminalNo}</td>
													<td align="center">${tranLog.billNo}</td>
													<td align="center"><fmt:formatNumber value="${tranLog.tranAmount/100}" pattern="#,###,##0.00" /></td>
													<td align="center">${tranLog.tranType}</td>
													<td align="center"><fmt:formatDate value="${tranLog.tranTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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