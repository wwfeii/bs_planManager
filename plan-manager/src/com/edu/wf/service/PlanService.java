package com.edu.wf.service;

import java.util.List;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.PageResult;
import com.edu.wf.vo.EchartsVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
public interface PlanService {
	
	/**
	 * 添加实体
	 * @param Plan
	 */
	public void addPlan(Plan plan);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param role
	 */
	public void update(Plan plan);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public Plan getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<Plan> getAll();
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResult getForPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 判断该字段值的实体是否存在
	 * @param fieldName 字段名
	 * @param fieldVal  字段值
	 * @return
	 */
	public boolean entityIsExist(String fieldName,String fieldVal);
	/**
	 * 根据状态
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param planStatus 计划状态 
	 * @param searchVal 搜索框内容
	 * @return
	 */
	public PageResult getForPage(Integer pageNum, Integer pageSize, String planStatus,String searchVal);
	/**
	 * 通过计划id查询所有的任务
	 * @param planId
	 * @param taskStatus 任务状态
	 * @return
	 */
	public List<Task> getTaskByPlanId(Long planId);
	/**
	 * 条件查询
	 * @param pageNum 页数
	 * @param pageSize 页大小
	 * @param taskStatus 任务状态
	 * @param searchVal 搜索内容
	 * @param planId 计划id
	 * @return
	 */
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String taskStatus, String searchVal, Long planId);
	/**
	 * 得到当前用户的计划数
	 * @return
	 */
	public int getPlanNumByCurrentUser();

	/**
	 * 得到当前用户负责的计划 未完成的
	 */
	public List<Plan> getCurrentUserPlans();

	/**
	 * 得到当前所有审核通过的计划
	 * @return
	 */
	public List<Plan> getCountPlans();

	public List<EchartsVo> getStateTask(Long planId);

	public boolean entityIsExist(String string, String planName,
			String string2, Long planId);

	public ResponseDataVo checkPlanDelete(String ids);

	public ResponseDataVo checkAuth(Long planId);

	public ResponseDataVo callbackAuth(Long planId);

}
