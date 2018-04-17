package com.edu.wf.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edu.wf.domin.User;
import com.edu.wf.utils.JsonUtils;
import com.edu.wf.utils.ThreadLocalSession;

/**
 * 用户登陆拦截
 *@author wangfei
 */
public class LoginInteceptor extends HandlerInterceptorAdapter{
	Logger logger = LoggerFactory.getLogger(LoginInteceptor.class);
	
	/**
	 * 请求前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String userInfo =(String)session.getAttribute("USER_SESSION_ID");
//		if(StringUtils.isEmpty(userInfo)){
//			//路径中包含这两个 就直接放行
//			if(request.getRequestURI().contains("login") || request.getRequestURI().contains("getValidateImg")){
//				return true;
//			}
//			logger.info("inteceptor 用户登陆拦截       uri="+request.getRequestURI());
//			//转发到登陆页面
//			request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
//			return false;
//		}
		//将用户登陆信息放入threadLocalSession中
		User user = JsonUtils.fromJson(userInfo, User.class);
		ThreadLocalSession.setUser(user);
		return true;
	}

}
