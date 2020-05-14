package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.modals.EditPromo;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class PromoDetails extends StackPane {

  @FXML
  private VBox subLeft;

  public PromoDetails () {
    FXUtil.loadFXML("/fxml/PromoDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    Node datePicker = getContent();
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
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
      new ModalEvent(ModalEvent.OPEN, new EditPromo())
    );
  }
}
