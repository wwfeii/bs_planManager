package com.edu.wf.service;

import java.util.List;

import com.edu.wf.vo.MineCheckVo;
import com.edu.wf.vo.PlanCheckVo;
import com.edu.wf.vo.ResponseDataVo;


/**
 *@author wangfei
 */
public interface ProcessService {
	
	/**
	 * 通过业务id(计划，任务) 组装审核实体
	 * @param bussnessId
	 * @return
	 */
	public PlanCheckVo getCheckInfo(Long bussnessId,String nodeType);
	/**
	 * 计划审核处理
	 * @param bussnessId 业务id
	 * @param checkId 下一步处理人
	 * @param checkInfo 审核意见
	 * @param isFinish 是否是最后一步
	 * @return
	 */
	public ResponseDataVo planCheck(Long bussnessId, Long checkId,
			String checkInfo,Boolean isFinish);
	/**
	 * 打回
	 * @param businessId
	 * @param checkInfo
	 * @return
	 */
	public ResponseDataVo callback(Long businessId, String checkInfo);
	
	/**
	 * 得到当前用户的审核数量
	 * @return
	 */
	public int getCheckNumByCurrentUser();
	/**
	 * 当前用户审核的信息 -- 任务 或者计划
	 * @return
	 */
	public List<MineCheckVo> getMineCheck();

}
