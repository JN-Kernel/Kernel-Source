package com.aode.dao;

import java.util.List;
import java.util.Map;

import com.aode.dto.Like;
import com.aode.dto.Topic;
import com.aode.dto.TopicContent;
import com.aode.dto.TopicReply;

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
	 * 通过catoreyId获取topic
	 * @param catoreyId
	 * @return
	 */
	public List<Topic> getTopicOfTitleAndIdByCatoreyId(Integer catoreyId);
	
	/**
	 * 保存topic
	 * @param topic
	 * @return
	 */
	public Integer saveTopic(Topic topic);
	
	/**
	 * 保存topicContent
	 * @param topicContent
	 * @return
	 */
	public Integer saveTopicContent(TopicContent topicContent);
	
	/**
	 * 通过topicId更新Topic
	 * @param topic
	 * @return
	 */
	public Integer updateTopicByTopicId(Topic topic);
	
	/**
	 * 通过topicId删除topic
	 * @param topicId
	 * @return
	 */
	public Integer deleteByTopicId(Integer topicId);
	
	/**
	 * 通过topicId取得topicContent
	 * @param topicId
	 * @return
	 */
	public TopicContent getTopicContentByTopicId(Integer topicId);
	
	/**
	 * 通过topicId以及分页数据获取topicReply
	 * @param topicReplyPage
	 * @return
	 */
	public List<TopicReply> getTopicReplysByTopicId(Integer topicId);
	
	/**
	 * 根据topicId和replyToReplyId获取子回复
	 * @param map
	 * @return
	 */
	public List<TopicReply> getChildReplysByTopicId(Map map);
	
	/**
	 * 通过userId和topicId获取like信息
	 * @param userIdAndTopicId
	 * @return
	 */
	public Integer getLike(Like userIdAndTopicId);
	
	/**
	 * 点赞记录
	 * @param like
	 * @return
	 */
	public Integer chickLike(Like like);
	
	/**
	 * 获取点赞数
	 * @param topicId
	 * @return
	 */
	public Integer getLikecountByTopicId(Integer topicId);
	
	/**
	 * 保存回复
	 * @param reply
	 * @return
	 */
	public Integer saveTopicReply(TopicReply reply);
	
	/**
	 * 取出用户发表的topic
	 * @param username
	 * @return
	 */
	public List<Topic> getTopicListByUserId(Integer userId);
}
