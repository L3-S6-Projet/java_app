package jscolendar.components.modals;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;

public class ChangePassWord extends StackPane {


  @FXML
  private JFXTextField accessibleOldPassword, accessibleNewPassword;
  @FXML
  private JFXPasswordField oldPasswordField, newPasswordField;
  @FXML
  private ToggleButton toggleButtonOldPassWord, toggleButtonNewPassword;

  @FXML
  public void initialize () {

  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new ChangePassWord())
    );
  }

  @FXML
  private void onCreate () {

  }

}
