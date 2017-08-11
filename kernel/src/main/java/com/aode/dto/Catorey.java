package com.aode.dto;

import java.util.List;

public class Catorey {

	private Integer catoreyId;
	
	private String catoreyName;
	
	private List<Topic> topics;

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	
	public Integer getCatoreyId() {
		return catoreyId;
	}

	public void setCatoreyId(Integer catoreyId) {
		this.catoreyId = catoreyId;
	}

	public String getCatoreyName() {
		return catoreyName;
	}

	public void setCatoreyName(String catoreyName) {
		this.catoreyName = catoreyName;
	}
	
	
}
