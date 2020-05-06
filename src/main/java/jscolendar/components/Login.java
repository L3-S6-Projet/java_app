package jscolendar.components;

import jscolendar.util.FXUtil;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class Login extends GridPane {
  @FXML private JFXTextField usernameField;
  @FXML private JFXPasswordField passwordField;

  public Login () {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);

    usernameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        usernameField.validate();
      }
    });

    passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
      if (!newValue) {
        passwordField.validate();
      }
    });
  }

  @FXML
  private void onSubmit (ActionEvent event) {
    // @TODO
  }
}
