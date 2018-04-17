package com.edu.wf.service;

import java.util.List;

import com.edu.wf.domin.ProcessNode;

/**
 *@author wangfei
 */
public interface NodeService {
	
	/**
	 * 添加实体
	 * @param Plan
	 */
	public void addNode(ProcessNode node);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param role
	 */
	public void update(ProcessNode node);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public ProcessNode getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<ProcessNode> getAll(String nodeType);
	/**
	 * 得到当前节点的下一个节点信息
	 * @param nodeType
	 * @param planProcessNo
	 * @return
	 */
	public ProcessNode getNextNodeName(String nodeType, Integer planProcessNo);

}
