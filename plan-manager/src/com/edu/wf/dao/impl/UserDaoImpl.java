package com.edu.wf.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.UserDao;
import com.edu.wf.domin.User;

/**
 *@author wangfei
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUserName(String userName) {
		List<User> list = hibernateTemplate.find("FROM User WHERE userName = '"+userName+"'");
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByRoleId(Long roleId) {
		List<User> list = hibernateTemplate.find("FROM User WHERE roleId = "+roleId);
		return list;
	}

}
