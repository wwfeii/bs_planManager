package com.edu.wf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面跳转API
 *@author wangfei
 */
@Controller
@RequestMapping("/page")
public class PageController{
	
	@RequestMapping("/index")
	public String goIndex(){
		return "index";
	}
	@RequestMapping("/top")
	public String goTop(){
		return "top";
	}
	
	@RequestMapping("/bottom")
	public String goBottom(){
		return "bottom";
	}
	
	@RequestMapping("/left")
	public String goLeft(){
		return "left";
	}
	@RequestMapping("/home")
	public String goHome(){
		return "home";
	}
	
	@RequestMapping("/project")
	public String goProject(){
		return "project";
	}
	@RequestMapping("/user")
	public String goUser(){
		return "user";
	}
	@RequestMapping("/plan")
	public String goPlan(){
		return "plan";
	}
	@RequestMapping("/role")
	public String goRole(){
		return "role";
	}
	@RequestMapping("/login")
	public String goLogin(){
		return "login";
	}
	@RequestMapping("/task")
	public String goTask(){
		return "task";
	}
	@RequestMapping("/process")
	public String goProcess(){
		return "process";
	}
	
	@RequestMapping("/test")
	public String goTest(){
		return "test";
	}
	
	@RequestMapping("/mineTask")
	public String goMineTask() throws Exception{
		return "mineTask";
	}


}
