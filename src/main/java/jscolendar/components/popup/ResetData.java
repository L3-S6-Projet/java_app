package jscolendar.components.popup;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;

public class ResetData extends StackPane {


  @FXML
  public void initialize () {

  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new ResetData())
    );
  }

  @FXML
  private void onCreate () {

  }
}
