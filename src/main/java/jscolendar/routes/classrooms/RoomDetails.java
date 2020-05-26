package jscolendar.routes.classrooms;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.api.ClassroomApi;
import io.swagger.client.model.Occupancies;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.models.Classroom;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class RoomDetails extends BorderPane {
  private final Classroom room;
  @FXML private StackPane datePickerPane;
  @FXML private Label title, name, capacity;
  @FXML private JFXComboBox<Label> select;

  public RoomDetails (Classroom room) {
    this.room = room;
    FXUtil.loadFXML("/fxml/classrooms/RoomDetails.fxml", this, this, I18n.getBundle());
  }

  @SuppressWarnings("Duplicates")
  @FXML
  private void initialize () {
    title.setText(I18n.get("calendar.title.room") + " \"" + room.nameProperty().get() + '\"');
    name.setText(room.nameProperty().get());
    capacity.setText(String.valueOf(room.capacityProperty().get()));

    ClassroomApi apiInstance = new ClassroomApi();

    FXApiService<Pair<Integer, Integer>, Occupancies> service = new FXApiService<>(request ->
      apiInstance.classroomsIdOccupanciesGet(room.getId(), request.getKey(), request.getValue(), 0));

    var manager = new CalendarDataManager(new Calendar(), service);
    CalendarComponent calendarComponent = new CalendarComponent(manager);
    CalendarView calendarView = calendarComponent.getView();

    select.getSelectionModel().select(2);

    select.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->  {
      switch (newValue.getId()) {
        case "day":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.DAY_VIEW) return;
          calendarView.showDayPage(); break;
        case "week":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.WEEK_VIEW) return;
          calendarView.showWeekPage(); break;
        case "month":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.MONTH_VIEW) return;
          calendarView.showMonthPage(); break;
      }
    });

    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setDialogParent(datePickerPane);
    jfxDatePicker.setOnAction(event -> calendarView.getSelectedPage().setDate(jfxDatePicker.getValue()));
    Node datePicker = getContent(jfxDatePicker);

    if (datePicker != null) {
      StackPane.setAlignment(datePicker, Pos.BOTTOM_LEFT);
      datePickerPane.getChildren().add(datePicker);
    }

    setCenter(calendarView);
    BorderPane.setMargin(calendarView, new Insets(0, 0, 15, 0));
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void editButton () {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new EditRoom()));
  }
}
