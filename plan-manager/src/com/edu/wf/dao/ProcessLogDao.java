package com.edu.wf.dao;

import com.edu.wf.domin.ProcessLog;

/**
 *@author wangfei
 */
public interface ProcessLogDao extends BaseDao<ProcessLog>{

	ProcessLog getLogsByBusinessId(Long businessId);
	
	

}
