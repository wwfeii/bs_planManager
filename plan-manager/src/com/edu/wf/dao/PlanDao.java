package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
public interface PlanDao extends BaseDao<Plan>{

	PageResult query(Integer pageNum, Integer pageSize, String taskStatus,
			String searchVal, Long planId);

	List<Task> getTaskByPlanId(Long planId);

	int getPlanNumByCurrentUser();

}
