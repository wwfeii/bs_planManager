package com.edu.wf.vo;
/**
 *@author wangfei
 */
public class TaskCheckVo {
	private Long taskId;
	private String commitUserName;
	private String checkUserName;
	public String getCommitUserName() {
		return commitUserName;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public void setCommitUserName(String commitUserName) {
		this.commitUserName = commitUserName;
	}
	public String getCheckUserName() {
		return checkUserName;
	}
	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	

}
