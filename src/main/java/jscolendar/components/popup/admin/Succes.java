package jscolendar.components.popup.admin;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;


public class Succes extends StackPane {

  private final String userName;
  private final String userMdp;
  public VBox body;
  public Label title;
  public Label msg;
  public Label id;
  public Label mdp;
  public JFXButton ok;

  public Succes(String id, String mdp) {
    userName = id;
    userMdp = mdp;
    FXUtil.loadFXML("/fxml/popup/admin/Succes.fxml", this, this);
  }

  @FXML
  private void initialize() {
    body.setPadding(new Insets(0, 0, 0, 24));
    ok.setTranslateX(280);
    this.id.setText("Nom d'utilisateur : " + userName);
    this.mdp.setText("Mot de passe : " + userMdp);
  }

}
