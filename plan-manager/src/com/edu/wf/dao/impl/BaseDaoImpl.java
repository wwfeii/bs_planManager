package com.edu.wf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.BaseDao;
import com.edu.wf.dao.RoleDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Project;
import com.edu.wf.domin.Role;
import com.edu.wf.domin.User;
import com.edu.wf.utils.GeneriscUtils;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;

import freemarker.template.utility.StringUtil;

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
			hibernateTemplate.flush();//保存后立即刷新
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
		hibernateTemplate.flush();//查询前先刷新缓存
		//hibernateTemplate.find("from "+clazz)
		User user = ThreadLocalSession.getUser();
		if("管理员".equals(user.getRoleName())){
			List<T> list = hibernateTemplate.find("from "+entityName +" order by createdTime desc" );
			return list;
		}else{
			List<String> sqlList = this.authSql();
			String andSqlString ="";
			String noAnd = "";
			if(sqlList == null){
				System.out.println("111");
				return new ArrayList();
			}
			if(sqlList !=null && sqlList.size() == 2){
				System.out.println("222");
				andSqlString = sqlList.get(0);
				noAnd = sqlList.get(1);
			}
			List<T> list = hibernateTemplate.find("from "+entityName +noAnd);
			return list;
		}
		
		
	}
	@Override
	public PageResult getAllForPage(Integer pageNum, Integer pageSize) {
		User user = ThreadLocalSession.getUser();
		if("管理员".equals(user.getRoleName())){
			List find = hibernateTemplate.find("from "+entityName);
			PageResult pageResult = new PageResult();
			pageResult.setList(find);
			pageResult.setTotalNum(find.size());
			return pageResult ;
		}
		List<String> sqlList = this.authSql();
		String andSqlString ="";
		String noAnd = "";
		if(sqlList == null){
			PageResult pageResult = new PageResult();
			pageResult.setList(new ArrayList());
			pageResult.setTotalNum(0);
			return pageResult;
		}
		if(sqlList !=null && sqlList.size() == 2){
			andSqlString = sqlList.get(0);
			noAnd = sqlList.get(1);
		}
		
		Session session = hibernateTemplate.getSessionFactory().openSession();
		//根据当前登陆人 获得计划
		//Long userId = ThreadLocalSession.getUser().getUserId();
		Query query = null;
		
		if(entityName.equals("Plan") && user.getRoleName().equals("项目负责人") ){
			String inVal = noAnd.split("in")[1];
			noAnd = " where planId in "+inVal;
			query = session.createQuery("from "+entityName+" "+noAnd+" order by createdTime desc");
		}else if(entityName.equals("Plan") && user.getRoleName().equals("计划负责人")){
			query = session.createQuery("from "+entityName+" "+noAnd+" order by createdTime desc");
		}
		else if(entityName.equals("Project")){
			query = session.createQuery("from "+entityName+" "+noAnd+" order by createdTime desc");
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
		List<String> sqlList = this.authSql();
		String andSqlString ="";
		String noAnd = "";
		if(sqlList == null){
			PageResult pageResult = new PageResult();
			pageResult.setList(new ArrayList());
			pageResult.setTotalNum(0);
			return pageResult;
		}
		if(sqlList !=null && sqlList.size() == 2){
			andSqlString = sqlList.get(0);
			noAnd = sqlList.get(1);
		}
		Query query = null;
		String likeVal = "";
		 Session session = hibernateTemplate.getSessionFactory().openSession();
		if("".equals(status) && !"".equals(searchVal)){//没有状态  有搜索内容
			query = session.createQuery("from "+entityName+"where "+searchField+"='"+searchVal+"' "+andSqlString+" order by createdTime desc");
		}else if("".equals(status) && "".equals(searchVal)){
			query = session.createQuery("from "+entityName+" order by createdTime desc");
		}else{
			if(!"".equals(searchVal)){//如果有查询条件
				likeVal =searchField+ " like '%"+searchVal+"%'";
				if(status .equals( Plan.STATUS_ALL)){//如果状态是全部
					query = session.createQuery("from "+entityName+" where "+likeVal+" "+andSqlString+" order by createdTime desc");
				}else{//不是全部
					query = session.createQuery("from "+entityName+ " where "+fieldName+"='"+status+"' "+andSqlString+" and "+likeVal+"  order by createdTime desc");

				}
				
			}
			else{//没有查询条件
				if(status .equals( Plan.STATUS_ALL)){
					query = session.createQuery("from "+entityName+" "+noAnd+" order by createdTime desc");
				}else{
					query = session.createQuery("from "+entityName+ " where "+fieldName+"='"+status+"' "+andSqlString+" order by createdTime desc");

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
	
	private  List<String> authSql() {
		//根据当前用户的权限来判断页面可展示的信息
				List<String> result = new ArrayList<String>();
				User user = ThreadLocalSession.getUser();
				Long userId = user.getUserId();
				Long roleId = user.getRoleId();
				Role role = hibernateTemplate.get(Role.class, roleId);
				String roleName = role.getRoleName();
				String  andSqlString=" and ";
				String noAnd = " where ";
				System.out.println("-=-=-=userRoleName-=-=-="+role.getRoleName());
				if(!"管理员".equals(role.getRoleName())){
					if(entityName.equals("Plan")){
						if(roleName.equals("项目负责人")){//当前 只支持一个负责人 负责一个项目， 后期可优化 支持多个
							List<Project> list = hibernateTemplate.find("from Project where projectLeaderId = "+userId);
							if(list == null || list.size()==0){
								return null;
							}
							Project project = list.get(0);
							List<Plan> plans = hibernateTemplate.find("from Plan where projectId = "+project.getProjectId());
							if(plans == null || plans.size() == 0){
								return null;
							}
							
							StringBuilder sb = new StringBuilder();
							sb.append("(");
							for(int i=0;i<plans.size();i++){
								long planId = plans.get(i).getPlanId();
								 if(i == (plans.size()-1)){
									sb.append(String.valueOf(planId));
								}else{
									sb.append(planId+",");
								}
								
							}
							sb.append(")");
							System.out.println("-=-==sb-=-=-="+sb.toString());
							andSqlString += "planLeaderId in "+sb.toString();//()
							noAnd += "planLeaderId in "+sb.toString();
							result.add(andSqlString);
							result.add(noAnd);
							System.out.println("-=-=-=andSqlString-=-=="+andSqlString);
							System.out.println("-=-=-=noAnd-=-=="+noAnd);
							return result;
						}else if(roleName.equals("计划负责人")){
							andSqlString  += "planLeaderId = "+userId;
							noAnd += "planLeaderId = "+userId;
							result.add(andSqlString);
							result.add(noAnd);
							System.out.println("-=-=-=andSqlString-=-=="+andSqlString);
							System.out.println("-=-=-=noAnd-=-=="+noAnd);
							return result;
						}else{
							List<String> zz = new ArrayList<String>();
							zz.add("all");
							return zz;
						}
					}else if(entityName.equals("Project")){//项目负责人
						andSqlString += "projectLeaderId="+userId;
						noAnd += "projectLeaderId="+userId;
						result.add(andSqlString);
						result.add(noAnd);
						System.out.println("-=-=-=andSqlString-=-=="+andSqlString);
						System.out.println("-=-=-=noAnd-=-=="+noAnd);
						return result;
						
					}else if(entityName.equals("Task")){
						if(roleName.equals("任务负责人")){
							andSqlString += "taskLeaderId = "+userId;
							noAnd += "taskLeaderId = "+userId;
							result.add(andSqlString);
							result.add(noAnd);
							System.out.println("-=-=-=andSqlString-=-=="+andSqlString);
							System.out.println("-=-=-=noAnd-=-=="+noAnd);
							return result;
						}else{
							List<String> zz = new ArrayList<String>();
							zz.add("all");
							return zz;
						}
					}else{
						List<String> zz = new ArrayList<String>();
						zz.add("all");
						return zz;
					}
				}else{
					
					List<String> zz = new ArrayList<String>();
					zz.add("all");
					return zz;
				}
				
		
	}
}
