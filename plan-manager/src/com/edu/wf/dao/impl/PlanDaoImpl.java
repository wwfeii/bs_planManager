package com.edu.wf.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.PlanDao;
import com.edu.wf.dao.ProcessLogDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.domin.Task;
import com.edu.wf.domin.User;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;
import com.edu.wf.vo.EchartsVo;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
@Repository
public class PlanDaoImpl  extends BaseDaoImpl<Plan> implements PlanDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private ProcessLogDao processLogDao;
	
	@Override
	public PageResult query(Integer pageNum, Integer pageSize,
			String taskStatus, String searchVal, Long planId) {
		String queryVal="FROM Task where ";
		int count = 1;
		if(!Task.STATUS_ALL.equals(taskStatus)){
			count++;
			queryVal+=" taskStatus = '"+taskStatus+"'";
		}
		if(!"".equals(searchVal)){
			if(count == 2){
				queryVal += " and ";
			}
			count ++;
			queryVal += " taskTitle like '%"+searchVal+"%' ";
		}
		if(!"".equals(planId)){
			if(count != 1){
				queryVal += " and ";
			}
			queryVal += " planId = "+planId;
		}
		queryVal+=" order by createdTime asc";
		Session session = hibernateTemplate.getSessionFactory().openSession();
		System.out.println("-=-=-=queryVal-=-=-="+queryVal);
		Query query = session.createQuery(queryVal);
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		List<Task> list = query.list();
		PageResult pageResult = new PageResult();
		pageResult.setList(list);
		session.close();
		return pageResult;
	}
	@Override
	public List<Task> getTaskByPlanId(Long planId) {
		hibernateTemplate.find("from Task where planId = "+planId);
		return null;
	}
	@Override
	public int getPlanNumByCurrentUser() {
		Long userId = ThreadLocalSession.getUser().getUserId();
		List list = hibernateTemplate.find(" from Plan where planLeaderId = "+userId+" and  planStatus !='"+Plan.STATUS_FINISH+"'");
		if(list !=null && list.size()>0){
			return list.size();
		}
		return 0;
	}
	@Override
	public List<Plan> getCurrentUserPlans() {
		Long userId = ThreadLocalSession.getUser().getUserId();
		List list = hibernateTemplate.find(" from Plan where planLeaderId = "+userId+" and  planStatus !='"+Plan.STATUS_FINISH+"'");
		if(list !=null){
			return list;
		}
		return null;
	}
	@Override
	public List<Plan> getCountPlans() {
		List<Plan> list = hibernateTemplate.find("from Plan where planStatus = '"+Plan.STATUS_RUN+"' or planStatus = '"+Plan.STATUS_FINISH+"'");
		
		return list;
	}
	@Override
	public List<EchartsVo> getStateTask(Long planId) {

			List<Task> tasks = hibernateTemplate.find("from Task where planId="+planId);
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (Task task : tasks) {
				String taskStatus = task.getTaskStatus();
				if(map.containsKey(taskStatus)){
					Integer integer = map.get(taskStatus);
					map.put(taskStatus, (integer+1));
				}else{
					map.put(taskStatus, 1);
				}
			}
			Set<String> key = map.keySet();
			List<EchartsVo> result = new ArrayList<EchartsVo>();
			for (String string : key) {
				EchartsVo echartsVo = new EchartsVo();
				echartsVo.setTaskStatusName(string);
				echartsVo.setStatusNum(map.get(string));
				System.out.println("[-=-=-=num-=-="+map.get(string));
				result.add(echartsVo);
			}
		return result;
	}
	@Override
	public boolean getEntityByName(String string, String planName,
			String string2, Long planId) {
		List list = hibernateTemplate.find("FROM Plan  where "+string+" = '"+planName+"' and "+string2+" != "+planId);
		if(list != null && list.size()>0){
			return true;//重复
		}
		return false;
	}
	@Override
	public ResponseDataVo checkPlanDelete(String ids) {
		if("管理员".equals(ThreadLocalSession.getUser().getRoleName())){
			return ResponseDataVo.ofSuccess(null);
		}
		String[] split = ids.split(",");
		String inVal="(";
		for (int i=0;i<split.length;i++) {
			String id = split[i];
			if(i == (split.length-1)){
				inVal=inVal+id+")";
			}else{
				inVal = inVal +id+",";
			}
		}
		System.out.println("-=-=-=inVal-=-=="+inVal);
		List list = hibernateTemplate.find("FROM Plan where planId in "+inVal+" and planStatus != '新建'");
		if(list != null && list.size()>0){
			return ResponseDataVo.ofFail("计划状态不为新建的不能删除");
		}else{
			return ResponseDataVo.ofSuccess(null);
		}
}
	@Override
	public ResponseDataVo checkAuth(Long planId) {
		User user = ThreadLocalSession.getUser();
		Plan dbplan = this.getEntityById(planId);
		if("新建".equals(dbplan.getPlanStatus()) && dbplan.getPlanLeaderId().equals(user.getUserId())){
			return ResponseDataVo.ofSuccess(null);//可以审核
		}else{
			ProcessLog processLog = processLogDao.getLogsByBusinessId(planId);
			if(processLog != null){
				if(user.getUserId().equals(processLog.getHandlearId())){
					return ResponseDataVo.ofSuccess(null);//可以审核
				}else{
					return ResponseDataVo.ofFail("你没有审核权限");
				}
			}else{
				return ResponseDataVo.ofFail("你没有审核权限");
			}
			
		}
	}
	@Override
	public ResponseDataVo callbackAuth(Long planId) {
		User user = ThreadLocalSession.getUser();
		Plan dbplan = this.getEntityById(planId);
		if("新建".equals(dbplan.getPlanStatus())){
			return ResponseDataVo.ofFail("新建状态不能打回");
		}else {
			ProcessLog processLog = processLogDao.getLogsByBusinessId(planId);
			if(processLog != null){
				if(user.getUserId().equals(processLog.getHandlearId())){
					return ResponseDataVo.ofSuccess(null);//可以审核
				}else{
					return ResponseDataVo.ofFail("你没有审核权限");
				}
			}else{
				return ResponseDataVo.ofFail("你没有审核权限");
			}
		}
	}
}
