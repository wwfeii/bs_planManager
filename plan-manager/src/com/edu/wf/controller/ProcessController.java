package com.edu.wf.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.service.ProcessService;
import com.edu.wf.vo.PlanCheckVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 *流程审核相关API
 */
@Controller
@RequestMapping("/processs")
public class ProcessController {
	Logger logger = LoggerFactory.getLogger(ProcessController.class);
	
	@Autowired
	private ProcessService processService;
	
	/**
	 * 通过业务id组装审核实体
	 * @param businessId 业务id
	 * @param nodeType 审核类型
	 * @return
	 */
	@RequestMapping(value="/getCheckInfo",method = RequestMethod.GET)
	@ResponseBody
	public PlanCheckVo getCheckInfo(@RequestParam("businessId")Long businessId,
			@RequestParam("nodeType")String nodeType){
		PlanCheckVo checkInfo = processService.getCheckInfo(businessId, nodeType);
		return checkInfo;
	}
	/**
	 * 计划审核
	 * @param bussnessId 业务id
	 * @param checkId 下一步审核人
	 * @param checkInfo 审核已经
	 * @return
	 */
	@RequestMapping(value = "/planCheck",method=RequestMethod.POST)
	@ResponseBody
	public ResponseDataVo planCheck(@RequestParam("bussnessId")Long bussnessId,
			@RequestParam("checkId")Long checkId,
			@RequestParam("checkInfo")String checkInfo,
			@RequestParam("isFinish")Boolean isFinish){
		
		ResponseDataVo responseDataVo = processService.planCheck(bussnessId,checkId,checkInfo,isFinish);
		return responseDataVo;
		
	}
	/**
	 * 打回
	 * @param businessId 业务id
	 * @param checkInfo 打回原因
	 * @return
	 */
	@RequestMapping(value="/callback",method=RequestMethod.POST)
	@ResponseBody
	public ResponseDataVo callback(@RequestParam("bussnessId")Long businessId,
			@RequestParam("checkInfo")String checkInfo){
		logger.info("进入计划打回API，businessid={},checkInfo={}",businessId,checkInfo);
		ResponseDataVo responseDataVo = processService.callback(businessId,checkInfo);
		return responseDataVo;
	}
	
	

}
