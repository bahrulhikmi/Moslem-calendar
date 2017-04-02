package com.example.moslemcalendar.models;

import java.util.Objects;
import java.util.UUID;

import com.example.moslemcalendar.Globals.EventType;

import android.graphics.Color;
import android.support.v7.internal.widget.ActivityChooserModel.HistoricalRecord;

public class Event {
	private int mMonth;
	private int mDay;
	private int mYear;
	private String mName;
	private UUID mArticleId;
	private EventType mEventType;
	private String mColor;
	private EventGroup mEventGroup;
	private int mId;
	
	public int getId(){
		return mId;
	}
	
	public void setId(int id){
		this.mId = id;
	}
	
	
	public String getColorHex(){
		return (mColor==null || mColor=="")?mEventGroup.getColor():mColor;		
	}
	
	public int getColorInt(){
		return Color.parseColor(getColorHex());		
	}
	
	public void setColor(String color)
	{
		mColor= color; 
	}
	
	public EventType getEventType() {
		return mEventType;
	}
	public void setEventType(EventType eventType) {
		mEventType = eventType;
	}
	public UUID getArticleId() {
		return mArticleId;
	}
	public void setArticleId(UUID articleId) {
		mArticleId = articleId;
	}
	public int getMonth() {
		return mMonth;
	}
	public void setMonth(int month) {
		mMonth = month;
	}
	public int getDay() {
		return mDay;
	}
	public void setDay(int day) {
		mDay = day;
	}
	public int getYear() {
		return mYear;
	}
	public void setYear(int year) {
		mYear = year;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	
	public boolean isHistorical()
	{
		return getClass() == EventHistorical.class;		
	}

	public boolean isTodo()
	{
		return getClass() == EventTodo.class;		
	}

	
	
}
