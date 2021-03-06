package com.aode.service;

import java.util.List;

import com.aode.dto.Catorey;
import com.aode.dto.Like;
import com.aode.dto.Topic;
import com.aode.dto.TopicReply;
import com.github.pagehelper.PageInfo;

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
	
	/**
	 * 取出topic的reply
	 * @param topicId
	 * @return
	 */
	public PageInfo<TopicReply> getTopicReplysByTopicId(Integer topicId,int pageNum,int pageSize);
	
	/**
	 * 点赞操作
	 * @param like
	 * @return
	 */
	public Boolean chickLike(Like like);
	
	/**
	 * 评论帖子
	 * @param reply
	 * @return
	 */
	public Boolean commentWithTopic(TopicReply reply);
	
	/**
	 * 取出所有分类
	 * @return
	 */
	public List<Catorey> getAllCatorey();
	
	/**
	 * 取得用户发表的文章
	 * @param userId
	 * @return
	 */
	public  PageInfo<Topic> getUserTopicList(Integer userId,Integer pageNum,Integer pageSize);
}
