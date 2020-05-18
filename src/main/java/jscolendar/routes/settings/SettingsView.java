package jscolendar.routes.settings;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;

public class SettingsView {

  @FXML
  private VBox container;
/*
  public SettingsView () {
    FXUtil.loadFXML("/fxml/Settings.fxml",this,this, I18n.getBundle());
  }*/

  //todo add css to view
  @FXML
  private void changePassWord () {
    container.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new ChangePassWord())
    );
  }

  @FXML
  private void resetData () {
    container.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new ResetData())
    );
  }
}
