package jscolendar.components;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class Login {
  @FXML private JFXTextField usernameField, accessiblePassword;
  @FXML private JFXPasswordField passwordField;

  @FXML public void initialize () {
    accessiblePassword.textProperty().bindBidirectional(passwordField.textProperty());
    accessiblePassword.getValidators().addAll(passwordField.getValidators());

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

    accessiblePassword.focusedProperty().addListener(((observable, oldValue, newValue) -> {
      if (!newValue) {
        accessiblePassword.validate();
      }
    }));
  }

  @FXML
  private void onSubmit () {
    if (!usernameField.validate() || !passwordField.validate()) return;

    // @TODO
  }
}
