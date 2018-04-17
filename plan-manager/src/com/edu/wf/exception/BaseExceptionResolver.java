package com.edu.wf.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.edu.wf.utils.JsonUtils;
import com.edu.wf.vo.ResponseDataVo;


/**
 *@author wangfei
 */

public class BaseExceptionResolver implements HandlerExceptionResolver{
	Logger log = LoggerFactory.getLogger(BaseExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception ex) {
		 ModelAndView mv = new ModelAndView();             
         /*  使用response返回    */  
         response.setStatus(200); //设置状态码  
         response.setContentType("application/json; charset=utf-8"); //设置ContentType
         response.setCharacterEncoding("UTF-8"); //避免乱码  
         response.setHeader("Cache-Control", "no-cache, must-revalidate");  
         try {  
        	 ResponseDataVo data = ResponseDataVo.ofFail(ex.getMessage());
        	 String json = JsonUtils.toJson(data);
             response.getWriter().write(json);  
         } catch (IOException e) {  
            log.error("与客户端通讯异常:"+ e.getMessage(), e);  
         }  

         log.debug("异常:" + ex.getMessage(), ex);  
         return mv;  
	}

}
