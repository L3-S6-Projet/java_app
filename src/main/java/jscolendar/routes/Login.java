package jscolendar.routes;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.AuthApi;
import io.swagger.client.model.LoginRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import jscolendar.UserSession;
import jscolendar.router.AppRouter;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

public class Login extends StackPane {//extend just to test pop
  @FXML
  private JFXTextField usernameField, accessiblePassword;
  @FXML
  private JFXPasswordField passwordField;
  @FXML
  private Label errorLabel;
  @FXML
  private ToggleButton toggleButton;

  @FXML
  public void initialize () {
    // doLogin("user.admin", "user.admin");
    doLogin("user.student", "user.student");
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

    usernameField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      KeyCode code = event.getCode();

      if ((code == KeyCode.TAB || code == KeyCode.ENTER) && !event.isShiftDown() && !event.isControlDown()) {
        event.consume();
        passwordField.requestFocus();
      }
    });

    passwordField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      KeyCode code = event.getCode();

      if (code == KeyCode.TAB && event.isShiftDown() && !event.isControlDown()) {
        event.consume();
        usernameField.requestFocus();
      }

      else if (code == KeyCode.ENTER && !event.isShiftDown() && !event.isControlDown()) {
        event.consume();
        onSubmit();
      }
    });

    // Auto-focus the username field
    Platform.runLater(() -> usernameField.requestFocus());
  }

  @FXML
  private void onSubmit () {
    if (!usernameField.validate() || !passwordField.validate()) return;
    if (toggleButton.isDisabled()) return;
    setFormDisabled(true);
    doLogin(usernameField.getText(), passwordField.getText());
  }

  private void setFormDisabled (boolean disabled) {
    usernameField.setDisable(disabled);
    passwordField.setDisable(disabled);
    toggleButton.setDisable(disabled);
  }

  private void doLogin (String username, String password) {
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
