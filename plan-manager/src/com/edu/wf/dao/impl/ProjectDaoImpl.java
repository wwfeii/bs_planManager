package com.edu.wf.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.ProjectDao;
import com.edu.wf.domin.Project;
import com.edu.wf.utils.ThreadLocalSession;

/**
 *@author wangfei
 */
@Repository
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public int getProjectNumByCurrentUser() {
		Long userId = ThreadLocalSession.getUser().getUserId();
		List list = hibernateTemplate.find("from Project where projectLeaderId="+userId+" and projectStatus != '"+Project.STATUS_FINISH+"'");
		if(list != null && list.size()>0){
			return list.size();
		}
		return 0;
	}

}
