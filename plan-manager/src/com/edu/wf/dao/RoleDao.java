package com.edu.wf.dao;


import com.edu.wf.domin.Role;

/**
 *@author wangfei
 */

public interface RoleDao extends BaseDao<Role>{

	Role getRoleByRoleName(String roleName) throws Exception;

}
