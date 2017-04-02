package com.example.moslemcalendar;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.moslemcalendar.fragments.MyFragment;
import com.example.moslemcalendar.models.DrawerData;

/**
 * This class manage Drawer data and registration
 * @author B
 *
 */
public class DrawerManager {
	
	private static DrawerManager mInstance;
	private ArrayList<DrawerData> Drawers;
	
	public static DrawerManager getInstance()
	{
		if(mInstance==null)
		{
			mInstance = new DrawerManager();
		}
		
		return mInstance;
	}
	
	private DrawerManager()
	{
		Drawers = new ArrayList<DrawerData>();
	}
	
	public String[] getDrawerNames()
	{
		String[] nameList = new String[Drawers.size()];
		int i=0;
		for (DrawerData drawer : Drawers) {
			nameList[i] = drawer.displayedText;
			i++;
		}		
		return nameList;		
	}
	
	public void SelectDrawer(FragmentActivity activity ,int position){
		MyFragment mf =null;
		
		try {
			mf = (MyFragment) Drawers.get(position).fragmentClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		if (mf!=null)
		{
			FragmentManager fragmentManager = activity.getSupportFragmentManager();
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							mf.CreateFragment()).commit();
		}
	}
	
	public void AddDrawer(String drawerName, Class<?> fragmentClass)
	{
		Drawers.add(new DrawerData(drawerName, fragmentClass));
	}
	
	public void ClearDrawer()
	{
		Drawers.clear();
	}
	
	
	

}
