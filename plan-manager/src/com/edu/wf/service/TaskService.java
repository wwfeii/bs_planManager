package com.edu.wf.service;

import java.util.List;

import com.edu.wf.domin.Task;
import com.edu.wf.vo.HomeTotalVo;
import com.edu.wf.vo.ResponseDataVo;


/**
 *@author wangfei
 */
public interface TaskService {
	
	/**
	 * 添加实体
	 * @param task
	 */
	public void addTask(Task task);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param task
	 */
	public void update(Task task);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public Task getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<Task> getAll();
	/**
	 * 修改计划下所有任务的状态
	 * @param bussnessId
	 */
	public void updateTaskStatusByPlanId(Long bussnessId);
	/**
	 * 按当前登陆人角色   分类统计数量
	 * @return
	 */
	public HomeTotalVo getTotalNum();
	
	/**
	 * 根据当前登陆人得到 任务总数
	 * @return
	 */
	public  int getTaskNumByCurrentUser();
	/**
	 * 得到当前登陆人的任务
	 * @return
	 */
	public List<Task> getCurrentUserTasks();
	/**
	 * 任务审核
	 * @param taskId
	 * @param checkDescription
	 * @return
	 */
	public ResponseDataVo checkTask(Long taskId, String checkDescription);

}
