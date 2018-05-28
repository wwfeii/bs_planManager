package com.edu.wf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.ProcessDao;
import com.edu.wf.dao.RoleDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.domin.Role;
import com.edu.wf.domin.Task;
import com.edu.wf.domin.User;
import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.vo.MineCheckVo;

/**
 *@author wangfei
 */
@Repository
public class ProcessDaoImpl extends BaseDaoImpl<ProcessLog> implements ProcessDao{
	Logger logger = LoggerFactory.getLogger(ProcessDaoImpl.class);
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private RoleDao roleDao;
	@Override
	public ProcessLog getProcessLogByBussnessId(Long bussnessId) {
		logger.info("进入流程日志查询，businessid={}",bussnessId);
		List list = hibernateTemplate.find("from ProcessLog where businessId = "+bussnessId +" order by createdTime desc");
		if(list == null || list.size() == 0){
			logger.warn("list.size=0");
			return null;
		}
		return (ProcessLog) list.get(0);
	}
	@Override
	public int getCheckNumByCurrentUser() {
		//审核数量
		//任务审核
		User user = ThreadLocalSession.getUser();
		Long userId = user.getUserId();
		Long roleId = user.getRoleId();
		Role role = roleDao.getEntityById(roleId);
		int CheckNum = 0;
		String roleType = role.getRoleType();
		if(Role.ROLE_LEADER.equals(roleType)){//负责人角色
			if("项目负责人".equals(role.getRoleName()) || "管理员".equals(role.getRoleName())){
				List list = hibernateTemplate.find("from Plan where processNextUserId="+userId+" and planStatus !='已完成' and planStatus!='新建'");
				if(list != null && list.size()>0){
					return list.size();
				}else{
					return 0;
				}
			}
			String sql = "from Plan where planLeaderId = "+userId +" and planStatus = '"+Plan.STATUS_RUN+"'";
			List<Plan> list = hibernateTemplate.find(sql);
			if(list != null && list.size()>0){
				for(Plan plan : list){
					List tasks = hibernateTemplate.find("from Task where taskStatus = '"+Task.STATUS_CHECK+"' and planId = "+plan.getPlanId());
					if(tasks!=null && tasks.size()>0){
						CheckNum += list.size();
					}
				}
			}
			return CheckNum;
			
		}else{//审核角色
			//计划审核  
			String  planSql = "from Plan where planStatus = '"+Plan.STATUS_CHECK+"' and  processNextUserId = "+userId;
			List list2 = hibernateTemplate.find(planSql);
			if(list2 != null && list2.size()>0){
				return list2.size();
			}
			return CheckNum;
		}

		
	}
	@Override
	public List<MineCheckVo> getMineCheck() {
		
		User user = ThreadLocalSession.getUser();
		Long userId = user.getUserId();
		Long roleId = user.getRoleId();
		Role role = roleDao.getEntityById(roleId);
		String roleType = role.getRoleType();
		
		//这里的查询 逻辑有问题，后期可优化    这样在for里面嵌套查询不好
		//可用 in
		if(Role.ROLE_LEADER.equals(roleType)){//负责人角色
			if("项目负责人".equals(role.getRoleName()) || "管理员".equals(role.getRoleName())){
				List<Plan> list = hibernateTemplate.find("from Plan where processNextUserId="+userId+" and planStatus !='已完成' and planStatus!='新建'");
				if(list != null && list.size()>0){
					List<MineCheckVo> mineCheckVos = new ArrayList<MineCheckVo>();
					for(Plan plan : list){
						MineCheckVo mineCheckVo = new MineCheckVo();
						mineCheckVo.setInfoId(plan.getPlanId());
						mineCheckVo.setInfoName(plan.getPlanName());
						mineCheckVo.setInfoState(plan.getPlanStatus());
						mineCheckVo.setLeaderId(plan.getPlanLeaderId());
						mineCheckVo.setLoaderName(plan.getPlanLeaderName());
						mineCheckVo.setCreateTime(plan.getCreatedTime());
						mineCheckVo.setType(MineCheckVo.TYPE_PLAN);
						mineCheckVos.add(mineCheckVo);
					}
					return mineCheckVos;
				}else{
					return null;
				}
			}
			List<Task> result = new ArrayList();
			String sql = "from Plan where planLeaderId = "+userId +" and planStatus = '"+Plan.STATUS_RUN+"'";
			List<Plan> list = hibernateTemplate.find(sql);
			if(list != null && list.size()>0){
				for(Plan plan : list){
					List tasks = hibernateTemplate.find("from Task where taskStatus = '"+Task.STATUS_CHECK+"' and planId = "+plan.getPlanId());
					if(tasks!=null && tasks.size()>0){
						result.addAll(tasks);
					}
				}
			}
			//转换实体类
			
			List<MineCheckVo> mineCheckVos = new ArrayList<MineCheckVo>();
			for(Task task : result){
				MineCheckVo mineCheckVo = new MineCheckVo();
				mineCheckVo.setInfoId(task.getTaskId());
				mineCheckVo.setInfoName(task.getTaskTitle());
				mineCheckVo.setInfoState(task.getTaskStatus());
				mineCheckVo.setLeaderId(task.getTaskLeaderId());
				mineCheckVo.setLoaderName(task.getTaskLeaderName());
				mineCheckVo.setCreateTime(task.getCreatedTime());
				mineCheckVo.setType(MineCheckVo.TYPE_TASK);
				mineCheckVos.add(mineCheckVo);
			}
			return mineCheckVos;
			
		}else{//审核角色
			//计划审核  
			String  planSql = "from Plan where planStatus = '"+Plan.STATUS_CHECK+"' and  processNextUserId = "+userId;
			List<Plan> list2 = hibernateTemplate.find(planSql);
			List<MineCheckVo> mineCheckVos = new ArrayList<MineCheckVo>();
			for(Plan plan : list2){
				MineCheckVo mineCheckVo = new MineCheckVo();
				mineCheckVo.setInfoId(plan.getPlanId());
				mineCheckVo.setInfoName(plan.getPlanName());
				mineCheckVo.setInfoState(plan.getPlanStatus());
				mineCheckVo.setLeaderId(plan.getPlanLeaderId());
				mineCheckVo.setLoaderName(plan.getPlanLeaderName());
				mineCheckVo.setCreateTime(plan.getCreatedTime());
				mineCheckVo.setType(MineCheckVo.TYPE_PLAN);
				mineCheckVos.add(mineCheckVo);
			}
			return mineCheckVos;
			
		}

	}

}
