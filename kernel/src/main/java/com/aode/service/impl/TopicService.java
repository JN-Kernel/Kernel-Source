package com.aode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.aode.dao.TopicMapper;
import com.aode.dto.Like;
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
//		System.out.println(((Page<TopicReply>)replys).getResult().get(1));
		PageInfo<TopicReply> page = new PageInfo<TopicReply>(replys);
		return page;
	}

	@Transactional
	@Override
	public Boolean chickLike(Like like) {
		Integer resultFirst = topicMapper.chickLike(like);
		Integer likeCount = topicMapper.getLikecountByTopicId(like.getTopicId());
		Topic topic = new Topic();
		topic.setLikecount(likeCount+1);
		Integer resultSecond = topicMapper.updateTopicByTopicId(topic);
		if(resultFirst > 0 && resultSecond > 0 ){
			return true;
		}else{
			//手动回滚
			 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			 return false;
		}
	}

	@Override
	public Boolean commentWithTopic(TopicReply reply) {
		return topicMapper.saveTopicReply(reply) > 0;
	}

}
