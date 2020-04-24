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
    menuContent.setStyle("-fx-tick-label-fill: #3F51B5");
    resetOpacity();
  }

  private void resetOpacity() {
    for (Label item : menuContent.getItems()) {
      item.setStyle("-fx-opacity: 0.6");
    }
  }

  @FXML
  private void selectItem() {
    resetOpacity();
    menuContent.getSelectionModel().getSelectedItem().setStyle("-fx-opacity: 1");
    String selectedItem = menuContent.getSelectionModel().getSelectedItem().getText();
    switch (selectedItem) {
      case "Emploi du temps":
        selectEDT();
        break;
      case "Enseignants":
        selectEns();
        break;
      case "Etudiants":
        selectEtu();
        break;
      case "Salles":
        selectSalles();
        break;
      case "Classes":
        selectClasses();
        break;
      case "Unités d'enseignement":
        selectUE();
        break;
      case "Paramètres":
        selectParam();
        break;
      case "Déconnexion":
        selectDeco();
        break;
      default:
        break;
    }
  }

  private void selectEDT() {
  }

  private void selectEns() {

  }

  private void selectEtu() {

  }

  private void selectSalles() {

  }

  private void selectClasses() {

  }

  private void selectUE() {

  }

  private void selectParam() {

  }

  private void selectDeco() {
    //todo return too login
  }


}
