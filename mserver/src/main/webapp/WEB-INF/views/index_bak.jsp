<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="Bookmark" type="image/x-icon" href="${ctx}/static/icon/favicon.ico" />
<link rel="icon" href="${ctx}/static/icon/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="${ctx}/static/icon/favicon.ico"	type="image/x-icon" />
<title>上海慧银信息科技有限公司</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/index.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/listGrid.css" >
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/formGrid.css" >

<script src="${ctx}/static/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${ctx}/static/js/popup.js" type="text/javascript"></script>
<script src="${ctx}/static/js/validate.js" type="text/javascript"></script>
<script src="${ctx}/static/js/common.js" type="text/javascript"></script>
<script src="${ctx}/static/js/ichart.1.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/widgets/jq-ui/jquery-ui-1.8.21.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/widgets/lhgdialog/lhgdialog.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/widgets/jq-ui/themes/base/jquery.ui.all.css" >
<script type="text/javascript">
var c1,c2,c3,c4,c5;
	  $(function(){
		  $.ajax({
				url:"${ctx}/getPieData?mid=<shiro:principal property='mid'/>",// 准备参数
				success:function(data) {
					var json=JSON.parse(data);
					c1=json.c1;
					c2=json.c2;
					c3=json.c3;
					c4=json.c4;
					c5=json.c5;
					c6=json.c6;

					var data = [
					        	{name : '支付宝支付',value : c1,color:'#0000FF',index:0},
					        	{name : '现金支付',value : c2,color:'#FF00FF',index:1},
					        	{name : '会员卡支付',value : c3,color:'#FFD700',index:2},
					        	{name : '银行卡支付',value : c4,color:'#80699b',index:3},
					        	{name : '微信支付',value : c5,color:'#3d96ae',index:4},
					        	{name : '其他支付',value : c6,color:'#FA8072',index:5}
				        	];
					var chart = new iChart.Pie3D({
						render : 'canvasDiv',
						data: data,
						title : {
							text : '收银易支付方式统计饼图',
							height:40,
							fontsize : 30,
							color : '#282828'
						},
						footnote : {
							text : 'wizarpos.com',
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
						radius:140,			
						listeners:{
							bound:function(se ,name,index){
								var url=encodeURI('${ctx}/goTo?mid=<shiro:principal property="mid"/>&type='+name);
								$.ajax({
									url:url,
									success:function(data) {
										MessageBox.Msg({title:name,content:data,mark:1,muli:1});
									},
									error:function(data){
										alert("数据加载失败！");
									}
								});
								
								
							}
						}
					});
					//利用自定义组件构造右侧说明文本
					chart.plugin(new iChart.Custom({
							drawFn:function(){
								//在右侧的位置，渲染说明文字
								chart.target.textAlign('start')
								.textBaseline('top')
								.textFont('600 20px Verdana')
								.fillText('支付方式统计\n',80,80,false,'#be5985',false,24)
								.textFont('600 12px Verdana')
								.fillText('2014年度',50,160,false,'#999999');
							}
					}));
					
					chart.draw();
					chart.resize(500,400);
				},
				error:function(data){
					alert("获取数据异常");
				}
			});
	   $.ajax({
		url:"${ctx}/getWeekData?mid=<shiro:principal property='mid'/>",
		type:"get",
		success:function(data) {
			var r=JSON.parse(data);
			var s1=r.s1;
			var s2=r.s2;
			var s3=r.s3;
			var s4=r.s4;
			var s5=r.s5;
			var s6=r.s6;
			var s7=r.s7;
			var labels=["一","二","三","四","五","六","七"];
			var data2 = [
			        	{
			        		name : '当前商户',
			        		value:[s1,s2,s3,s4,s5,s6,s7],
			        		color:'#1f7e92',
			        		line_width:3
			        	}
			       ];
			var chart2 = new iChart.LineBasic2D({
						render : 'canvasDiv2',
						data: data2,
						title : '7天内订单汇总',
						width : 500,
						height : 400,
						coordinate:{height:'90%',background_color:'#f6f9fa'},
						
						sub_option:{
							hollow_inside:false,//设置一个点的亮色在外环的效果
							point_size:10
						},
						labels:["一","二","三","四","五","六","七"],
						animation : true,//开启过渡动画
						animation_duration:600,//600ms完成动画
						tip:{
							enable:true,
							shadow:true,
							listeners:{
								 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
								parseText:function(tip,name,value,text,i){
									 return "<span style='color:#005268;font-size:12px;'>"+labels[i]+":订单数:<br/>"+
										"</span><span style='color:#005268;font-size:20px;'>"+value+"</span>";
								}
							}
						}
					});
			chart2.draw();
		},
		error:function (data){
			alert("加载失败!");
		}
	});
	   $.ajax({
			url:'${ctx}/getList',
			type:"post",
			data:{"mid":<shiro:principal property="mid"/>},
			success:function(data) {
				//var json=JSON.parse(data);
				//alert(data[0][0]);
			     
				var arr = new Array();
 
              
				html="";
				for(var i =0;i <data.length;i++){
					var o=document.getElementById(i+".1");	
					var b=document.getElementById(i+".2");
					o.innerText=data[i][1];
					b.innerText=data[i][0];
				}
			},
			error:function(data){
				alert("获取数据失败！");
			}
		});
		});
	 
		</script>
</head>
<body class="main_body">
	<div class="top"><jsp:include page="top.jsp"></jsp:include></div>
	<div class="middle">
		<div class="left"><jsp:include page="sidebar.jsp"></jsp:include></div>
		<div class="position">当前位置：<font class="last_position">首页</font></div>
		<div  style="width:500px; height:400px; float:left;margin-top:10px;"  id="canvasDiv"></div>
		<div  style="height:400px;width:500px; float:left; margin-top:10px;margin-left:10px;" id="canvasDiv2"></div>
		<!--  
		<div  style="margin-left:10px; margin-top:20px;padding-left:10px;">
		 <span style="padding-bottom:20px;padding-left:110px;"><b>本月产品销量top10</b></span>
		<table class="list_grid list_detail" style="width:400px; height:300px;">
		<tr>
		<th>产品</th><th>数量</th>
		</tr>
	  <tr><td id="0.1" align="center"></td><td id="0.2" align="center"></td></tr>
	  <tr><td id="1.1" align="center"></td><td id="1.2" align="center"></td></tr>
	  <tr><td id="2.1" align="center"></td><td id="2.2" align="center"></td></tr>
	  <tr><td id="3.1" align="center"></td><td id="3.2" align="center"></td></tr>
	  <tr><td id="4.1" align="center"></td><td id="4.2" align="center"></td></tr>
	  <tr><td id="5.1" align="center"></td><td id="5.2" align="center"></td></tr>
	  <tr><td id="6.1" align="center"></td><td id="6.2" align="center"></td></tr>
	  <tr><td id="7.1" align="center"></td><td id="7.2" align="center"></td></tr>
	  <tr><td id="8.1" align="center"></td><td id="8.2" align="center"></td></tr>
	  <tr><td id="9.1" align="center"></td><td id="9.2" align="center"></td></tr>				
		</table>
		</div>
		-->
	</div>
	<div class="bottom"><jsp:include page="foot.jsp"></jsp:include></div>
</body>
</html>