package jscolendar.components.popup;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

public class Confirmation extends VBox {

  public VBox body;
  public Label title;
  public Label message;
  public HBox buttons;
  public JFXButton annuler;
  public JFXButton confirm;


  public Confirmation() {
    FXUtil.loadFXML("/fxml/popup/Confirmation.fxml", this, this);
  }

  @FXML
  private void initialize() {
    title.setPadding(new Insets(21, 24, 27, 24));
    message.setPadding(new Insets(0, 24, 35, 24));
    message.setText("Voulez-vous...\n...\n...");
    buttons.setPadding(new Insets(0, 16, 8, 80));
  }

  @FXML
  private void annul() {

  }

  @FXML
  private void confirm() {

  }
}
