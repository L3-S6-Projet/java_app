package jscolendar.components;

import com.jfoenix.controls.JFXPopup;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import jscolendar.components.popup.admin.Edition;
import jscolendar.components.popup.admin.Succes;
import jscolendar.components.popup.etu.Info;
import jscolendar.util.CalendarMonth;
import jscolendar.util.FXUtil;

public class Etu extends StackPane {
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

  //window
  Stage stage;
  int width;
  int height;


//todo change popup to JFXDialog

  /*
    public Etu() {
      FXUtil.loadFXML("/fxml/EtuView.fxml", this, this);
    }
  */
  public Etu(Stage stage) {
    this.stage = stage;
    this.width = (int) stage.getWidth();
    this.height = (int) stage.getHeight();
    FXUtil.loadFXML("/fxml/EtuView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    menuContent.setStyle("-fx-tick-label-fill: #3F51B5");
    resetOpacity();
  }

  @FXML
  private void selectItem() {
    resetOpacity();
    menuContent.getSelectionModel().getSelectedItem().setStyle("-fx-opacity: 1");
    String selectedItem = menuContent.getSelectionModel().getSelectedItem().getId();
    switch (selectedItem) {
      case "Home":
        selectHome();
        break;
      case "EDT":
        selectEDT();
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
    if (body.getChildren().size() == 1) {
      body.getChildren().add(new CalendarMonth());
    }

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
    /*this.getChildren().remove(0,this.getChildren().size());
    this.getChildren().add(new Login());*/
  }
}
