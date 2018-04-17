package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.domin.ProcessNode;

/**
 *@author wangfei
 */
public interface NodeDao extends BaseDao<ProcessNode>{
	public List<ProcessNode> getAll(String nodeType);

}
