package jscolendar.components.popup;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class CreateTeacher extends StackPane {


  @FXML
  private JFXTextField firstName, lastName, email, phoneNumber, level;
  @FXML
  private Label errorLabel;
  @FXML
  private JFXButton cancel, save;


  @FXML
  public void initialize () {
    firstName.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        firstName.validate();
      }
    });

  }

}
