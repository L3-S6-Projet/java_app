package jscolendar.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.TeacherResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;


public class DetailsView extends StackPane {


  @FXML
  private Label name, userName, email, phoneNumber, teacher;
  @FXML
  private Label title;
  @FXML
  private VBox services;
  @FXML
  private JFXButton returnButton;
  @FXML
  private JFXComboBox<Label> select;
  @FXML
  private VBox subLeft;
  @FXML
  private VBox info;
  @FXML
  private JFXListView<Label> infoContent;

  public DetailsView () {
    FXUtil.loadFXML("/fxml/popup/enseign/DetailsView.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    infoContent = new JFXListView<>();
    title.setText("Enseignant(e)");
    TeacherApi apiInstance = new TeacherApi();
    TeacherResponse result = null;
    try {
      result = apiInstance.teachersIdGet(10);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeacherApi#teachersGet");
      e.printStackTrace();
    }
    assert result != null;
    name.setText(result.getTeacher().getFirstName() + " " + result.getTeacher().getLastName());
    userName.setText(result.getTeacher().getUsername());
    email.setText(result.getTeacher().getEmail());
    phoneNumber.setText(result.getTeacher().getPhoneNumber());
    teacher.setText("Professeur");
    JFXDatePicker datePicker = new JFXDatePicker();
    datePicker.setOverLay(true);
    subLeft.getChildren().add(datePicker);
  }

}
