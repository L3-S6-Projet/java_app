package jscolendar.events;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.layout.Region;

public class ModalEvent extends Event {
  public final static EventType<ModalEvent> OPEN = new EventType<>(Event.ANY, "OPEN");
  public final static EventType<ModalEvent> CLOSE = new EventType<>(Event.ANY,"CLOSE");

  private Region modalContent;

  public ModalEvent (EventType<? extends Event> eventType) {
    super(eventType);
  }

  public ModalEvent (EventType< ? extends Event> eventType, Region content) {
    this(eventType);
    this.modalContent = content;
  }

  public Region getContent () {
    return modalContent;
  }
}
