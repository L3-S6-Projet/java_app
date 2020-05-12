package jscolendar.components;

import io.swagger.client.api.AuthApi;
import io.swagger.client.model.LoginRequest;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import jscolendar.UserSession;
import jscolendar.router.AppRouter;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

public class Login {
  @FXML private JFXTextField usernameField, accessiblePassword;
  @FXML private JFXPasswordField passwordField;
  @FXML private Label errorLabel;
  @FXML private ToggleButton toggleButton;

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
    setFormDisabled(true);
    doLogin(usernameField.getText(), usernameField.getText());
  }

  private void setFormDisabled(boolean disabled) {
    usernameField.setDisable(disabled);
    passwordField.setDisable(disabled);
    toggleButton.setDisable(disabled);
  }

  private void doLogin(String username, String password) {
    AuthApi apiInstance = new AuthApi();
    LoginRequest request = new LoginRequest();

    request.setUsername(username);
    request.setPassword(password);

    var service = new FXApiService<>(apiInstance::login);
    service.setRequest(request);

    service.setOnSucceeded(event -> {
      var response = service.getValue();

      UserSession session = UserSession.getInstance();
      session.init(response);

      setFormDisabled(false);
      AppRouter.goTo("/main", "admin");
    });

    service.setOnFailed(_e -> {
      var errorMessage = APIErrorUtil.getErrorMessage(service.getException());
      setFormDisabled(false);
      errorLabel.setText(errorMessage);
    });

    service.start();
  }

}
