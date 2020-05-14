package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jscolendar.components.modals.ChangePassWord;
import jscolendar.components.modals.ResetData;
import jscolendar.events.ModalEvent;

public class SettingsView extends StackPane {

  //todo add css to view
  @FXML
  private void changePassWord () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new ChangePassWord())
    );
  }

  @FXML
  private void resetData () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new ResetData())
    );
  }
}
