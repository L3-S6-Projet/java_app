package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.SubjectResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class StudentSubjects extends VBox {

  @FXML
  private JFXListView<Label> infoContent, groupContent, enseignContent;
  @FXML
  private Label promo, name, title;

  private Integer id;

  public StudentSubjects(Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/subjects/StudentSubject.fxml", this, this, I18n.getBundle());
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
    }
    //todo enseig + group

  }
}
