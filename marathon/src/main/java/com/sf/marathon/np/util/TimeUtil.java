package com.sf.marathon.np.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.elasticsearch.common.joda.time.DateTime;

public class TimeUtil {

	private TimeUtil() {

	}

	public static Date now() {
		return new Date();
	}

	public static String getDateymd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(now());
	}

	public static Date add(Date date, int min) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, min);
		return cal.getTime();
	}
	
	public static String formatLong(String parttern, Long t) {
		DateTime dateTime = new DateTime(t);
		return dateTime.toString(parttern);
	}
	
	private static Date convertString2Date(String parttern, String dateString) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.parse(dateString);
	}
	
	public static String convertDate2String(String parttern, Date dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.format(dateString);
	}

	public static List<String> getIntervalTimeList(String start, String end, int interval) throws ParseException {
		Date startDate = convertString2Date("yyyy-MM-dd HH:mm", start);
		Date endDate = convertString2Date("yyyy-MM-dd HH:mm", end);
		List<String> list = new ArrayList<>();
		while (startDate.getTime() <= endDate.getTime()) {
			list.add(convertDate2String("yyyy-MM-dd HH:mm", startDate));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			calendar.add(Calendar.MINUTE, interval);
			if (calendar.getTime().getTime() > endDate.getTime()) {
				if (!startDate.equals(endDate)) {
					list.add(convertDate2String("yyyy-MM-dd HH:mm", endDate));
				}
				startDate = calendar.getTime();
			} else {
				startDate = calendar.getTime();
			}

		}
		return list;
	}

}
