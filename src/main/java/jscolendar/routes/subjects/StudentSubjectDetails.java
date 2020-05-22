package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.SubjectResponse;
import io.swagger.client.model.SubjectResponseSubjectGroups;
import io.swagger.client.model.SubjectResponseSubjectTeachers;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import org.kordamp.ikonli.javafx.FontIcon;

public class StudentSubjectDetails extends VBox {

  @FXML
  private JFXListView<VBox> infoContent, groupContent, enseignContent;
  @FXML
  private Label promo, name, title;

  private Integer id;

  public StudentSubjectDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/subjects/StudentSubjectDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize() {
    SubjectsApi apiInstance = new SubjectsApi();
    SubjectResponse result = null;

    try {
      result = apiInstance.subjectsIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.ue") + " \"" + result.getSubject().getName() + '\"');
      name.setText(result.getSubject().getName());
      promo.setText(result.getSubject().getClassName());

      var enseign = result.getSubject().getTeachers();
      for (SubjectResponseSubjectTeachers teachers : enseign) {
        VBox content = new VBox();
        if (teachers.getInCharge()) {
          Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
          Label responsibility = new Label(I18n.get("calendar.details.ue.menu.teacher.responsable"));
          content.getChildren().addAll(teacherName, responsibility);
        } else {
          Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
          FontIcon icon = new FontIcon("mdi-delete");
          icon.setOnMouseClicked(event -> supprElement(event));
          teacherName.setGraphic(new FontIcon("mdi-delete"));
          content.getChildren().addAll(teacherName);
        }
        enseignContent.getItems().add(content);
      }
      var groups = result.getSubject().getGroups();
      int nb = 0;
      JFXListView<VBox> groupContent = new JFXListView<>();
      for (SubjectResponseSubjectGroups group : groups) {
        VBox content = new VBox();
        Label groupName = new Label(group.getName());
        Label subtitle = new Label(group.getCount() + I18n.get("calendar.details.ue.menu.group.student"));
        if (nb > 1) {
          FontIcon icon = new FontIcon("mdi-delete");
          icon.setOnMouseClicked(event -> supprElement(event));
          groupName.setGraphic(new FontIcon("mdi-delete"));
        }
        nb++;
        content.getChildren().addAll(groupName, subtitle);
        groupContent.getItems().add(content);
      }
    }


  }

  private void supprElement(MouseEvent event) {
    //todo make suppr popup
  }
}
