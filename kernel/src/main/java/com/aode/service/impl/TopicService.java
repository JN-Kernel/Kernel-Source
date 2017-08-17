package com.aode.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aode.dao.TopicMapper;
import com.aode.dto.Topic;
import com.aode.service.ITopicService;

@Service("topicService")
public class TopicService implements ITopicService {

	@Resource
	TopicMapper topicMapper;
	
	@Override
	public Integer publish(Topic topic) {
		return topicMapper.save(topic);
	}

	@Override
	public Integer save(Topic topic) {
		return topicMapper.save(topic);
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

}
