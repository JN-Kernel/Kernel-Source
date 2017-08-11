package com.aode.dto;

public class TopicCustomize extends Topic{
	private Integer topicContentId;
	
	private String content;

	public Integer getTopicContentId() {
		return topicContentId;
	}

	public void setTopicContentId(Integer topicContentId) {
		this.topicContentId = topicContentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
