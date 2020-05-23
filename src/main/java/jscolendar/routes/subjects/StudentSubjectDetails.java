package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXListView;
import io.swagger.client.model.StudentSubjectsGroups;
import io.swagger.client.model.StudentSubjectsSubjects;
import io.swagger.client.model.TeacherSubjectsTeachers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class StudentSubjectDetails extends VBox {


  @FXML
  private JFXListView<VBox> enseigContent;
  @FXML
  private JFXListView<VBox> infoContent, groupContent;
  @FXML
  private Label promo, name, title;
  private final StudentSubjectsSubjects subject;

  public StudentSubjectDetails(StudentSubjectsSubjects subject) {
    this.subject = subject;
    FXUtil.loadFXML("/fxml/subjects/StudentSubjectDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize() {

    title.setText(I18n.get("calendar.title.ue") + " \"" + subject.getName() + '\"');
    name.setText(subject.getName());
    promo.setText(subject.getClassName());

    var enseign = subject.getTeachers();
    for (TeacherSubjectsTeachers teachers : enseign) {
      VBox content = new VBox();
      if (teachers.getInCharge()) {
        Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
        Label responsibility = new Label(I18n.get("calendar.details.ue.menu.teacher.responsable"));
        content.getChildren().addAll(teacherName, responsibility);
      } else {
        Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
        content.getChildren().addAll(teacherName);
      }
      enseigContent.getItems().add(content);
    }

    var groups = subject.getGroups();
    for (StudentSubjectsGroups group : groups) {
      VBox content = new VBox();
      Label groupName = new Label(group.getName());
      Label subtitle = new Label(group.getCount() + I18n.get("calendar.details.ue.menu.group.student"));

      content.getChildren().addAll(groupName, subtitle);
      groupContent.getItems().add(content);
    }

  }

  @FXML
  private void returnButton() {//todo fix this (i think it's because his parent extend --> AbstractSmallTableView<TeacherSubject>
    ((StackPane) this.getParent()).getChildren().remove(this);
  }
}
