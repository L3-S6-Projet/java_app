package jscolendar.components.popup.etu;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;


public class Info extends StackPane {

  public VBox body;
  public VBox classe;
  public Label year;
  public Label ue;
  public VBox ens;
  public Label ensTitle;
  public Label coordinates;
  public HBox group;
  public VBox infoGroup;
  public Label groupTitle;
  public Label group1;
  public Label group2;
  public JFXButton close;


  public Info() {
    FXUtil.loadFXML("/fxml/popup/etu/Info.fxml", this, this);
  }

  @FXML
  private void initialize() {
    body.setPadding(new Insets(0, 0, 0, 24));
  }
}
