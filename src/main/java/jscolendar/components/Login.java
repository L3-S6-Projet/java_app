package jscolendar.components;

import com.jfoenix.controls.JFXTextArea;
import io.swagger.client.ApiCallback;
import io.swagger.client.ApiException;
import io.swagger.client.api.AuthApi;
import io.swagger.client.model.LoginRequest;
import io.swagger.client.model.SuccessfulLoginResponse;
import javafx.application.Platform;
import javafx.scene.control.Label;
import jscolendar.router.AppRouter;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import jscolendar.util.APIErrorUtil;

import java.util.List;
import java.util.Map;

public class Login {
  @FXML private JFXTextField usernameField, accessiblePassword;
  @FXML private JFXPasswordField passwordField;
  @FXML private Label errorLabel;

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
    doLogin(usernameField.getText(), usernameField.getText());
  }

  private void doLogin(String username, String password) {
    AuthApi apiInstance = new AuthApi();
    LoginRequest request = new LoginRequest();

    request.setUsername(username);
    request.setPassword(password);

    try {
      apiInstance.loginAsync(request, new ApiCallback<>() {
        @Override
        public void onFailure(ApiException e, int i, Map<String, List<String>> map) {
          Platform.runLater(() -> errorLabel.setText(APIErrorUtil.getErrorMessage(apiInstance.getApiClient(), e)));
        }

        @Override
        public void onSuccess(SuccessfulLoginResponse successfulLoginResponse, int i, Map<String, List<String>> map) {
          // TODO: authenticate user
          AppRouter.goTo("/main", "admin");
        }

        @Override
        public void onUploadProgress(long l, long l1, boolean b) {

        }

        @Override
        public void onDownloadProgress(long l, long l1, boolean b) {

        }
      });
    } catch (ApiException e) {
      System.err.println("Exception when calling AuthApi#login");
      e.printStackTrace();
    }
  }

}
