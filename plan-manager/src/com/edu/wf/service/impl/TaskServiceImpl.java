package com.edu.wf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.PlanDao;
import com.edu.wf.dao.TaskDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.service.PlanService;
import com.edu.wf.service.ProcessService;
import com.edu.wf.service.ProjectService;
import com.edu.wf.service.TaskService;
import com.edu.wf.service.UserService;
import com.edu.wf.vo.HomeTotalVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private PlanService planService;
	@Autowired
	private UserService userService;
	@Autowired
	private PlanDao planDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private ProcessService processService;

	@Override
	public void addTask(Task task) {
		Plan plan = planService.getEntityById(task.getPlanId());
		task.setPlanName(plan.getPlanName());//设置计划名称
		//更新任务对应计划的任务数量
		plan.setTaskTotalNum(plan.getTaskTotalNum()+1);
		plan.setTaskDisFinishNum(plan.getTaskDisFinishNum()+1);
		planDao.update(plan);
		task.setTaskLeaderName(userService.getEntityById(task.getTaskLeaderId()).getUserName());//设置负责人名称
		task.setTaskStatus(Task.STATUS_CREATE);//设置任务状态
		taskDao.save(task);
		
	}

	@Override
	public void deleteById(Long id) {
		Task task = taskDao.getEntityById(id);
		Plan plan = planService.getEntityById(task.getPlanId());
		plan.setTaskTotalNum(plan.getTaskTotalNum()-1);
		String taskStatus = task.getTaskStatus();
		if(taskStatus.equals(Task.STATUS_CREATE)|| taskStatus.equals(Task.STATUS_CHECK)){
			plan.setTaskDisFinishNum(plan.getTaskDisFinishNum()-1);
		}else if(taskStatus.equals(Task.STATUS_FINISH)){
			plan.setTaskFinishNum(plan.getTaskFinishNum() -1);
		}
		planDao.update(plan);
		taskDao.delete(new Task(id));
		
	}

	@Override
	public void update(Task task) {
		Task dbTask = taskDao.getEntityById(task.getTaskId());
		dbTask.setTaskTitle(task.getTaskTitle());
		dbTask.setTaskDescription(task.getTaskDescription());
		dbTask.setTaskLeaderId(task.getTaskLeaderId());
		dbTask.setTaskLeaderName(userService.getEntityById(task.getTaskLeaderId()).getUserName());
		taskDao.update(dbTask);
	}

	@Override
	public Task getEntityById(Long id) {
		return taskDao.getEntityById(id);
	}

	@Override
	public List<Task> getAll() {
		return taskDao.getAll();
	}

	@Override
	public void updateTaskStatusByPlanId(Long bussnessId) {
		String sql = "update tb_task set TASK_STATUS = '"+Task.STATUS_DISEXECUTE+"' where PLAN_ID = "+bussnessId;
		hibernateTemplate.getSessionFactory().openSession().createSQLQuery(sql).executeUpdate();
		
	}

	@Override
	public HomeTotalVo getTotalNum() {
		HomeTotalVo homeTotalVo = new HomeTotalVo();
		homeTotalVo.setTaskNum(this.getTaskNumByCurrentUser());
		homeTotalVo.setPlanNum(planService.getPlanNumByCurrentUser());
		homeTotalVo.setProjectNum(projectService.getProjectNumByCurrentUser());
		homeTotalVo.setCheckNum(processService.getCheckNumByCurrentUser());
		return homeTotalVo;
	}

	@Override
	public int getTaskNumByCurrentUser() {
		int num = taskDao.getTaskNumByCurrentUser();
		return num;
	}

	@Override
	public List<Task> getCurrentUserTasks() {
		List<Task> tasks = taskDao.getCurrentUserTasks();
		return tasks;
	}

	@Override
	public ResponseDataVo checkTask(Long taskId, String checkDescription) {
		if(checkDescription == null || checkDescription == ""){
			return ResponseDataVo.ofFail("审核说明不能为空");
		}
		Task task = taskDao.getEntityById(taskId);
		//修改任务状态
		task.setCheckInfo(checkDescription);
		if(Task.STATUS_CHECK.equals(task.getTaskStatus())){
			task.setTaskStatus(Task.STATUS_FINISH);
		}else{
			task.setTaskStatus(Task.STATUS_CHECK);
		}
		
		taskDao.update(task);
		//List<Task> tasks = taskDao.getCurrentUserTasks();
		return ResponseDataVo.ofSuccess(new ArrayList());
	}

	@Override
	public List<Task> getTasksByCurrentUser(Long userId) {
		return taskDao.getCurrentUserAllTasks(userId);
	}

}
