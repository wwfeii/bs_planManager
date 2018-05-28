package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author wangfei
 *计划实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_PLAN")
public class Plan extends BaseEntity{
	
	/*
	 * 计划状态:新建
	 */
	public static final String STATUS_CREATE="新建";
	
	/*
	 * 计划状态:审核中
	 */
	public static final String STATUS_CHECK="审核中";
	/*
	 * 计划状态:进行中
	 */
	public static final String STATUS_RUN="进行中";
	/*
	 * 计划状态:已完成
	 */
	public static final String STATUS_FINISH="已完成";
	
	public static final String STATUS_ALL="全部";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "PLAN_ID", length = 50, nullable = false)
	private Long planId;//计划id
	
	@Column(name="PLAN_NAME",length=50,nullable=false)
	private String planName;//计划名称
	
	@Column(name="PROJECT_ID",length=50,nullable=false)
	private Long projectId;//对应的项目id
	
	@Column(name="PROJECT_NAME",length=50,nullable=false)
	private String projectName;//对应的项目名称
	
	@Column(name="PLAN_LEADER_ID",length=50,nullable=false)
	private Long planLeaderId;//计划负责人id 
	@Column(name="PLAN_LEADER_NAME",length=50,nullable=false)
	private String planLeaderName;//计划负责人名称
	@Column(name="PLAN_STATUS",length=50,nullable=false)
	private String planStatus;//计划当前状态
	@Column(name="PLAN_PROCESSNO",length=50,nullable=true)
	private Integer planProcessNo = 1;//计划在流程中的顺序
	
	@Column(name="PROCESS_NEXT_USERID",length=50,nullable=true)
	private Long processNextUserId;//计划的下一步负责人
	@Column(name="TASK_TOTAL_NUM",length=50,nullable=true)
	private Integer taskTotalNum = 0;//总的任务数
	
	@Column(name="TASK_DISFINISH_NUM",length=50,nullable=true)
	private Integer taskDisFinishNum = 0;//未完成的任务数
	
	@Column(name="TASK_FINISH_NUM",length=50,nullable=true)
	private Integer taskFinishNum = 0;//已完成的任务数

	
	
	public Long getProcessNextUserId() {
		return processNextUserId;
	}

	public void setProcessNextUserId(Long processNextUserId) {
		this.processNextUserId = processNextUserId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

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

	public Long getPlanLeaderId() {
		return planLeaderId;
	}

	public Integer getPlanProcessNo() {
		return planProcessNo;
	}

	public void setPlanProcessNo(Integer planProcessNo) {
		this.planProcessNo = planProcessNo;
	}

	public void setPlanLeaderId(Long planLeaderId) {
		this.planLeaderId = planLeaderId;
	}

	public String getPlanLeaderName() {
		return planLeaderName;
	}

	public void setPlanLeaderName(String planLeaderName) {
		this.planLeaderName = planLeaderName;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public Integer getTaskTotalNum() {
		return taskTotalNum;
	}

	public void setTaskTotalNum(Integer taskTotalNum) {
		this.taskTotalNum = taskTotalNum;
	}

	public Integer getTaskDisFinishNum() {
		return taskDisFinishNum;
	}

	public void setTaskDisFinishNum(Integer taskDisFinishNum) {
		this.taskDisFinishNum = taskDisFinishNum;
	}

	public Integer getTaskFinishNum() {
		return taskFinishNum;
	}

	public void setTaskFinishNum(Integer taskFinishNum) {
		this.taskFinishNum = taskFinishNum;
	}

	public Plan(Long planId) {
		super();
		this.planId = planId;
	}

	public Plan() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Plan [planId=" + planId + ", planName=" + planName
				+ ", projectId=" + projectId + ", projectName=" + projectName
				+ ", planLeaderId=" + planLeaderId + ", planLeaderName="
				+ planLeaderName + ", planStatus=" + planStatus
				+ ", planProcessNo=" + planProcessNo + ", processNextUserId="
				+ processNextUserId + ", taskTotalNum=" + taskTotalNum
				+ ", taskDisFinishNum=" + taskDisFinishNum + ", taskFinishNum="
				+ taskFinishNum + "]";
	}
	
	

}
