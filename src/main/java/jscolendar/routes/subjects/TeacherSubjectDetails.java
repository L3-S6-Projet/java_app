package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXListView;
import io.swagger.client.model.TeacherSubjectsGroups;
import io.swagger.client.model.TeacherSubjectsTeachers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.models.TeacherSubject;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class TeacherSubjectDetails extends VBox {
  @FXML private JFXListView<VBox> infoContent, groupContent, enseignContent;
  @FXML private Label promo, name, title;
  private final TeacherSubject subject;

  public TeacherSubjectDetails (TeacherSubject subject) {
    this.subject = subject;
    FXUtil.loadFXML("/fxml/subjects/TeacherSubjectDetails.fxml", this, this, I18n.getBundle());
  }


  @FXML
  private void initialize() {

    title.setText(I18n.get("calendar.title.ue") + " \"" + subject.nameProperty().get() + '\"');
    name.setText(subject.nameProperty().get());
    promo.setText(subject.classnameProperty().get());

    var enseign = subject.teachersProperty().get();
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
      enseignContent.getItems().add(content);
    }

    var groups = subject.groupsProperty().get();
    for (TeacherSubjectsGroups group : groups) {
      System.out.println(group);
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
