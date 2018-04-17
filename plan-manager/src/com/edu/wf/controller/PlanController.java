package com.edu.wf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Project;
import com.edu.wf.domin.Task;
import com.edu.wf.service.PlanService;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 *计划管理api
 */
@Controller
@RequestMapping("/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	
	private static final Integer PAGE_SIZE = 10;//默认pageSize=10  可在configuration.properties中改
	
	/**
	 * 添加计划
	 * @param plan
	 * @return
	 */
	@RequestMapping(value="/addPlan",method=RequestMethod.POST)
	public String addPlan(Plan plan){
		planService.addPlan(plan);
		return "redirect:/plan/getAll.action";
		
	}
	/**
	 * 分页查询--跳转页面
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request,@RequestParam(defaultValue="1",value="pageNum")Integer pageNum){
		PageResult result = planService.getForPage(pageNum, PAGE_SIZE);
		request.setAttribute("plans", result.getList());
		request.setAttribute("total", result.getTotalNum());
		return "forward:/page/plan.action";		
	}
	
	/**
	 * 分页查询--返回json
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping(value = "/getJson",method = RequestMethod.POST)
	@ResponseBody
	public List getJson(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum,
			@RequestParam(defaultValue=Task.STATUS_ALL,value="planStatus")String planStatus,
			@RequestParam(defaultValue="",value="searchVal")String searchVal,
			@RequestParam(defaultValue="",value="planId")Long planId){
		PageResult result = planService.getForPage(pageNum, PAGE_SIZE, planStatus, searchVal);
		return result.getList();
		
	}
	
	/**
	 * 通过计划id  条件查询所有的任务 跳转页面
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getTaskForPage")
	public String getTaskForPage(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum,
			@RequestParam(defaultValue=Task.STATUS_ALL,value="taskStatus")String taskStatus,
			@RequestParam(defaultValue="",value="searchVal")String searchVal,
			@RequestParam(defaultValue="",value="planId")Long planId,
			Model model){
		PageResult result = planService.getForPage(pageNum, PAGE_SIZE,taskStatus, searchVal, planId);
		List tasks = result.getList();
		List<Plan> list = this.getAllJson();
		model.addAttribute("plans", list);
		model.addAttribute("tasks", tasks);
		model.addAttribute("newPlanId", planId);
		return "forward:/page/task.action?planId="+planId;
		
	}
	
	
	/**
	 * 通过计划id  条件查询所有的任务 返回json数据
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping(value = "/getTaskJson",method = RequestMethod.POST)
	@ResponseBody
	public List getTaskJson(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum,
			@RequestParam(defaultValue=Task.STATUS_ALL,value="taskStatus")String taskStatus,
			@RequestParam(defaultValue="",value="searchVal")String searchVal,
			@RequestParam(defaultValue="",value="planId")Long planId,
			Model model){
		PageResult result = planService.getForPage(pageNum, PAGE_SIZE,taskStatus, searchVal, planId);
		return result.getList();
		
	}
	
	/**
	 * 得到总记录数
	 * @return
	 */
	@RequestMapping("/getTotal")
	@ResponseBody
	public Integer getTotal(){
		int size = planService.getAll().size();
		return size;
	}

	/**
	 * 删除项目
	 * 
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam String ids){
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String id = idArray[i];
			if(!StringUtils.isEmpty(id)){
				planService.deleteById(Long.parseLong(id));
			}
		}
		return "redirect:/plan/getAll.action";
	}
	/**
	 * 得到所有，以json格式返回
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/getAllJson")
	@ResponseBody
	public List<Plan> getAllJson(){
		List<Plan> list = planService.getAll();
		return list;
	}
	@RequestMapping(value="/getAllPlan")
	public String getAllPlan(HttpServletRequest request){
		List<Plan> list = planService.getAll();
		request.setAttribute("plans", list);
		return "task";
	}

	/**
	 * 修改信息
	 * @param plan
	 * @return
	 */
	@RequestMapping(value = "/updatePlan",method = RequestMethod.POST)
	public String updateProject(Plan plan){
		planService.update(plan);
		return "redirect:/plan/getAll.action";
	}
	
	/**
	 * 通过id  得到详情
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getPlanById")
	@ResponseBody
	public Plan getProjectById(@RequestParam("planId") Long planId){
		Plan plan = planService.getEntityById(planId);
		return plan;
	}
}
