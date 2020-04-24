package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;


public class Admin extends HBox {

  public HBox body;
  public VBox menu;
  public Label title;
  public Label subtitle;
  public ListView<Label> menuContent;
  public Label emploiDuTemps;
  public Label enseignants;
  public Label etudiants;
  public Label salles;
  public Label classes;
  public Label unitesEns;
  public Label parametres;
  public Label deconnexion;
  public Label copyRigth1;
  public Label copyRigth2;


  public Admin() {
    FXUtil.loadFXML("/fxml/AdminView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    menuContent.getItems().addAll(emploiDuTemps, enseignants, etudiants, salles, classes, unitesEns, parametres, deconnexion);

  }


}
