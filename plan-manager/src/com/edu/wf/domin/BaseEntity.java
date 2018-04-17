package com.edu.wf.domin;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.utils.TimeUtils;


/**
 *@author wangfei
 *所有实体父类
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity implements Serializable{
	
	/** 创建时间 */
	@Column(name = "CREATED_TIME", length = 32, nullable = false)
	protected String createdTime = TimeUtils.format(new Date(), TimeUtils.DATE_TIME_FORMAT);
	/** 创建人主键 */
	@Column(name = "CREATOR_ID", length = 50, nullable = false)
	protected Long creatorId;
	/** 创建人名称 */
	@Column(name = "CREATOR_NAME", length = 50, nullable = false)
	protected String creatorName;
	/** 修改时间 */
	@Column(name = "UPDATED_TIME", length = 32, nullable = true)
	protected String updatedTime = TimeUtils.format(new Date(), TimeUtils.DATE_TIME_FORMAT);
	/** 修改人主键 */
	@Column(name = "UPDATOR_ID", length = 50, nullable = true)
	protected Long updatorId;
	/** 修改人名称 */
	@Column(name = "UPDATOR_NAME", length = 50, nullable = true)
	protected String updatorName;
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Long getUpdatorId() {
		return updatorId;
	}
	public void setUpdatorId(Long updatorId) {
		this.updatorId = updatorId;
	}
	public String getUpdatorName() {
		return updatorName;
	}
	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}
	
	

}
