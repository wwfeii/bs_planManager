package com.edu.wf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.ProcessDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.domin.ProcessNode;
import com.edu.wf.domin.User;
import com.edu.wf.service.NodeService;
import com.edu.wf.service.PlanService;
import com.edu.wf.service.ProcessLogService;
import com.edu.wf.service.ProcessService;
import com.edu.wf.service.TaskService;
import com.edu.wf.service.UserService;
import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.vo.PlanCheckVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
@Service
public class ProcessServiceImpl implements ProcessService{

	@Autowired
	private PlanService planService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private ProcessLogService processLogService;
	@Override
	public PlanCheckVo getCheckInfo(Long bussnessId,String nodeType) {
		PlanCheckVo checkVo = new PlanCheckVo();
		if(nodeType.equals(ProcessNode.TYPE_PLAN)){//计划审核
			Plan plan = planService.getEntityById(bussnessId);
			//当前节点名
			checkVo.setCurrentProcessNodeName(plan.getPlanStatus());
			ProcessNode nextNode = nodeService.getNextNodeName(nodeType,plan.getPlanProcessNo());
			if(nextNode == null){
				checkVo.setNextProcessNodeName("结束");
				checkVo.setCheckUsers(new ArrayList<User>());
			}else{
				checkVo.setNextProcessNodeName(nextNode.getNodeName());
				//通过角色id得到用户
				List<User> users = userService.getUserByRoleId(nextNode.getRoleId());
				checkVo.setCheckUsers(users);
			}
			
			
			if(plan.getPlanProcessNo() != 1){//不是第一步时需要获取上一步审核意见
				ProcessLog processLog = processDao.getProcessLogByBussnessId(bussnessId);
				checkVo.setPreCheckInfo(processLog.getCheckInfo());
			}
		}else{
			
		}
		return checkVo;
	}
	@Override
	public ResponseDataVo planCheck(Long bussnessId, Long checkId,
			String checkInfo,Boolean isFinish) {
		//审核  1.修改计划状态    2.插入流程日志  
		Plan plan = planService.getEntityById(bussnessId);
		if(isFinish){//如果是审核流程最后一步，需要修改计划下所有任务的状态
			plan.setPlanProcessNo(plan.getPlanProcessNo()+1);
			plan.setPlanStatus(Plan.STATUS_RUN);
			planService.update(plan);
			//修改任务的状态
			taskService.updateTaskStatusByPlanId(bussnessId);
		}else {
			List<ProcessNode> list = nodeService.getAll(ProcessNode.TYPE_PLAN);
			ProcessNode node = list.get(plan.getPlanProcessNo());
			plan.setPlanProcessNo(plan.getPlanProcessNo()+1);
			plan.setPlanStatus(node.getNodeName());
			plan.setProcessNextUserId(checkId);
			planService.update(plan);
		}
		ProcessLog processLog = new ProcessLog();
		processLog.setBusinessId(bussnessId);
		processLog.setCheckInfo(checkInfo);
		processLog.setHandlearId(ThreadLocalSession.getUser().getUserId());//处理人为当前登陆人
		processLog.setOperateType(ProcessLog.TYPE_CHECK);
		processLogService.save(processLog);
		List<Plan> data = planService.getAll();
		return ResponseDataVo.ofSuccess(data);
	}
	@Override
	public ResponseDataVo callback(Long businessId, String checkInfo) {
		//打回 1.修改计划状态，插入日志
		Plan plan = planService.getEntityById(businessId);
		List<ProcessNode> list = nodeService.getAll(ProcessNode.TYPE_PLAN);
		ProcessNode node = list.get(plan.getPlanProcessNo()-2);
		plan.setPlanProcessNo(plan.getPlanProcessNo()-1);
		plan.setPlanStatus(node.getNodeName());
		//更具流程日志查询上一步审核人
		ProcessLog processLog = processLogService.getLogsByBusinessId(businessId);
		plan.setProcessNextUserId(processLog.getHandlearId());
		planService.update(plan);
		
		ProcessLog processLog2 = new ProcessLog();
		processLog2.setBusinessId(businessId);
		processLog2.setCheckInfo(checkInfo);
		processLog2.setHandlearId(ThreadLocalSession.getUser().getUserId());
		processLog2.setOperateType(ProcessLog.TYPE_CHECK);
		processLogService.save(processLog2);
		List<Plan> data = planService.getAll();
		return ResponseDataVo.ofSuccess(data);
	}
	@Override
	public int getCheckNumByCurrentUser() {
		int num = processDao.getCheckNumByCurrentUser();
		return num;
	}


}
