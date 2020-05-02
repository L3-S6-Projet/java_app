package jscolendar.util;

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
    return "Date{" +
      "day=" + day +
      ", month=" + month +
      ", beginHour=" + beginHour +
      ", beginMinute=" + beginMinute +
      '}';
  }
}
