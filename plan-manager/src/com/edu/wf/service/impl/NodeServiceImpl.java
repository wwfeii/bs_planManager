package com.edu.wf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.NodeDao;
import com.edu.wf.domin.ProcessNode;
import com.edu.wf.service.NodeService;
import com.edu.wf.service.RoleService;
/**
 *@author wangfei
 */
@Service
public class NodeServiceImpl implements NodeService{
	
	@Autowired
	private NodeDao nodeDao;
	@Autowired
	private RoleService roleService;

	@Override
	public void addNode(ProcessNode node) {
		node.setRoleName(roleService.getEntityById(node.getRoleId()).getRoleName());
		nodeDao.save(node);
		
	}

	@Override
	public void deleteById(Long id) {
		ProcessNode node = new ProcessNode();
		node.setNodeId(id);
		nodeDao.delete(node);
		
	}

	@Override
	public void update(ProcessNode node) {
		ProcessNode dbNode = nodeDao.getEntityById(node.getNodeId());
		dbNode.setNodeName(node.getNodeName());
		dbNode.setNodeNo(node.getNodeNo());
		dbNode.setRoleId(node.getRoleId());
		dbNode.setRoleName(roleService.getEntityById(node.getRoleId()).getRoleName());
		nodeDao.update(dbNode);
		
	}

	@Override
	public ProcessNode getEntityById(Long id) {
		return nodeDao.getEntityById(id);
	}

	@Override
	public List<ProcessNode> getAll(String nodeType) {
		return nodeDao.getAll(nodeType);
	}

	@Override
	public ProcessNode getNextNodeName(String nodeType, Integer planProcessNo) {
		List<ProcessNode> nodes = this.getAll(nodeType);
		if(nodes == null ||nodes.size()==planProcessNo){
			return null;
		}
		ProcessNode processNode = nodes.get(planProcessNo);//这里 因为 节点 默认顺序是  从1开始  list中是从0开始  所以这里获取下一个节点就不用加1
		return processNode;
	}

}
