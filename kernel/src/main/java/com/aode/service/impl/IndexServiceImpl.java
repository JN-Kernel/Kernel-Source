package com.aode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aode.dao.TopicMapper;
import com.aode.dto.Like;
import com.aode.dto.Topic;
import com.aode.service.IIndexService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("IndexService") 
public class IndexServiceImpl implements IIndexService {

	 @Resource    
	 private TopicMapper topicMapper; 
	

	 
	@Override
	public  PageInfo<Topic> search(String q,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Topic> topicList = topicMapper.searchTopicOfTitleAndId(q);	
		PageInfo<Topic> pageInfo = new PageInfo<Topic>(topicList);
		return pageInfo;
	}



	@Override
	public List<Topic> getLatestTopicList() {		
		PageHelper.startPage(1,8);	//取出最新的8条数据
		List<Topic> topicList = topicMapper.getLatestTopicOfTitleAndId();
		Page<Topic> PageTopic = (Page<Topic>) topicList;
		return PageTopic.getResult();
	}



	@Override
	public List<Topic> getLikeTopicList() {
		PageHelper.startPage(1,8);	//取出最新的8条数据
		List<Topic> topicList = topicMapper.getLikeTopicOfTitleAndId();
		Page<Topic> PageTopic = (Page<Topic>) topicList;
		return PageTopic.getResult();
	}



	@Override
	public PageInfo<Topic> getTopicList(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Topic> topicList = topicMapper.getLatestTopicOfTitleAndId();
		PageInfo<Topic> pageInfo = new PageInfo<Topic>(topicList);
		return pageInfo;
	}



	@Override
	public PageInfo<Topic> getTopicListByCatoreyId(Integer catoreyId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Topic> topicList = topicMapper.getTopicOfTitleAndIdByCatoreyId(catoreyId);
		PageInfo<Topic> pageInfo = new PageInfo<Topic>(topicList);
		return pageInfo;
	}



	@Override
	public Boolean isLiked(Integer userId, Integer topicId) {
		Like temp = new Like();
		temp.setUserId(userId);
		temp.setTopicId(topicId);
		Integer likeId = topicMapper.getLike(temp);
		if(likeId == null){
			return false;
		}
		return true;
	}


}
