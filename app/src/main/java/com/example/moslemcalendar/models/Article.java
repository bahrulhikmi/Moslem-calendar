package com.example.moslemcalendar.models;

import java.util.Date;
import java.util.UUID;

public class Article {
	private UUID mId;	
	private String mContent;
	private Date mLastUpdated;
	
	public UUID getId() {
		return mId;
	}
	public void setId(UUID id) {
		mId = id;
	}
	public String getContent() {
		return mContent;
	}
	public void setContent(String content) {
		mContent = content;
	}
	public Date getLastUpdated() {
		return mLastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		mLastUpdated = lastUpdated;
	}
	
	
	
	

}
