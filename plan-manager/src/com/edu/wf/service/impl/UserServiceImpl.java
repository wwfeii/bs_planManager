package com.edu.wf.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.UserDao;
import com.edu.wf.domin.Role;
import com.edu.wf.domin.User;
import com.edu.wf.service.RoleService;
import com.edu.wf.service.UserService;
import com.edu.wf.utils.Md5Utils;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.TimeUtils;

/**
 *@author wangfei
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;

	@Override
	public User getUserByUserName(String userName) {
		
		return userDao.getUserByUserName(userName);
	}

	@Override
	public void addUser(User user) {
		//设置用户的默认密码未6个6
		user.setUserPwd(Md5Utils.MD5(User.DEFAULT_PWD));
		//通过角色id得到角色名
		Role role = roleService.getEntityById(user.getRoleId());
		user.setRoleName(role.getRoleName());
//		user.setCreatorId(1L);
//		user.setCreatorName("admin");
//		user.setUpdatorId(1L);
//		user.setUpdatorName("admin");
		userDao.save(user);
		
	}

	@Override
	public void deleteById(Long id) {
		User user = new User(id);
		userDao.delete(user);
		
	}

	@Override
	public void update(User user) {
		User dbUser = userDao.getEntityById(user.getUserId());
		dbUser.setUserName(user.getUserName());
		dbUser.setTelPhone(user.getTelPhone());
		dbUser.setRoleId(user.getRoleId());
		dbUser.setRoleName(roleService.getEntityById(user.getRoleId()).getRoleName());
		userDao.update(dbUser);
	}

	@Override
	public User getEntityById(Long id) {
		return userDao.getEntityById(id);
	}

	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize) {
		return userDao.getAllForPage(pageNum, pageSize);
	}

	@Override
	public List<User> getUserByRoleId(Long roleId) {
		return userDao.getUserByRoleId(roleId);
	}

	@Override
	public boolean entityIsExist(String fieldName, String fieldVal) {
		return userDao.getEntityByName(fieldName, fieldVal);
	}

}
