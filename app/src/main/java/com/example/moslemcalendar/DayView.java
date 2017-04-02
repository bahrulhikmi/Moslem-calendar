package com.example.moslemcalendar;
import android.content.Context;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DayView extends LinearLayout{

	public DayView(Context context, int day, int secondaryDay) {
		super(context);
		
		mCreateVIew(context, day, secondaryDay);
	}
	
	private void mCreateVIew(Context context, int day, int secondaryDay)
	{
		setOrientation(VERTICAL);
		
		LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView tv = new TextView(context);
		tv.setText(""+day);
				
		tv.setLayoutParams(lp);
		
		addView(tv);
		
		tv = new TextView(context);
		tv.setText(""+secondaryDay);
		tv.setLayoutParams(lp);
		addView(tv);
		

		
		
	}
	
	

}
