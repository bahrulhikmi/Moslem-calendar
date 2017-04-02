package com.example.moslemcalendar.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.moslemcalendar.CalendarLab;
import com.example.moslemcalendar.DbHelper;
import com.example.moslemcalendar.R;
import com.example.moslemcalendar.models.DayData;
import com.example.moslemcalendar.models.Event;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;



public class EventFormFragment extends Fragment  {

	String colors[] = {CalendarLab.BLUE_HIGH, CalendarLab.BLUE, CalendarLab.BLUE_LOW,
			CalendarLab.GREEN_HIGH, CalendarLab.GREEN, CalendarLab.GREEN_LOW,
			CalendarLab.RED_HIGH, CalendarLab.RED, CalendarLab.RED_LOW,
			CalendarLab.YELLOW_HIGH, CalendarLab.YELLOW, CalendarLab.YELLOW_LOW};
	
	View mView ;
	DayData mDayData;
	Context mContext;
	
	public static final String EXTRA_EVENT_DAYDATA = "com.bahrulh.moslemcalendar.daydata";	
	
	//Options configuration
	ArrayList<View> Options;
	int sizeBox = 60;
	int growth = 5;
	int margin = 2*growth;
	int screenWidth;
	boolean isGrow =false;
	DbHelper dbHelper;
	EditText eventName;
	String eventColor;
	
	public EventFormFragment(DayData dayData) {
		mDayData = dayData;
		mContext = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.event_form, container, false );
		
		eventName = (EditText) mView.findViewById(R.id.inputTxtEventName);		
		
		Button btnSave = (Button) mView.findViewById(R.id.buttonSave_event_form);
		btnSave.setOnClickListener(onSaveClickListener);
		
		Button btnDelete = (Button) mView.findViewById(R.id.buttonDelete_event_form);
		btnDelete.setOnClickListener(onDeleteClickListener);
		
		//Get screen width
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		sizeBox = (int) ((screenWidth-margin)/6.0)-margin;
		
		createOptions();
		dbHelper = new DbHelper(getActivity());
		return mView;		
	}
	
	public void createOptions()
	{		
		Options = new ArrayList<View>();
		LinearLayout linear1 = (LinearLayout) mView.findViewById(R.id.colorOption1);				
		LinearLayout linear2 = (LinearLayout) mView.findViewById(R.id.colorOption2);
		for (int i=0; i<12;i++)	
		{
			View opt = new View(getActivity());
			LayoutParams params = new LayoutParams(sizeBox, sizeBox) ;	
			params.setMargins(margin, 0, 0, margin);
			opt.setLayoutParams(params);
			opt.setBackgroundColor(Color.parseColor(colors[i]));
			opt.setTag(i);
			opt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {	
					int tag = (Integer) v.getTag();
					final int newSize = isGrow? sizeBox+growth: sizeBox;
					eventColor = colors[tag];
					final int color = Color.parseColor(eventColor);
					
					refreshOptions();
					Drawable dr = new Drawable() {						
						@Override
						public void setColorFilter(ColorFilter cf) {
						}
						
						@Override
						public void setAlpha(int alpha) {
						}
						@Override
						public int getOpacity() {
							return 0;
						}
						
						@Override
						public void draw(Canvas canvas) {
							Paint paint = new Paint();
							paint.setColor(Color.GRAY);
							paint.setStrokeWidth(12);
							canvas.drawColor(color);
							//top
							canvas.drawLine(0, 0, newSize, 0, paint);
							//right
							canvas.drawLine(newSize, 0, newSize, newSize, paint);
							//bttm
							canvas.drawLine(0, newSize, newSize, newSize, paint);
							//left
							canvas.drawLine(0, 0, 0, newSize, paint);
							
						}
					};
					
					LayoutParams params = new LayoutParams(newSize, newSize) ;	
					params.setMargins(margin, 0, 0, margin);
					v.setLayoutParams(params);
					v.setBackgroundDrawable(dr);
				}
			});
			if(i>5)
				linear2.addView(opt);
			else
				linear1.addView(opt);
			Options.add(opt);
		}
	}
	
	public void refreshOptions(){
		for (int i=0; i<12;i++)	
		{
		 LayoutParams params = new LayoutParams(sizeBox, sizeBox) ;	
		 params.setMargins(margin, 0, 0, margin);
		 Options.get(i).setLayoutParams(params);
		 Options.get(i).setBackgroundColor(Color.parseColor(colors[i]));		 
		}
	}
	
	private OnClickListener onSaveClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Event e = new Event();
			e.setName(eventName.getText().toString());
			e.setColor(eventColor);
			e.setDay(mDayData.getSecondaryDay());
			e.setMonth(mDayData.getSecondaryMonth());
			e.setYear(mDayData.getSecondaryYear());
			dbHelper.AddEvent(e);
			getActivity().finish();
		}
	};
	
	private OnClickListener onDeleteClickListener  = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			dbHelper.DeleteEvent(mDayData.getEventId());
			getActivity().finish();
		}
	};
}
