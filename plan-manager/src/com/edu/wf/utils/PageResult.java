package com.edu.wf.utils;

import java.io.Serializable;
import java.util.List;

/**
 *@author wangfei
 *分页返回对象
 * 
 * */
public class PageResult implements Serializable{
	private static final long serialVersionUID = 6535060303515340718L;
	
	private List list;
	private Integer totalNum;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	

}
