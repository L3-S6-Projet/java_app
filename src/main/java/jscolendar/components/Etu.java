package jscolendar.components;

import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.PopupWindow;
import jscolendar.components.popup.admin.Confirmation;
import jscolendar.components.popup.admin.Edition;
import jscolendar.components.popup.admin.Succes;
import jscolendar.components.popup.etu.Info;
import jscolendar.util.FXUtil;

public class Etu extends HBox {
  public HBox body;
  public VBox menu;
  public Label title;
  public Label subtitle;
  public ListView<Label> menuContent;
  public Label home;
  public Label emploiDuTemps;
  public Label unitesEns;
  public Label parametres;
  public Label deconnexion;
  public Label copyRigth1;
  public Label copyRigth2;
  public JFXPopup popConfirm;

  public Etu() {
    FXUtil.loadFXML("/fxml/EtuView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    menuContent.setStyle("-fx-tick-label-fill: #3F51B5");
    resetOpacity();
  }

  //TODO fix pb with accentuated character because switch doesn't work
  @FXML
  private void selectItem() {
    resetOpacity();
    menuContent.getSelectionModel().getSelectedItem().setStyle("-fx-opacity: 1");
    String selectedItem = menuContent.getSelectionModel().getSelectedItem().getText();
    switch (selectedItem) {
      case "Home":
        selectHome();
        break;
      case "Emploi du temps":
        selectEDT();
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
        System.out.println("error");
        break;
    }
  }

  private void resetOpacity() {
    for (Label item : menuContent.getItems()) {
      item.setStyle("-fx-opacity: 0.6");
    }
  }

  //TODO Warning add temp popup just to test it
  private void selectHome() {
    popConfirm = new JFXPopup();
    popConfirm.setPopupContent(new Edition());
    popConfirm.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    popConfirm.show(deconnexion);
  }

  private void selectEDT() {
    popConfirm = new JFXPopup();
    popConfirm.setPopupContent(new Confirmation());
    popConfirm.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    popConfirm.show(deconnexion);

  }

  private void selectUE() {
    popConfirm = new JFXPopup();
    popConfirm.setPopupContent(new Succes("lol", "XD"));
    popConfirm.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    popConfirm.show(deconnexion);
  }

  private void selectParam() {
    popConfirm = new JFXPopup();
    popConfirm.setPopupContent(new Info());
    popConfirm.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    popConfirm.show(deconnexion);
  }

  private void selectDeco() {
    //todo return too login
  }
}
