package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import javax.persistence.Entity;

/**
 *@author wangfei
 *任务实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_TASK")
public class Task extends BaseEntity{
	
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskTitle=" + taskTitle
				+ ", taskDescription=" + taskDescription + ", taskStatus="
				+ taskStatus + ", taskLeaderId=" + taskLeaderId
				+ ", taskLeaderName=" + taskLeaderName + ", planName="
				+ planName + ", planId=" + planId + ", checkInfo=" + checkInfo
				+ "]";
	}
	public static final String STATUS_ALL="全部";
	public static final String STATUS_CREATE="新建";
	/**
	 * 待执行
	 */
	public static final String STATUS_DISEXECUTE="待执行";
	public static final String STATUS_CHECK="审核中";
	public static final String STATUS_FINISH="已完成";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TASK_ID", length = 50, nullable = false)
	private Long taskId;//任务id
	
	@Column(name = "TASK_TITLE", length = 50, nullable = false)
	private String taskTitle;//任务标题
	
	@Column(name = "TASK_DESCRIPTION", length = 300, nullable = true)
	private String taskDescription;//任务描述
	
	@Column(name = "TASK_STATUS", length = 50, nullable = false)
	private String taskStatus;//任务状态
	
	@Column(name = "TASK_LEADER_ID", length = 50, nullable = false)
	private Long taskLeaderId;//任务负责人
	
	@Column(name = "TASK_LEADER_NAME", length = 50, nullable = false)
	private String taskLeaderName;//任务负责人
	
	@Column(name = "PLAN_NAME", length = 50, nullable = false)
	private String planName;//任务对应计划名
	
	@Column(name = "PLAN_ID", length = 50, nullable = false)
	private Long planId;//任务计划id
	
	@Column(name = "CHECK_INFO", length = 200, nullable = true)
	private String checkInfo;//审核说明
	
	
	public String getCheckInfo() {
		return checkInfo;
	}
	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskTitle() {
		return taskTitle;
	}
	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public Long getTaskLeaderId() {
		return taskLeaderId;
	}
	public void setTaskLeaderId(Long taskLeaderId) {
		this.taskLeaderId = taskLeaderId;
	}
	public String getTaskLeaderName() {
		return taskLeaderName;
	}
	public void setTaskLeaderName(String taskLeaderName) {
		this.taskLeaderName = taskLeaderName;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Long getPlanId() {
		return planId;
	}
	public void setPlanId(Long planId) {
		this.planId = planId;
	}
	public Task() {
		super();
	}
	public Task(Long taskId) {
		super();
		this.taskId = taskId;
	}

	
	
	

}
