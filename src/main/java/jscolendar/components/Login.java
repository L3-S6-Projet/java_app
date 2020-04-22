package jscolendar.components;

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
  public JFXTextField password;
  public VBox field;
  @FXML
  Button connexion;
  @FXML
  Text copyRight;


  public Login(int width, int height) {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
    this.width = width;
    this.height = height;
  }

  public Login() {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    left.setMinHeight(910);
    field.setPadding(new Insets(266, 0, 0, 0));
  }
}
