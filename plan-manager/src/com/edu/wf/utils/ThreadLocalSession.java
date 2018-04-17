package com.edu.wf.utils;

import com.edu.wf.domin.User;

/**
 *@author wangfei
 */
public class ThreadLocalSession {

	
	private static final ThreadLocal<User> LOGIN_USER = new ThreadLocal<User>();

	public static User getUser() {
		return LOGIN_USER.get();
	}
	public static void setUser(User user){
		LOGIN_USER.set(user);
	}
	
	
}
