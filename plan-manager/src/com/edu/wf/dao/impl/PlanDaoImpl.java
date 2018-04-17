package com.edu.wf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.PlanDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Task;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;

/**
 *@author wangfei
 */
@Repository
public class PlanDaoImpl  extends BaseDaoImpl<Plan> implements PlanDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
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
		queryVal+=" order by createdTime desc";
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery(queryVal);
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		List list = query.list();
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

}
