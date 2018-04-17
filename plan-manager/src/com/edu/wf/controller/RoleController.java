package com.edu.wf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.Role;
import com.edu.wf.exception.CustomExcpetion;
import com.edu.wf.service.RoleService;
import com.edu.wf.utils.PageResult;
import com.edu.wf.vo.ResponseDataVo;

/**
 * 角色相关API
 *@author wangfei
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;
	
	private static final Integer PAGE_SIZE = 10;//默认pageSize=10  可在configuration.properties中改
	
	/**
	 * 添加角色 
	 * @return
	 * @throws CustomExcpetion 
	 */
	@RequestMapping(value="/addRole",method = RequestMethod.POST)
	@ResponseBody
	public ResponseDataVo<Role> addRole(Role role) throws CustomExcpetion{
		roleService.addRole(role);
		List<Role> roles = roleService.getAll();
		return ResponseDataVo.ofSuccess(roles);
	}
	
	/**
	 * 分页查询--跳转页面
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request,@RequestParam(defaultValue="1",value="pageNum")Integer pageNum){
		PageResult result = roleService.getForPage(pageNum, PAGE_SIZE);
		request.setAttribute("roles", result.getList());
		request.setAttribute("total", result.getTotalNum());
		return "forward:/page/role.action";		
	}
	
	/**
	 * 分页查询--返回json
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getJson")
	@ResponseBody
	public List getJson(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum,
			@RequestParam(defaultValue="",value="searchVal")String searchVal){
		PageResult result = roleService.getForPage(pageNum, PAGE_SIZE,"", searchVal);
		return result.getList();
		
	}
	
	/**
	 * 得到总记录数
	 * @return
	 */
	@RequestMapping("/getTotal")
	@ResponseBody
	public Integer getTotal(){
		int size = roleService.getAll().size();
		return size;
	}
	
	/**
	 * 删除角色
	 * 
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam String ids){
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String id = idArray[i];
			if(!StringUtils.isEmpty(id)){
				roleService.deleteById(Long.parseLong(id));
			}
		}
		return "redirect:/role/getAll.action";
	}
	
	/**
	 * 得到所有角色信息
	 * @return
	 */
	@RequestMapping(value="getAllJson",method=RequestMethod.GET)
	@ResponseBody
	public List<Role> getAllJson(){
		List<Role> all = roleService.getAll();
		return all;
	}
	
	/**
	 * 修改信息
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/updateRole",method = RequestMethod.POST)
	public String updateProject(Role role){
		roleService.update(role);
		return "redirect:/role/getAll.action";
	}
	
	/**
	 * 通过id  得到详情
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/getRoleById")
	@ResponseBody
	public Role getProjectById(@RequestParam("roleId") Long roleId){
		Role role = roleService.getEntityById(roleId);
		return role;
	}
	/**
	 * 通过角色名得到角色详情
	 * @param roleName
	 * @return
	 */
	@RequestMapping(value = "/getRoleDetail",method = RequestMethod.POST)
	@ResponseBody
	public ResponseDataVo<Role> getRoleDetail(@RequestParam("roleName")String roleName) throws Exception{
		ResponseDataVo<Role> rolesDataVo = roleService.getRoleByRoleName(roleName);
		if(rolesDataVo == null){
			logger.info("通过角色名{},没有查询到信息",roleName);
			throw new Exception("请输入正确的用户名");
		}
		return rolesDataVo;
	}

}
