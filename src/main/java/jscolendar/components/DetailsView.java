package jscolendar.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;


public class DetailsView extends StackPane {

  @FXML
  private Label title;
  @FXML
  private VBox services;
  @FXML
  private JFXButton returnButton;
  @FXML
  private JFXComboBox<Label> select;
  @FXML
  private VBox subLeft;
  @FXML
  private VBox info;
  @FXML
  private JFXListView<Label> infoContent;

  public DetailsView () {
    FXUtil.loadFXML("/fxml/popup/enseign/DetailsView.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    infoContent = new JFXListView<>();
    title.setText("truc");
    infoContent.getItems().add(new Label("tezrtezr"));
    info.getChildren().add(infoContent);
    JFXDatePicker datePicker = new JFXDatePicker();
    datePicker.setOverLay(true);
    subLeft.getChildren().add(datePicker);
  }

}
