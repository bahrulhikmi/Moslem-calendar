package com.example.moslemcalendar;


import java.security.cert.LDAPCertStoreParameters;
import java.util.ArrayList;

import org.joda.time.DateTimeConstants;

import com.example.moslemcalendar.Globals.CalendarType;
import com.example.moslemcalendar.fragments.EventFormFragment;
import com.example.moslemcalendar.models.DayData;
import com.example.moslemcalendar.models.Event;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MonthView extends TableLayout {

	private String[] mDays;
	private int selectedDayIndex=-1;
	private String[] mMonths;
	private String[] mSecMonths;
	private Context mContext;
	public int mFirstDay = DateTimeConstants.SUNDAY;
	public boolean mIsDayShortName =true;
	public boolean mIsShowArabicNumber = true;
	public MonthData mMonthData;	
	private CalendarType mCalendarType;
	private CalendarType mSecondaryCalendarType;
	private Fragment parentFragment;
	
	public MonthView(Context context, CalendarType calendarType, CalendarType secondaryCalendarType, Fragment parent) {
		super(context);
		mContext = context;
		mCalendarType = calendarType;
		mSecondaryCalendarType = secondaryCalendarType;
		parentFragment = parent;
		mInitialize();
	}
	
	private void mInitialize()
	{
		mDays = Globals.getDaysName(mFirstDay==DateTimeConstants.MONDAY, getResources(), mIsDayShortName);		
		mMonths = Globals.getMonthNames(getResources(), CalendarType.ISO);
		mSecMonths = Globals.getMonthNames(getResources(), CalendarType.ISLAMIC);
		mMonthData = new MonthData(mFirstDay, mCalendarType, mSecondaryCalendarType, mContext);
		DisplayMonth();
	}
	
	public void Reload()
	{
		mMonthData.reloadData();
		DisplayMonth();
	}
	
	public void DisplayMonth()
	{
		ArrayList<Event> loDisplayedEvents = new ArrayList<Event>();
		removeAllViews();
		
		//Calendar Header
		LinearLayout monthTop = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.month_top, null);
		TextView tvMonth = (TextView) monthTop.findViewById(R.id.textViewDay);
		tvMonth.setText(""+mMonths[mMonthData.getMonth()]+" "+mMonthData.getYear());
		
		if(selectedDayIndex!=-1)
		{			
			DayData temp = mMonthData.Days[selectedDayIndex];
			String text=""+temp.getDay()+" "+mMonths[mMonthData.getMonth()]+" "+mMonthData.getYear()+" (" + 
					temp.getSecondaryDay()+" "+mSecMonths[temp.getSecondaryMonth()]+" "+temp.getSecondaryYear()+" )";
			tvMonth = (TextView) monthTop.findViewById(R.id.textViewDaySecondary);
			tvMonth.setText(text);
			
			
		}
		
		TextView imgView = (TextView) monthTop.findViewById(R.id.previousMonth);
		imgView.setTag("previous");
		imgView.setText(mMonths[mMonthData.getPreviousMonth()]);
		imgView.setOnClickListener(changeMonthClickListener);
		
		imgView = (TextView) monthTop.findViewById(R.id.nextMonth);
		imgView.setTag("next");
		imgView.setText(mMonths[mMonthData.getNextMonth()]);
		imgView.setOnClickListener(changeMonthClickListener);

		addView(monthTop);
		
		//Days Name
		TableRow tr = new TableRow(mContext);
		android.widget.TableRow.LayoutParams lp = new TableRow.LayoutParams();
		
		tr.setWeightSum(0.7f);
		lp.weight = 0.1f;		
		TextView tv=null;
		for (int i=0; i<7 ;i++)
		{
			tv = new TextView(mContext);
			tv.setText(mDays[i]);
			tv.setPadding(10, 3, 10, 3);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.days_style);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			tv.setLayoutParams(lp);
			tr.addView(tv);
		}
		
		addView(tr);
		//TODO: refactor following code, simplify by add more function to DayView class
		//Days	
		int liDayIndex=0;
		DayData dayData = null;
		for (int i=0;i<6;i++)
		{			
			tr = new TableRow(mContext);
			tr.setWeightSum(0.7f);
			
			for(int j=0; j<7;j++)
			{
				dayData = mMonthData.Days[liDayIndex];
				
				if(dayData.getDay()==0)
					break;
				LinearLayout dayView = (LinearLayout) LayoutInflater.from(mContext)
						.inflate(R.layout.day_item, null);
				TextView day1 = (TextView) dayView.findViewById(R.id.day1);
				TextView day2 = (TextView) dayView.findViewById(R.id.day2);
				day1.setText(""+dayData.getDay());
				if (mIsShowArabicNumber)
					day2.setText(dayData.getSecondaryDayArabic());
				else
					day2.setText(""+dayData.getSecondaryDay());
				dayView.setLayoutParams(lp);
				dayView.setBackgroundResource(R.drawable.days_style2);
				dayView.setOnLongClickListener(onDateLongClickListener);
				if(dayData.isEvent()){
					Event loEvent = dayData.getEvent(mContext);
					dayView.setBackgroundColor(loEvent.getColorInt());
					loDisplayedEvents.add(loEvent);
				}

				if(dayData.isToday())
				{
					dayView.setBackgroundColor(Color.GREEN);
				}
				
				if(dayData.isAdditionalDay())
				{
					day1.setTextColor(Color.GRAY);
					day2.setTextColor(Color.GRAY);
				}
				
				if(liDayIndex== selectedDayIndex)
				{
					dayView.setBackgroundColor(Color.GRAY);
				}
				
				dayView.setTag(""+liDayIndex);
				dayView.setOnClickListener(onDateClickListener);
				
				tr.addView(dayView);
				liDayIndex++;
			}			
			addView(tr);
		}
		
		DisplayEvent(loDisplayedEvents);
	}
	
	
	private void DisplayEvent(ArrayList<Event> events)
	{
		for (Event event : events) {	
			RelativeLayout rel = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.month_event, null);
			View colInd = (View) rel.findViewById(R.id.colorIndicator);
			TextView tv = (TextView) rel.findViewById(R.id.event_text);
			colInd.setBackgroundColor(event.getColorInt());
			tv.setText("" + event.getDay() + " : " + event.getName());
			addView(rel);
			
			//Update Calendarview			
		}	
	}
	
	
	private OnClickListener changeMonthClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (v.getTag().equals("previous"))
			{
				mMonthData.previousMonth();
			}
			else
			{
				mMonthData.nextMonth();
			}
			
			selectedDayIndex = -1;
			DisplayMonth();
		}
	};
	
	private OnClickListener onDateClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {	
			
			selectedDayIndex = Integer.parseInt(v.getTag().toString());
			
			DisplayMonth();
		}
	};
	
	private OnLongClickListener onDateLongClickListener = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
				
				selectedDayIndex = Integer.parseInt(v.getTag().toString());
				Intent intent = new Intent(mContext, EventFormActivity.class);
				intent.putExtra(EventFormFragment.EXTRA_EVENT_DAYDATA, mMonthData.Days[selectedDayIndex] );
				mContext.startActivity(intent);

			return true;
		}
	};
}
