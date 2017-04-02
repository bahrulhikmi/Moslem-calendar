package com.example.moslemcalendar.fragments;

import com.example.moslemcalendar.Globals;
import com.example.moslemcalendar.Globals.CalendarType;
import com.example.moslemcalendar.MonthView;
import com.example.moslemcalendar.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment {
	MonthView mMonthView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mMonthView =new MonthView(getActivity(), CalendarType.ISO, CalendarType.ISLAMIC, this); 
		return mMonthView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mMonthView.Reload();
	}
	
}
