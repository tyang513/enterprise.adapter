//登录方法
function login() {
	var $user_name = $("#user-name"), user_name = $.trim($user_name.val()), $password = $("#password"), password = $
			.trim($password.val());
	if (user_name == '') {
		$user_name.val("");
		$user_name.siblings("font").html("用户名不能为空。");
		return false;
	} else {
		$user_name.siblings("font").html("");
	}

	if (password == '') {
		$password.val("");
		$password.siblings("font").html("密码不能为空。");
		return false;
	} else {
		$password.siblings("font").html("");
	}
	
	$("#passwordHidden").val($.md5(password));
	/* 请求登录接口 */
//	$.ajax({
//		url : "/proxy/main/login.do",
//		type : "POST",
//		contentType : 'application/json',
//		dataType : 'json',
//		data : {},
//		success : function(data) {
//
//		}
//	});
	$("#loginForm").submit();
}

function sendFindPwdEmail() {
	var $regEmail = $("#regEmail"), regEmail = $.trim($regEmail.val());
	if (regEmail == "") {
		$regEmail.siblings("font").html("请输人注册邮箱地址！");
		return false;
	} else if (!checkIsEmail(regEmail)) {
		$regEmail.siblings("font").html("邮箱地址错误，请重新输入！");
		return false;
	} else {
		$regEmail.siblings("font").html("");
		$.ajax({
			url : basePath + "/api/v1/findPasswdByEmail",
			type : 'get',
			data : data,
			cache : false,
			success : function(flag) {
				if (flag) {
					$("#sendEmailTip").html($.t('login.sendResetPwdEmailTip'));
				} else {
					$("#sendEmailTip").html($.t('login.serviceError'));
				}
			}
		});
	}

}
function checkIsEmail(email) {// 验证邮箱
	var email_reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	// var email_reg =
	// /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{1,}$/;
	if (email_reg.test(email)) {
		return true;
	} else {
		return false;
	}
}

/**
 * 检验输入的值
 * 
 * @return {[type]} [description]
 */
function checkInput(_this) {
	var _id = $(_this).attr("id") || "";
	if (_id == "email") {
		checkEmail(_this, false);
	} else if (_id == "password") {
		checkPassword(_this);
	} else if (_id == "regEmail") {
		checkRegEmail(_this);
	} else if (_id == "cfmRegEmail") {
		checkRegEmail(_this);
	}
}

function validateInputValue() {
	var _emailFlag = checkEmail($("#email"), false);
	var _pwdFlag = checkPassword($("#password"));

	return _emailFlag && _pwdFlag;
}

function callback() {
	window.location.href = url_account + "/api/v1/center";
	// var qs = window.location.search.substring(1);
	// if(qs){
	// var backurl = window.location.search.substring(9);
	// window.location.href = backurl;
	// }else{
	// window.location.href = url_account+"/api/v1/center";
	// }
}

function setCookie(_value, callback, _rm) {
	if (_rm) {
		$.cookie('tdppt', _value, {
			expires : 7,
			path : '/',
			domain : td_cookie_domain,
			secure : false,
			raw : false
		});
	} else {
		var date = new Date();
		date.setTime(date.getTime() + (30 * 60 * 1000));
		$.cookie('tdppt', _value, {
			expires : date,
			path : '/',
			domain : td_cookie_domain,
			secure : false,
			raw : false
		});
	}
	callback && callback();
}

function registFromLogin() {
	var qs = window.location.search.substring(1);
	if (qs) {
		window.location.href = url_account + "/regist.jsp?" + qs;
	} else {
		window.location.href = url_account + "/regist.jsp";
	}
}

function forgetPassword() {
	$("#basic_login").hide();
	$("#forget_pwd").show();
	$("#confirm_email").hide();
}

function backLogin() {
	$("#forget_pwd").hide();
	$("#basic_login").show();
	$("#confirm_email").hide();
}

function notReceivedEmail() {
	$("#forget_pwd").hide();
	$("#basic_login").hide();
	$("#confirm_email").show();
}

function sendConfirmEmail() {
	var _emailFlag = checkRegEmail($("#cfmRegEmail"));

	if (_emailFlag) {
		var data = {
			email : encode($("#cfmRegEmail").val().trim()),
			backurl : curBackUrl(),
			t : Math.random()
		}
		$.ajax({
			url : basePath + "/api/v1/sendConfirmEmail",
			type : 'get',
			data : data,
			cache : false,
			success : function(flag) {
				if (flag) {
					$("#confirmEmailTip")
							.html($.t('login.sendConfirmEmailTip'));
				} else {
					$("#confirmEmailTip").html($.t('login.serviceError'));
				}
			}
		});
	}
	;
}

function curBackUrl() {
	var qs = window.location.search.substring(1);
	var backurl = url_account + "/api/v1/center";
	if (qs) {
		backurl = window.location.search.substring(9);
	}
	return backurl;
}

function keyLogin() {
	var _event = arguments.callee.caller.arguments[0] || window.event;
	if (_event.keyCode == 13) {// 回车键的键值为13
		login();
	}
}