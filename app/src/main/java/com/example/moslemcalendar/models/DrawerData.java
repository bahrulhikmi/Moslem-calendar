package com.example.moslemcalendar.models;

import android.support.v4.app.Fragment;

public class DrawerData {
	public String drawerTitle;
	public String displayedText;
	public Class fragmentClass;
	
	public DrawerData(String drawerName, Class fragmentClass)
	{
		this.drawerTitle = drawerName;
		this.displayedText = drawerName;
		this.fragmentClass = fragmentClass;
	}
}
