package org.jgs1905.control;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.logging.Logger;

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
 * Servlet implementation class UserControl
 */
@WebServlet("/user")
public class UserControl extends HttpServlet {
	private static final long serialVersionUID = 18498465484654L;
	private static final Logger LOGGER = Logger.getAnonymousLogger(UserControl.class.getCanonicalName());
	private UserService userService = new UserService();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method =  request.getParameter("method");
		
		LOGGER.info("======================"+method+"===========================");
		
		
		switch (method) {
		case "login":
			login(request,response);
			break;
		case "regist":
			try {
				regist(request,response);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "forget":
			
			break;

		default:
			break;
		}
	
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
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
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
	private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
		User user = new User();
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		UserService userService = new UserService();
		int result = userService.regist(user);
		
		HttpSession session = request.getSession();
		if (result == 1) {
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/user/login.jsp");
		} else {
			request.setAttribute("message", "注册失败，请重试！");
			request.setAttribute("user", user);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
