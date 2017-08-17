package com.aode.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aode.service.ITopicService;

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
