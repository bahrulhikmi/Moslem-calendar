 package com.example.moslemcalendar;

import android.support.v4.app.Fragment;

import com.example.moslemcalendar.Globals.EventType;
import com.example.moslemcalendar.fragments.EventListFragment;
import com.example.moslemcalendar.fragments.MyFragment;

public class MyFragmentEventHistorical extends MyFragment {

	@Override
	public Fragment CreateFragment() {
		return new EventListFragment(EventType.HISTORY);
	}


}
