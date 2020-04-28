package jscolendar.components.popup.enseign;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

public class Edition extends VBox {

  public VBox body;
  public Label title;

  public VBox salle;
  public Label salleLabel;
  public JFXComboBox salleList;

  public VBox classe;
  public Label classeLabel;
  public JFXComboBox classeList;

  public VBox effectif;
  public Label effectifLabel;
  public JFXComboBox effectifList;

  public VBox duration;
  public Label wrongDurationMessage;
  public StackPane durationLayout;
  public Label durationLabel;
  public JFXTextField durationInput;
  public TextField emptyDurationInput;

  public VBox date;
  public Label wrongDateMessage;
  public StackPane dateLayout;
  public Label dateLabel;
  public JFXTextField dateInput;
  public TextField emptyDateInput;


  public HBox buttons;
  public JFXButton annuler;
  public JFXButton save;


  public Edition() {
    FXUtil.loadFXML("/fxml/popup/enseign/Edition.fxml", this, this);
  }

  @FXML
  private void initialize() {
  }


}
