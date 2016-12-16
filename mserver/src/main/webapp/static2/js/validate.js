/**
 * 显示与隐藏
 */
function toogleTwoE(objShow, objHidden) {
	$(objShow).css("display","inline-block");
	$(objHidden).css("display","none");
}

/**
 * 验证输入框的合法性
 */
function checkValidates(obj) {
	var val = $(obj).val();
	var validates = $(obj).attr("validates");
	var label = $(obj).attr("label");
	//var txtTips = "#"+obj.id+"TxtTips";//获取提示信息id
	//var errorMsg = "#"+obj.id+"ErrorMsg";//获取错误提示信息id
	var validtesFlag = 0;//0表示正确，1表示错误
	if(validates != undefined) {
		if(validates.indexOf('required') > -1) {//必填项
			if(val==""){
				$("#"+obj.id).tips({
					side:3,
					msg:label+'为必填项',
					bg:'#FF0000',
					time:3
				});
				$("#"+obj.id).focus();
				validtesFlag = 1
				return;
			}else {
				validtesFlag = 0;
			}
		}
		if(validates.indexOf('imageUpload') > -1) {//图片必须上传
			if(val==""){
				bootbox.alert("<label style='color: red;'>请上传"+label+"！</label>");
				/*var showId=obj.id.replace("path","image");
				var focusId=obj.className;
				$("#"+showId).tips({
					side:2,
					msg:label+'必须上传！',
					bg:'#FF0000',
					time:3
				});
				$("#"+focusId).focus();*/
				validtesFlag = 1
				return;
			}else {
				validtesFlag = 0;
			}
		}
		if(val!="") {
			if(validates.indexOf('number') > -1) {//正整数
				var pattern = /^\d+$/;
				if(!pattern.test(val)){
					$("#"+obj.id).tips({
						side:3,
						msg:'请输入正整数',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1
					return;
				}else {
					validtesFlag = 0;
				}
			}
			if(validates.indexOf('money') > -1) {//金额
				var pattern = /^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
				if(!pattern.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'请输入正确的金额',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//Add by YLQ
			if(validates.indexOf('tel') > -1) {//手机号
				var pattern = /^1[3587][0-9]{9}$/;
				if(!pattern.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'请输入正确的号码',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//Add by YLQ 新增邮箱验证
			if(validates.indexOf('email') > -1) {//邮箱
				var pattern = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
				if(!pattern.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'请输入有效的Email',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			if(validates.indexOf('equals') > -1) {//验证两个值是否相等
				var substr = validates.substring(validates.indexOf('equals')+7);
				var  endPosition = substr.indexOf("]");
				var target = substr.substr(0,endPosition);
				if($("#" + target).val() != val) {
					//toogleTwoE(errorMsg,txtTips);
					var message="与"+$("#" + target).attr("label")+"不一致";
					$("#"+obj.id).tips({
						side:3,
						msg:message,
						bg:'#FF0000',
						time:3
					});
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			if(validates.indexOf('maxLength') > -1) {//输入框最大值验证
				var substr = validates.substring(validates.indexOf('maxLength')+10, validates.length-1);
				var maxLength = parseInt(substr);
				var m = val.match(/[\u4e00-\u9fff\uf900-\ufaff]/g);
	        	var ChineseLength = (!m?0:m.length);
	        	var valLength = (val.length - ChineseLength) + ChineseLength*2;
				if(valLength>maxLength) {
					//toogleTwoE(errorMsg,txtTips);
					var message="字母长度不能超过"+maxLength+",中文长度不能超过"+maxLength/2;
					$("#"+obj.id).tips({
						side:3,
						msg:message,
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			/*
			 * 验证座机号码
			 */
			if(validates.indexOf('phone') > -1) {//座机号码
				var pattern = /^\d+$/;
				var reg = /^\d{3}-\d{8}$/;
				var reg1 = /^\d{3}-\d{7}$/;
				var reg2 = /^\d{4}-\d{7}$/;
				var reg3 = /^\d{4}-\d{8}$/;
				var reg4 = /^\d{3}-\d{6}$/;
				var reg5 = /^\d{4}-\d{6}$/;
				if(reg.test(val) ||reg1.test(val)|| reg2.test(val)|| reg3.test(val) || reg4.test(val) || reg5.test(val)) {
					validtesFlag = 0;
				}else {
					$("#"+obj.id).tips({
						side:3,
						msg:'座机号码格式不正确,应如0514-1234567',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}
			}
			if(validates.indexOf('IDCard') > -1) {//身份证号
				var reg = /^\d{15}(\d\d[0-9xX])?$/;
				if(!reg.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:label+'不合法',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 中英文字符、数字和"_"或"-"
			if(validates.indexOf('noSpecial') > -1) {
				var reg = /^[A-Za-z0-9\u4E00-\u9FA5_-]+$/;
				if(!reg.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'不能有特殊字符',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 不含中文字符
			if(validates.indexOf('noChinese') > -1) {
				var reg = /^[^\u4e00-\u9fa5]+$/;
				if(!reg.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'不能有中文',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 英文、数字、'_'和空格
			if(validates.indexOf('englishName') > -1) {//英文名称
				var reg = /^[A-Za-z0-9_\s]+$/;
				var reg2 = /^[^\u4e00-\u9fa5]+$/;//不含汉字
				if(!reg.test(val) || !reg2.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:"只能由英文、数字、'_'和空格组成",
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 英文、数字、'_'
			if(validates.indexOf('loginName') > -1) {//英文名称
				var reg = /^[A-Za-z0-9_]+$/;
				var reg2 = /^[^\u4e00-\u9fa5]+$/;//不含汉字
				if(!reg.test(val) || !reg2.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:"只能由英文、数字和'_'组成",
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 纯数字
			if(validates.indexOf('onlyNum') > -1) {//纯数字
				var reg = /^\d+$/;
				if(!reg.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'只能输入数字',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 商户扣率(0-100保留两位小数)
			if(validates.indexOf('merDiscount') > -1) {
				var pattern = /^(0|[1-9]\d{0,1}|100)(\.\d{1,2})?$/;
				if(!pattern.test(val)) {
					$("#"+obj.id).tips({
						side:3,
						msg:'请输0-100之间,可保留两位小数',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				}else {
					validtesFlag = 0;
				}
			}
			//bin.cheng 省市区验证
			if(validates.indexOf('proCityArea') > -1) {
				if(val=="省份"||val=="") {
					$("#"+obj.id).tips({
						side:3,
						msg:'省份为必选项！',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				} else if(val=="市"||val==""){
					$("#"+obj.id).tips({
						side:3,
						msg:'城市为必选项！',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				} else if(val=="县"||val==""){
					$("#"+obj.id).tips({
						side:3,
						msg:'县城为必选项！',
						bg:'#FF0000',
						time:3
					});
					$("#"+obj.id).focus();
					validtesFlag = 1;
					return;
				} else {
					validtesFlag = 0;
				}
			}
		}
	}
	if(validtesFlag == 0) {
		//toogleTwoE(errorMsg,txtTips);
		//$(errorMsg).html('<img src="http://cashier2.wizarpos.com/portal-web/static/images/validate_ok.png" />');
		return true;
	}else {
		return false;
	}
}


/**
 * 验证表单
 */
function checkFormValidate(obj) {
	for(i=0;i<obj.length;i++){
		if($(obj[i]).css("visibility")!="hidden") {
			if(checkValidates(obj[i])) {
				continue;
			}else {
				return false;
			}
		}
	}
	return true;
}

/**
 *	实时监控文本框中文字数量
 */
function countTextareaNumber(obj) {
	var val = $(obj).val();
	var m = val.match(/[\u4e00-\u9fff\uf900-\ufaff]/g);
	var ChineseLength = (!m?0:m.length);
	var valLength = (val.length - ChineseLength) + ChineseLength*2;
	$("#"+ obj.id +"Number").html(valLength);
}

/**
*	时间查询，前后时间做校验
*/
function checkQueryTime()
{
	var d1 = document.getElementById("startTime").value;
	var d2 = document.getElementById("endTime").value;
	var time1 = new Date(d1.replace("-", "/").replace("-", "/"));
	var time2 = new Date(d2.replace("-", "/").replace("-", "/"));
	if(time1 > time2)
	{
	    return false;
	}else {
		return true;
	}
}

/**
 * 更据id验证输入框的合法性
 */
function checkValidatesById(id) {
	var obj = document.getElementById(id);
	return checkValidates(obj);
}