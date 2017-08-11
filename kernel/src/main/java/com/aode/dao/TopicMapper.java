package com.aode.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.aode.dto.Topic;
import com.aode.dto.TopicCustomize;

public interface TopicMapper {

	/**
	 * 根据时间排序最新的数据
	 * @param 取出的条数
	 * @return
	 */
	public List<Topic> getLatestTopicOfTitleAndId();
	
	/**
	 * 
	 * @param 根据条件获取数据
	 * @return
	 */
	public List<Topic> searchTopicOfTitleAndId(String param);
	
	/**
	 *	根据like排序取出的数据
	 * @param count
	 * @return
	 */
	public List<Topic> getLikeTopicOfTitleAndId();
	
	/**
	 * 根据id取得topic
	 * @param topicId
	 * @return
	 */
	public Topic getTopicById(Integer topicId);
	
	/**
	 * 取出topic的所有信息
	 * @param topicId
	 * @return
	 */
	public TopicCustomize getTopicContentByTopicId(Integer topicId);
	
	/**
	 * 通过catoreyId获取topic
	 * @param catoreyId
	 * @return
	 */
	public List<Topic> getTopicOfTitleAndIdByCatoreyId(Integer catoreyId);
}
