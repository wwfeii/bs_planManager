package com.edu.wf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.PlanDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.domin.User;
import com.edu.wf.service.PlanService;
import com.edu.wf.service.ProjectService;
import com.edu.wf.service.UserService;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.vo.EchartsVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
@Service
public class PlanServiceImpl implements PlanService{
	@Autowired
	private PlanDao planDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private UserService userService;
	@Override
	public void addPlan(Plan plan) {
		User user = userService.getEntityById(plan.getPlanLeaderId());
		plan.setPlanLeaderName(user.getUserName());
		plan.setProjectName(projectService.getEntityById(plan.getProjectId()).getProjectName());
		plan.setPlanStatus(Plan.STATUS_CREATE);//设置计划状态为新建
		plan.setProcessNextUserId(ThreadLocalSession.getUser().getUserId());//默认值为他自己
		planDao.save(plan);
	}

	@Override
	public void deleteById(Long id) {
		planDao.delete(new Plan(id));
	}

	@Override
	public void update(Plan plan) {
		Plan dbPlan = planDao.getEntityById(plan.getPlanId());
		dbPlan.setPlanName(plan.getPlanName());
		dbPlan.setPlanLeaderId(plan.getPlanLeaderId());
		dbPlan.setPlanLeaderName(userService.getEntityById(plan.getPlanLeaderId()).getUserName());
		dbPlan.setProjectId(plan.getProjectId());
		dbPlan.setProjectName(projectService.getEntityById(plan.getProjectId()).getProjectName());
		planDao.update(dbPlan);
	}

	@Override
	public Plan getEntityById(Long id) {
		return planDao.getEntityById(id);
	}

	@Override
	public List<Plan> getAll() {
		return planDao.getAll();
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize) {
		return planDao.getAllForPage(pageNum, pageSize);
	}

	@Override
	public boolean entityIsExist(String fieldName, String fieldVal) {
		return planDao.getEntityByName(fieldName, fieldVal);
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String planStatus,String searchVal) {
		return planDao.getAllForPage(pageNum, pageSize, "planStatus", planStatus,"planName",searchVal);
	}

	@Override
	public List<Task> getTaskByPlanId(Long planId) {
		
		return planDao.getTaskByPlanId(planId);
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String taskStatus, String searchVal, Long planId) {
		return planDao.query(pageNum,pageSize,taskStatus,searchVal,planId);
	}

	@Override
	public int getPlanNumByCurrentUser() {
		int num = planDao.getPlanNumByCurrentUser();
		return num;
	}

	@Override
	public List<Plan> getCurrentUserPlans() {
		List<Plan> list = planDao.getCurrentUserPlans();
		return list;
	}

	@Override
	public List<Plan> getCountPlans() {
		List<Plan> list = planDao.getCountPlans();
		return list;
	}

	@Override
	public List<EchartsVo> getStateTask(Long planId) {
		List<EchartsVo> result= planDao.getStateTask(planId);
		return result;
	}

	@Override
	public boolean entityIsExist(String string, String planName,
			String string2, Long planId) {
		return planDao.getEntityByName(string, planName,string2,planId);
	}

	@Override
	public ResponseDataVo checkPlanDelete(String ids) {
		return planDao.checkPlanDelete(ids);
	}

	@Override
	public ResponseDataVo checkAuth(Long planId) {
		
		return planDao.checkAuth(planId);
	}

	@Override
	public ResponseDataVo callbackAuth(Long planId) {
		return planDao.callbackAuth(planId);
	}

}
