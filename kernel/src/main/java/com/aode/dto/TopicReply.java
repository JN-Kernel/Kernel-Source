package com.aode.dto;

import java.util.Date;
import java.util.List;

public class TopicReply {
	private Integer topicReplyId;
	
	private String content;
	
	private Integer topicId;
	
	private Integer userId;
	
	private Date replytime;
	
	private Integer status;

	private Integer replyToUserId;
	
	private Integer replyToReplyId;

	private String reviewer;	//评论者
	
	private String author;		//发表人，可以是作者或者是发表评论的人
	
	private List<TopicReply> childReplys;
	
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

	public Integer getReplyToUserId() {
		return replyToUserId;
	}

	public void setReplyToUserId(Integer replyToUserId) {
		this.replyToUserId = replyToUserId;
	}

	public Integer getReplyToReplyId() {
		return replyToReplyId;
	}

	public void setReplyToReplyId(Integer replyToReplyId) {
		this.replyToReplyId = replyToReplyId;
	}


	public List<TopicReply> getChildReply() {
		return childReplys;
	}

	public void setChildReply(List<TopicReply> childReplys) {
		this.childReplys = childReplys;
	}

	
	public List<TopicReply> getChildReplys() {
		return childReplys;
	}

	public void setChildReplys(List<TopicReply> childReplys) {
		this.childReplys = childReplys;
	}

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "\nTopicReply [\ntopicReplyId=" + topicReplyId + ",\n content=" + content + ",\n topicId=" + topicId
				+ ", userId=" + userId + ", replytime=" + replytime + ", status=" + status + ", replyToUserId="
				+ replyToUserId + ", replyToReplyId=" + replyToReplyId+ ",\n reviewer="
				+ reviewer + ",\n author=" + author  + ",\n childReplys=" + childReplys + "\n]\n";
	}

	
	
	
}
