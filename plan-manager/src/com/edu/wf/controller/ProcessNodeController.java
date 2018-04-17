package com.edu.wf.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.wf.domin.ProcessNode;
import com.edu.wf.domin.Project;
import com.edu.wf.service.NodeService;
import com.edu.wf.utils.PageResult;

/**
 *@author wangfei
 */
/**
 * 流程节点相关API
 * @author wangfei
 *
 */
@Controller
@RequestMapping("/node")
public class ProcessNodeController {
	@Autowired
	private NodeService nodeService;
	
	/**
	 * 查询所有节点信息 并跳转页面
	 * @return
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<ProcessNode> nodes = nodeService.getAll(ProcessNode.TYPE_PLAN);
		model.addAttribute("nodes", nodes);
		return "forward:/page/process.action";		
	}
	
	/**
	 * 添加项目
	 * @param node
	 * @return
	 */
	@RequestMapping(value="/addNode",method=RequestMethod.POST)
	public String addNode(ProcessNode node){
		nodeService.addNode(node);
		return "redirect:/node/getAll.action";
		
	}
	
	/**
	 * 更具id上次节点
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/deleteNode",method = RequestMethod.GET)
	public String deleteNode(@RequestParam("nodeId")Long nodeId){
		nodeService.deleteById(nodeId);
		return "redirect:/node/getAll.action";
	}
	
	/**
	 * 更新节点信息
	 * @param node
	 * @return
	 */
	@RequestMapping(value="/updateNode",method=RequestMethod.POST)
	public String updateNode(ProcessNode node){
		nodeService.update(node);
		return "redirect:/node/getAll.action";
	}
	
	@RequestMapping(value="/getNodeById",method = RequestMethod.GET)
	@ResponseBody
	public ProcessNode getNodeById(@RequestParam("nodeId")Long nodeId){
		ProcessNode node = nodeService.getEntityById(nodeId);
		return node;
	}

}
