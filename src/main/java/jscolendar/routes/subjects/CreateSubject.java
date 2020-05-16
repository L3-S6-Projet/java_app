package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.models.ClassModel;
import jscolendar.models.Teacher;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class CreateSubject extends VBox {
  @FXML private JFXTextField name;
  @FXML private JFXComboBox<ClassModel> promo;
  @FXML private JFXComboBox<Teacher> teacher;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;


  public CreateSubject () {
    FXUtil.loadFXML("/fxml/subjects/CreateSubject.fxml", this, this, I18n.getBundle());
  }

  @FXML
  public void initialize () {
  }

  @FXML
  private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML
  private void onCreate () {

  }
}
