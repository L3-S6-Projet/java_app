package jscolendar.components;

import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.modals.EditSubject;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;


public class UEDetails extends StackPane {

  @FXML
  private VBox calendar;
  @FXML
  private VBox subLeft;
  @FXML
  private JFXTabPane menu;

  public UEDetails () {
    FXUtil.loadFXML("/fxml/UEDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    Node datePicker = getContent();
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
    calendar.getChildren().add(new CalendarRoute());
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
