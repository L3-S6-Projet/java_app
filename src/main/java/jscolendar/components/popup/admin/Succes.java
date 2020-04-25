package jscolendar.components.popup.admin;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;


public class Succes extends VBox {

  public VBox body;
  public Label title;
  public Label msg;
  public Label id;
  public Label mdp;
  public JFXButton ok;

  public Succes() {
    FXUtil.loadFXML("/fxml/popup/admin/Succes.fxml", this, this);
  }


  @FXML
  private void initialize() {
    id.setText("Nom d'utilisateur : lol");
    mdp.setText("Mot de passe : lol");
    ok.setTranslateX(280);
  }

}
