package com.aode.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.Topic;
import com.aode.service.ITopicService;
import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;

@Controller
@RequestMapping("/topic")
public class TopicController {
	@Resource
	ITopicService topicService;
	
	@RequestMapping("/get")
	public String getTopic(){
		return "s";
	}
	
	
	
}
