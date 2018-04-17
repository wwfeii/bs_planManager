package com.edu.wf.vo;

import java.util.List;

/**
 *@author wangfei
 */
public class ResponseDataVo<T> {
	
	private int code;
	private String msg;
	private List<T> data;

	public ResponseDataVo(){
		
	}
	
	public ResponseDataVo(int code, String msg, List<T> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static ResponseDataVo ofSuccess(List data){
		return new ResponseDataVo(200,"请求成功",data);
	}
	public static ResponseDataVo ofFail(String msg){
		return new ResponseDataVo(500,msg,null);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
