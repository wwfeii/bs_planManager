package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author wangfei
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_NODE")
public class ProcessNode extends BaseEntity{
	/**
	 * 节点类型:计划审核节点
	 */
	public static final String TYPE_PLAN="typePlan";
	/**
	 * 节点类型:任务审核节点
	 */
	public static final String TYPE_TASK="typeTask";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "NODE_ID", length = 50, nullable = false)
	private Long nodeId;//节点id
	
	@Column(name="NODE_NAME",length=50,nullable=false)
	private String nodeName;//节点名称
	
	@Column(name="NODE_NO",length=20,nullable=false)
	private Long nodeNo;//节点顺序
	
	@Column(name="ROLE_ID",length=20,nullable=false)
	private Long roleId;//节点负责角色id
	
	@Column(name="ROLE_NAME",length=20,nullable=false)
	private String roleName;//节点负责角色名
	
	@Column(name="NODE_TYPE",length=50,nullable=true)
	private String nodeType;//节点类型

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(Long nodeNo) {
		this.nodeNo = nodeNo;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNode_type(String node_type) {
		this.nodeType = node_type;
	}
	
	
}
