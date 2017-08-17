package com.aode.dao;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.aode.dto.Topic;

@RunWith(value=SpringJUnit4ClassRunner.class) 
@WebAppConfiguration 
@ContextConfiguration(value="classpath:applicationContext.xml")
public class TopicMapperTest {

	@Resource
	private TopicMapper topicMapper;
	@Test
	public void testGetTopicById() {
		Topic topic =topicMapper.getTopicById(1);
		System.out.println(topic);
	}

	@Test
	public void testGetTopicContentByTopicId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTopicOfTitleAndIdByCatoreyId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateTopicByTopicId() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByTopicId() {
		fail("Not yet implemented");
	}

}
