package com.edu.wf.service;

import java.util.List;

import com.edu.wf.domin.User;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
public interface UserService {
	/**
	 * 通过登陆名查找用户
	 * @param userName 登陆名
	 * @return
	 */
	public User getUserByUserName(String userName);
	/**
	 * 添加用户
	 * @param user
	 */
	public void addUser(User user);
	
	/**
	 * 根据id删除实体
	 * @param id
	 */
	public void deleteById(Long id);
	
	/**
	 * 更新实体信息
	 * @param user
	 */
	public void update(User user);
	
	/**
	 * 通过id得到实体信息
	 * @param id
	 * @return
	 */
	public User getEntityById(Long id);
	
	/**
	 * 得到所有实体信息
	 * @return
	 */
	public List<User> getAll();
	/**
	 * 分页查询
	 * @param pageNum 页数
	 * @param pageSize 每页大小
	 * @return
	 */
	public PageResult getForPage(Integer pageNum,Integer pageSize);
	
	/**
	 * 通过角色id查询用户
	 * @param roleId
	 * @return
	 */
	public List<User> getUserByRoleId(Long roleId);
	/**
	 * 判断该字段值的实体是否存在
	 * @param fieldName 字段名
	 * @param fieldVal  字段值
	 * @return
	 */
	public boolean entityIsExist(String fieldName,String fieldVal);

}
