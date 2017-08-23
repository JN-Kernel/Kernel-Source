package com.aode.dto;

import java.util.List;

public class TopicContent {

	private Integer topicContentId;

	private String content;

	private Integer topicId;

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

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	@Override
	public String toString() {
		return "TopicContent [topicContentId=" + topicContentId + ", content=" + content + ", topicId=" + topicId + "]";
	}

}
