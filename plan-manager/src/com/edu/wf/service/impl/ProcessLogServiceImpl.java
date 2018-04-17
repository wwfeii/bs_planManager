package com.edu.wf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.ProcessLogDao;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.service.ProcessLogService;

/**
 *@author wangfei
 */
@Service
public class ProcessLogServiceImpl implements ProcessLogService{

	@Autowired
	private ProcessLogDao logDao;
	@Override
	public void save(ProcessLog processLog) {
		logDao.save(processLog);
		
	}
	@Override
	public ProcessLog getLogsByBusinessId(Long businessId) {
		ProcessLog processLog = logDao.getLogsByBusinessId(businessId);
		return processLog;
	}

}
