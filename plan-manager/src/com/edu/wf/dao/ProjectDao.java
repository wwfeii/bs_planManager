package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.Project;

/**
 *@author wangfei
 */
public interface ProjectDao extends BaseDao<Project>{

	int getProjectNumByCurrentUser();

	List<Project> getProjectsByCurrentUser();

}
