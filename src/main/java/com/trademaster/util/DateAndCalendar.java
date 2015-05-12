package com.trademaster.util;

import java.util.Calendar;
import java.util.Date;

public final class DateAndCalendar {
	
	//Convert Date to Calendar
	public static Calendar dateToCalendar(Date date) {
 
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
 
	}
 
	//Convert Calendar to Date
	public static Date calendarToDate(Calendar calendar) {
		return calendar.getTime();
	}

}
