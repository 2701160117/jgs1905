package org.jgs1905.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.jgs1905.entity.User;
import org.jgs1905.service.UserService;

/**
 * 用户控制器
 * @author Administrator
 *
 */
@WebServlet("/user")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
    
	public UserControl() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method =  request.getParameter("method");
		
		switch (method) {
		case "login":
			login(request, response);
			break;
		case "register":
			register(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			response.sendRedirect(request.getContextPath() + "/error/404.jsp");
			break;
		}
	
	}

	/**
	 * 	退出登录方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().removeAttribute("onlineUser");
		response.sendRedirect(request.getContextPath() + "/user/index.jsp");
	}


	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		User result = null;
		try {
			result = userService.login(user);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		if (result != null) {
			// 将登录用户保存到session
			session.setAttribute("onlineUser", result);
			
			// 登录成功，判断是否保存cookie
			String rememberMe = request.getParameter("rememberMe");
			if ("rememberMe".equals(rememberMe)) {
				// 1.创建cookie对象
				String encodeUsername = "";
				try {
					encodeUsername = URLEncoder.encode(result.getUsername(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Cookie usernameCookie = new Cookie("username", encodeUsername);
				Cookie passwordCookie = new Cookie("password", result.getPassword());
				Cookie rememberMeCookie = new Cookie("rememberMe", rememberMe);
				
				// 2.设置cookie的生存时间（单位：秒）
				usernameCookie.setMaxAge(60 * 60 * 24 * 7);
				passwordCookie.setMaxAge(60 * 60 * 24 * 7);
				rememberMeCookie.setMaxAge(60 * 60 * 24 * 7);
				
				// 3.使用response对象将cookie写回到浏览器
				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
				response.addCookie(rememberMeCookie);
			} else {
				// 1.创建cookie对象
				Cookie usernameCookie = new Cookie("username", "");
				Cookie passwordCookie = new Cookie("password", "");
				Cookie rememberMeCookie = new Cookie("rememberMe", "");
				
				// 2.设置cookie的生存时间（单位：秒）
				usernameCookie.setMaxAge(0);
				passwordCookie.setMaxAge(0);
				rememberMeCookie.setMaxAge(0);
				
				// 3.使用response对象将cookie写回到浏览器
				response.addCookie(usernameCookie);
				response.addCookie(passwordCookie);
				response.addCookie(rememberMeCookie);
			}
			
			response.sendRedirect(request.getContextPath() + "/main.jsp");
		} else {
			session.setAttribute("message", "用户名或密码错误！");
			request.setAttribute("user", user);
			request.getRequestDispatcher("/user/index.jsp").forward(request, response);
		}
	}

	/**
	 * 注册处理方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 获取请求参数并封装
				User user = new User();
				try {
					BeanUtils.populate(user, request.getParameterMap());
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				
				// 调用service方法保存用户
				int result = 0;
				try {
					result = userService.add(user);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				// 判断是否注册成功
				if (result == 0) {
					request.setAttribute("message", "注册失败，请重试！");
					request.setAttribute("user", user);
					request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/user/index.jsp");
				}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
