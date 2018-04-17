package com.edu.wf.vo;

import java.util.List;

import com.edu.wf.domin.User;

/**
 *@author wangfei
 *对应界面 审核弹框
 */
public class PlanCheckVo {
	//当前流程名
	private  String currentProcessNodeName;
	//下一步流程名
	private String nextProcessNodeName;
	//审核人list
	private List<User> checkUsers;
	//上一步审核意见
	private String preCheckInfo;
	public String getCurrentProcessNodeName() {
		return currentProcessNodeName;
	}
	public void setCurrentProcessNodeName(String currentProcessNodeName) {
		this.currentProcessNodeName = currentProcessNodeName;
	}
	public String getNextProcessNodeName() {
		return nextProcessNodeName;
	}
	public void setNextProcessNodeName(String nextProcessNodeName) {
		this.nextProcessNodeName = nextProcessNodeName;
	}
	public List<User> getCheckUsers() {
		return checkUsers;
	}
	public void setCheckUsers(List<User> checkUsers) {
		this.checkUsers = checkUsers;
	}
	public String getPreCheckInfo() {
		return preCheckInfo;
	}
	public void setPreCheckInfo(String preCheckInfo) {
		this.preCheckInfo = preCheckInfo;
	}
	
	
	

}
