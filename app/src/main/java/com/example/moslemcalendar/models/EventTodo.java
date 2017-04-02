package com.example.moslemcalendar.models;

import com.example.moslemcalendar.Globals.EventType;

public class EventTodo extends Event {
	private int mCompleted;
	private int mAmountRequired;
	
	public EventTodo()
	{
		setEventType(EventType.TODO);
	}
}
