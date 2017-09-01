package com.aode.dto;

import java.util.List;

public class Catorey {

	private Integer catoreyId;
	
	private String catoreyname;
	
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

	public String getCatoreyname() {
		return catoreyname;
	}

	public void setCatoreyname(String catoreyname) {
		this.catoreyname = catoreyname;
	}

	
	
	
}
