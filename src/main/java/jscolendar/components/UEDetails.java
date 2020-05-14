package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jscolendar.components.modals.EditSubject;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class UEDetails extends StackPane {
  public UEDetails () {
    FXUtil.loadFXML("/fxml/UEDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void selectCalendarType () {
  }

  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditSubject())
    );
  }

}
