package com.aode.service;

import com.aode.dto.Topic;

public interface ITopicService {
	/**
	 * 发表Topic
	 * @param topic
	 * @return
	 */
	public Integer publish(Topic topic);
	
	/**
	 * 保存Topic
	 * @param topic
	 * @return
	 */
	public Integer save(Topic topic);
	
	/**
	 * 通过topicId取得Topic
	 * @param topicId
	 * @return
	 */
	public Topic getTopicByTopicId(Integer topicId);
	
	/**
	 * 通过topicId删除Topic
	 * @param topicId
	 * @return
	 */
	public Integer deleteByTopicId(Integer topicId);
	
	/**
	 * 通过topicId更新Topic
	 * @param topic
	 * @return
	 */
	public Integer updateByTopicId(Topic topic);
}
