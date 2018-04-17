package com.edu.wf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Project;
import com.edu.wf.service.ProjectService;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 *项目管理api
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	private static final Integer PAGE_SIZE = 10;//默认pageSize=10  可在configuration.properties中改
	
	/**
	 * 添加项目
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/addProject",method=RequestMethod.POST)
	public String addProject(Project project){
		projectService.addProject(project);
		return "redirect:/project/getAll.action";
		
	}
	/**
	 * 分页查询--跳转页面
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request,@RequestParam(defaultValue="1",value="pageNum")Integer pageNum){
		PageResult result = projectService.getForPage(pageNum, PAGE_SIZE);
		request.setAttribute("projects", result.getList());
		request.setAttribute("total", result.getTotalNum());
		return "forward:/page/project.action";		
	}
	
	/**
	 * 分页查询--返回json
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping(value = "/getJson",method = RequestMethod.POST)
	@ResponseBody
	public List getJson(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum,
			@RequestParam(defaultValue=Project.STATUS_ALL,value="projectStatus")String projectStatus,
			@RequestParam(defaultValue="",value="searchVal")String searchVal){
		PageResult result = projectService.getForPage(pageNum, PAGE_SIZE, projectStatus, searchVal);
		return result.getList();
		
	}
	/**
	 * 得到总记录数
	 * @return
	 */
	@RequestMapping("/getTotal")
	@ResponseBody
	public Integer getTotal(){
		int size = projectService.getAll().size();
		return size;
	}

	/**
	 * 删除项目
	 * 
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam String ids){
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String id = idArray[i];
			if(!StringUtils.isEmpty(id)){
				projectService.deleteById(Long.parseLong(id));
			}
		}
		return "redirect:/project/getAll.action";
	}
	/**
	 * 得到所有，以json格式返回
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/getAllJson")
	@ResponseBody
	public List<Project> getAllJson(){
		List<Project> list = projectService.getAll();
		return list;
	}
	
	/**
	 * 修改项目信息
	 * @param project
	 * @return
	 */
	@RequestMapping(value = "/updateProject",method = RequestMethod.POST)
	public String updateProject(Project project){
		projectService.update(project);
		return "redirect:/project/getAll.action";
	}
	
	/**
	 * 通过项目id  得到项目详情
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getProjectById")
	@ResponseBody
	public Project getProjectById(@RequestParam("projectId") Long projectId){
		Project project = projectService.getEntityById(projectId);
		return project;
	}

}
