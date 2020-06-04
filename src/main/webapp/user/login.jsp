<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="bp" value="${ pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户登录界面</title>
<link href="../static/css/applicationcss.css" rel="stylesheet"/>
<link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/5.13.0/css/all.css" rel="stylesheet">
</head>
<body>
	<div id="login_box">
		<h2>LOGING</h2>
		<form action="${bp}/user?method=login" method="post">
		<div id="form">
			<div id="input_box">
					<i class="fas fa-user"></i>
					<input type="text" placeholder="Username" name="username" value="${ user ne null ? user.username : cookie.username.value}"/>
				</div>
				<div id="input_box">
					<i class="fal fa-unlock-alt"></i>
					<input type="password" placeholder="Password" name="password" value="${ cookie.password.value }"/>
				</div>
				<div id="input_checkbox">
					<input type="checkbox"  name="rememberMe" value="rememberMe" ${ cookie.rememberMe.value eq "rememberMe" ? "checked" : "" }/>记住密码
				</div>
		</div>
		
		<button type="submit">Sign in</button><br>
		</form>
		<div id="Register">
			<a href="${bp}/user?method=regist">Register Now</a>
		</div><br>
		<div id="Sign">
			<a href="#"> 忘记密码？</a>
		</div>
		
	</div>
	
	
</body>
</html>