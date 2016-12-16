<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>商户门户</title>
<link href="${ctx}/static/css/style_hy.css" type="text/css"
	rel="stylesheet">
<script src="${ctx}/static/js/Chart.js"></script>
</head>
<script type="text/javascript">
$(function(){  
	var roleId = '<shiro:principal property="roleIds"/>';
	var replacePayment = '<shiro:principal property="replacePayment"/>';
	if (roleId != "" && roleId != "null") {
		$.ajax({
			url:"${ctx}/memberRole/getResources",
			data:{"id":roleId},
			success:function(data){
				var ress = data.obj;
				for (var i = 1; i < ress.length; i++) {
					var str = ress[i];
					if (str == "t2") {
						document.getElementById("merchant").style.display="block";
					} else if (ress[i] == "t6") {
						document.getElementById("agent").style.display="block";
					} else if (ress[i] == "t8") {
						document.getElementById("system").style.display="block";
					} else if (ress[i] == "t13") {
						if (replacePayment=="1") {
							document.getElementById("bill").style.display="block";
						}
					} else if (ress[i] == "t17") {
						if (replacePayment=="1") {
							//document.getElementById("diference").style.display="block";
						}
					}else if (ress[i] == "t20") {
						if (replacePayment=="1") {
							document.getElementById("withdraw").style.display="block";
						}
					}else if (ress[i] == "t22") {
						if (replacePayment=="1") {
							document.getElementById("transfer").style.display="block";
						}
					} else if (ress[i] == "t24") {
						//if (replacePayment=="1") {
							document.getElementById("profit").style.display="block";
						//}
					} else {
						document.getElementById("t"+str).style.display="block";
						if (str == "8_10") {
							document.getElementById("userManagerLink").style.display="block";
						}
					}
				}
				var agentId = '<shiro:principal property="agentId"/>';
				var grade = agentId.substr(6,1);
				if (grade=="3") {//三级代理商隐藏代理管理菜单
					document.getElementById("agent").style.display="none";
					document.getElementById("t6_7").style.display="none";
				}
			}
		});
	}
});
function menuSelected() {
	var menuSelected = document.getElementById("activeStr").value;
	document.getElementById(menuSelected).className = "active";
}
</script>
<body>
	<div id="sidebar-nav">
		<div class="subNavNosub">
			<i class="icon-home"></i>
			<a href="${ctx}/mUser/index">首页</a>
		</div>
		<div class="subNavBox">
			<div class="subNav" id="merchant" style="display: none;"><i class="icon-user"></i>商户管理</div>
            <ul class="navContent">
				<li id="t2_3" style="display:none;"><a class="icon-th-large" <c:if test="${active=='merchant_purchaseProduct'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/merchantIntoApplication/list"><em class="subitem" >商户进件</em></a></li>
				<%-- <li id="t5_10" style="display: none;"><a class="icon-th" <c:if test="${active=='merchant_merchantManager'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/merchantDef/list"><em class="subitem" >商户管理</em></a></li> --%>
				<li id="t2_4" style="display:none;"><a class="icon-th" <c:if test="${active=='merchant_merchantManager'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/merchantDef/list"><em class="subitem" >商户管理</em></a></li>
				<li id="t2_5" style="display:none;"><a class="icon-th-list" <c:if test="${active=='merchant_totalPayment'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/tranLog/totalPayment"><em class="subitem" >商户交易汇总</em></a></li>
			</ul>
			<div class="subNav" id="agent" style="display: none;"><i class="icon-group"></i><!-- 代理商 -->管理</div>
            <ul class="navContent">
            	<li id="t6_7" style="display:none;"><a class="icon-sitemap" <c:if test="${active=='agent_agentManagement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/agentManage/list"><em class="subitem" >下级<!-- 代理商 -->管理</em></a></li>
			</ul>
			<div class="subNav" id="bill" style="display: none;"><i class="icon-strikethrough"></i>对账</div>
            <ul class="navContent">
            	<li id="t13_14" style="display: none;"><a class="icon-resize-full" <c:if test="${active=='bill_alipayStatement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/bill/list"><em class="subitem" >支付宝对账单</em></a></li>
            	<li id="t13_15" style="display: none;"><a class="icon-resize-small" <c:if test="${active=='bill_baiduStatement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/bill/list2"><em class="subitem" >百度钱包对账单</em></a></li>
            	<li id="t13_16" style="display: none;"><a class="icon-download-alt" <c:if test="${active=='bill_payThirdBill'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/bill/query"><em class="subitem" >第三方对账单查询</em></a></li>
            	<li id="t17_18" style="display: none;"><a class="icon-remove-sign" <c:if test="${active=='bill_payBillDifference'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/payBillDifference/query"><em class="subitem" >对账单差错查询</em></a></li>
            	<li id="t17_19" style="display: none;"><a class="icon-ok-sign" <c:if test="${active=='bill_billManager'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/billQuery/query"><em class="subitem" >系统对账单查询</em></a></li>
			</ul>
			<div class="subNav" id="diference" style="display: none;"><i class="icon-eye-open"></i>差错</div>
			<ul class="navContent">
			</ul>
			<div class="subNav" id="withdraw" style="display: none;"><i class="icon-suitcase"></i>提现审核</div>
			<ul class="navContent">
            	<li id="t20_21" style="display: none;"><a class="icon-sitemap" <c:if test="${active=='withdraw_queryPayLog'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/virtAssetLog/queryPayLog?pageNo=1"><em class="subitem" >提现审核</em></a></li>
			</ul>
			<div class="subNav" id="transfer" style="display: none;"><i class="icon-random"></i>转账</div>
			<ul class="navContent">
            	<li id="t22_23" style="display: none;"><a class="icon-sitemap" <c:if test="${active=='transfer_queryFinancialLog'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/virtAssetLog/queryFinancialLog?pageNo=1"><em class="subitem" >财务审核</em></a></li>
			</ul>
			<div class="subNav" id="profit" style="display: none;"><i class="icon-strikethrough"></i>分润管理</div>
            <ul class="navContent">
                <li id="t24_25" style="display:block;"> <a class="icon-sitemap"  <c:if test="${active=='profit_profitQuery'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/profit/query"><em class="subitem" >分润明细</em></a></li>
            	<li id="t24_26" style="display:block;"> <a class="icon-th-list" <c:if test="${active=='profit_profitSum'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/profit/queryTotal"><em class="subitem" >分润汇总</em></a></li>
			</ul>
			<div class="subNav" id="system" style="display: none;"><i class="icon-cog"></i>系统管理</div>
            <ul class="navContent">
                <li id="t8_9" style="display: none;"><a class="icon-user-md" <c:if test="${active=='system_agentManagement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/agentManage/selfAgentDetail"><em class="subitem" >基本设置</em></a></li>
            	<li id="t8_10" style="display: none;"><a class="icon-user-md" <c:if test="${active=='system_userManagement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/mUser/list"><em class="subitem" >用户管理</em></a></li>
				<li id="t8_11" style="display: none;"><a class="icon-cogs" <c:if test="${active=='system_roleManagement'}"> style=" width:180px; background:#00AEEF;" </c:if> href="${ctx}/memberRole/list"><em class="subitem" >角色管理</em></a></li>
				<li id="t8_12" style="display: none;"><a class="icon-lock" href="javaScript:changePassword()"><em class="subitem" >修改密码</em></a></li>
			</ul>
		</div>
	</div>
</body>
</html>
