package com.example.moslemcalendar.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.moslemcalendar.CalendarLab;
import com.example.moslemcalendar.DbHelper;

public class DayData implements Parcelable{
	
	private int mDay;
	private boolean mToday;
	private boolean mEvent;
	private int mSecondaryDay;
	private boolean mAdditionalDay;
	private int mSecondaryMonth;
	private int mSecondaryYear;
	private int eventId;
	private Event event;

	public int getSecondaryMonth() {
		return mSecondaryMonth;
	}

	public void setSecondaryMonth(int secondaryMonth) {
		mSecondaryMonth = secondaryMonth;
	}

	public int getSecondaryYear() {
		return mSecondaryYear;
	}

	public void setSecondaryYear(int secondaryYear) {
		mSecondaryYear = secondaryYear;
	}

	public int getDay() {
		return mDay;
	}
	
	public String getDayArabic() {
		return CalendarLab.DecimalToArabic(""+mDay);
	}

	public void setDay(int day) {
		mDay = day;
	}

	public boolean isToday() {
		return mToday;
	}

	public void setToday(boolean today) {
		mToday = today;
	}

	public boolean isEvent() {
		return event!=null;
	}

	public int getSecondaryDay() {
		return mSecondaryDay;
	}
	
	public String getSecondaryDayArabic() {
		return CalendarLab.DecimalToArabic(""+mSecondaryDay);
	}

	public void setSecondaryDay(int secondaryDay) {
		mSecondaryDay = secondaryDay;
	}

	public boolean isAdditionalDay() {
		return mAdditionalDay;
	}

	public void setAdditionalDay(boolean additionalDay) {
		mAdditionalDay = additionalDay;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEvent(Event event) {
		eventId = event.getId();
		this.event = event;
	}	
	public Event getEvent(Context con) {
		if (event==null)
		{
			DbHelper db = new DbHelper(con);
			event = db.getEvent(eventId);
		}
		return event;
	}
	
	//implement PARCEL
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mDay);
		dest.writeInt(mToday?1:0);
		dest.writeInt(mEvent?1:0);
		dest.writeInt(mSecondaryDay);
		dest.writeInt(mAdditionalDay?1:0);
		dest.writeInt(mSecondaryMonth);
		dest.writeInt(mSecondaryYear);
		dest.writeInt(eventId);
	}
	
	public static final Parcelable.Creator<DayData> CREATOR = new Creator<DayData>() {
		
		@Override
		public DayData[] newArray(int size) {
			return new DayData[size];
		}
		
		@Override
		public DayData createFromParcel(Parcel source) {
			return new DayData(source);
		}
	};
	
	public DayData(Parcel source){
		mDay = source.readInt();
		mToday = source.readInt()==1;
		mEvent = source.readInt()==1;
		mSecondaryDay = source.readInt();
		mAdditionalDay = source.readInt()==1;
		mSecondaryMonth = source.readInt();
		mSecondaryYear = source.readInt();
		eventId = source.readInt();			
	}

	public DayData() {
	}
	



}
