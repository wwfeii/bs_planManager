package com.edu.wf.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.ProcessLogDao;
import com.edu.wf.domin.ProcessLog;

/**
 *@author wangfei
 *
 */
@Repository
public class ProcessLogDaoImpl extends BaseDaoImpl<ProcessLog> implements ProcessLogDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Override
	public ProcessLog getLogsByBusinessId(Long businessId) {
		List list = hibernateTemplate.find("from ProcessLog where businessId = "+businessId+" order by createdTime desc");
		if(list == null || list.size()==0){
			return null;
		}
		return (ProcessLog) list.get(0);
	}

}
