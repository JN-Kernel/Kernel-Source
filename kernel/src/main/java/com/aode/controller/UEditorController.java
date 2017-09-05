package com.aode.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.Topic;
import com.aode.service.ITopicService;
import com.baidu.ueditor.ActionEnter;
import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;

@Controller
@RequestMapping("/ueditor")
public class UEditorController {
	
	/**
	 * ueditor后台初始化
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/conf/config",produces="application/json;charset=UTF-8")
	public String config(HttpServletRequest request) {
	        String rootPath = request.getSession().getServletContext().getRealPath("/");
	        System.out.println(rootPath);
	        String exec = new ActionEnter(request, rootPath).exec();
			return exec;
	 
	    }
	
	
	
}
