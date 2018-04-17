package com.edu.wf.dao;

import java.util.List;

import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
public interface BaseDao <T>{
	public void save(T entity);
	public void update(T entity);
	public void delete(T entity);
	/**
	 * 通过id查询
	 * @param id id值
	 * @return
	 */
	public T getEntityById(Long id);
	/**
	 * 查询表中所有记录
	 * @return
	 */
	public List<T> getAll();
	/**
	 * 分页查询
	 * @param pageNum 第几页
	 * @param pageSize 每页大小
	 * @return
	 */
	public PageResult getAllForPage(Integer pageNum,Integer pageSize);
	/**
	 * 分页查询--增加一个状态条件
	 * @param pageNum 第几页
	 * @param pageSize 每页大小
	 * @return
	 */
	public PageResult getAllForPage(Integer pageNum,Integer pageSize,String fieldName,String status,String searchField,String searchVal);
	
	/**
	 * 根据名称 查询是否重复  重复 :true   不重复:false
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public boolean getEntityByName(String fieldName,String fieldValue);
}
