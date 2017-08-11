package com.aode.service;

import java.util.List;

import com.aode.dto.Topic;
import com.github.pagehelper.PageInfo;

public interface IIndexService {
	public PageInfo<Topic> search(String q,int pageNum,int pageSize);
	
	public List<Topic> getLatestTopicList();
	
	public List<Topic> getLikeTopicList();
	
	public PageInfo<Topic> getTopicList(int pageNum,int pageSize);
	
	public PageInfo<Topic> getTopicListByCatoreyId(Integer catoreyId,int pageNum,int pageSize);
}
