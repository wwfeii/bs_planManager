package com.edu.wf.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.TaskDao;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.ThreadLocalSession;

/**
 *@author wangfei
 */
@Repository
public class TaskDaoImpl extends BaseDaoImpl<Task> implements TaskDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public int getTaskNumByCurrentUser() {
		Long userId = ThreadLocalSession.getUser().getUserId();
		//只要状态不是完成的 都算不完成
		List list = hibernateTemplate.find("from Task where taskLeaderId="+userId+" and taskStatus != '"+Task.STATUS_FINISH+"'");
		if(list == null || list.size() == 0){
			return 0;
		}
		return list.size();
	}
	@Override
	public List<Task> getCurrentUserTasks() {
		Long userId = ThreadLocalSession.getUser().getUserId();
		//只要状态不是完成的 都算不完成
		List list = hibernateTemplate.find("from Task where taskLeaderId="+userId+" and taskStatus != '"+Task.STATUS_FINISH+"'");
		return list;
	}

}
