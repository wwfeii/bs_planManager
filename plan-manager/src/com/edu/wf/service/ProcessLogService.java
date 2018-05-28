package com.edu.wf.service;

import com.edu.wf.domin.ProcessLog;

/**
 *@author wangfei
 */
public interface ProcessLogService {
	
	void save(ProcessLog processLog);

	ProcessLog getLogsByBusinessId(Long businessId);
}
