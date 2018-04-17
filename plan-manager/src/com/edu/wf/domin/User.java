package com.edu.wf.domin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 *@author wangfei
 *用户实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_USER")
public class User extends BaseEntity implements Serializable{
	//用户的默认密码为6个6
	public static final String DEFAULT_PWD="666666";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "USER_ID", length = 50, nullable = false)
	private Long userId;//用户id
	
	@Column(name="USER_NAME",length=20,nullable=false)
	private String userName;//用户昵称
	
	//@Column(name="LOGIN_NAME",length=20,nullable=false)
	//private String loginName;//登陆名
	
	@Column(name="USER_PWD",length=50,nullable=false)
	private String userPwd;//登录密码
	
	@Column(name="ROLE_ID",length=50,nullable=false)
	private Long roleId;//所属角色id
	
	@Column(name="ROLE_NAME",length=50,nullable=false)
	private String roleName;//所属角色名
	
	@Column(name="TEL_PHONE",length=50,nullable=false)
	private String telPhone;//电话号码

	

//	public String getLoginName() {
//		return loginName;
//	}
//
//	public void setLoginName(String loginName) {
//		this.loginName = loginName;
//	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long departmentId) {
		this.roleId = departmentId;
	}

	public String getDepartmentName() {
		return roleName;
	}

	public void setDepartmentName(String departmentName) {
		this.roleName = departmentName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userPwd=" + userPwd + ", departmentId=" + roleId
				+ ", departmentName=" + roleName + ", telPhone="
				+ telPhone + "]";
	}

	public User(Long userId, String userName, String userPwd,
			Long departmentId, String departmentName, String telPhone) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.roleId = departmentId;
		this.roleName = departmentName;
		this.telPhone = telPhone;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long userId) {
		super();
		this.userId = userId;
	}
	
	
}
