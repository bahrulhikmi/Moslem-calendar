package com.example.moslemcalendar;

import android.support.v4.app.Fragment;

import com.example.moslemcalendar.fragments.EventFormFragment;
import com.example.moslemcalendar.fragments.SingleFragmentActivity;
import com.example.moslemcalendar.models.DayData;

public class EventFormActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		DayData dayData = getIntent().getParcelableExtra(EventFormFragment.EXTRA_EVENT_DAYDATA);
		return new EventFormFragment(dayData);
	}

}
