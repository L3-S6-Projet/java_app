package jscolendar.routes.students;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.YearMonthView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.StudentResponse;
import io.swagger.client.model.StudentResponseStudentSubjects;
import io.swagger.client.model.StudentSubjects;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.text.MessageFormat;
import java.util.List;

import static jscolendar.util.datePickerContent.getContent;

public class StudentDetails extends BorderPane {
  private final Integer id;
  @FXML private VBox subLeft;
  @FXML private Label title, name, userName, promo;
  @FXML private TextFlow subjectList;
  @FXML private JFXComboBox<Label> select;

  public StudentDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/students/StudentDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    StudentsApi apiInstance = new StudentsApi();
    StudentResponse result = null;
    StudentSubjects classResult = null;

    try {
      result = apiInstance.studentsIdGet(id);
      classResult = apiInstance.studentsIdSubjectsGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null && classResult != null) {
      title.setText(I18n.get("calendar.title.student") + " \"" + result.getStudent().getFirstName() + " " + result.getStudent().getLastName() + '\"');
      name.setText(result.getStudent().getFirstName() + " " + result.getStudent().getLastName());
      userName.setText(result.getStudent().getUsername());
      promo.setText(classResult.getSubjects().get(0).getClassName());

      List<StudentResponseStudentSubjects> listOfSubject = result.getStudent().getSubjects();
      StringBuilder subjects = new StringBuilder();
      subjects.append(I18n.get("calendar.details.enseignement")).append('\n');
      subjects.append(I18n.get("calendar.details.student.enseign.firstLine"));
      for (StudentResponseStudentSubjects subjet : listOfSubject) {
        subjects.append("\n - ").append(subjet.getName()).append(", ").append(subjet.getGroup());
      }
      subjects.append("\n").append(MessageFormat.format(I18n.get("calendar.details.student.enseign.secondLine"),result.getStudent().getTotalHours()));
      subjectList.getChildren().add(new Text(subjects.toString()));
    }



    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var studentApi = new StudentsApi();
    service = new FXApiService<>(request ->
      studentApi.studentsIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));
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
    jfxDatePicker.setOnAction(event -> {
      calendarView.getSelectedPage().setDate(jfxDatePicker.getValue());

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);

    setCenter(calendarView);
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }


  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditStudent())
    );
  }
}
