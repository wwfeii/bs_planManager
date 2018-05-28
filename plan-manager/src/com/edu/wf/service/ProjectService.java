package com.edu.wf.service;

import java.util.List;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Project;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
public interface ProjectService {
	/**
	 * 添加实体
	 * @param project
	 */
	public void addProject(Project project);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param project
	 */
	public void update(Project project);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public Project getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<Project> getAll();
	/**
	 * 分页查询
	 * @param pageNum 页数
	 * @param pageSize 每页大小
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
//	/**
//	 * 通过项目id 获得项目下的所有计划
//	 * @param projectId
//	 * @return
//	 */
//	public List<Plan> getPlansByProjectId(Long projectId);
	
	/**
	 * 得到当前登陆人负责的项目
	 * @return
	 */
	public int getProjectNumByCurrentUser();

	public List<Project> getMineProject();
}
