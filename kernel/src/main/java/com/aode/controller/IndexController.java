package com.aode.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aode.dto.Topic;
import com.aode.service.IIndexService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Resource
	private IIndexService iIndexService;
	
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
	 * 取得最新发表的topic
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
}
