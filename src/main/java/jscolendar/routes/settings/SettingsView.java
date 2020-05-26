package jscolendar.routes.settings;

import io.swagger.client.model.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.events.ModalEvent;

public class SettingsView {
  @FXML private VBox container, itemContainer;
  @FXML private Separator separator;
  @FXML private HBox resetData;

  @FXML private void initialize () {
    if (UserSession.getInstance().getUser().getKind() == Role.ADM) return;

    itemContainer.getChildren().removeAll(separator, resetData);
  }

  @FXML
  private void changePassWord () {
    container.fireEvent(new ModalEvent(ModalEvent.OPEN, new ChangePassWord()));
  }

  @FXML
  private void resetData () {
    container.fireEvent(new ModalEvent(ModalEvent.OPEN, new ResetData()));
  }
}
