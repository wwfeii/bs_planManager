package com.edu.wf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.BaseDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.User;
import com.edu.wf.utils.GeneriscUtils;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;

/**
 *@author wangfei
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T>{
	
	private Class<T> clazz;
	private String entityName;
	private  Object obj;
	private Long currentUserId;//当前登陆人id
	private String currentUserName;//当前登陆人名
	@SuppressWarnings("unchecked")
	public  BaseDaoImpl() {
		clazz = GeneriscUtils.getSuperClassGenricType(this.getClass(), 0);
		try {
			obj = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		entityName = clazz.getSimpleName();
		
	}
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	@Override
	public void save(T entity) {
		initCurrentUser();
		try {
			//通过反射  设置创建人id
			entity.getClass().getMethod("setCreatorId", Long.class).invoke(entity, currentUserId);
			
			//通过反射 设置创建人名称
			entity.getClass().getSuperclass().getMethod("setCreatorName", String.class).invoke(entity, currentUserName);
			
			//通过反射 设置更新人id
			entity.getClass().getMethod("setUpdatorId", Long.class).invoke(entity, currentUserId);

			
			//通过反射 设置更新人name
			entity.getClass().getMethod("setUpdatorName", String.class).invoke(entity, currentUserName);

			hibernateTemplate.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void initCurrentUser(){
		//从threadLocal中获得当前登陆人的信息
			User user = ThreadLocalSession.getUser();
			if(user!=null){
				currentUserId = user.getUserId();
				currentUserName = user.getUserName();
			}
	}

	@Override
	public void update(T entity) {
		initCurrentUser();
		try {
			//通过反射 设置更新人id
			entity.getClass().getMethod("setUpdatorId", Long.class).invoke(entity, currentUserId);
			//通过反射 设置更新人name
			entity.getClass().getMethod("setUpdatorName", String.class).invoke(entity, currentUserName);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		hibernateTemplate.update(entity);
	}

	@Override
	public void delete(T entity) {
		hibernateTemplate.delete(entity);
	}

	@Override
	public T getEntityById(Long id) {
		T t = hibernateTemplate.get(clazz, id);
		return t;
	}

	@Override
	public List<T> getAll() {
		//hibernateTemplate.find("from "+clazz)
		@SuppressWarnings("unchecked")
		List<T> list = hibernateTemplate.find("from "+entityName);
		return list;
	}
	@Override
	public PageResult getAllForPage(Integer pageNum, Integer pageSize) {
		Session session = hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from "+entityName+" order by createdTime desc");
		ScrollableResults scroll = query.scroll();//滚动
		scroll.last();//滚到最后
		int total = scroll.getRowNumber()+1;//得到总记录数  因为从0开始 所有需要加1
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		List list = query.list();
		PageResult pageResult = new PageResult();
		pageResult.setList(list);
		pageResult.setTotalNum(total);
		//这里的关闭连接 应该放到finally里面
		session.close();
		return pageResult;
	}
	@Override
	public boolean getEntityByName(String fieldName, String fieldValue) {
		List list = hibernateTemplate.find("FROM "+entityName +" where "+fieldName+" = '"+fieldValue+"'");
		if(list != null && list.size()>0){
			return true;//重复
		}
		return false;
	}
	@Override
	public PageResult getAllForPage(Integer pageNum, Integer pageSize,
			String fieldName,String status,String searchField,String searchVal) {
		Query query = null;
		String likeVal = "";
		 Session session = hibernateTemplate.getSessionFactory().openSession();
		if("".equals(status) && !"".equals(searchVal)){//没有状态  有搜索内容
			query = session.createQuery("from "+entityName+"where "+searchField+"='"+searchVal+"' order by createdTime desc");
		}else if("".equals(status) && "".equals(searchVal)){
			query = session.createQuery("from "+entityName+" order by createdTime desc");
		}else{
			if(!"".equals(searchVal)){//如果有查询条件
				likeVal =searchField+ " like '%"+searchVal+"%'";
				if(status .equals( Plan.STATUS_ALL)){//如果状态是全部
					query = session.createQuery("from "+entityName+" where "+likeVal+" order by createdTime desc");
				}else{//不是全部
					query = session.createQuery("from "+entityName+ " where "+fieldName+"='"+status+"' and "+likeVal+"  order by createdTime desc");

				}
				
			}
			else{//没有查询条件
				if(status .equals( Plan.STATUS_ALL)){
					query = session.createQuery("from "+entityName+" order by createdTime desc");
				}else{
					query = session.createQuery("from "+entityName+ " where "+fieldName+"='"+status+"' order by createdTime desc");

				}
			}
		}
		ScrollableResults scroll = query.scroll();//滚动
		scroll.last();//滚到最后
		int total = scroll.getRowNumber()+1;//得到总记录数  因为从0开始 所有需要加1
		query.setFirstResult((pageNum-1)*pageSize);
		query.setMaxResults(pageSize);
		List list = query.list();
		PageResult pageResult = new PageResult();
		pageResult.setList(list);
		pageResult.setTotalNum(total);
		//关闭连接
		session.close();
		return pageResult;
	}
}
