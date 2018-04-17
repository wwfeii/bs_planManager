package com.edu.wf.service;


import java.util.List;

import com.edu.wf.domin.Role;
import com.edu.wf.exception.CustomExcpetion;
import com.edu.wf.utils.PageResult;
import com.edu.wf.vo.ResponseDataVo;

/**
 *@author wangfei
 */
public interface RoleService {
	/**
	 * 添加实体
	 * @param Role
	 */
	public void addRole(Role role) throws CustomExcpetion;
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param role
	 */
	public void update(Role role);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public Role getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<Role> getAll();
	
	/**
	 * 判断该字段值的实体是否存在
	 * @param fieldName 字段名
	 * @param fieldVal  字段值
	 * @return
	 */
	public boolean entityIsExist(String fieldName,String fieldVal);
	/**
	 * 分页查询
	 * @param pageNum 页数
	 * @param pageSize 页大小
	 * @return
	 */
	public PageResult getForPage(Integer pageNum, Integer pageSize);
	/**
	 * 带条件的分页查询
	 * @param pageNum
	 * @param pageSize
	 * @param searchVal 搜索值
	 * @return
	 */
	public PageResult getForPage(Integer pageNum, Integer pageSize,
			String string, String searchVal);
	/**
	 * 通过角色名得到角色详情
	 * @param roleName 角色名
	 * @return
	 */
	public ResponseDataVo<Role> getRoleByRoleName(String roleName) throws Exception;

}
