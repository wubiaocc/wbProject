<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript" src="${ctx}/static2/ace/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static2/js/myjs/head.js"></script>
<link rel="stylesheet" href="${ctx}/static/css/common.css" />
<link rel="stylesheet" href="${ctx}/static2/ace/css/chosen.css" />
<link rel="stylesheet" href="${ctx}/static2/ace/css/dropzone.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<link rel="stylesheet" href="${ctx}/static2/ace/css/datepicker.css" />

<script type="text/javascript" src="${ctx}/static/js/popup.js"></script>
<link href="${ctx}/static/css/style_hy.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">

<script type="text/javascript" src="${ctx}/static2/ace/js/dropzone.js"></script>
<script type="text/javascript">
Dropzone.autoDiscover = false;//不会提示  Dropzone already attached
	$(document).ready(function(){
		$(".dropzone").dropzone({
			url: '${ctx}/file/upload',
			dictDefaultMessage: "<h5>文件拖到此处或点击此处上传</h5><br>",
			dictRemoveFile:"删除",
			dictCancelUpload: "取消上传",
	        dictCancelUploadConfirmation: "你确定要取消上传吗?",
	        acceptedFiles: ".xls",//限定上传xls文件
// 	        uploadMultiple:false,//不允许一次提交多个文件
// 	        addRemoveLinks : true,//队列文件添加删除按钮
			init: function() {
				this.on("success", function(file,obj) {
					insertFileId(obj.file_id,obj.file_originalName,obj.file_url);
				});
			}
		});
	});

	var fileId; //文件id
	var fileOriginalName; //文件名称
	var fileUrl;//文件相对路径
	//上传文件回调
	function insertFileId(file_id, file_originalName, file_url) {

		fileId = file_id;
		fileOriginalName = file_originalName;
		fileUrl = file_url;
	}

	function deleteFileId(file_id) {

	}

	function save() {
		if (fileId == null || fileId == "" || typeof (fileId) == 'undefined') {

			bootbox.alert("请上传文件");
			return false;
		}
		$("#zhongxin1").hide();
		$("#zhongxin2").show();
		productMakeSure();
	}

	function batchImportProductResult() {
		$.ajax({
			url : '${ctx}/mrtProInfo/batchImportMrtResult',
			type : "post",
			success : function(data) {
				$("#reload_flag").val("true");
				if (data.code == 0) {
					$("#message").append(data.obj + "<br>");

				} else if (data.code == 3) {
					clearInterval(Interval);
					$("#message").append(data.obj + "<br>");
					$("#jiazai").remove();
					$("#message1").remove();
					
				}else if(data.code == 1){
					clearInterval(Interval);
					$("#message").append(data.obj + "<br>");
					$("#jiazai").remove();
					$("#message1").remove();
				}
			}
		})

	}

	var Interval;
	
	function productMakeSure() {
		$.ajax({
			url : '${ctx}/mrtProInfo/batchImportMrt',
			data : "productFile=" + fileId,
			type : "post",
			success : function(data) {

				
				if (data.code == 0) {

					Interval=setInterval(batchImportProductResult, 5000);

				} else {
					bootbox.alert(data.obj, function() {
						top.Dialog.close();
					})
					setTimeout(function() {
						top.Dialog.close();
					}, "3000");

				}
			}
		})

	}

	function goBack() {
			window.location.href = "${ctx}/mrtProInfo/list";
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
					<div id="zhongxin1">
						<div class="row">

							<div class="col-xs-12">
									<input type="hidden" id="reload_flag" value="false" />
									<table class="table table-striped table-bordered table-hover">
										<tr>
											<td style="width: 100px; text-align: right; padding-top: 13px;">
												文档模板下载：
											</td>
											<td style="width: 120px; padding-top: 13px;"><a id="add"
												class="btn btn-mini btn-purple"
												href="${ctx}/mrtProInfo/download">
													<i class="ace-icon fa fa-file-excel-o bigger-120"></i>点击下载模板
											</a></td>
										</tr>
										<tr>
											<td colspan="2">
												<div align="center">
													<form  method="post" class="dropzone" style="height: 250px;background-color: #BBBBBB;">
														<div id="dropzone" class="fallback" style="height: 250px;">
															<input name="file" type="file" multiple="" />
														</div>
													</form>
												</div>
											</td>
										</tr>
									</table>

							</div>
						</div>
						<!-- /.row -->
					</div>
					<div id="zhongxin2" class="center"
						style="display: none; margin-top: 100px;">
						<img src="${ctx}/static2/images/jiazai.gif" id="jiazai"></img>
						<div id="message1">数据处理中...<br/></div>
						<div id="message"><br/></div>
					</div>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
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
	<script src="${ctx}/static2/ace/js/ace/elements.fileinput.js"></script>
	<!-- 下拉框 -->
	<script src="${ctx}/static2/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="${ctx}/static2/js/jquery.tips.js"></script>
</body>
</html>
