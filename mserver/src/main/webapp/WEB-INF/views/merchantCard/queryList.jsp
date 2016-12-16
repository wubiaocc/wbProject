<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/staticNew/ace/js/jquery.js"></script>
<link rel="stylesheet" href="${ctx}/staticNew/css/common.css" />
<link rel="stylesheet" href="${ctx}/staticNew/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<link rel="stylesheet" href="${ctx}/staticNew/ace/css/datepicker.css" />
<script type="text/javascript" src="${ctx}/static/js/validate.js"></script>
<!-- 百度echarts -->
<script src="${ctx}/plugins/echarts/echarts.min.js"></script>
<style type="text/css">
   .ls-container{ padding:10px;}
.ls-title{ font-size:14px; height:30px; line-height:30px; font-weight:bold;}
.ls-table{ width:100%; border-collapse:collapse;}
.ls-table td,.ls-table th{ padding:5px; font-weight:normal; border-collapse:collapse;}
.ls-input,.ls-txt{ height:70px; border:1px #e8e8e8 solid; width:280px; border-radius:3px;}
.ls-input:focus,.ls-txt:focus{ border:1px #14a9f7 solid;}
.ls-bb{ border-bottom:1px #d8d8d8 solid; margin:10px 0;}
.ls-btn-grey,.ls-btn-blue{ height:24px; background:#f8f8f8; display:inline-block; padding:0 15px; line-height:24px; color:#333; border:1px #c8c8c8 solid; border-radius:3px; cursor:pointer;}
.ls-btn-grey:hover{border:1px #a8a8a8 solid; background:#e8e8e8;}
.ls-btn-blue{ background:#14a9f7; color:#fff; border:1px #158dcc solid;}
.ls-btn-blue:hover{border:1px #0e7db7 solid; background:#0f9ee8;}
.ls-btable{ border:1px #e8e8e8 solid; border-collapse:collapse; width:100%;}
.ls-btable td{border:1px #e8e8e8 solid; padding:5px;}
.ls-select{ border:1px #e8e8e8 solid;}
.checkbox{width:15px;height:15px;line-height:13px;margin-right:2px; vertical-align:-2px;*vertical-align:middle;_vertical-align:3px; }
 </style>
 <style type="text/css">
   input[type=checkbox].css-checkbox {
	position: absolute;
	z-index: -1000;
	left: -1000px;
	overflow: hidden;
	clip: rect(0 0 0 0);
	height: 1px;
	width: 1px;
	margin: -1px;
	padding: 0;
	border: 0;
}
input[type=checkbox].css-checkbox + label.css-label {
	padding-left: 16px;
	height: 15px;
	display: inline-block;
	line-height: 15px;
	background-repeat: no-repeat;
	background-position: 0 0;
	font-size: 15px;
	vertical-align: middle;
	cursor: pointer;
}
input[type=checkbox].css-checkbox:checked + label.css-label {
	background-position: 0 -15px;
}
label.css-label {
	background-image: url(${ctx}/static/images/checkbox1.png);
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}
 </style>
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
	if (!checkQueryTime()) {
 		$("#endTime").tips({
 			side:3,
 			msg:'结束时间要大于开始时间',
 			bg:'#FF0000',
 			time:3
 		});
		return;
	} 
	$("#serachFrom").submit();
}
//导出
function toExcel() {
	window.location.href="${ctx}/merchantCard/export?" + $("#serachFrom").serialize()+ "&random=" + Math.random().toString();
}
//详情
function detail(id) {	
	window.location.href = "${ctx}/merchantCard/queryById?id=" + id;
}
//打标签
function settingGet(id,cardNo,labelIds){
	   $.ajax({
		   url:'${ctx}/merchantCard/settingLabel',
		   type:'get',
		   success:function(data){
			   bootbox.dialog({
				message: data,
				buttons:{
					"success" :
					 {
						"label" : "确定",
						"className" : "btn btn-primary",
						"callback": function() {
							//Example.show("great success");
							settingAdd();
						}
					},
					"danger" :
					{
						"label" : "取消",
						"className" : "btn btn-danger",
						"callback": function() {
							//Example.show("uh oh, look out!");
						}
					}
				}
			 });
			   $('#add').click(function() {
				    var size = $('#select1 option:selected').size();
				    if(size == 0){
/* 				    	var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/error.png" />请选择标签！</div> <div class="pop_up_content_button"><a class="ok button" onclick="MessageBox.hid(1)">确定</a></div>';
				    	MessageBox.Msg({title:"提示",content:c,mark:1,muli:1}); */
				    	bootbox.dialog({
							message: "<span class='bigger-110'>请选择标签！</span>",
							buttons: 			
							{
								"danger" :
								{
									"label" : "确定",
									"className" : "btn btn-primary",
									"callback": function() {
										//Example.show("uh oh, look out!");
									}
								}, 
							}
						});
				    }else{
				    	$('#select1 option:selected').appendTo('#select2');
				    }
			    });
			    $('#remove').click(function() {
			    	var size = $('#select2 option:selected').size();
				    if(size == 0){
/* 				    	var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/error.png" />请选择标签！</div> <div class="pop_up_content_button"><a class="ok button" onclick="MessageBox.hid(1)">确定</a></div>';
				    	MessageBox.Msg({title:"提示",content:c,mark:1,muli:1}); */
				    	bootbox.dialog({
							message: "<span class='bigger-110'>请选择标签！</span>",
							buttons: 			
							{
								"danger" :
								{
									"label" : "确定",
									"className" : "btn btn-primary",
									"callback": function() {
										//Example.show("uh oh, look out!");
									}
								}, 
							}
						});
				    }else{
				    	$('#select2 option:selected').appendTo('#select1');
				    }
			    });
			    $('#select1').dblclick(function(){
			        var size = $('option:selected').size();
				    if(size == 0){
				    	bootbox.dialog({
							message: "<span class='bigger-110'>请选择标签！</span>",
							buttons: 			
							{
								"danger" :
								{
									"label" : "确定",
									"className" : "btn btn-primary",
									"callback": function() {
										//Example.show("uh oh, look out!");
									}
								}, 
							}
						});
				    }else{
				    	$("option:selected",this).appendTo('#select2');
				    } 
			    });
			    $('#select2').dblclick(function(){
			    	var size = $('option:selected').size();
				    if(size == 0){
/* 				    	var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/error.png" />请选择标签！</div> <div class="pop_up_content_button"><a class="ok button" onclick="MessageBox.hid(1)">确定</a></div>';
				    	MessageBox.Msg({title:"提示",content:c,mark:1,muli:1}); */
				    	bootbox.dialog({
							message: "<span class='bigger-110'>请选择标签！</span>",
							buttons: 			
							{
								"danger" :
								{
									"label" : "确定",
									"className" : "btn btn-primary",
									"callback": function() {
										//Example.show("uh oh, look out!");
									}
								}, 
							}
						});
				    }else{
				    	$("option:selected",this).appendTo('#select1');
				    }
			    });
			    
			    $("#id").val(id);
			    $("#cardNo").val(cardNo);
			    
			 
			    if(labelIds){
			    	labelNames_ = labelIds.split(",");
			    	for(var i=0;i<labelNames_.length;i++){
			    		var labelName = labelNames_[i];
			    		var remove ="#select1 option[value='"+labelName+"']";
			    		$(remove).appendTo('#select2');
			    		$(remove).remove();
			    	
			    	}
			    } 
		   },
		   error:function(){
			   
		   }
	   });
}


function settingAdd(){
	var id = $("#id").val();
	var cardNo = $("#cardNo").val();
	//console.info(cardNo);
	var labelNameArray = new Array();
	var labelIdArray = new Array();
	var options = $("#select2 option");
	if(options.length < 1){
/* 		var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/error.png" />至少选择一个标签!</div> <div class="pop_up_content_button"><a class="ok button" onclick="MessageBox.hid(1)">确定</a></div>';
		MessageBox.Msg({title:"提示",content:c,mark:1,muli:1}); */
		bootbox.dialog({
			message: "<span class='bigger-110'>至少选择一个标签!</span>",
			buttons: 			
			{
				"danger" :
				{
					"label" : "确定",
					"className" : "btn btn-primary",
					"callback": function() {
						//Example.show("uh oh, look out!");
					}
				}, 
			}
		});
		return;
	}

	for(var i=0;i<options.length;i++){
		var option = options[i];
		console.info(option);
		labelNameArray.push(option.text);
		labelIdArray.push(option.value);
	}

	var message ="确认为卡号为"+cardNo+"的会员卡设置标签："+labelNameArray.toString()+"吗？";
/* 	var c = '<div class="pop_up_content" style="width:500px"><div class="pop_up_content_msg"><img class="msg_icon" src="${ctx}/static/images/confirm.png" />'+msg+'</div> <div class="pop_up_content_button"><a class="ok button" onclick="labelSetting(\''+id+'\',\''+labelIdArray.toString()+'\')" >确定</a><a class="cancel button" onclick="MessageBox.hid(1);">取消</a></div>';
	MessageBox.Msg({title:"提示",content:c,mark:1,muli:1}); */
	bootbox.confirm(message, function(result) {
		if(result) {
			$.ajax({
				url:'${ctx}/merchantCard/labelSetting',
				data:{id:id,labelIds:labelIdArray.toString()},
				type:'post',
				success:function(data) {
					bootbox.alert({  
						buttons: {  
							"确定": {  
								label: '确定',  
								className: 'btn btn-primary'  
							}  
						},  
						message: data.message,  
						callback: function() {  
							window.location.href = "${ctx}/merchantCard/query";
						} 
						
					});
				},
				error:function(data){
					bootbox.alert({  
						buttons: {  
							"确定": {  
								label: '确定',  
								className: 'btn btn-primary'  
							}  
						},  
						message: data.message,  
						callback: function() {  
							window.location.href = "${ctx}/merchantCard/query";
						}
						
					});
				}
			})
		}
	});
	
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
						<form id="serachFrom" action="${ctx}/merchantCard/query" method="get">
							<input type="hidden" name="pageSize" value="${page.pageSize}">
							<table style="margin-top:5px;">
								<tr>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<input class="nav-search-input" autocomplete="off" type="text" name="cardNo" value="${param['cardNo']}" placeholder="这里输入卡号" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<input class="nav-search-input" autocomplete="off" type="text" name="username" value="${param['username']}" placeholder="这里输入姓名" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<input class="nav-search-input" autocomplete="off" type="text" name="mobileNo" value="${param['mobileNo']}" placeholder="这里输入手机号" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<input class="nav-search-input" autocomplete="off" type="text" name="labelName" value="${param['labelName']}" placeholder="这里输入标签名" />
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
										</div>
									</td>
								</tr>
							</table>
							<table>
								<tr>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<select name="canceled" class="query_select" style="width:150px">
											    <option value="">全部</option>
											    <option value="0" <c:if test="${param['canceled'] == '0'}">selected="selected"</c:if>>未注销</option>
											    <option value="1" <c:if test="${param['canceled'] == '1'}">selected="selected"</c:if>>注销</option>
										    </select>
										</span>
										</div>
									</td>
									<td>
										<div class="nav-search">
										<span class="input-icon">
											<select name="freeze" class="query_select" style="width:150px">
										        <option value="">全部</option>
										        <option value="0" <c:if test="${param['freeze'] == '0'}">selected="selected"</c:if>>解冻</option>
										        <option value="1" <c:if test="${param['freeze'] == '1'}">selected="selected"</c:if>>冻结</option>
										    </select>
										</span>
										</div>
									</td>
									<td style="padding-left:2px;">
										<%-- <div class="input-group">
											<input class="span10 date-picker" name="startTime" id="startTime"  value="${param['startTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:88px;" placeholder="开始时间" title="开始时间"/>
											<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
										</div> --%>
										<div class="input-group">
											<input class="form-control date-picker" name="startTime" id="startTime"  value="${param['startTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:96px;height: 29px;font-size: 13px;" placeholder="开始时间" title="开始时间"/>
											<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
										</div>
									</td>
									<td>
										<i class="fa fa-exchange"></i>
									</td>
									<td style="padding-left:2px;">
										<%-- <div class="input-group">
											<input class="span10 date-picker" name="endTime" id="endTime"  value="${param['endTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:88px;" placeholder="结束时间" title="结束时间"/>
										<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
										</div> --%>
										<div class="input-group">
											<input class="form-control date-picker" name="endTime" id="endTime"  value="${param['endTime']}" type="text" data-date-format="yyyy-mm-dd" style="width:96px;height: 29px;font-size: 13px;" placeholder="结束时间" title="结束时间"/>
											<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>
										</div>
									</td>
									<td style="vertical-align:middle;padding-left:2px;">
										<a class="btn btn-mini btn-info" onclick="searchs();"  title="检索">查询</a>
									</td>
									<td style="vertical-align:middle;padding-left:2px;">
										<a class="btn btn-mini btn-info" onclick="toExcel();" title="导出到EXCEL">导出</a>
									</td>
								</tr>
							</table>
						</form>
						<!-- 检索  -->
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
								<thead>
									<tr>
										<th class="center">卡号</th>
										<th class="center">姓名</th>
										<th class="center">手机号</th>
										<th class="center">余额(元)</th>
										<th class="center">冻结标识</th>
										<th class="center">注销标识</th>
										<th class="center">激活时间</th>
										<th class="center">积分</th>
										<th class="center">标签</th>
										<th class="center" style="width: 200px;">操作</th>
									</tr>
								</thead>
								<tbody>
								<!-- 开始循环 -->	
								<c:choose>
									<c:when test="${not empty page.result}">
										<c:forEach items="${page.result}" var="merchantCard" varStatus="status">
											<tr>
												
												<td class="center">${merchantCard.cardNo }</td>
												<td class="center">${merchantCard.username }</td>
												<td class="center">${merchantCard.mobileNo }</td>
												<td align="right"><fmt:formatNumber value="${merchantCard.balance/100}" pattern="##,###,###,##0.00"/></td>
											    <td class="center"><c:if test="${merchantCard.freeze=='0'}">否</c:if><c:if test="${merchantCard.freeze=='1'}">是</c:if></td>
												<td class="center"><c:if test="${merchantCard.canceled=='0'}">否</c:if><c:if test="${merchantCard.canceled=='1'}">是</c:if></td>
												<td class="center"><fmt:formatDate value="${merchantCard.activeTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
												<td align="right">${merchantCard.points}</td>
												<td class="center">${merchantCard.labelNames}</td>
						
												<td class="center">
													<a title="详情" class="btn btn-mini btn-info" onclick="detail('${merchantCard.id }')"><i class="ace-icon fa fa-file-text-o bigger-120"></i>详情</a>		
													<a title="打标签" class="btn btn-mini btn-info" onclick="settingGet('${merchantCard.id }','${merchantCard.cardNo}','${merchantCard.labelIds}')"><i class="ace-icon fa fa-pencil-square bigger-120"></i>打标签</a>
													<c:if test="${mid eq '100100210000014'}">
													<a title="修改积分" class="btn btn-mini btn-danger" onclick="editPoint('${merchantCard.id }')"><i class="ace-icon fa fa-pencil-square bigger-120"></i>修改积分</a>
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
	<script src="${ctx}/staticNew/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="${ctx}/staticNew/ace/js/ace/ace.js"></script>
	<script src="${ctx}/staticNew/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!-- 下拉框 -->
	<script src="${ctx}/staticNew/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="${ctx}/staticNew/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());
	</script>
</body>
</html>