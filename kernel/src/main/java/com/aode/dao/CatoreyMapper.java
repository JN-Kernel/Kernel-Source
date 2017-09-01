package com.aode.dao;

import java.util.List;
import java.util.Map;

import com.aode.dto.Catorey;
import com.aode.dto.Like;
import com.aode.dto.Topic;
import com.aode.dto.TopicContent;
import com.aode.dto.TopicReply;

public interface CatoreyMapper {

	/**
	 * 取得所有分类
	 * @return
	 */
	public List<Catorey> getAllCatorey();
	
	/**
	 * 各更新分类
	 * @param catorey
	 * @return
	 */
	public Integer updateCatorey(Catorey catorey);
	
	/**
	 * 保存分类
	 * @param catorey
	 * @return
	 */	
	public Integer saveCatorey(Catorey catorey);
}
