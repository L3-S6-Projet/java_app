package jscolendar.routes;

import com.calendarfx.view.CalendarView;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.RolestudentApi;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.OccupanciesDays;
import io.swagger.client.model.OccupanciesOccupancies;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HomeStudent {


  private final Integer id = 2;//todo set id value
  @FXML
  private VBox graph;
  @FXML
  private Label header, graphText;
  @FXML
  private HBox body;
  @FXML
  private JFXListView<VBox> linksContent, modificationsContent, contactContent;

  @FXML
  public void initialize() {
    ///profile/last-occupancies-modifications
    setHeader();


    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var subjectApi = new SubjectsApi();
    service = new FXApiService<>(request ->
      subjectApi.subjectsIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));
    var manager = new CalendarDataManager(new Calendar(), service);
    CalendarComponent calendarComponent = new CalendarComponent(manager);
    CalendarView calendarView = calendarComponent.getView();
    //todo make calendar in daily format

    ProgressBar bar = new ProgressBar();
    bar.setProgress(0.63);
    graph.getChildren().addAll(bar);
    body.getChildren().addAll(calendarView);


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
