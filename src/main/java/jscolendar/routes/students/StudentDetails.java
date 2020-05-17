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
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class StudentDetails extends StackPane {


  private final Integer id;
  @FXML
  private HBox header;
  @FXML
  private VBox calendar, subLeft;
  @FXML
  private Label title, subjectList, name, userName, promo;

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

      var listOfSubject = result.getStudent().getSubjects();
      StringBuilder subjects = new StringBuilder();
      subjects.append(I18n.get("calendar.details.student.enseign.firstLine"));
      for (StudentResponseStudentSubjects subjet : listOfSubject) {
        subjects.append("\n - " + subjet.getName() + ", " + subjet.getGroup());
      }
      subjects.append("\n" + I18n.get("calendar.details.student.enseign.secondLine") + " " + result.getStudent().getTotalHours() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
      subjectList.setWrapText(true);
      subjectList.setText(subjects.toString());

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
