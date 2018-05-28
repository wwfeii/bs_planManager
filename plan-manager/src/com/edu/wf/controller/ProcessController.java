package com.edu.wf.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.Role;
import com.edu.wf.service.ProcessService;
import com.edu.wf.vo.MineCheckVo;
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
	
	@RequestMapping(value="/mineCheck")
	public String mineCheck (Model model){
		//一个是计划审核  一个是任务审核，根据用户角色判断
		
		List<MineCheckVo> list = processService.getMineCheck();
		String type = MineCheckVo.TYPE_PLAN;
		if(list!=null && list.size()>0){
			type = list.get(0).getType();
		}
		model.addAttribute("checks", list);
		model.addAttribute("total", list.size());
		model.addAttribute("type", type);
		return "forward:/page/mineCheck.action";
	}
	
	

}
