package com.edu.wf.controller;

import java.util.List;

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

import com.edu.wf.dao.TaskDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Role;
import com.edu.wf.domin.Task;
import com.edu.wf.service.PlanService;
import com.edu.wf.service.TaskService;
import com.edu.wf.vo.HomeTotalVo;
import com.edu.wf.vo.ResponseDataVo;
import com.edu.wf.vo.TaskCheckVo;


//1.完成我的任务。我的计划 项目 我的审核
//2.部分验证  删除。审核等。
//3.修改页面的提交方式
//4.统计页面。
//5.权限管理  管理员 项目负责人 计划负责人 任务负责人
/**
 *@author wangfei
 *任务管理api
 */
@Controller
@RequestMapping("/task")
public class TaskController {
	
	public static final Logger LOG  = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private TaskDao taskDao;
	
	/**
	 * 添加任务
	 */
	@RequestMapping(value="/addTask",method=RequestMethod.POST)
	public String addTask(Task task,Model model){
		taskService.addTask(task);
		List<Task> list = planService.getTaskByPlanId(task.getPlanId());
		model.addAttribute("taskTotaoNum", list.size());
		return "forward:/plan/getTaskForPage.action?planId="+task.getPlanId();
	}
	
	/**
	 * 删除任务
	 * 
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam String ids,@RequestParam("planId") long planId){
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String id = idArray[i];
			if(!StringUtils.isEmpty(id)){
				taskService.deleteById(Long.parseLong(id));
			}
		}
		return "redirect:/plan/getTaskForPage.action?planId="+planId;
	}
	
	@RequestMapping(value="getTotal")
	@ResponseBody
	public Integer getTotal(@RequestParam("planId")Long planId){
		return planService.getTaskByPlanId(planId).size();
	}
	
	/**
	 * 修改信息
	 * @param task
	 * @return
	 */
	@RequestMapping(value = "/updateTask",method = RequestMethod.POST)
	public String updateTask(Task task){
		taskService.update(task);
		return "redirect:/plan/getTaskForPage.action?planId="+task.getPlanId();
	}
	
	/**
	 * 通过id  得到详情
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/getTaskById")
	@ResponseBody
	public Task getProjectById(@RequestParam("taskId") Long taskId){
		LOG.info("进入task,getTaskById方法,参数taskId={}",taskId);
		Task task = taskService.getEntityById(taskId);
		return task;
	}
	@RequestMapping("/getTaskCheckInfoById")
	@ResponseBody
	public TaskCheckVo getTaskCheckInfoById(@RequestParam("taskId") Long taskId){
		System.out.println("-=-=taskId-=-="+taskId);
		Task task = taskService.getEntityById(taskId);
		TaskCheckVo taskCheckVo = new TaskCheckVo();
		taskCheckVo.setCommitUserName(task.getTaskLeaderName());
		taskCheckVo.setTaskId(task.getTaskId());
		Plan plan = planService.getEntityById(task.getPlanId());
		taskCheckVo.setCheckUserName(plan.getPlanLeaderName());
		return taskCheckVo;
	}
	/**
	 * 分别统计 任务 计划 项目 审核的总数 首页展示
	 * @return
	 */
	@RequestMapping("/queryTotalNum")
	public String queryTotalNum(HttpServletRequest request,Model model){
		HomeTotalVo homeTotalVo= taskService.getTotalNum();
		model.addAttribute("totalNum", homeTotalVo);
		return "forward:/page/home.action";
	}
	/**
	 * 得到当前登陆人的任务  并跳转到对应页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/getMineTasks")
	public String getMineTasks(Model model){
		List<Task> tasks = taskService.getCurrentUserTasks();
		model.addAttribute("tasks", tasks);
		model.addAttribute("total", tasks.size());
		return "forward:/page/mineTask.action";
	}
	/**
	 * 审核任务
	 * @param taskId 任务id
	 * @param checkDescription 任务审核描述
	 * @return
	 */
	@RequestMapping(value = "/checkTask",method = RequestMethod.POST)
	@ResponseBody
	public ResponseDataVo checkTask(@RequestParam("taskId")Long taskId,
			@RequestParam("checkDescription")String checkDescription){
		taskService.checkTask(taskId,checkDescription);
		List<Task> tasks = taskDao.getCurrentUserTasks();
		return ResponseDataVo.ofSuccess(tasks);
	}

}
