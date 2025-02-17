package jscolendar.routes;

import com.calendarfx.view.CalendarView;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.RolestudentApi;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.UserSession;
import jscolendar.components.CalendarComponent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.models.StudentSubject;
import jscolendar.routes.subjects.StudentSubjectDetails;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HomeStudent extends VBox {


  private Integer id;
  @FXML
  private VBox graph, container;
  @FXML
  private Label header, graphText;
  @FXML
  private HBox body;
  @FXML
  private JFXListView<VBox> modificationsContent;
  @FXML
  private JFXListView<Label> linksContent, contactContent;

  @FXML
  public void initialize() {
    this.id = UserSession.getInstance().getUser().getId();
    setHeader();
    setLastModifications();
    CalendarView calendarView = setCalendar();
    setProgressContent();
    setContactsAndLinks();

    body.getChildren().addAll(calendarView);
  }


  private CalendarView setCalendar() {
    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var subjectApi = new SubjectsApi();
    service = new FXApiService<>(request ->
      subjectApi.subjectsIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));
    var manager = new CalendarDataManager(new Calendar(), service);
    CalendarComponent calendarComponent = new CalendarComponent(manager);
    CalendarView calendarView = calendarComponent.getView();
    calendarView.showDayPage();
    return calendarView;
  }

  private void setProgressContent() {
    ProgressBar bar = new ProgressBar();
    int pourcent = 50;//todo make link with API when api can answer this request
    bar.setProgress(pourcent * 0.01);
    graphText.setText(MessageFormat.format(I18n.get("home.student.graph"), pourcent));
    graph.getChildren().addAll(bar);
  }

  private void setContactsAndLinks() {
    var roleStudentApi = new RolestudentApi();
    try {
      var results = roleStudentApi.studentsIdSubjectsGet(id);
      for (StudentSubjectsSubjects result : results.getSubjects()) {
        for (TeacherSubjectsTeachers teacher : result.getTeachers()) {
          Label contact = new Label(teacher.getLastName() + " " + teacher.getFirstName());
          contact.setOnMouseClicked(event -> {
            container.getChildren().clear();
            container.getChildren().add(new StudentSubjectDetails(new StudentSubject(result)));

          });
          contactContent.getItems().add(contact);
        }
        Label link = new Label(result.getName());
        link.setOnMouseClicked(event -> {
          container.getChildren().clear();
          container.getChildren().add(new StudentSubjectDetails(new StudentSubject(result)));
        });
        linksContent.getItems().add(link);
      }
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

  private void setLastModifications() {
    var roleStudentApi = new RolestudentApi();
    try {
      ProfileRecentModifications result = roleStudentApi.profileLastOccupanciesModificationsGet();
      for (ProfileRecentModificationsModifications modifications : result.getModifications()) {
        createModificationField(modifications);
      }
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

  private void createModificationField(ProfileRecentModificationsModifications modifications) {
    VBox body = new VBox();
    HBox header = new HBox();
    Label title = new Label(modifications.getOccupancy().getSubjectName());
    Date priviousStartHour = new Date((long) modifications.getOccupancy().getPreviousOccupancyStart() * 1000);
    Date priviousEndHour = new Date((long) modifications.getOccupancy().getPreviousOccupancyEnd() * 1000);
    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    SimpleDateFormat year = new SimpleDateFormat("dd/MM/yyyy");
    year.setTimeZone(TimeZone.getTimeZone("GMT"));

    String startTime = sdf.format(priviousStartHour);
    String endTime = sdf.format(priviousEndHour);
    String priviousDate = year.format(priviousStartHour);

    Date newStartHour = new Date((long) modifications.getOccupancy().getOccupancyStart() * 1000);
    Date newEndHour = new Date((long) modifications.getOccupancy().getOccupancyEnd() * 1000);
    String newStart = sdf.format(newStartHour);
    String newEnd = sdf.format(newEndHour);
    String newDate = year.format(newStartHour);
    Date modificationDate = new Date((long) modifications.getModificationTimestamp());
    String modificationDateString = year.format(modificationDate);

    Label date = new Label(modificationDateString);


    String endOfText = "";
    switch (modifications.getModificationType()) {
      case EDIT:
        endOfText = I18n.get("home.student.modification.item.modif") + MessageFormat.format(I18n.get("home.student.modification.modif.date"), newStart, newEnd, newDate);
        break;
      case CREATE:
        endOfText = I18n.get("home.student.modification.item.create");
        break;
      case DELETE:
        endOfText = I18n.get("home.student.modification.item.supp");
        break;
      default:
        break;
    }

    Label text = new Label(MessageFormat.format(I18n.get("home.student.modification.item"), priviousDate, startTime, endTime) + endOfText);


    header.getChildren().addAll(title, date);

    body.getChildren().addAll(header, text);
    modificationsContent.getItems().addAll(body);
  }

  private void setHeader() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    int start = (int) timestamp.getTime() / 1000;
    long duration = (7 * 24 * 60 * 60);
    Integer end = (int) (timestamp.getTime() / 1000 + duration);
    RolestudentApi apiInstance = new RolestudentApi();
    OccupanciesOccupancies nextOccupancy = new OccupanciesOccupancies();
    nextOccupancy.setStart(end);
    try {
      Occupancies result = apiInstance.studentsIdOccupanciesGet(id, start, end, 15);
      for (OccupanciesDays occupanciesDays : result.getDays()) {
        for (OccupanciesOccupancies occupancies : occupanciesDays.getOccupancies()) {
          if (occupancies.getStart() == null | occupancies.getStart() < nextOccupancy.getStart()) {
            nextOccupancy = occupancies;
          }
        }
      }
    } catch (ApiException e) {
      System.err.println("Exception when calling RolestudentApi#studentsIdOccupanciesGet");
      e.printStackTrace();
    }

    Date remaining = new Date((long) (nextOccupancy.getStart() * 1000) - start);
    Date hourStart = new Date((long) nextOccupancy.getStart() * 1000);
    Date hourEnd = new Date((long) nextOccupancy.getEnd() * 1000);
    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    String startTime = sdf.format(hourStart);
    String endTime = sdf.format(hourEnd);
    String remainingTime = sdf.format(remaining);

    String headerContent = MessageFormat.format(I18n.get("home.student.header.first"), remainingTime,
      nextOccupancy.getClassroomName(), startTime, endTime) + '\n' + MessageFormat.format(I18n.get("home.student.header.second")
      , nextOccupancy.getClassroomName(), nextOccupancy.getTeacherName());
    header.setText(headerContent);
    header.setWrapText(true);
  }

}
