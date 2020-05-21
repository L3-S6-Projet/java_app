package jscolendar.components;

import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.LoadEvent;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayViewBase;
import com.calendarfx.view.page.DayPage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jscolendar.events.NotificationEvent;
import jscolendar.events.ScolendarCalendarEvent;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.I18n;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

public class CalendarComponent {
  private final CalendarView calendarView = new CalendarView();
  private final CalendarDataManager manager;

  public final StringProperty currentDateProperty = new SimpleStringProperty();
  public final StringProperty currentDateSubtitleProperty = new SimpleStringProperty();


  public CalendarComponent (CalendarDataManager manager) {
    this.manager = manager;
    configure();
  }

  public void configure () {
    VBox.setVgrow(calendarView, Priority.ALWAYS);
    manager.getCalendar().setStyle("custom");

    var calendarSource = new CalendarSource();
    calendarSource.getCalendars().add(manager.getCalendar());
    calendarView.getCalendarSources().clear();
    calendarView.getCalendarSources().add(calendarSource);

    calendarView.getDayPage().setShowDayPageLayoutControls(false);
    calendarView.getDayPage().setShowNavigation(false);
    calendarView.getDayPage().setShowDate(false);
    calendarView.getDayPage().getDetailedDayView().setShowAllDayView(false);
    calendarView.getDayPage().setDayPageLayout(DayPage.DayPageLayout.DAY_ONLY);
    calendarView.getDayPage().getDetailedDayView().setEarlyLateHoursStrategy(
      DayViewBase.EarlyLateHoursStrategy.HIDE);
    calendarView.getDayPage().getDetailedDayView().setShowScrollBar(false);

    calendarView.getMonthPage().setShowNavigation(false);
    calendarView.getMonthPage().setShowDate(false);

    calendarView.getWeekPage().getDetailedWeekView().getWeekView().setHoursLayoutStrategy(DayViewBase.HoursLayoutStrategy.FIXED_HOUR_COUNT);
    calendarView.getWeekPage().getDetailedWeekView().getWeekView().setVisibleHours(15);
    calendarView.getWeekPage().getDetailedWeekView().setShowAllDayView(false);
    calendarView.getWeekPage().setShowNavigation(false);
    calendarView.getWeekPage().setShowDate(false);
    calendarView.getWeekPage().getDetailedWeekView().showTimeScaleViewProperty().setValue(true);
    calendarView.getWeekPage().getDetailedWeekView().getTimeScaleView()
      .earlyLateHoursStrategyProperty().set(DayViewBase.EarlyLateHoursStrategy.HIDE);
    calendarView.getWeekPage().getDetailedWeekView().showScrollBarProperty().setValue(false);
    calendarView.getWeekPage().setSelectionMode(SelectionMode.SINGLE);

    calendarView.showPageToolBarControlsProperty().setValue(false);
    calendarView.setShowToolBar(false);

    manager.getCalendar().addEventHandler(event -> {
      event.consume();
      if (event.getEventType() == ScolendarCalendarEvent.CALENDAR_LOAD_ERROR)
        calendarView.fireEvent(new NotificationEvent(((ScolendarCalendarEvent) event).getMessage()));
    });

    calendarView.dateProperty().addListener((observable, oldValue, newValue) -> updateCurrentDate(newValue));
    calendarView.getMonthPage().addEventFilter(LoadEvent.LOAD, manager);

    calendarView.showMonthPage();
    calendarView.setDate(LocalDate.now());
    updateCurrentDate(calendarView.getDate());
  }

  public CalendarView getView () {
    return calendarView;
  }

  public LocalDate getDate () {
    return calendarView.getDate();
  }

  public void updateCurrentDate (LocalDate date) {
    switch (calendarView.selectedPageProperty().get().getPrintViewType()) {
      case MONTH_VIEW:
        currentDateProperty.set(capitalize(DateTimeFormatter.ofPattern("MMMM").format(date)));
        currentDateSubtitleProperty.set(DateTimeFormatter.ofPattern("yyyy").format(date));
        break;
      case WEEK_VIEW:
        currentDateProperty.set(MessageFormat.format(
          I18n.get("calendar.week"), date.get(WeekFields.ISO.weekOfYear())));
        currentDateSubtitleProperty.set(capitalize(calendarView.getMonthPage().getDateTimeFormatter().format(date)));
        break;
      case DAY_VIEW:
        currentDateProperty.set(capitalize(DateTimeFormatter.ofPattern("EEEE d").format(date)));
        currentDateSubtitleProperty.set(capitalize(calendarView.getMonthPage().getDateTimeFormatter().format(date)));
        break;
    }
  }

  private String capitalize (String string) {
    return string.substring(0, 1).toUpperCase().concat(string.substring(1));
  }
}
