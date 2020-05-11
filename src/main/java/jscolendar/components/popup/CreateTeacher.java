package jscolendar.components.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class CreateTeacher extends StackPane {


  @FXML
  private JFXTextField firstName, lastName, email, phoneNumber, grade;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;


  public CreateTeacher () {
    FXUtil.loadFXML("/fxml/popup/CreateTeacher.fxml", this, this, I18n.getBundle());
  }

  @FXML
  public void initialize () {
    //this.getChildren().addAll(firstName,lastName,email,phoneNumber,grade,cancel,save);

  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new CreateTeacher())
    );
  }

  @FXML
  private void onCreate () {

  }
}
