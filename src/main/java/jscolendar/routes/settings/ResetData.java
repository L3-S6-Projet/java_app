package jscolendar.routes.settings;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class ResetData extends VBox {


  public ResetData () {
    FXUtil.loadFXML("/fxml/settings/ResetData.fxml", this, this, I18n.getBundle());
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
