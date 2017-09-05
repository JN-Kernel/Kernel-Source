package com.aode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.aode.dao.CatoreyMapper;
import com.aode.dao.TopicMapper;
import com.aode.dto.Catorey;
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

	@Resource
	CatoreyMapper catoreyMapper;
	

	@Transactional
	@Override
	public Integer save(Topic topic) {
		Integer saveTopic = topicMapper.saveTopic(topic);
		topic.getTopicContent().setTopicId(topic.getTopicId());
		Integer saveContent = topicMapper.saveTopicContent(topic.getTopicContent());
		return (saveTopic >0 && saveContent >0) ? 1:0;
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
		topic.setTopicId(like.getTopicId());
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

	@Transactional
	@Override
	public Boolean commentWithTopic(TopicReply reply) {
		
		Integer saveReply = topicMapper.saveTopicReply(reply);
		Topic topic = topicMapper.getTopicById(reply.getTopicId());
		topic.setReplycount(topic.getReplycount()+1);
		Integer saveCount = topicMapper.updateTopicByTopicId(topic);
		
		return (saveReply >0 && saveCount > 0);
	}

	@Override
	public List<Catorey> getAllCatorey() {
		return catoreyMapper.getAllCatorey();
	}

	@Override
	public PageInfo<Topic> getUserTopicList(Integer userId,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Topic> topicList = topicMapper.getTopicListByUserId(userId);
		PageInfo<Topic> page = new PageInfo<Topic>(topicList);
		return page;
	}

	@Override
	public Integer publish(Topic topic) {
		return save(topic);
	}

}
