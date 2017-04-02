package com.example.moslemcalendar.fragments;

import java.util.ArrayList;

import com.example.moslemcalendar.CalendarLab;
import com.example.moslemcalendar.Globals.EventType;
import com.example.moslemcalendar.R;
import com.example.moslemcalendar.R.id;
import com.example.moslemcalendar.R.layout;
import com.example.moslemcalendar.models.Event;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventListFragment extends ListFragment {
	private ArrayList<Event> mEvent;
	private EventType mFilterType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mEvent = CalendarLab.getEvent(getActivity(), mFilterType);
		setListAdapter(new EventAdapter());
	}

	public EventListFragment()
	{
		this.mFilterType = EventType.ALL;
	}
	
	public EventListFragment(EventType filter)
	{
		this.mFilterType = filter;
	}
	
	private class EventAdapter extends ArrayAdapter<Event> {
		public EventAdapter() {
			super(getActivity(), 0, mEvent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_event, null);
			}

			TextView tvName = (TextView) convertView
					.findViewById(R.id.eventName);
			tvName.setText(mEvent.get(position).getName());

			return convertView;
		}

	}
}
