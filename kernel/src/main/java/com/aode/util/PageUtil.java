package com.aode.util;

import java.util.Map;

public class PageUtil {
	private Integer pageNum;	//当前页数
	
	private Integer pageSize;	//每页条数
	
	private Integer pages;		//总页数
	
	private Map<Integer,String> resultMap;	//id，以及对应内容 

	
	
	public PageUtil(Integer pageNum,Integer pageSize,Integer pages){
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.pages = pages;
	}
	
	public PageUtil(Integer pageNum,Integer pageSize,Integer pages,Map<Integer,String> resultMap){
		this(pageNum, pageSize,pages);
		this.resultMap = resultMap;
	}
	
	
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Map<Integer, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<Integer, String> resultMap) {
		this.resultMap = resultMap;
	}
	
	
}
