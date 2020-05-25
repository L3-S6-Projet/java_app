package jscolendar.models;

import com.calendarfx.model.LoadEvent;
import io.swagger.client.model.Occupancies;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.util.Pair;
import jscolendar.events.ScolendarCalendarEvent;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CalendarDataManager implements EventHandler<LoadEvent> {
  private final Calendar calendar;
  private final FXApiService<Pair<Integer, Integer>, Occupancies> service;
  private final Set<Integer> loadedEntryIds = new HashSet<>();
  public final BooleanProperty isRunning = new SimpleBooleanProperty();

  public CalendarDataManager (Calendar calendar, FXApiService<Pair<Integer, Integer>, Occupancies> service) {
    this.calendar = calendar;
    this.service = service;
    this.isRunning.bind(service.runningProperty());

    initService();
  }

  private void initService () {
    service.setOnSucceeded(_e -> {
      var occupancies = service.getValue().getDays()
        .stream()
        .flatMap(occupanciesDays -> occupanciesDays.getOccupancies().stream())
        .map(Occupancy::new)
        .filter(this::isNotLoaded)
        .collect(Collectors.toList());

      if (occupancies.isEmpty()) return;

      calendar.startBatchUpdates();
      occupancies.stream().map(OccupancyEntryMapper::map).forEach(calendar::addEntry);
      calendar.stopBatchUpdates();
    });

    service.setOnFailed(_e -> calendar.fireEvent(
      new ScolendarCalendarEvent(
        ScolendarCalendarEvent.CALENDAR_LOAD_ERROR, calendar, APIErrorUtil.getErrorMessage(service.getException()))));
  }

  public Calendar getCalendar () {
    return calendar;
  }

  @Override
  public void handle (LoadEvent event) {
    if (service.isRunning()) return;

    int start = (int) event.getStartDate().toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.MIN);
    int end = (int) event.getEndDate().toEpochSecond(LocalTime.NOON, ZoneOffset.MAX);

    service.setRequest(new Pair<>(start, end));
    service.restart();
  }

  private boolean isNotLoaded (Occupancy occupancy) {
    return loadedEntryIds.add(occupancy.id);
  }
}
