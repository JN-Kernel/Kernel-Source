package com.aode.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.Topic;
import com.aode.dto.TopicReply;
import com.aode.service.IIndexService;
import com.aode.service.ITopicService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Resource
	private IIndexService iIndexService;
	
	@Resource
	ITopicService topicService;
	
	/**
	 * 搜索
	 * @param request
	 * @param q
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", produces= "application/json;charset=UTF-8")
	public PageInfo<Topic> search(HttpServletRequest request,String q,Integer pageNum){
		if(pageNum == null){
			pageNum = 1;
		}
		Integer pageSize = 10;
		PageInfo<Topic> pageInfo =  iIndexService.search(q, pageNum, pageSize);
		return pageInfo;
		
	}
	

	@RequestMapping(value = "/test1", method = RequestMethod.GET, produces= "text/html;charset=UTF-8")
	public String test(){
		return "success";
		
	}
	
	/**
	 * 取得最新发表的topic列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLatestTopicList", method = RequestMethod.POST, produces= "application/json;charset=UTF-8")
	public List<Topic> getLatestTopicList(){
		List<Topic> list = iIndexService.getLatestTopicList();
		if(list.isEmpty()){
			return null;
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getLikeTopicList", method = RequestMethod.POST, produces= "application/json;charset=UTF-8")
	public List<Topic> getLikeTopicList(){
		List<Topic> list = iIndexService.getLikeTopicList();
		if(list.isEmpty()){
			return null;
		}
		return list;
	}
	

	/**
	 * 取得TopicList
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopicList", method = RequestMethod.POST, produces= "application/json;charset=UTF-8")
	public PageInfo<Topic> getTopicList(Integer pageNum){
		System.out.println(pageNum);
		if(pageNum == null){
			pageNum = 1;
		}
		Integer pageSize = 10;
		return iIndexService.getTopicList(pageNum, pageSize);
	}
	
	/**
	 * 根据类别取得列表
	 * @param request
	 * @param catoreyId
	 * @param pageNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopicListByCatoreyId", produces= "application/json;charset=UTF-8")
	public PageInfo<Topic> getTopicListByCatoreyId(HttpServletRequest request,Integer catoreyId,Integer pageNum){
		if(pageNum == null){
			pageNum = 1;
		}
		Integer pageSize = 10;
		PageInfo<Topic> pageInfo =  iIndexService.getTopicListByCatoreyId(catoreyId, pageNum, pageSize);
		return pageInfo;
		
	}
	
	/**
	 * 取出topic的内容
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTopic",produces= "application/json;charset=UTF-8")
	public Map<String,Object> getTopic(Integer topicId){
		Map<String,Object> msg = new HashMap<String, Object>();
		Topic topic = topicService.getTopicByTopicId(topicId);
		if(topic != null){
			msg.put("data", topic);
			msg.put("stauts", "success");
		}else{
			msg.put("data", "找不到该文章！");
			msg.put("stauts", "error");
		}
		return msg;
	}
	
	/**
	 * 取出topicReply
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTopicReplys", method=RequestMethod.POST, produces= "application/json;charset=UTF-8")
	public Map<String,Object> getTopicReplys(Integer topicId,Integer pageNum){
		Map<String,Object> msg = new HashMap<String, Object>();
		Integer pageSize = 8;
		PageInfo<TopicReply> replys = topicService.getTopicReplysByTopicId(topicId, pageNum, pageSize);
		if(replys.getSize() <= 0 ){
			msg.put("data", "暂时还没有评论，赶紧发表你的看法吧！");
			msg.put("stauts", "nodata");
		}else{
			msg.put("data", replys);
			msg.put("stauts", "success");
		}
		return msg;	
	}
	
}
