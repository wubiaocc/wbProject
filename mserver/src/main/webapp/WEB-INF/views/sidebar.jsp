<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="Bookmark" type="image/x-icon" href="${ctx}/static/icon/favicon.ico" />
<link rel="icon" href="${ctx}/static/icon/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/icon/favicon.ico"	type="image/x-icon" />
<title>商户门户</title>
<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
</head>
<body>
	<div class="sider_bar">
		<ul class="first_menu" style="border-top:none">
			<li><img src="${ctx}/static/images/set.png" style="vertical-align:middle"/>设置</li>
		</ul>
		<ul class="second_menu">
			<%-- <li><a <c:if test="${active=='operatorManager'}">class="active"</c:if> href="<%=basePath%>operator/list?mid=<shiro:principal/>&pageNo=1">操作员管理</a></li> --%>
			<li><a <c:if test="${active=='cashier_productCategoryManager'}">class="active"</c:if> href="${ctx}/productCategory/list?mid=<shiro:principal/>&parentId=">品类管理</a></li>
			<li><a <c:if test="${active=='cashier_productManager'}">class="active"</c:if> href="${ctx}/product/list?mid=<shiro:principal/>&pageNo=1">商品管理</a></li>
			<li>-------</li>
			<li><a <c:if test="${active=='member_ticketDefSet'}">class="active"</c:if> href="${ctx}/ticketDef/list?mid=<shiro:principal/>&pageNo=1">券设置</a></li>
		</ul>
		<ul class="first_menu">
			<li><img src="${ctx}/static/images/stockManager.png" style="vertical-align:middle"/>库存管理</li>
	    </ul>
		<ul class="second_menu">
			<li><a <c:if test="${active=='cashier_stockIn'}">class="active"</c:if> href="${ctx}/stockIn/add?mid=<shiro:principal/>">入库</a></li>
			<li><a <c:if test="${active=='cashier_stockOut'}">class="active"</c:if> href="${ctx}/stockOut/add?mid=<shiro:principal/>">出库</a></li>
			<li><a <c:if test="${active=='cashier_stockTake'}">class="active"</c:if> href="${ctx}/stockTake/add?mid=<shiro:principal/>">盘点</a></li>
		</ul>
		<ul class="first_menu">
			<li><img src="${ctx}/static/images/query.png" style="vertical-align:middle"/>统计查询</li>
		</ul>
		<ul class="second_menu">
			<li><a <c:if test="${active=='cashier_productQuery'}">class="active"</c:if> href="${ctx}/product/query?mid=<shiro:principal/>&pageNo=1">商品查询</a></li>
			<li><a <c:if test="${active=='cashier_productStockQuery'}">class="active"</c:if> href="${ctx}/productStock/query?mid=<shiro:principal/>&pageNo=1">库存查询</a></li>
			<li><a <c:if test="${active=='cashier_stockInQuery'}">class="active"</c:if> href="${ctx}/stockIn/query?mid=<shiro:principal/>&pageNo=1">入库单</a></li>
			<li><a <c:if test="${active=='cashier_stockOutQuery'}">class="active"</c:if> href="${ctx}/stockOut/query?mid=<shiro:principal/>&pageNo=1">出库单</a></li>
			<li><a <c:if test="${active=='cashier_stockTakeQuery'}">class="active"</c:if> href="${ctx}/stockTake/query?mid=<shiro:principal/>&pageNo=1">盘点单</a></li>
			<li><a <c:if test="${active=='cashier_saleOrderQuery'}">class="active"</c:if> href="${ctx}/saleOrder/query?mid=<shiro:principal/>&pageNo=1">销售单</a></li>
			<li><a <c:if test="${active=='cashier_totalSaleOrdersQuery'}">class="active"</c:if> href="${ctx}/operShift/queryTotalOperShift?mid=<shiro:principal/>">班次汇总</a></li>
		</ul>
		<ul class="first_menu">
			<li><img src="${ctx}/static/images/query.png" style="vertical-align:middle"/>会员易</li>
		</ul>
		<ul class="second_menu">
			<li><a <c:if test="${active=='member_tranLogQuery'}">class="active"</c:if> href="${ctx}/tranLog/query?mid=<shiro:principal/>&pageNo=1">交易明细查询</a></li>
			<li><a <c:if test="${active=='member_totalTranLogsQuery'}">class="active"</c:if> href="${ctx}/tranLog/queryTotalTranLog?mid=<shiro:principal/>&pageNo=1">交易汇总查询</a></li>
			<li><a <c:if test="${active=='member_merchantCardQuery'}">class="active"</c:if> href="${ctx}/merchantCard/query?mid=<shiro:principal/>&pageNo=1">会员卡查询</a></li>
			<li><a <c:if test="${active=='member_merchantCardQueryTotal'}">class="active"</c:if> href="${ctx}/merchantCard/queryTotal?mid=<shiro:principal/>">会员卡汇总</a></li>
			<li><a <c:if test="${active=='member_ticketInfoQueryTotal'}">class="active"</c:if> href="${ctx}/ticketInfo/queryTotal?mid=<shiro:principal/>">券汇总</a></li>
			<li><a <c:if test="${active=='member_cardInteger'}">class="active"</c:if> href="${ctx}/cardInteger/list?mid=<shiro:principal/>">积分交易流水查询</a></li>
			<li><a <c:if test="${active=='member_memberOfUnion'}">class="active"</c:if> href="${ctx}/memberOfUnion/query?mid=<shiro:principal/>&pageNo=1">会员共享商户号审核</a></li>
		    <li><a <c:if test="${active=='member_terminal'}">class="active"</c:if> href="${ctx}/terminal/query?mid=<shiro:principal/>&pageNo=1">商户终端管理</a></li>
		</ul>
		<ul class="first_menu">
			<li><img src="${ctx}/static/images/query.png" style="vertical-align:middle"/>便民通</li>
		</ul>
		<ul class="second_menu">
			<li> <a <c:if test="${active=='conv_convTradingTotal'}">class="active"</c:if> href="${ctx}/convTrand/query?mid=<shiro:principal/>&pageNo=1">交易明细查询 </a>
			<li> <a <c:if test="${active=='conv_earnestMoneyRecharge'}">class="active"</c:if> href="${ctx}/earnestMoneyRecharge/query?mid=<shiro:principal/>&pageNo=1">备付金充值明细查询 </a></li>
			<li> <a <c:if test="${active=='conv_cardEarnestTradingTotal'}">class="active"</c:if> href="${ctx}/earnestTradingContentDetail/query?mid=<shiro:principal/>&pageNo=1">备付金支付流水查询</a> </li>
			<li> <a <c:if test="${active=='conv_cardTradingReport'}">class="active"</c:if> href="${ctx}/convTrandTotal/queryTotal?mid=<shiro:principal/>">业务内容统计分析</a></li>
			<li> <a <c:if test="${active=='conv_cardEarnestReport'}">class="active"</c:if> href="">备付金统计分析</a></li>
		</ul>
		<ul>
			<li class="first_menu"><img src="${ctx}/static/images/bak.png" style="vertical-align:middle"/>管理</li>
		</ul>
			<ul class="second_menu">
				<%--<li><a <c:if test="${active=='dataDealerBackup'}">class="active"</c:if> href="${ctx}/dataDealer/backUp">数据备份</a></li>
				<%-- <li><a <c:if test="${active=='dataDealerRecover'}">class="active"</c:if> href="<%=basePath%>dataDealer/recover">数据恢复</a></li> --%>
				<li><a <c:if test="${active=='cashier_excelBatchImport'}">class="active"</c:if> href="${ctx}/excel/batchImport">批量导入</a></li>
				
				
			</ul>
		<ul>
			<li class="first_menu"><img src="${ctx}/static/images/bak.png" style="vertical-align:middle"/>餐桌</li>
		</ul>
			<ul class="second_menu">
				<li><a <c:if test="${active=='cashier_table'}">class="active"</c:if> href="${ctx}/table/list?mid=<shiro:principal/>&pageNo=1">餐桌管理</a></li>
				<li><a <c:if test="${active=='cashier_timeSheet'}">class="active"</c:if> href="${ctx}/orderStage/list?mid=<shiro:principal/>&pageNo=1">餐台预定时段</a></li>
		   </ul>
		</div>
</body>
</html>