package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author wangfei
 *角色实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_ROLE")
public class Role extends BaseEntity{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ROLE_ID", length = 50, nullable = false)
	private Long roleId;//角色id
	@Column(name = "ROLE_NAME", length = 50, nullable = false)
	private String roleName;//角色名
	@Column(name = "ROLE_DESCRIPTION", length = 300, nullable = true)
	private String roleDescription;//角色描述
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
	public Role(Long roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public Role() {
		super();
	}
	public Role(Long roleId) {
		super();
		this.roleId = roleId;
	}
	
	

}
