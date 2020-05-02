package jscolendar.util;

import java.time.Month;

public class Date {
  int day, month, year, beginHour, beginMinute, duration;

  public Date(int day, int month, int year, int beginHour, int beginMinute, int duration) {
    this.day = day;
    this.month = month;
    this.year = year;
    this.beginHour = beginHour;
    this.beginMinute = beginMinute;
    this.duration = duration;
  }

  @Override
  public String toString() {
    return day +
      " " + Month.of(month) +
      "." + beginHour +
      ":" + beginMinute;
  }

  //// Get the number of days in that month
  //YearMonth yearMonthObject = YearMonth.of(1999, 2);
  //int daysInMonth = yearMonthObject.lengthOfMonth(); //28

}
