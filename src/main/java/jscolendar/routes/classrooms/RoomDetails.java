package jscolendar.routes.classrooms;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.ClassroomApi;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.ClassroomGetResponse;
import io.swagger.client.model.Occupancies;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.UserSession;
import jscolendar.components.CalendarComponent;
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class RoomDetails extends StackPane {


  private final Integer id;


  @FXML
  private HBox header;
  @FXML
  private VBox subLeft, calendar;
    @FXML
  private Label title, name, capacity;
  @FXML
  private JFXComboBox<Label> select;

  public RoomDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/classrooms/RoomDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {

    ClassroomApi apiInstance = new ClassroomApi();
    ClassroomGetResponse result = null;

    try {
      result = apiInstance.classroomsIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.room") + " \"" + result.getClassroom().getName() + '\"');
      name.setText(result.getClassroom().getName());
      capacity.setText(String.valueOf(result.getClassroom().getCapacity()));
    }
    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> {
      System.out.println(jfxDatePicker.getValue());

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);



    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var promoAPI = new ClassroomApi();
    service = new FXApiService<>(request ->
      promoAPI.classroomsIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));


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

    calendar.getChildren().add(calendarView);
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void selectCalendarType () {
  }

  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditRoom())
    );
  }


}
