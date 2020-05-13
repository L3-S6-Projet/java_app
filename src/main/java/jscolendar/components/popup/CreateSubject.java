package jscolendar.components.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;

public class CreateSubject extends StackPane {
  @FXML
  private JFXTextField name, promo, responsibleTeacher;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;

  @FXML
  public void initialize () {

  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new CreateTeacher())
    );
  }

  @FXML
  private void onCreate () {

  }
}
