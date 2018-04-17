package com.edu.wf.domin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *@author wangfei
 *流程日志实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TB_PROCESS_LOG")
public class ProcessLog extends BaseEntity{
	
	/**
	 * 操作类型： 审核
	 */
	public static final String TYPE_CHECK="审核";
	/**
	 * 操作类型： 打回
	 */
	public static final String TYPE_REPULSE="打回";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "LOG_ID", length = 50, nullable = false)
	private Long logId;//日志id
	
	@Column(name = "HANDLEAR_ID", length = 50, nullable = false)
	private Long handlearId;//处理人id
	
	@Column(name = "BUSINESS_ID", length = 50, nullable = false)
	private Long businessId;//业务id
	
	@Column(name = "OPERATE_TYPE", length = 50, nullable = false)
	private String operateType;//操作类型
	
	@Column(name = "CHECK_INFO", length = 300, nullable = false)
	private String checkInfo;//操作意见

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getHandlearId() {
		return handlearId;
	}

	public void setHandlearId(Long handlearId) {
		this.handlearId = handlearId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}
	
	
	
	

}
