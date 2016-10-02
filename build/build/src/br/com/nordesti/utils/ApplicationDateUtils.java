package br.com.nordesti.utils;

import java.sql.Date;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class ApplicationDateUtils {

  public static Date asDate(LocalDate localDate) {
	return java.sql.Date.valueOf(localDate);
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalTime asLocalTime(Time time) {
    return Instant.ofEpochMilli(time.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
  }
  
  public static Time asTime(LocalTime localTime) {
    return java.sql.Time.valueOf(localTime);
  }
}
