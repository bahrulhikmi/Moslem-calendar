package com.example.moslemcalendar;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

import com.example.moslemcalendar.Globals.EventType;
import com.example.moslemcalendar.models.Event;
import com.example.moslemcalendar.models.EventHistorical;
import com.example.moslemcalendar.models.EventTodo;

public class CalendarLab {
	
	public static final String RED = "#ff9999";
	public static final String RED_LOW ="#ffcccc";
	public static final String RED_HIGH ="#ff6666";
	public static final String GREEN ="#66ff66";
	public static final String GREEN_LOW ="#99ff99";
	public static final String GREEN_HIGH="#33ff33";
	public static final String BLUE ="#6666ff";
	public static final String BLUE_LOW="#9999ff";
	public static final String BLUE_HIGH="#3333ff";
	public static final String YELLOW="#ffff66";
	public static final String YELLOW_LOW="#ffff99";
	public static final String YELLOW_HIGH="#ffff33";
	
	private static ArrayList<Event> events;
	private static DbHelper dbHelper;

//	private static void GenerateEvent() {		
//		events = new ArrayList<Event>();
//		Random ran = new Random(10);
//
//		for (int i = 0; i < 50; i++) {
//			Event e = null;
//			if (ran.nextInt() % 2 == 0) {
//				{
//					e = new EventHistorical();
//					e.setName("Event (Historical)" + (i + 1));
//				}
//			} else {
//
//				e = new EventTodo();
//				e.setName("Event (Todo)" + (i + 1));
//			}
//
//			events.add(e);
//		}
//	}
//		
	private static final String arabic = "\u0660\u0661\u0662\u0663\u0664\u0665\u0666\u0667\u0668\u0669";
	public static String ArabicToDecimal(String number) {
	    char[] chars = new char[number.length()];
	    for(int i=0;i<number.length();i++) {
	        char ch = number.charAt(i);
	        int index = arabic.indexOf(ch);
	        if (index >= 0)
	            ch = (char) (index + '0');
	        chars[i] = ch;
	    }
	    return new String(chars);
	}
	
	public static String DecimalToArabic(String number) {
	    char[] chars = new char[number.length()];
	    for(int i=0;i<number.length();i++) {
	    	int index = Character.getNumericValue(number.charAt(i));
	    	char ch = index==-1?number.charAt(i) : arabic.charAt(index);
	        chars[i] = ch;
	    }
	    return new String(chars);
	}
	
	private static void getEvent(Context context)
	{
		if (dbHelper==null)
		{
			dbHelper = new DbHelper(context);
		}
		
		events = dbHelper.getEvents();
	}

	public static ArrayList<Event> getEvent(Context context, EventType eventType) {
		if (events == null || events.size() == 0)
			getEvent(context);
		ArrayList<Event> result = new ArrayList<Event>();

		for (Event event : events) {
			if (eventType== EventType.ALL || event.getEventType() == eventType) {
				result.add(event);
			}
		}
		return result;
	}
}
