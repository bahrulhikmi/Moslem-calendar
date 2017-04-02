package com.example.moslemcalendar;

import java.util.ArrayList;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DateTimeUtils;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import com.example.moslemcalendar.Globals.CalendarType;
import com.example.moslemcalendar.models.DayData;
import com.example.moslemcalendar.models.Event;

import android.content.Context;
import android.provider.Settings.Global;

public class MonthData {
	public DayData[] Days;
	private int mTodayDayOfMonth;
	private int mTodayMonth;
	private int mTodayYear;
	private int firstDay;
	private CalendarType calendarType;
	private CalendarType secondaryCalendarType;
	private DateTime cal, prevCal, secondaryCal;
	public ArrayList<Event> events;
	private DbHelper dbHelper;
private int mTodayIndex;

	public MonthData(int firstDay, CalendarType calType, CalendarType secondaryCalendarType, Context context) {		
		this.firstDay = firstDay;
		this.calendarType = calType;
		this.secondaryCalendarType = secondaryCalendarType;
		dbHelper = new DbHelper(context);
		mInitialize();
	}

	private void mInitialize() {
		cal = new DateTime(getChronology(false));	
		events = dbHelper.getEvents(cal.getMonthOfYear(), cal.getYear());
		mTodayDayOfMonth = cal.getDayOfMonth();
		mTodayMonth = cal.getMonthOfYear()-1;
		mTodayYear = cal.getYear();
		Days = new DayData[42];
		mFillDays();
	}

	public void setType(CalendarType calType) {
		calendarType = calType;
		mInitialize();
	}

	public int getTodayIndex(){
		return mTodayIndex;
	}
	
	
	/**
	 * Get current index of the month, started from 0
	 * @return
	 */
	public int getMonth() {
		return cal.getMonthOfYear() - 1;
	}
	public int getNextMonth(){
	return (getMonth()+1)%11;
	}
	
	public int getPreviousMonth(){
		return (getMonth()==0?11:getMonth()-1);
	}

	public int getYear() {
		return cal.getYear();
	}

	public void setMonthAndYear(int month, int year) {
		cal = cal.withMonthOfYear(month).withYear(year);
		mFillDays();
	}

	public void nextMonth() {
		int lMonth = cal.getMonthOfYear() + 1;
		if (lMonth > 12) {
			lMonth = 1;
			cal = cal.plusYears(1);
		}
		cal = cal.withMonthOfYear(lMonth);
		mFillDays();
	}
	
	public void reloadData(){
		mFillDays();
	}

	public void previousMonth() {
		int lMonth = cal.getMonthOfYear() - 1;
		if (lMonth < 1) {
			lMonth = 12;
			cal = cal.minusYears(1);
		}
		cal = cal.withMonthOfYear(lMonth);
		mFillDays();
	}

	private Chronology getChronology(boolean isSecondary) {
		CalendarType cal = isSecondary?secondaryCalendarType: calendarType;
		switch (cal) {
		case ISLAMIC:
			return IslamicChronology
					.getInstance();
		case ISO:
			return ISOChronology
					.getInstance();
		default:
			return  ISOChronology
					.getInstance();

		}
	}

	private void mFillDays() {
		
		int liFirstDayOfMonth;
		int liNextMonthDay = 1;
		int liPreviousMonthDay;
		int liDay = 1;
		// Set current calendar to first day so we can get first day of the
		// month
		cal = cal.withDayOfMonth(1);
		liFirstDayOfMonth = cal.getDayOfWeek() - 1;
		if (firstDay == DateTimeConstants.SUNDAY) {
			liFirstDayOfMonth++;
			if (liFirstDayOfMonth > 6)
				liFirstDayOfMonth = 0;
		}

		// Get previous calendar
		prevCal = cal.withMonthOfYear(cal.getMonthOfYear()).minusMonths(1);
		liPreviousMonthDay = prevCal.dayOfMonth().getMaximumValue()
				- liFirstDayOfMonth + 1;

		// Set secondary calendar
		if (liFirstDayOfMonth != 0)
			secondaryCal = prevCal.withDayOfMonth(liPreviousMonthDay)
					.withChronology(getChronology(true));
		else
			secondaryCal = cal.withChronology(getChronology(true));

		DayData dayData = null;
		for (int i = 0; i < 42; i++) {

			dayData = new DayData();
			if (liDay == 1 && i < liFirstDayOfMonth) {
				dayData.setDay(liPreviousMonthDay++);
				dayData.setAdditionalDay(true);
			} else if (liDay > cal.dayOfMonth().getMaximumValue()) {
				dayData.setDay(liNextMonthDay++);
				dayData.setAdditionalDay(true);
			} else {
				if (liDay == mTodayDayOfMonth && getMonth() == mTodayMonth
						&& getYear() == mTodayYear) {
					dayData.setToday(true);
					mTodayIndex = i;
				}
				dayData.setDay(liDay++);
			}
			dayData.setSecondaryDay(secondaryCal.getDayOfMonth());
			dayData.setSecondaryMonth(secondaryCal.getMonthOfYear()-1);
			dayData.setSecondaryYear(secondaryCal.getYear());
			
			secondaryCal = secondaryCal.plusDays(1);

			Days[i] = dayData;
		}
		
		events = dbHelper.getEvents();
		
		//TODO: There must be a way to optimize this
		//Learn how to map a list maybe or use dictionary?
		Event loEvent;
		DayData loDay;
		for (int i=0;i<events.size();i++)
		{
			for(int j=0;j<42;j++)
			{
				loEvent = events.get(i);
				loDay = Days[j];
				if(loEvent.getDay()==loDay.getSecondaryDay()&& loEvent.getMonth()==loDay.getSecondaryMonth())
				{
					Days[j].setEvent(events.get(i));
					break;
				}
			}
		}

	}

}
