package jscolendar.components.popup.admin;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

public class Edition extends VBox {

  public VBox body;
  public Label title;
  public Label msg;
  public HBox buttons;
  public JFXButton annuler;
  public JFXButton save;

  public Edition() {
    FXUtil.loadFXML("/fxml/popup/admin/Edition.fxml", this, this);
  }

  @FXML
  private void initialize() {
    title.setText("Edition");
    msg.setText("A remplir seulement pour changer de mot de passe.");
    annuler.setText("ANNULER");
    save.setText("SAVE");

    title.setPadding(new Insets(21, 24, 27, 24));
    msg.setPadding(new Insets(0, 24, 35, 24));
    buttons.setPadding(new Insets(0, 16, 8, 80));
  }
}
