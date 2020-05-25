package jscolendar.routes.classes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.routes.teachers.CreateTeacher;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class EditPromo extends VBox {

  @FXML
  private JFXTextField name, level;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;

  public EditPromo () {
    FXUtil.loadFXML("/fxml/classes/EditPromo.fxml", this, this, I18n.getBundle());
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
