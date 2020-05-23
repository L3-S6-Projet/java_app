package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.model.TeacherSubjectsGroups;
import io.swagger.client.model.TeacherSubjectsTeachers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jscolendar.models.TeacherSubject;
import jscolendar.router.AppRouter;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import org.kordamp.ikonli.javafx.FontIcon;

public class TeacherSubjectDetails extends VBox {
  @FXML
  private HBox header;
  private final TeacherSubject subject;
  @FXML
  private JFXListView<VBox> infoContent, groupContent, enseignContent;
  @FXML
  private Label promo, name;

  public TeacherSubjectDetails(TeacherSubject subject) {
    this.subject = subject;
    FXUtil.loadFXML("/fxml/subjects/TeacherSubjectDetails.fxml", this, this, I18n.getBundle());
  }


  @FXML
  private void initialize() {

    JFXButton arrow = new JFXButton();
    arrow.setGraphic(new FontIcon("mdi-arrow-left"));
    arrow.setOnAction(event -> {
      AppRouter.goTo("main/home");
    });
    Label title = new Label(I18n.get("calendar.title.ue") + " \"" + subject.nameProperty() + '\"');
    name.setText(subject.nameProperty().get());
    promo.setText(subject.classnameProperty().get());
    header.getChildren().addAll(arrow, title);
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

}
