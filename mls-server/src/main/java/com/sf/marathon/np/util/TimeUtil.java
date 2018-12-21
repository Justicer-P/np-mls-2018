package com.sf.marathon.np.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private TimeUtil() {

	}

	public static Date now() {
		return new Date();
	}

	public static String getDateYYDD() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		return sdf.format(now());
	}

	public static Date add(Date date, int min) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, min);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(TimeUtil.getDateYYDD());
	}
	
}
