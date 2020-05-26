package jscolendar.routes.students;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.StudentResponse;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.models.Student;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import java.text.MessageFormat;

import static jscolendar.util.datePickerContent.getContent;

public class StudentDetails extends BorderPane {
  private final Student student;
  @FXML private VBox subjects;
  @FXML private Label title, name, userName, promo;
  @FXML private JFXComboBox<Label> select;
  @FXML private StackPane datePickerPane;

  public StudentDetails (Student student) {
    this.student = student;
    FXUtil.loadFXML("/fxml/students/StudentDetails.fxml", this, this, I18n.getBundle());
  }

  @SuppressWarnings("Duplicates")
  @FXML
  private void initialize () {
     title.setText(I18n.get("calendar.title.student") + " \"" + student.firstNameProperty().get() +
       " " + student.lastNameProperty().get() + '\"');
     name.setText(student.firstNameProperty().get() + " " + student.lastNameProperty().get());
     promo.setText(student.classNameProperty().get());

    StudentsApi apiInstance = new StudentsApi();
    FXApiService<Integer, StudentResponse> userService = new FXApiService<>(apiInstance::studentsIdGet);
    userService.setOnSucceeded(event -> {
      var response = userService.getValue().getStudent();
      userName.setText(response.getUsername());

      response.getSubjects().forEach(subject ->
        subjects.getChildren().add(
          new Label(" - ".concat(subject.getName()).concat(", ").concat(subject.getGroup()))));

      subjects.getChildren().add(new Label(MessageFormat.format(
        I18n.get("calendar.details.student.enseign.secondLine"), response.getTotalHours())));
    });

    userService.setOnFailed(_e ->
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(userService.getException()))));

    userService.setRequest(student.getId());
    userService.start();

    FXApiService<Pair<Integer, Integer>, Occupancies> service = new FXApiService<>(request ->
      apiInstance.studentsIdOccupanciesGet(student.getId(), request.getKey(), request.getValue(), 0));
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
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new EditStudent()));
  }
}
