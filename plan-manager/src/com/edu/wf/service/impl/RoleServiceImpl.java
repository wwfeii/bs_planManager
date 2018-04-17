package com.edu.wf.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.wf.dao.RoleDao;
import com.edu.wf.domin.Role;
import com.edu.wf.exception.CustomExcpetion;
import com.edu.wf.service.RoleService;
import com.edu.wf.utils.PageResult;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;

	@Override
	public void addRole(Role role) throws CustomExcpetion {
		//判断该角色名是否存在
		boolean flag = roleDao.getEntityByName("roleName", role.getRoleName());
		if(flag){
				throw new CustomExcpetion("角色名已存在");
		}
		roleDao.save(role);
		
	}

	@Override
	public void deleteById(Long id) {
		roleDao.delete(new Role(id));
	}

	@Override
	public void update(Role role) {
		Role dbRole = roleDao.getEntityById(role.getRoleId());
		dbRole.setRoleName(role.getRoleName());
		dbRole.setRoleDescription(role.getRoleDescription());
		roleDao.update(dbRole);
	}

	@Override
	public Role getEntityById(Long id) {
		return roleDao.getEntityById(id);
	}

	@Override
	public List<Role> getAll() {
		return roleDao.getAll();
	}

	@Override
	public boolean entityIsExist(String fieldName, String fieldVal) {
		return roleDao.getEntityByName(fieldName, fieldVal);
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize) {
		return roleDao.getAllForPage(pageNum, pageSize);
	}

	@Override
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String string, String searchVal) {
		return roleDao.getAllForPage(pageNum, pageSize, "", "", "roleName", searchVal);
	}

	@Override
	public ResponseDataVo<Role> getRoleByRoleName(String roleName) throws Exception {
		Role role = roleDao.getRoleByRoleName(roleName);
		if(role == null){
			return null;
		}
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(role);
		return ResponseDataVo.ofSuccess(roles);
	}

}
