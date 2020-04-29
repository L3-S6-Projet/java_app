package jscolendar.components.popup.login;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.util.FXUtil;

public class ForgotPWD extends StackPane {

  public VBox body;
  public Text msg;

  public ForgotPWD() {
    FXUtil.loadFXML("/fxml/popup/login/ForgotPWD.fxml", this, this);
  }

  @FXML
  private void initialize() {
    body.setPadding(new Insets(0, 0, 0, 24));
    msg.setText("mot de passe oublié");
    msg.setWrappingWidth(500);
  }
}
