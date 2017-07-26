<!DOCTYPE html>
<HTML>
<HEAD>
<META content="text/html; charset=UTF-8" http-equiv="Content-Type">
<TITLE>proxy</TITLE>
<LINK rel="stylesheet" type="text/css" href="../css/app/themes/orange/login.min.css" media="all">
<link rel="shortcut icon" href="../css/app/themes/orange/images/talkingdata.png" type="image/x-icon" />
<SCRIPT>
	var basePath = 'http://localhost:8080/proxy/';
</SCRIPT>
<META name="GENERATOR" content="MSHTML 9.00.8112.16636">
</HEAD>
<BODY onkeydown="keyLogin()" class="regist">
<form action="../main/login.do" method="post" id="loginForm">
	<DIV class="content-regist">
		<EM><IMG src="../css/app/themes/orange/images/lgoo2.png"></A></EM>
		<DIV id="basic_login">
			<H3>登录</H3>
			<UL class="clearfix">
				<LI><INPUT id="user-name" class="input-regist" name="userId"
					type="text" placeholder="请输入用户名"> <FONT class="email-tip"></FONT></LI>
				<LI><INPUT id="password" class="input-regist" name="passwordShow"
					type="password" placeholder="6-32位密码"> 
					<INPUT id="passwordHidden" name="password" type="hidden" >
					<FONT></FONT></LI>
				<DIV class="remember clearfix">
					<STRONG><A onclick="forgetPassword()"
						href="javascript:void(0)">忘记密码?</A> </STRONG>
				</DIV>
				<a class="btn-login" onclick="login()" href="javascript:void(0)">登录</a>
			</UL>
		</DIV>
		<DIV style="display: none;" id="forget_pwd">
			<H3>
				<A class="back" onclick="backLogin()" href="javascript:void(0)">返回</A>重设密码
			</H3>
			<UL class="clearfix">
				<LI class="findtips">获取重设密码邮件</LI>
				<LI><INPUT id="regEmail" class="input-regist" name="regEmail"
					type="text" placeholder="请输入用户名..."> <FONT></FONT></LI>
				<FONT id="sendEmailTip"></FONT>
				<A class="btn-login" onclick="sendFindPwdEmail()"
					href="javascript:void(0)">重设密码</A>
			</UL>
		</DIV>
		<DIV style="display: none;" id="confirm_email">
			<H3>
				<A class="back" onclick="backLogin()" href="javascript:void(0)">登录</A>
			</H3>
			<UL class="clearfix">
				<LI><INPUT id="cfmRegEmail" class="input-regist" name="cfmRegEmail" type="text" placeholder="请输入用户名..."> <FONT></FONT></LI>
				<FONT id="confirmEmailTip"></FONT>
				<A class="btn-login" onclick="sendConfirmEmail()" href="javascript:void(0)">获取邮件</A>
			</UL>
		</DIV>
	</DIV>
	<span><c:if test="${loginMsg != null }">
		${loginMsg}
	</c:if></span>
	
	</form>
	<SCRIPT type="text/javascript" src="../js/lib/jquery/jquery-1.10.2.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="../js/lib/jquery/jquery.md5.js"></SCRIPT>
	<SCRIPT type="text/javascript" src="../js/zf/login.js"></SCRIPT>
	
</BODY>
</HTML>
