package jscolendar.components.popup.login;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.util.FXUtil;

public class ForgotPWD extends VBox {

  public VBox body;
  public Text msg;

  public ForgotPWD() {
    FXUtil.loadFXML("/fxml/popup/login/ForgotPWD.fxml", this, this);
  }

  @FXML
  private void initialize() {
    msg.setText("mot de passe oublié");
  }
}
