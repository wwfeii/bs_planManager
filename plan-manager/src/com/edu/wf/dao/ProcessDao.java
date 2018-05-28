package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.ProcessLog;
import com.edu.wf.vo.MineCheckVo;

/**
 *@author wangfei
 */
public interface ProcessDao extends BaseDao<ProcessLog>{
	
	public ProcessLog getProcessLogByBussnessId(Long bussnessId);

	public int getCheckNumByCurrentUser();

	public List<MineCheckVo> getMineCheck();

}
