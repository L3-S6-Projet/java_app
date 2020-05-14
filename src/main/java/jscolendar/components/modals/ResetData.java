package jscolendar.components.modals;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class ResetData extends StackPane {


  public ResetData () {
    FXUtil.loadFXML("/fxml/modals/ResetData.fxml", this, this, I18n.getBundle());
  }

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
