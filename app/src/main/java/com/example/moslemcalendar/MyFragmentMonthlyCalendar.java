package com.example.moslemcalendar;

import com.example.moslemcalendar.fragments.CalendarFragment;
import com.example.moslemcalendar.fragments.MyFragment;

import android.support.v4.app.Fragment;

public class MyFragmentMonthlyCalendar extends MyFragment {

	@Override
	public Fragment CreateFragment() {
		return new CalendarFragment();
	}

}
