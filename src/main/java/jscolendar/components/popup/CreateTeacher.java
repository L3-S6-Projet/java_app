package jscolendar.components.popup;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class CreateTeacher extends StackPane {


  @FXML
  private JFXTextField firstName, lastName, email, phoneNumber, level;
  @FXML
  private Label title, errorLabel;

  @FXML
  public void initialize () {

  }

}
