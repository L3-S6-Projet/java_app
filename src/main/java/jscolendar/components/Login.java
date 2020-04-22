package jscolendar.components;

import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

public class Login extends VBox {

  public Login () {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }
}
