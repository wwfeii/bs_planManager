package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author wangfei
 *项目实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_PROJECT")
public class Project extends BaseEntity{
	
	/*
	 * 项目状态：全部
	 */
	public static final String STATUS_ALL="全部";
	
	/*
	 * 项目状态：新建
	 */
	public static final String STATUS_CREATE="新建";
	/*
	 * 项目状态:进行中
	 */
	public static final String STATUS_NEWS="进行中";
	/*
	 * 项目状态:已完成
	 */
	public static final String STATUS_FINISH="已完成";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PROJECT_ID", length = 50, nullable = false)
	private Long projectId;//项目id
	
	@Column(name = "PROJECT_NAME", length = 50, nullable = false)
	private String projectName;//项目名称
	
	@Column(name = "PROJECT_LEADER_ID", length = 50, nullable = true)
	private Long projectLeaderId;//项目负责人id
	
	@Column(name = "PROJECT_LEADER_NAME", length = 50, nullable = true)
	private String projectLeaderName;//项目负责人名称
	
	@Column(name = "PROJECT_STATUS", length = 50, nullable = false)
	private String projectStatus;//项目状态

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Long getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(Long projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectName="
				+ projectName + ", projectLeaderId=" + projectLeaderId
				+ ", projectLeaderName=" + projectLeaderName
				+ ", projectStatus=" + projectStatus + "]";
	}

	public Project() {
		super();
	}

	public Project(Long projectId) {
		super();
		this.projectId = projectId;
	}
	
	

}
