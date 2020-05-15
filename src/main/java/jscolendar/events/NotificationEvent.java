package jscolendar.events;

import javafx.event.Event;
import javafx.event.EventType;

public class NotificationEvent extends Event {
  public final static EventType<NotificationEvent> MESSAGE = new EventType<>(Event.ANY, "MESSAGE");
  public final String message;

  public NotificationEvent (String message) {
    super(MESSAGE);
    this.message = message;
  }
}
