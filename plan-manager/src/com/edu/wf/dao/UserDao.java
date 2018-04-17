package com.edu.wf.dao;


import java.util.List;

import com.edu.wf.domin.User;

/**
 *@author wangfei
 */
public interface UserDao extends BaseDao<User> {
	public User getUserByUserName(String userName);
	/**
	 * 通过角色id查询用户
	 * @param roleId
	 * @return
	 */
	public List<User> getUserByRoleId(Long roleId);
}
