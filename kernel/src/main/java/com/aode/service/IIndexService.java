package com.aode.service;

import java.util.List;

import com.aode.dto.Topic;
import com.github.pagehelper.PageInfo;

public interface IIndexService {
	/**
	 * 查找Topic，分页处理
	 * @param q
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Topic> search(String q,int pageNum,int pageSize);
	
	/**
	 * 取得最新的Topic列表，
	 * 数量：最多8条
	 * @return
	 */
	public List<Topic> getLatestTopicList();
	
	/**
	 * 取得likeCount最多的Topic列表
	 * 数量：最多8条
	 * @return
	 */
	public List<Topic> getLikeTopicList();
	
	/**
	 * 取出所有的Topic列表，分页处理
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Topic> getTopicList(int pageNum,int pageSize);
	
	/**
	 * 通过catoreyId取出topic列表，分页处理
	 * @param catoreyId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Topic> getTopicListByCatoreyId(Integer catoreyId,int pageNum,int pageSize);
}
