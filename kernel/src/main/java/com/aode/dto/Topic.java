package com.aode.dto;

import java.util.Date;

public class Topic {

	private Integer topicId;

	private String title;

	private Date publishtime;

	private Integer status;

	private Integer replycount;

	private Integer likecount;

	private Integer userId;

	private Integer catoreyId;

	private TopicContent topicContent;

	private String catoreyname;
	
	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Integer getReplycount() {
		return replycount;
	}

	public void setReplycount(Integer replycount) {
		this.replycount = replycount;
	}

	public Integer getLikecount() {
		return likecount;
	}

	public void setLikecount(Integer likecount) {
		this.likecount = likecount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCatoreyId() {
		return catoreyId;
	}

	public void setCatoreyId(Integer catoreyId) {
		this.catoreyId = catoreyId;
	}

	public TopicContent getTopicContent() {
		return topicContent;
	}

	public void setTopicContent(TopicContent topicContent) {
		this.topicContent = topicContent;
	}

	
	
	public String getCatoreyname() {
		return catoreyname;
	}

	public void setCatoreyname(String catoreyname) {
		this.catoreyname = catoreyname;
	}

	@Override
	public String toString() {
		return "Topic [topicId=" + topicId + ", title=" + title + ", publishtime=" + publishtime + ", status=" + status
				+ ", replycount=" + replycount + ", likecount=" + likecount + ", userId=" + userId + ", catoreyId="
				+ catoreyId + ", topicContent=" + topicContent + ", catoreyname=" + catoreyname + "]";
	}

	
}
