
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="bp" value="${ pageContext.request.contextPath }"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Login-Register</title>
<link rel="stylesheet" href="${bp}/static/css/style.css">
<link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/5.13.0/css/all.css" rel="stylesheet">
</head>
<body>

	<div class="container">
		<div class="login-box">
			<div class="apple-btn login-apple">
				<li class="red-btn"></li>
				<li class="yellow-btn"></li>
				<li class="green-btn"></li>
			</div>
			<div class="title">Login</div>
			<form action="${ bp }/user?method=login" method="post" id="loginForm">
			
			<div class="input">
				<i class="fas fa-user"></i>
				<input type="text" id="login-user" name="username" value="${ cookie.username.value}" placeholder="Username">
			</div>
			<div class="input">
				<i class="fas fa-key"></i>
				<input type="password" id="login-password" name="password" value="${ cookie.password.value }" placeholder="Password">
			</div><div class="input">
				<input type="checkBox" id="rememberMe" name="rememberMe" value="rememberMe" ${ cookie.rememberMe.value eq "rememberMe" ? "checked" : "" }><span style="color:rgb(217,220,223)">记住我</span>
			</div>
			<div class="btn login-btn">
			    <button class="login" type="submit">登录</button>
			</div>
			<div>
				<p style="color:red;">${ message }</p>
			</div>
			</form>
			<div class="change-box login-change">
				<div class="change-btn toSign">
					<span>去注册</span>
				</div>
			</div>
		</div>

		<div class="sign-box">
			<div class="apple-btn sign-apple">
				<li class="red-btn"></li>
				<li class="yellow-btn"></li>
				<li class="green-btn"></li>
			</div>
			<div class="title">Sign</div>
			<form action="${ bp }/user?method=register" method="post" id="registForm">
			<div class="input">
				<i class="fas fa-file-signature"></i>
				<input type="text" id="login-user" name="nickname" value="${ user.nickname }" placeholder="Nickname">
			</div>
			<div class="input">
				<i class="fas fa-user-plus"></i>
				<input type="text" id="sign-user" name="username" value="${ user.username }" placeholder="Have A Good Name?">
			</div>
			<div class="input">
				<i class="fas fa-unlock"></i>
				<input type="password" id="sign-password" name="password" value="${ user.password }" placeholder="Keep Secret">
			</div>
			<div class="btn sign-btn">
				<button class="register" type="submit">注册</button>
			</div>
			
			<div>
				<p style="color:red;">${ message }</p>
			</div>
			</form>
			<div class="change-box sign-change">
				<div class="change-btn toLogin">
					<span>去登陆</span>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ bp }/static/js/jquery-3.5.1.js"></script>
	<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	<script type="text/javascript" src="${ bp }/static/js/jquery.validate.min.js"></script>
	<script src="${bp}/static/js/script.js"></script>
	<script type="text/javascript">
	$(function() {
		// 指定表单进行校验
		$('#loginForm').validate({
			rules: {
				username: "required",
				password: 'required',
			},
			messages: {
				username: '用户名不能为空！',
				password: '密码不能为空！'
			}
		});
	});
	$(function() {
			// 自定义规则
			$.validator.addMethod('myUsernameRule', function(value, element, param) {
				let usernameRegx = /^\w+$/;
				return this.optional(element) || usernameRegx.test(value);
			});
			
			// 指定表单进行校验
			$('#registForm').validate({
				rules: {
					nickname: {
						required: true,
						maxlength: 10,
					},
					username: {
						required: true,
						minlength: 6,
						maxlength: 20,
						myUsernameRule: true
					},
					password: {
						required: true,
						minlength: 3,
						maxlength: 20,
						myUsernameRule: true
					}
					
				},
				messages: {
					nickname: {
						required: "昵称不能为空",
						maxlength: "昵称不能超过10位",
					},
					username: {
						required: "用户名不能为空",
						minlength: "用户名不能少于6位",
						maxlength: "用户名不能超过20位",
						myUsernameRule: "用户名必须是数字字母下划线组合"
					},
					password: {
						required: "密码不能为空",
						minlength: "密码不能少于6位",
						maxlength: "密码不能超过20位",
					},
					
				}
			});
		})
	</script>
</body>
</html>