package com.aode.dto;

import java.util.Date;

public class TopicReply {
	private Integer topicReplyId;
	
	private String content;
	
	private Integer topicId;
	
	private Integer userId;
	
	private Date replytime;
	
	private Integer status;


	
	
	public Integer getTopicReplyId() {
		return topicReplyId;
	}

	public void setTopicReplyId(Integer topicReplyId) {
		this.topicReplyId = topicReplyId;
	}

	public Date getReplytime() {
		return replytime;
	}

	public void setReplytime(Date replytime) {
		this.replytime = replytime;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TopicReply [topicReplyId=" + topicReplyId + ", content=" + content + ", topicId=" + topicId
				+ ", userId=" + userId + ", replytime=" + replytime + ", status=" + status + "]";
	}
	
	
}
