package com.example.moslemcalendar;

import android.content.res.Resources;

public class Globals {
	
	public static int REQ_CODE_EVENT_FORM =1;
	
	public enum EventType {
		ALL, TODO, HISTORY
	}

	public enum CalendarType {
		ISLAMIC, ISO
	}

	private static int[] DaysName_FirstSunday = { R.string.Sunday,
			R.string.Monday, R.string.Tuesday, R.string.Wednesday,
			R.string.Thursday, R.string.Friday, R.string.Saturday };

	private static int[] DaysName_FirstMonday = { R.string.Monday,
			R.string.Tuesday, R.string.Wednesday, R.string.Thursday,
			R.string.Friday, R.string.Saturday, R.string.Sunday };


	private static int[] Months = { R.string.january, R.string.february,
			R.string.march, R.string.april, R.string.may, R.string.june,
			R.string.july, R.string.august, R.string.september,
			R.string.october, R.string.november, R.string.december };
	
	private static int[] IslamicMonths = { R.string.muharram, R.string.safar,
		R.string.rabialawwal, R.string.rabialakhir, R.string.jumadawal, R.string.jumadakhir,
		R.string.rajab, R.string.shaban, R.string.ramadhan,
		R.string.shawwal, R.string.dhualqadda, R.string.dhualhijja };

	public static String[] getDaysName(boolean startMonday, Resources res,
			boolean shortName) {
		int charLength = shortName ? 3 : 0;
		if (startMonday) {
			return getStringFromResourcesId(DaysName_FirstMonday, res,
					charLength);
		} else

			return getStringFromResourcesId(DaysName_FirstSunday, res,
					charLength);
	}

	public static String[] getDaysName(Resources res, boolean shortName) {
		return getDaysName(false, res, shortName);
	}

	public static String[] getMonthNames(Resources res, CalendarType calendarType) {
		if(calendarType==CalendarType.ISO)
		return getStringFromResourcesId(Months, res);
		else
			return getStringFromResourcesId(IslamicMonths, res);
	}

	public static String[] getStringFromResourcesId(int[] ids, Resources res) {
		return getStringFromResourcesId(ids, res, 0);
	}

	public static String[] getStringFromResourcesId(int[] ids, Resources res,
			int charLength) {
		String[] results = new String[ids.length];

		for (int i = 0; i < ids.length; i++) {
			results[i] = res.getString(ids[i]);
			if (charLength > 0)
				results[i] = results[i].substring(0, charLength);
		}

		return results;
	}
}
