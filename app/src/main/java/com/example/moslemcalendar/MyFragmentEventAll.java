package com.example.moslemcalendar;

import com.example.moslemcalendar.fragments.EventListFragment;
import com.example.moslemcalendar.fragments.MyFragment;

import android.support.v4.app.Fragment;

public class MyFragmentEventAll extends MyFragment {

	@Override
	public Fragment CreateFragment() {
		return new EventListFragment();
	}

}
