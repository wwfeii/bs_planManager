package com.edu.wf.dao;

import com.edu.wf.domin.ProcessLog;

/**
 *@author wangfei
 */
public interface ProcessDao extends BaseDao<ProcessLog>{
	
	public ProcessLog getProcessLogByBussnessId(Long bussnessId);

	public int getCheckNumByCurrentUser();

}
