package com.edu.wf.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;














import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.hibernate.context.ThreadLocalSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.dsna.util.images.ValidateCode;

import com.edu.wf.dao.RoleDao;
import com.edu.wf.domin.Plan;
import com.edu.wf.domin.Role;
import com.edu.wf.domin.User;
import com.edu.wf.service.UserService;
import com.edu.wf.utils.JsonUtils;
import com.edu.wf.utils.Md5Utils;
import com.edu.wf.utils.PageResult;
import com.edu.wf.utils.ThreadLocalSession;

/**
 * 用户相关操作API
 *@author wangfei
 */
@Controller
@RequestMapping("/user")
//@Scope(value="session")//将springmvc变成多例的
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired RoleDao roleDao;
	
	@Value("${USER_SESSION_ID}")
	private String USER_SESSION_ID;//用户登陆session名
	
	//@Value("${PAGE_SIZE}")
	private static final Integer PAGE_SIZE = 10;//默认pageSize=10  可在configuration.properties中改
	
	private String valStr;
	/**
	 * 登陆
	 * @param userName 用户名
	 * @param validateCode 验证码
	 * @param model 
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login(@RequestParam String userName,@RequestParam String userPwd,
			@RequestParam String validateCode,Model model,HttpServletRequest request){
		System.out.println("loginThreead===="+Thread.currentThread().getName());
		//取到session中的验证码
		//String valCode = (String) request.getSession().getAttribute("valCode");
		//request.getSession().removeAttribute("valCode");
		if(!valStr.equalsIgnoreCase(validateCode)){//不区分大小写
			model.addAttribute("message", "验证码错误");
			return "login";
		}
		if(!StringUtils.isEmpty(userName)){
			User user = userService.getUserByUserName(userName);//通过用户名查询用户
			if(user == null){
				model.addAttribute("userNameMsg", "用户名不存在");
				return "login";
			}else{
				String pwd = user.getUserPwd();
				System.out.println(Md5Utils.MD5(userPwd));
				if(!pwd.equals(Md5Utils.MD5(userPwd))){//进行md5加密 然后进行比较
					model.addAttribute("userPwdMsg", "密码输入错误");
					return "login";
				}
				//登陆成功后，将用户信息保存到session中
				HttpSession session = request.getSession();
				//讲用户角色 设置到session中，用于判断是否有查看页面的权限
				Long roleId = user.getRoleId();
				Role role = roleDao.getEntityById(roleId);
				String roleName = "";
				String dbRoleName = role.getRoleName();
				if("管理员".equals(dbRoleName)){
					roleName="all";
				}else if("项目负责人".equals(dbRoleName) || "计划负责人".equals(dbRoleName) || "任务负责人".equals(dbRoleName)){
					roleName = dbRoleName;
				}else{//不是这几个角色 就没权限
					roleName="notany";
				}
				session.setAttribute("roleName", roleName);
				session.setAttribute("loginName", user.getUserName());//用于首页展示
				session.setAttribute("USER_SESSION_ID", JsonUtils.toJson(user));//将用户信息保存到session中
				session.setMaxInactiveInterval(1000*60*30);//设置session的过期时间是半小时
				ThreadLocalSession.setUser(user);
				System.out.println("userController==="+ThreadLocalSession.getUser());
				return "index";
			}
		}else{
			model.addAttribute("userNameMsg", "用户名不能为空");
			return "login";
		}
		
		
	}
	/**
	 * 获取验证码
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getValidateImg")
	public void getValidateImg(HttpServletResponse response,HttpServletRequest request) throws IOException{
		ValidateCode vc=new ValidateCode(98,38,4,9);  
		
        String code=vc.getCode();  
        valStr = code;
        //request.getSession(true).setAttribute("valCode", code);//将验证码放入sessiong中
        System.out.println("-=-=-code-=-=-="+code);
        vc.write(response.getOutputStream());
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String addUser(User user){
		userService.addUser(user);
		return "redirect:/user/getAll.action";
		
	}
	/**
	 * 得到所有的user
	 * @return
	 */
	@RequestMapping("/getAll2")
	public String getAll2(HttpServletRequest request){
		List<User> list = userService.getAll();
		request.setAttribute("users", list);
		request.setAttribute("total", list.size());
		request.setAttribute("classId", "userInfo");
		return "forward:/page/user.action";
	}
	/**
	 * 分页查询--跳转页面
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getAll")
	public String getAll(HttpServletRequest request,@RequestParam(defaultValue="1",value="pageNum")Integer pageNum){
		PageResult result = userService.getForPage(pageNum, PAGE_SIZE);
		request.setAttribute("users", result.getList());
		request.setAttribute("total", result.getTotalNum());
		return "forward:/page/user.action";
		
	}
	
	/**
	 * 分页查询--返回json
	 * @param request
	 * @param pageNum 页数
	 * @return
	 */
	@RequestMapping("/getJson")
	@ResponseBody
	public List getJson(@RequestParam(defaultValue="1",value="pageNum")Integer pageNum){
		PageResult result = userService.getForPage(pageNum, PAGE_SIZE);
		return result.getList();
		
	}
	
	@RequestMapping("/getTotal")
	@ResponseBody
	public Integer getTotal(){
		int size = userService.getAll().size();
		return size;
	}
	
	/**
	 * 删除用户
	 * 
	 */
	@RequestMapping(value="/delete")
	public String delete(@RequestParam String ids){
		String[] idArray = ids.split(",");
		for(int i=0;i<idArray.length;i++){
			String id = idArray[i];
			if(!StringUtils.isEmpty(id)){
				userService.deleteById(Long.parseLong(id));
			}
		}
		return "redirect:/user/getAll.action";
	}
	/**
	 * 通过角色id得到用户
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="/getUserByRoleId",method=RequestMethod.GET)
	@ResponseBody
	public List<User> getUserByRoleId(@RequestParam("roleId") Long roleId){
		List<User> list = userService.getUserByRoleId(roleId);
		return list;
	}
	
	/**
	 * 注销
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute("USER_SESSION_ID");//删除用户登陆的session
	    response.sendRedirect("http://localhost:8080/plan-manager/login.jsp");
		//return "redirect:/page/login.action";
	}
	
	/**
	 * 修改信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/updateUser",method = RequestMethod.POST)
	public String updateUser(User user){
		userService.update(user);
		return "redirect:/user/getAll.action";
	}
	
	/**
	 * 通过id  得到详情
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUserById")
	@ResponseBody
	public User getProjectById(@RequestParam("userId") Long userId){
		User user = userService.getEntityById(userId);
		return user;
	}

}
