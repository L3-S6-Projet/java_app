package jscolendar.routes.calendar;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import io.swagger.client.api.OccupanciesApi;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.Occupancies;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.UserSession;
import jscolendar.components.CalendarComponent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import java.net.URL;
import java.util.ResourceBundle;

public class CalendarRoute implements Initializable {
  @FXML private VBox container;
  @FXML private Label dateLabel, dateSubtitleLabel;
  @FXML private JFXComboBox<Label> select;
  @FXML private JFXButton forward, back;
  private CalendarComponent calendarComponent;
  private CalendarView calendarView;

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    var user = UserSession.getInstance().getUser();
    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;

    switch (user.getKind()) {
      case ADM:
        var occupanciesApi = new OccupanciesApi();
        service = new FXApiService<>(request ->
          occupanciesApi.occupanciesGet(request.getKey(), request.getValue(), 0));
        break;
      case STU:
        var studentsApi = new StudentsApi();
         service = new FXApiService<>(request ->
           studentsApi.studentsIdOccupanciesGet(user.getId(), request.getKey(), request.getValue(), 0));
        break;
      case TEA:
        var teacherApi = new TeacherApi();
        service = new FXApiService<>(request ->
          teacherApi.teachersIdOccupanciesGet(user.getId(), request.getKey(), request.getValue(), 0));
        break;
    }

    var manager = new CalendarDataManager(new Calendar(), service);
    calendarComponent = new CalendarComponent(manager);
    calendarView = calendarComponent.getView();

    forward.disableProperty().bind(manager.isRunning);
    back.disableProperty().bind(manager.isRunning);
    dateLabel.textProperty().bind(calendarComponent.currentDateProperty);
    dateSubtitleLabel.textProperty().bind(calendarComponent.currentDateSubtitleProperty);

    calendarView.selectedPageProperty().addListener((observable, oldValue, newValue) -> {
      switch (newValue.getPrintViewType()) {
        case DAY_VIEW: select.getSelectionModel().select(0); break;
        case WEEK_VIEW: select.getSelectionModel().select(1); break;
        case MONTH_VIEW: select.getSelectionModel().select(2); break;
      }
      calendarComponent.updateCurrentDate((calendarComponent.getDate()));
    });

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

    container.getChildren().add(calendarView);
    calendarView.getStylesheets().add("calendar.css");
  }

  @FXML
  private void goForward () {
    calendarView.getSelectedPage().goForward();
  }

  @FXML
  private void goBack () {
    calendarView.getSelectedPage().goBack();
  }

  @FXML
  private void goToday () {
    calendarView.getSelectedPage().goToday();
  }
}
