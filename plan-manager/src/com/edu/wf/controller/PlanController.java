package com.edu.wf.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.domin.Project;
import com.edu.wf.domin.Task;
import com.edu.wf.domin.User;
import com.edu.wf.service.PlanService;
import com.edu.wf.service.ProcessLogService;
import com.edu.wf.service.TaskService;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.vo.EchartsVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 *计划管理api
 */
@Controller
@RequestMapping("/plan")
public class PlanController {
	@Autowired
	private PlanService planService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ProcessLogService processLogService;
	
	private static final Integer PAGE_SIZE = 10;//默认pageSize=10  可在configuration.properties中改
	public static final Logger LOG = LoggerFactory.getLogger(PlanController.class);
	
	/**
	 * 添加计划
	 * @param plan
	 * @return
	 */
	@RequestMapping(value="/addPlan",method=RequestMethod.POST)
	public String addPlan(Plan plan){

		LOG.info("增加计划，参数,plan={}",plan.toString());
		planService.addPlan(plan);
		List<Plan> list = planService.getAll();
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
		LOG.info("进入planController getJson方法，参数planStatus={},searchVal={}",planStatus,searchVal);
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
		LOG.info("进入plan,getTaskForPage,参数planId={},searchVal={}",planId,searchVal);
		PageResult result = planService.getForPage(pageNum, PAGE_SIZE,taskStatus, searchVal, planId);
		List<Task> tasks = result.getList();
		System.out.println("-=-=-taskId-=-=-="+tasks.get(0).getTaskId());
		List<Plan> list = this.getAllNoJson();
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
	@RequestMapping(value="/getAllNoJson")
	public List<Plan> getAllNoJson(){
		List<Plan> list = planService.getAll();
		return list;
	}
	@RequestMapping(value="/getAllPlan")
	public String getAllPlan(HttpServletRequest request){
		//List<Plan> list = planService.getAll();
		//得到当前登陆用户角色
		User loginUser = ThreadLocalSession.getUser();
		if("任务负责人".equals(loginUser.getRoleName())){//直接查询当前用户负责的任务信息
			LOG.info("改用户是--任务负责人");
			LOG.info("userId-=-=="+loginUser.getUserId());
			List<Task> tasks = taskService.getTasksByCurrentUser(loginUser.getUserId());
			request.setAttribute("tasks", tasks);
			return "forward:/page/task.action";
		}else{
			PageResult pageResult = planService.getForPage(1, 100);//
			request.setAttribute("plans", pageResult.getList());
			return "forward:/page/task.action";
		}
		
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

	/**
	 * 得到当前人负责的计划
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getMinePlans",method = RequestMethod.GET)
	public String getMinePlans(HttpServletRequest request){
		System.out.println("-=-=-=getMinePlans-=-=-=");
		List<Plan> plans = planService.getCurrentUserPlans();
		List<Plan> resPlans = new ArrayList<Plan>();
		for (Plan plan : plans) {
			ProcessLog processLog = processLogService.getLogsByBusinessId(plan.getPlanId());
			if(processLog == null){
			}else{
				if(ProcessLog.TYPE_REPULSE.equals(processLog.getOperateType())){
					plan.setPlanStatus(plan.getPlanStatus()+"(打回)");
				}else{
					
				}
			}
			resPlans.add(plan);
			
		}
		request.setAttribute("idsList", resPlans);
		request.setAttribute("plans", plans);
		request.setAttribute("total", plans.size());
		request.setAttribute("flag", "minePlan");//标识，是从这里跳转过去的
		return "forward:/page/plan.action";	

		
	}
	/**
	 * 统计页面 查询所有审核通过的计划
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/getCountPlans",method=RequestMethod.GET)
	public String getCountPlans(Model model){
		List<Plan> list = planService.getCountPlans();
		model.addAttribute("plans",list);
		return "forward:/page/count.action";
	}
	/**
	 * 得到不同状态任务的数量
	 * @return
	 */
	@RequestMapping(value="/getStateTask",method=RequestMethod.GET)
	@ResponseBody
	public List<EchartsVo> getStateTask(@RequestParam("planId") Long planId){
		List<EchartsVo> resultEchartsVos = planService.getStateTask(planId);
		return resultEchartsVos;
	}
	
	@RequestMapping("/checkPlanName")
	@ResponseBody
	public ResponseDataVo checkPlanName(@RequestParam("planName")String planName,@RequestParam("planId")Long planId){
		boolean flag = planService.entityIsExist("planName", planName,"planId",planId);
		if(flag){//重复
			return ResponseDataVo.ofFail("计划名已存在");
		}else{
			return ResponseDataVo.ofSuccess(null);
		}
	}
	/**
	 * 验证是否可以删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/checkPlanDelete")
	@ResponseBody
	public ResponseDataVo checkPlanDelete(@RequestParam("ids")String ids){
		return planService.checkPlanDelete(ids);
		
	}
	/**
	 * 验证是否有审核权限
	 * @param planId
	 * @return
	 */
	@RequestMapping("/checkAuth")
	@ResponseBody
	public ResponseDataVo checkAuth(@RequestParam("planId")Long planId){
		return planService.checkAuth(planId);
	}
	
	@RequestMapping("/callbackAuth")
	@ResponseBody
	public ResponseDataVo  callbackAuth(@RequestParam("planId")Long planId){
		return planService.callbackAuth(planId);
	}
	
	
	}
