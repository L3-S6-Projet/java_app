package jscolendar.routes;

import com.calendarfx.view.CalendarView;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.Occupancies;
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

public class HomeStudent {


  private final Integer id = 2;//todo set id value
  @FXML
  private VBox container, graph, lastModifications;
  @FXML
  private Label header, graphText;
  @FXML
  private HBox body;

  @FXML
  public void initialize() {
    ///profile/last-occupancies-modifications
    String headerContent = I18n.get("home.student.header.first") + " " + "->nb<-" + I18n.get("home.student.header.minutes") + " " +
      I18n.get("home.student.header.en") + "->la salle<-" +
      I18n.get("home.student.header.de") + "->beginHour<-" + I18n.get("home.student.header.a") +
      "->endHour<-" + "\n" + I18n.get("home.student.header.second") + "->nom du cours<-" +
      I18n.get(("home.student.header.avec")) + "->Nom du prof<-";
    header.setText(headerContent);
    header.setWrapText(true);
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
}
