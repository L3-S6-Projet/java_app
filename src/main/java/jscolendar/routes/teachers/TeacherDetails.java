package jscolendar.routes.teachers;

import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.TeacherResponse;
import io.swagger.client.model.TeacherResponseTeacherServices;
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
import jscolendar.models.Teacher;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.text.MessageFormat;

import static jscolendar.util.datePickerContent.getContent;


public class TeacherDetails extends BorderPane {
  private final Teacher teacher;
  @FXML private Label title, name, userName, email, phoneNumber, rank;
  @FXML private VBox serviceDetails;
  @FXML private JFXComboBox<Label> select;
  @FXML private StackPane datePickerPane;

  public TeacherDetails (Teacher teacher) {
    this.teacher = teacher;
    FXUtil.loadFXML("/fxml/teachers/TeacherDetails.fxml", this, this, I18n.getBundle());
  }

  @SuppressWarnings("Duplicates")
  @FXML
  private void initialize () {
    select.getSelectionModel().selectLast();
    title.setText(I18n.get("calendar.title.enseinant") + " \"" + teacher.firstNameProperty().get() +
      " " + teacher.lastNameProperty().get() + '\"');
    name.setText(teacher.firstNameProperty().get() + " " + teacher.lastNameProperty().get());
    email.setText(teacher.emailProperty().get());
    phoneNumber.setText(teacher.phoneNumberProperty().get());

    TeacherApi apiInstance = new TeacherApi();
    FXApiService<Integer, TeacherResponse> teacherService = new FXApiService<>(apiInstance::teachersIdGet);

    teacherService.setOnSucceeded(_e -> {
      var response = teacherService.getValue().getTeacher();
      userName.setText(response.getUsername());
      rank.setText("Professeur"); // @TODO :: use response.getRank(), Rank model and toString method

      response.getServices().forEach(service ->
        serviceDetails.getChildren().add(new Label(buildServiceString(service)))
      );

      serviceDetails.getChildren().add(new Label(MessageFormat.format(
        I18n.get("calendar.details.services.value"), response.getTotalService())));
    });

    teacherService.setOnFailed(_e ->
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(teacherService.getException()))));

    teacherService.setRequest(teacher.getId());
    teacherService.start();


    FXApiService<Pair<Integer, Integer>, Occupancies> service = new FXApiService<>(request ->
      apiInstance.teachersIdOccupanciesGet(teacher.getId(), request.getKey(), request.getValue(), 0));

    var manager = new CalendarDataManager(new Calendar(), service);
    var calendarComponent = new CalendarComponent(manager);
    var calendarView = calendarComponent.getView();

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

  private String buildServiceString (TeacherResponseTeacherServices service) {
    var sb = new StringBuilder();
    sb.append(I18n.get("calendar.details.teacher.firstPart")).append(service.getPropertyClass())
      .append(',').append(I18n.get("calendar.details.teacher.secondPart")).append(",");
    if (service.getCm() != null) {
      sb.append(" ").append(service.getCm()).append(I18n.get("calendar.details.teacher.hours"))
        .append(" ").append(I18n.get("calendar.details.teacher.cm")).append(',');
    }
    if (service.getTp() != null) {
      sb.append(" ").append(service.getTp()).append(I18n.get("calendar.details.teacher.hours"))
        .append(" ").append(I18n.get("calendar.details.teacher.tp")).append(',');
    }
    if (service.getTd() != null) {
      sb.append(" ").append(service.getTd()).append(I18n.get("calendar.details.teacher.hours"))
        .append(" ").append(I18n.get("calendar.details.teacher.td")).append(',');
    }
    if (service.getProject() != null) {
      sb.append(" ").append(service.getProject())
        .append(I18n.get("calendar.details.teacher.hours")).append(" ")
        .append(I18n.get("calendar.details.teacher.projet")).append(',');
    }
    if (service.getAdministration() != null) {
      sb.append(" ").append(service.getAdministration())
        .append(I18n.get("calendar.details.teacher.hours")).append(" ")
        .append(I18n.get("calendar.details.teacher.admin")).append(',');
    }
    if (service.getExternal() != null) {
      sb.append(" ").append(service.getExternal())
        .append(I18n.get("calendar.details.teacher.hours")).append(" ")
        .append(I18n.get("calendar.details.teacher.extern")).append(',');
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append('.');

    return sb.toString();
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void editButton () {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new EditTeacher()));
  }
}
