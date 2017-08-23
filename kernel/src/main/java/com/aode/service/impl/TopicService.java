package com.aode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aode.dao.TopicMapper;
import com.aode.dto.Topic;
import com.aode.dto.TopicReply;
import com.aode.service.ITopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("topicService")
public class TopicService implements ITopicService {

	@Resource
	TopicMapper topicMapper;
	
	@Override
	public Integer publish(Topic topic) {
		return topicMapper.saveTopic(topic);
	}

	@Transactional
	@Override
	public Integer save(Topic topic) {
		topicMapper.saveTopic(topic);
		topic.getTopicContent().setTopicId(topic.getTopicId());
		topicMapper.saveTopicContent(topic.getTopicContent());
		return 1;
	}

	@Override
	public Topic getTopicByTopicId(Integer topicId) {
		return topicMapper.getTopicById(topicId);
	}

	@Override
	public Integer deleteByTopicId(Integer topicId) {
		return topicMapper.deleteByTopicId(topicId);
	}

	@Override
	public Integer updateByTopicId(Topic topic) {
		return topicMapper.updateTopicByTopicId(topic);
	}

	@Override
	public PageInfo<TopicReply> getTopicReplysByTopicId(Integer topicId,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<TopicReply> replys = topicMapper.getTopicReplysByTopicId(topicId);
		PageInfo<TopicReply> page = new PageInfo<TopicReply>(replys);
		return page;
	}

}
