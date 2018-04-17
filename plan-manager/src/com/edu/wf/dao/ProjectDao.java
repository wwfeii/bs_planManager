package com.edu.wf.dao;

import com.edu.wf.domin.Project;

/**
 *@author wangfei
 */
public interface ProjectDao extends BaseDao<Project>{

	int getProjectNumByCurrentUser();

}
