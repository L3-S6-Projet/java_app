package jscolendar.components;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.util.FXUtil;


public class Login extends HBox {

  public HBox body;
  public VBox left;
  public Label title;
  public Label subtitle;
  public Label idLabel;
  public Label passwordLabel;
  public JFXTextField id;
  public JFXPasswordField password;
  public Label linkForgotPWD;
  public VBox field;
  public Button show;
  public Button connexion;
  public Text copyRight;
  public Text linkCopyRight;


  public Login() {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    left.setMinHeight(910);
    field.setPadding(new Insets(266, 0, 0, 0));
    idLabel.setVisible(true);
    passwordLabel.setVisible(false);
  }

  @FXML
  private void showIdLabel() {
    idLabel.setVisible(true);
    id.setPromptText("");
    if (password.getText().isEmpty()) {
      passwordLabel.setVisible(false);
      password.setPromptText("Mot de passe");
    }
  }

  @FXML
  private void showPasswordLabel() {
    passwordLabel.setVisible(true);
    password.setPromptText("");
    if (id.getText().isEmpty()) {
      idLabel.setVisible(false);
      id.setPromptText("Nom d'utilisateur");
    }
  }

  @FXML
  private void showMDP() {
  }

  @FXML
  private void forgotPassword() {
    linkForgotPWD.setStyle("-fx-text-fill: #3F51B5");
    //TODO add action when link is clicked
  }

  @FXML
  private void connexion() {
    String userId = id.getText();
    String mdp = password.getText();


  }

  @FXML
  private void conditions() {
    linkCopyRight.setStyle("-fx-fill: #3F51B5");
    //TODO add action when link is clicked
  }

}
