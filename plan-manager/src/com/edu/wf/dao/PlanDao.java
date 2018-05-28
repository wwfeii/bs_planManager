package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.PageResult;
import com.edu.wf.vo.EchartsVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
public interface PlanDao extends BaseDao<Plan>{

	PageResult query(Integer pageNum, Integer pageSize, String taskStatus,
			String searchVal, Long planId);

	List<Task> getTaskByPlanId(Long planId);

	int getPlanNumByCurrentUser();

	List<Plan> getCurrentUserPlans();

	List<Plan> getCountPlans();

	List<EchartsVo> getStateTask(Long planId);

	boolean getEntityByName(String string, String planName, String string2,
			Long planId);

	ResponseDataVo checkPlanDelete(String ids);

	ResponseDataVo checkAuth(Long planId);

	ResponseDataVo callbackAuth(Long planId);

}
