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

  int width;
  int height;

  public HBox body;
  public VBox left;
  public Label title;
  public Label subtitle;
  public JFXTextField id;
  public JFXPasswordField password;
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
  }

  @FXML
  private void showMDP() {
  }

  @FXML
  private void forgotPassword() {

  }

  @FXML
  private void connexion() {

  }

  @FXML
  private void condition() {

  }

}
