package com.example.moslemcalendar.fragments;

import com.example.moslemcalendar.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_fragment_container);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment =  fm.findFragmentById(R.id.fragmentContainer);
		if(fragment==null)
		{
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
		
	}

}
