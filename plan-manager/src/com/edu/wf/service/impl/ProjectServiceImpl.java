package com.edu.wf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.ProjectDao;
import com.edu.wf.domin.Project;
import com.edu.wf.domin.User;
import com.edu.wf.service.ProjectService;
import com.edu.wf.service.UserService;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private UserService userService;

	@Override
	public void addProject(Project project) {
		User user = userService.getEntityById(project.getProjectLeaderId());
		project.setProjectLeaderName(user.getUserName());
		//设置项目状态
		project.setProjectStatus(Project.STATUS_CREATE);
		projectDao.save(project);
	}

	@Override
	public void deleteById(Long id) {
		projectDao.delete(new Project(id));
	}

	@Override
	public void update(Project project) {
		//修改 操作 注意  不要直接把页面传过来的 对象进行保存  
		Project dbProject = projectDao.getEntityById(project.getProjectId());
		dbProject.setProjectName(project.getProjectName());
		dbProject.setProjectLeaderId(project.getProjectLeaderId());
		User user = userService.getEntityById(project.getProjectLeaderId());
		dbProject.setProjectLeaderName(user.getUserName());
		projectDao.update(dbProject);
	}

	@Override
	public Project getEntityById(Long id) {
		return projectDao.getEntityById(id);
	}

	@Override
	public List<Project> getAll() {
		return projectDao.getAll();
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize) {
		
		return projectDao.getAllForPage(pageNum, pageSize);
	}

	@Override
	public boolean entityIsExist(String fieldName, String fieldVal) {
		return projectDao.getEntityByName(fieldName, fieldVal);
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String projectStatus, String searchVal) {
		return projectDao.getAllForPage(pageNum, pageSize, "projectStatus", projectStatus, "projectName", searchVal);
	}

	@Override
	public int getProjectNumByCurrentUser() {
		int num = projectDao.getProjectNumByCurrentUser();
		return num;
	}

}
