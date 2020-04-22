package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.util.FXUtil;


public class Login extends VBox {

  public HBox body;
  public VBox left;
  public ImageView picture;
  public Label title;
  public Label subtitle;
  public TextField id;
  public TextField password;
  public VBox field;
  @FXML
  Button connexion;
  @FXML
  Text copyRight;


  public Login() {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }
}
