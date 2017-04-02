package com.example.moslemcalendar.models;

import java.util.UUID;

public class EventGroup {
	
	private UUID mGroupId;
	private String groupName;
	private String mColor;

	public UUID getGroupId() {
		return mGroupId;
	}
	public void setGroupId(UUID groupId) {
		mGroupId = groupId;
	}
	public String getColor() {
		return mColor;
	}
	public void setColor(String color) {
		mColor = color;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	
}
