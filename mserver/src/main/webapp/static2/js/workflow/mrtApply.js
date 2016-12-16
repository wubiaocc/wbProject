function subimtBtn() {
	// var reloadFlag = $("#reload_flag").val();
	// 中间关联信息
	var profitId_ = $("#profitId").val();

	var form = $("#mrtApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {
				bootbox.alert(data.message);
				$("#profitId").val(data.obj.profitId);
				$("#mrtApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	form.ajaxSubmit(options);
}

// 银行卡收单保存
function subimtBankApply() {
	var merchantType = $("#merchantType").val();
	// 中间关联信息
	var profitId_ = $("#profitId").val();

	var form = $("#bankBasicFrom");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {
				bootbox.alert(data.message);
				$("#profitId").val(data.obj.profitId);
				$("#bankApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	form.ajaxSubmit(options);

}

// 银行卡收单保存
function subimtBankApplyBtn(taskId) {
	var merchantType = $("#merchantType").val();
	// 中间关联信息
	var profitId_ = $("#profitId").val();

	var form = $("#bankBasicFrom");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {
				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});
				$("#profitId").val(data.obj.profitId);
				$("#bankApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();

		}
	};
	form.ajaxSubmit(options);
}

/**
 * 申请提交审核
 */
function subimtApplyKeysForm() {
	// 中间关联信息

	var form = $("#applyKeysForm");

	// 中间关联信息
	var profitId_ = $("#profitId").val();

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert({
					buttons : {
						ok : {
							label : '确定',
						}
					},
					message : data.message,
					callback : function() {
						top.Dialog.close();
					},
				});

				form.find("input[name=profitId_]").remove();
			} else {
				bootbox.alert(data.message);
			}
		}
	};
	form.ajaxSubmit(options);
}

// 微信收单保存
function subimtWeixinApply() {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#weixinApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message);

				$("#profitId").val(data.obj.profitId);
				$("#weixinApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("weixinApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		return;
	}
}
// 微信收单保存
function subimtWeixinApplyBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#weixinApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#weixinApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();

			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("weixinApplyForm"))) {
		form.ajaxSubmit(options);
	} else {

		form.find("input[name=profitId_]").remove();
		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();

		return;
	}
}

// 支付宝收单保存
function subimtAlipayApply() {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#alipayApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message);

				$("#profitId").val(data.obj.profitId);
				$("#alipayApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("alipayApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		return;
	}
}

// 支付宝收单保存
function subimtAlipayApplyBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#alipayApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#alipayApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("alipayApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();
		return;
	}
}

// 百度收单保存
function subimtBaiduApply(formId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#" + formId);

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message);

				$("#profitId").val(data.obj.profitId);
				$("#baiduApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	// form.ajaxSubmit(options);
	if (checkFormValidate(document.getElementById(formId))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		return;
	}
}

// 百度收单保存
function subimtBaiduApplyBtn(formId, taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#" + formId);

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");

	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#baiduApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();

			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();

		}
	};
	if (checkFormValidate(document.getElementById(formId))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();

		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();

		return;
	}
}

// QQ收单保存
function subimtQqApply() {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#qqApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message);

				$("#profitId").val(data.obj.profitId);
				$("#qqApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("qqApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		return;
	}
}

// QQ收单保存
function subimtQqApplyBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#qqApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#qqApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();

			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();

		}
	};
	if (checkFormValidate(document.getElementById("qqApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();

		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();

		return;
	}
}

// 大商户收单保存
function subimtBigMerApply() {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#superMerApplyForm");

	form.append("<input type='hidden' name='profitId_' value='" + profitId_
			+ "'>");
	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message);

				$("#profitId").val(data.obj.profitId);
				$("#bigMerKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("superMerApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();
		return;
	}
}

// 大商户收单保存
function subimtBigMerApplyBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#superMerApplyForm");

	form.append("<input type='hidden' name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#bigMerKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}
			form.find("input[name=profitId_]").remove();

			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();
		}
	};
	if (checkFormValidate(document.getElementById("superMerApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		form.find("input[name=profitId_]").remove();

		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();

		return;
	}
}

/**
 * 取消申请 applyFlag 1.慧商户号,2.银行收单,3.微信收单,4.支付宝,5.QQ钱包,6.百度钱包,7.大商户
 */
function removeApplay(ctx, profitId, applyFlag, taskId, reApply) {

	$.ajax({
		url : ctx + '/sysMrtApply/complete',
		type : 'post',
		data : "taskId=" + taskId + "&reApply=" + reApply + "&profitId="
				+ profitId + "&type=" + applyFlag,
		success : function(data) {
			if (data.code == 0) {
				bootbox.alert({
					buttons : {
						ok : {
							label : '确定',
						}
					},
					message : data.message,
					callback : function() {
						top.Dialog.close();
					},
				});

			} else {
				bootbox.alert(data.message);
			}

		},
		error : function(data) {
		}
	});
}
// 商户申请提交处理
function subimtApplyBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();

	var form = $("#mrtApplyForm");

	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");

	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");

	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}

	var options = {
		success : function(data) {
			if (data.code == 0) {

				bootbox.alert(data.message, function() {
					top.Dialog.close();
				});

				$("#profitId").val(data.obj.profitId);
				$("#mrtApplyKey").val(data.obj.id);
			} else {
				bootbox.alert(data.message);
			}

			form.find("input[name=profitId_]").remove();
			form.find("input[name=taskId]").remove();
			form.find("input[name=startFlowFlag]").remove();

		}
	};
	form.ajaxSubmit(options);
}
/**
 * 微信审核通过
 */
function weiXinPass(ctx, profitId, applyFlag, taskId, reApply) {
	$.ajax({
		url : ctx + '/sysMrtApply/weiXinComplete',
		type : 'post',
		data : "taskId=" + taskId + "&reApply=" + reApply + "&profitId="
		+ profitId + "&type=" + applyFlag,
		success : function(data) {
			if (data.code == 0) {
				bootbox.alert({
					buttons : {
					ok : {
							label : '确定',
						}
					},
					message : data.message,
					callback : function() {
						top.Dialog.close();
					},
				});
			} else {
				bootbox.alert(data.message);
			}
		},
		error : function(data) {
		}
	});
}
//微信审核不通过，再次提交运维审核
function subimtWeixinApplyAgainBtn(taskId) {
	// 中间关联信息
	var profitId_ = $("#profitId").val();
	var form = $("#weixinApplyForm");
	
	form.append("<input type='hidden'  name='profitId_' value='" + profitId_
			+ "'>");
	form.append("<input type='hidden'  name='taskId' value='" + taskId + "'>");
	form.append("<input type='hidden'  name='submitAgain' value='" + true + "'>");
	
	if (taskId == '') {
		form.append("<input type='hidden'  name='startFlowFlag' value='" + true
				+ "'>");
	}
	
	var options = {
			success : function(data) {
				if (data.code == 0) {
					
					bootbox.alert(data.message, function() {
						top.Dialog.close();
					});
					
					$("#profitId").val(data.obj.profitId);
					$("#weixinApplyKey").val(data.obj.id);
				} else {
					bootbox.alert(data.message);
				}
				form.find("input[name=profitId_]").remove();
				
				form.find("input[name=taskId]").remove();
				form.find("input[name=startFlowFlag]").remove();
				form.find("input[name=submitAgain]").remove();
			}
	};
	if (checkFormValidate(document.getElementById("weixinApplyForm"))) {
		form.ajaxSubmit(options);
	} else {
		
		form.find("input[name=profitId_]").remove();
		form.find("input[name=taskId]").remove();
		form.find("input[name=startFlowFlag]").remove();
		form.find("input[name=submitAgain]").remove();
		
		return;
	}
}