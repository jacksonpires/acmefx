package br.com.nordesti.utils;

import java.sql.Time;
import java.time.LocalTime;

public class TimeUtils {

	public static Time asTime(LocalTime localTime) {
		Time sqlTime = new Time(localTime.getHour(), localTime.getMinute(), localTime.getSecond());
		return sqlTime;
	}
	
	public static LocalTime asLocalTime(Time time) {
		return time.toLocalTime();
	}

}
