package com.example.moslemcalendar;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.moslemcalendar.models.Event;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper {
	static final String DB_NAME = "moslem_calendar.db";
	static final int DB_VERSION = 3;
	
	static final String C_ID = BaseColumns._ID;
	static final String C_MONTH = "month";
	static final String C_DAY = "day";
	static final String C_YEAR = "year";
	static final String C_COLOR = "color";
	static final String C_DESC = "desc";
	
	static final String T_Event = "event";
	
	private ContentDb dbHelper;
	private SQLiteDatabase db;
	
	
	public DbHelper(Context context)
	{
		dbHelper = new ContentDb(context);
	}
	
	public void AddEvent(Event event)
	{
		db = dbHelper.getWritableDatabase();
		ContentValues insertVal = new ContentValues();
		insertVal.put(C_DAY, event.getDay());
		insertVal.put(C_COLOR, event.getColorHex());
		insertVal.put(C_MONTH, event.getMonth());
		insertVal.put(C_YEAR, event.getYear());
		insertVal.put(C_DESC, event.getName());
		
		db.insert(T_Event, null, insertVal);
		db.close();
	}
	
	public void DeleteEvent(Event event)
	{
		db = dbHelper.getWritableDatabase();
	
		db.delete(T_Event, C_ID+"="+event.getId(), null);
		db.close();
	}
	
	public void DeleteEvent(int eventId)
	{
		db = dbHelper.getWritableDatabase();
	
		db.delete(T_Event, C_ID+"="+eventId, null);
		db.close();
	}
	
	
	public Event getEvent(int eventId)
	{
		Event e = new Event();
		String s = "select * from " + T_Event + " where "+C_ID+"="+eventId;
		Cursor cursor = db.rawQuery(s, null);
		return e;
		
	}
	
	public ArrayList<Event> getEvents(){
		ArrayList<Event> list = new ArrayList<Event>();
		db = dbHelper.getReadableDatabase();
		String s = "select * from "+T_Event;
		Cursor cursor = db.rawQuery(s, null);
		CursorToCollections(cursor, T_Event, list);
		db.close();
		return list;
	}	
	
	public ArrayList<Event> getEvents(int month, int year){
		ArrayList<Event> list = new ArrayList<Event>();
		db = dbHelper.getReadableDatabase();	
		String s = "select * from " + T_Event + " where "
				+ C_MONTH + "=" + month + " AND (" + C_YEAR + " = " + year +
				" OR "+C_YEAR+" IS NULL OR "+C_YEAR+" = \"\" OR "+C_YEAR+" = 0)"
		+ " order by " + C_ID + " asc";
		Cursor cursor = db.rawQuery(s, null);
		CursorToCollections(cursor, T_Event, list);
		db.close();
		return list;
	}

	
	private <T> void CursorToCollections(Cursor cursor, String tableName, ArrayList<T> collections)
	{
		if(cursor.moveToFirst())
		{
			switch (tableName) {
			case T_Event:			
					do{
						Event data  = new Event();
						data.setId(cursor.getInt(0));
						data.setDay(cursor.getInt(1));
						data.setMonth(cursor.getInt(2));
						data.setYear(cursor.getInt(3));
						data.setColor(cursor.getString(4));
						data.setName(cursor.getString(5));
						collections.add((T) data);
					}while(cursor.moveToNext());					
				break;
	
			default:
				break;
			}
		
		if(!cursor.isClosed())
			cursor.close();
		}
	}
	
	private class ContentDb extends SQLiteOpenHelper
	{

		public ContentDb(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			String query = "create table "   +  
                    T_Event    +" ("+
					C_ID       +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ 
					C_DAY      +" INTEGER, "+ 
					C_MONTH    +" INTEGER, "+
					C_YEAR     +" INTEGER, "+
					C_COLOR    +" text, "+
					C_DESC     +" text) ";
	
			db.execSQL(query);
			
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists "+T_Event);
			onCreate(db);
			
		}
		
	}

}
