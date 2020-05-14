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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.modals.EditTeacher;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;


public class TeacherDetails extends StackPane {


  @FXML
  private HBox body, header;
  @FXML
  private Label name, userName, email, phoneNumber, teacher;
  @FXML
  private Label title;
  @FXML
  private VBox services, subLeft, all, info;
  @FXML
  private JFXButton returnButton, edit;
  @FXML
  private JFXComboBox<Label> select;
  @FXML
  private JFXListView<Label> infoContent;
  private final Integer id;

  public TeacherDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/TeacherDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    infoContent = new JFXListView<>();
    select.getSelectionModel().selectLast();
    title.setText("Enseignant(e)");

    TeacherApi apiInstance = new TeacherApi();
    TeacherResponse result = null;

    try {
      result = apiInstance.teachersIdGet(id);
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
    datePicker.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> dateListener());
    subLeft.getChildren().add(datePicker);
  }

  private void dateListener () {

  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditTeacher())
    );

  }

  @FXML
  private void selectCalendarType () {
    //todo change calendar type
  }

}
