package jscolendar.events;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import javafx.event.EventType;
import jscolendar.models.Occupancy;

public class ScolendarCalendarEvent extends CalendarEvent {
  public static final EventType<ScolendarCalendarEvent> CALENDAR_LOAD_ERROR = new EventType<>(CalendarEvent.ANY, "CALENDAR_LOAD_ERROR");
  private String message = "";

  public ScolendarCalendarEvent (EventType<? extends CalendarEvent> eventType, Calendar calendar, String message) {
    super(eventType, calendar);
    this.message = message;
  }

  public ScolendarCalendarEvent (EventType<? extends CalendarEvent> eventType, Calendar calendar, Entry<Occupancy> entry) {
    super(eventType, calendar, entry);
  }

  public String getMessage () {
    return message;
  }
}
