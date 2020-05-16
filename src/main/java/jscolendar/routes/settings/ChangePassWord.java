package jscolendar.routes.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class ChangePassWord extends VBox {


  @FXML
  private JFXTextField accessibleOldPassword, accessibleNewPassword;
  @FXML
  private JFXPasswordField oldPasswordField, newPasswordField;
  @FXML
  private ToggleButton toggleButtonOldPassWord, toggleButtonNewPassword;


  public ChangePassWord () {
    FXUtil.loadFXML("/fxml/settings/ChangePassWord.fxml", this, this, I18n.getBundle());
  }

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
