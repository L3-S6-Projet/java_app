package jscolendar.components.modals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class CreateStudent extends StackPane {

  @FXML
  private JFXTextField firstName, lastName, promo;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;


  public CreateStudent () {
    FXUtil.loadFXML("/fxml/modals/CreateStudent.fxml", this, this, I18n.getBundle());
  }

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
