package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.Task;

/**
 *@author wangfei
 */
public interface TaskDao extends BaseDao<Task>{

	int getTaskNumByCurrentUser();

	List<Task> getCurrentUserTasks();

}
