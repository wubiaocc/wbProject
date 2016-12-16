<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="Bookmark" type="image/x-icon" href="${ctx}/static/icon/favicon.ico" />
<link rel="icon" href="${ctx}/static/icon/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/icon/favicon.ico"	type="image/x-icon" />
<title>门户</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/index.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/listGrid.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/formGrid.css" >
<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/popup.js" type="text/javascript"></script>
<script src="${ctx}/static/js/validate.js" type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/js/ichart.1.2.min.js" type="text/javascript"></script>
<%-- <script type="text/javascript" src="${ctx}/widgets/jq-ui/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lhgdialog/lhgdialog.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/jq-ui/themes/base/jquery.ui.all.css" > --%>
<link href="${ctx}/static/css/style_hy.css" type="text/css" rel="stylesheet">
<script src="${ctx}/static/js/Chart.js"></script>
<!-- <script type="text/javascript">
var c1,c2,c3,c4;
$(function(){
	  $.ajax({
			url:'${ctx}/profit/profitPersent',// 准备参数
			success:function(data) {
				var json=JSON.parse(data);
				c1=json.c1;
				c2=json.c2;
				c3=json.c3;
				c4=json.c4;
				var data = [
				        	{name : '银行卡支付分润',value : c1,color:'#0000FF',index:0},
				        	{name : '支付宝支付分润',value : c2,color:'#FF00FF',index:1},
				        	{name : '微信支付分润',value : c3,color:'#FFD700',index:2},
				        	{name : '其他支付分润',value : c4,color:'#80699b',index:3},
			        	];
				var chart = new iChart.Pie3D({
					render : 'canvasDiv',
					data: data,
					title : {
						text : '近7日支付方式分润比',
						textAlign: 'left',
						height:40,
						fontsize : 20,
						color : '#282828',
						font:'Song',
						fontweight:'bold'
					},
					footnote : {
						//text : 'wizarpos.com',
						color : '#486c8f',
						fontsize : 12,
						padding : '0 38'
					},
					sub_option : {
						mini_label_threshold_angle : 40,//迷你label的阀值,单位:角度
						mini_label:{//迷你label配置项
							fontsize:20,
							fontweight:600,
							color : '#ffffff'
						},
						label : {
							background_color:null,
							sign:false,//设置禁用label的小图标
							padding:'0 4',
							border:{
								enable:false,
								color:'#666666'
							},
							fontsize:11,
							fontweight:600,
							color : '#4572a7'
						},
						border : {
							width : 2,
							color : '#ffffff'
						},
						listeners:{
							parseText:function(d, t){
								return d.get('value')+"%";//自定义label文本
							}								
						} 
					},
					legend:{
						enable:true,
						padding:0,
						offsetx:10,
						offsety:50,
						color:'#3e576f',
						fontsize:20,//文本大小
						sign_size:20,//小图标大小
						line_height:28,//设置行高
						sign_space:10,//小图标与文本间距
						border:false,
						align:'left',
						background_color : null//透明背景
					}, 
					animation : true,//开启过渡动画
					animation_duration:800,//800ms完成动画 
					shadow : true,
					shadow_blur : 6,
					shadow_color : '#aaaaaa',
					shadow_offsetx : 0,
					shadow_offsety : 0,
					background_color:'#f1f1f1',
					align:'right',//右对齐
					offsetx:-10,//设置向x轴负方向偏移位置60px
					offset_angle:-90,//逆时针偏移120度
					width : 800,
					height : 700,
					radius:140
				});
				//利用自定义组件构造右侧说明文本
				chart.plugin(new iChart.Custom({
						drawFn:function(){
							//在右侧的位置，渲染说明文字
							chart.target.textAlign('start')
							.textBaseline('top')
							.textFont('600 20px Verdana')
							.fillText('分润统计\n',80,80,false,'#be5985',false,24)
							.textFont('600 12px Verdana')
							.fillText('近7天',60,140,false,'#999999');
						}
				}));
				chart.draw();
				chart.resize(500,400);
			},
			error:function(data){
				//alert("获取数据异常");
			}
		});
});
</script> -->
<!-- <script type="text/javascript">
$(function() {
	$.ajax({
		type : "get",
		url : "${ctx}/profit/lastSevenDaysProfit",
		success : function(data) {
			var sevenDays = JSON.parse(data);
			//var sevenDays = json.seven;
			var data1 = [ {
				name : '近7日分润曲线图：',
				value : sevenDays,
				color : '#0d8ecf',
				line_width : 2
			} ];
			var labels = [ "1", "2", "3", "4", "5", "6", "7" ];
			var line1 = new iChart.LineBasic2D({
				render : 'canvasDiv1',
				data : data1,
				align : 'center',
				title : {
						text : '近7日分润曲线图',
						height:40,
						fontsize : 20,
						color : '#282828',
						font:'Song',
						fontweight:'bold'
					},
				footnote : '',
				width : 348,
				height : 400,
				sub_option : {
					smooth : true,//平滑曲线
					point_size : 10
				},
				tip : {
					enable : true,
					shadow : true
				},
				legend : {
					enable : false
				},
				crosshair : {
					enable : true,
					line_color : '#62bce9'
				},
				coordinate : {
					width : '90%',
					axis : {
						color : '#9f9f9f',
						width : [ 0, 0, 2, 2 ]
					},
					grids : {
						vertical : {
							way : 'share_alike',
							value : 12
						}
					},	
					scale:[{
						position:'left',
						start_scale:0,
						scale_color:'#9f9f9f'
						},{
						position:'bottom',
						labels:labels
						}]
				}
			});
			//开始画图
			line1.draw();
		},
		error : function(e) {
			//alert("加载失败");
		}
	});
});
</script> -->
</head>
<body>
<div class="top"><jsp:include page="top_new.jsp"></jsp:include></div>
<div class="hy_content">
	<div class="top"><jsp:include page="leftBar.jsp"></jsp:include></div>
</div>
<div class="hy_right_con">
    	<div class="conbox">
        	<div class="column1 bdgrey">
        		<table class="list_grid">
        			<%-- <tr>
        				<td>历史累计分润：${totalProfit}元</td>
        			</tr> --%>
        			<%-- <tr>
        				<td>昨日分润：${yesterdayProfit}元，银行交易分润：${bankCardProfit}元，支付宝支付分润：${alipayProfit}元，微信支付分润：${weixinProfit}元</td>
        			</tr> --%>
        			<%-- <tr>
        				<td>上周分润：${lastWeekProfit}元，上月分润：${lastMonthProfit}元</td>
        			</tr> --%>
        			<tr>
        				<td>当前商户数：${totalMerNum}家，上周新增数：${lastWeekAddMer}家，上月新增数：${lastMonthAddMer}家</td>
        			</tr>
        			<tr>
        				<td>昨日商户交易汇总：${yesterdayTranTotal}元，上周商户交易汇总：${lastWeekTranTotal}元</td>
        			</tr>
        			<tr>
        				<td>上月商户交易汇总：${lastMonthTranTotal}元，本年商户交易汇总：${yearTranTotal}元</td>
        			</tr>
        			<tr>
        				<td>下级<!-- 代理商 -->数：${childAgentNum}家</td>
        			</tr>
        		</table>
            </div>
            <!-- <div class="column1 bdgrey mt20">
                <div style="width:500px; height:400px; float:left;margin-top:10px;"  id="canvasDiv"></div>
		 		<div id="canvasDiv1" style="width:348px; height:400px;float:left;margin-left:88px;margin-top:10px;"></div>
          		<div id="iChart2" style="clear:both; margin-top:430px;margin-left:25px;margin-bottom:20px; width:400px;height:380px;"></div>
            </div> -->
        </div>
    </div>    
</body>
</html>
