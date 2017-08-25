package com.aode.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aode.dto.Topic;
import com.aode.dto.TopicContent;
import com.aode.dto.TopicReply;

@RunWith(value=SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(value="classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
public class TopicMapperTest {

	@Resource
	private TopicMapper topicMapper;
	@Test
	public void testGetTopicById() {
//		fail("Not yet implemented");
//		Topic topic =topicMapper.getTopicById(1);
//		System.out.println(topic);
	}


	@Test
	public void testGetTopicContentByTopicId() {
//		fail("Not yet implemented");
//		TopicContent topic =topicMapper.getTopicContentByTopicId(2);
//		int i = 1/0;
//		System.out.println(topic);
		List<TopicReply> replys = topicMapper.getTopicReplysByTopicId(1);
		System.out.println(replys);
	}

	@Test
	public void testGetTopicOfTitleAndIdByCatoreyId() {
//		fail("Not yet implemented");
//		List<Topic> topic =topicMapper.getTopicOfTitleAndIdByCatoreyId(1);
//		System.out.println(topic);
	}

	@Transactional
	@Test
	public void testSave() {
		fail("Not yet implemented");
		Topic topic = new Topic();
		topic.setTitle("test save2");
		topic.setLikecount(1);
		topic.setPublishtime(new Date());
		topic.setReplycount(1);
		topic.setUserId(1);
		topic.setCatoreyId(1);
		topic.setStatus(1);
		System.out.println(topic);
		int s = topicMapper.saveTopic(topic);
		
		System.out.println(s);
	}

	@Test
	public void testUpdateTopicByTopicId() {
//		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByTopicId() {
//		fail("Not yet implemented");
	}

}
