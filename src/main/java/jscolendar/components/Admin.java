package jscolendar.components;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.popup.admin.Edition;
import jscolendar.components.popup.admin.NewUE;
import jscolendar.components.popup.admin.Succes;
import jscolendar.util.CalendarDay;
import jscolendar.util.CalendarMonth;
import jscolendar.util.CalendarWeek;
import jscolendar.util.FXUtil;


public class Admin extends StackPane {

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
  public JFXDialog popup;

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
    String selectedItem = menuContent.getSelectionModel().getSelectedItem().getId();
    switch (selectedItem) {
      case "EDT":
        selectEDT();
        break;
      case "Ens":
        selectEns();
        break;
      case "Etu":
        selectEtu();
        break;
      case "Salles":
        selectSalles();
        break;
      case "Classes":
        selectClasses();
        break;
      case "UE":
        selectUE();
        break;
      case "Param":
        selectParam();
        break;
      case "Deco":
        selectDeco();
        break;
      default:
        System.out.println("error");
        break;
    }
  }

  private void selectEDT() {
    if (body.getChildren().size() == 1)
      body.getChildren().add(new CalendarMonth());
  }

  private void selectEns() {
    popup = new JFXDialog();
    popup.setContent(new Edition());
    popup.show(this);
  }

  private void selectEtu() {
    popup = new JFXDialog();
    popup.setContent(new NewUE());
    popup.show(this);
  }

  private void selectSalles() {
    popup = new JFXDialog();
    popup.setContent(new Succes("Test", "LOL"));
    popup.show(this);
  }

  private void selectClasses() {
    popup = new JFXDialog();
    popup.setContent(new jscolendar.components.popup.enseign.Edition());
    popup.show(this);
  }

  private void selectUE() {
    popup = new JFXDialog();
    popup.setContent(new CalendarMonth());
    popup.show(this);
  }

  private void selectParam() {
    popup = new JFXDialog();
    popup.setContent(new CalendarDay());
    popup.show(this);

  }

  private void selectDeco() {
    popup = new JFXDialog();
    popup.setContent(new CalendarWeek());
    popup.show(this);

    //todo return too login
  }


}
