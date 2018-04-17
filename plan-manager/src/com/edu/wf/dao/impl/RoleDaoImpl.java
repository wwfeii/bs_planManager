package com.edu.wf.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.edu.wf.dao.RoleDao;
import com.edu.wf.domin.Role;
import com.edu.wf.exception.CustomExcpetion;

/**
 *@author wangfei
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public Role getRoleByRoleName(String roleName) throws Exception{
		List list = hibernateTemplate.find("from Role where roleName = '"+roleName+"'");
		if(list == null || list.size() == 0){
			return null;
		}
		return (Role) list.get(0);
	}

}
