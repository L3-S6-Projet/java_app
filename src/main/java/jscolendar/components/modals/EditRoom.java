package jscolendar.components.modals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class EditRoom extends VBox {

  @FXML
  private JFXTextField name;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;


  public EditRoom () {
    FXUtil.loadFXML("/fxml/modals/EditRoom.fxml", this, this, I18n.getBundle());
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
