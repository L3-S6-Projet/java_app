package jscolendar.routes.students;

import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.model.StudentResponse;
import io.swagger.client.model.StudentResponseStudentSubjects;
import io.swagger.client.model.StudentSubjects;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.List;

import static jscolendar.util.datePickerContent.getContent;

public class StudentDetails extends StackPane {


  private final Integer id;
  @FXML
  private HBox header;
  @FXML
  private VBox calendar, subLeft;
  @FXML
  private Label title, name, userName, promo;
  @FXML
  private TextFlow subjectList;

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
      subjects.append("\n").append(I18n.get("calendar.details.student.enseign.secondLine")).append(" ")
        .append(result.getStudent().getTotalHours()).append(I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
      subjectList.getChildren().add(new Text(subjects.toString()));
    }

    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> {
      System.out.println(jfxDatePicker.getValue());

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
    calendar.getChildren().add(new CalendarRoute());
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
      new ModalEvent(ModalEvent.OPEN, new EditStudent())
    );
  }
}
