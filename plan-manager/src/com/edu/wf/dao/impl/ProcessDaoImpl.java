package com.edu.wf.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.ProcessDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.ProcessLog;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.ThreadLocalSession;

/**
 *@author wangfei
 */
@Repository
public class ProcessDaoImpl extends BaseDaoImpl<ProcessLog> implements ProcessDao{
	Logger logger = LoggerFactory.getLogger(ProcessDaoImpl.class);
	@Autowired
	private HibernateTemplate hibernateTemplate;
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
		Long userId = ThreadLocalSession.getUser().getUserId();
		String sql = "from Plan where planLeaderId = "+userId +" and planStatus = '"+Plan.STATUS_RUN+"'";
		List<Plan> list = hibernateTemplate.find(sql);
		int CheckNum = 0;
		if(list != null && list.size()>0){
			for(Plan plan : list){
				List tasks = hibernateTemplate.find("from Task where taskStatus = '"+Task.STATUS_CHECK+"' and planId = "+plan.getPlanId());
				if(tasks!=null && tasks.size()>0){
					CheckNum += list.size();
				}
			}
		}
		//计划审核  就是要这么回事的
		String  planSql = "from Plan where planStatus = '"+Plan.STATUS_CHECK+"' and  processNextUserId = "+userId;
		List list2 = hibernateTemplate.find(sql);
		if(list2 != null && list2.size()>0){
			CheckNum += list2.size();
		}
		return CheckNum;
	}

}
