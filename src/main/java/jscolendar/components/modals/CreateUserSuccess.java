package jscolendar.components.modals;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class CreateUserSuccess extends VBox {
  @FXML private Text username, password;

  public CreateUserSuccess (String username, String password) {
    FXUtil.loadFXML("/fxml/modals/CreateUserSuccess.fxml", this, this, I18n.getBundle());
    this.username.setText(I18n.get("modal.createUser.success.username")
      .concat(" ").concat(username).concat(System.lineSeparator()));
    this.password.setText(I18n.get("modal.createUser.success.password")
      .concat(" ").concat(password).concat(System.lineSeparator()));
  }

  @FXML
  private void onClick () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }
}
