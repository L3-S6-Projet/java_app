package jscolendar.components.popup.enseign;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

import java.util.ArrayList;

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

  private final ArrayList<Label> emptyLabel = new ArrayList<>();
  private final ArrayList<Label> labelList = new ArrayList<>();
  private final ArrayList<JFXTextField> jfxTextFieldsList = new ArrayList<>();
  private final ArrayList<TextField> textFieldsList = new ArrayList<>();

  public HBox buttons;
  public JFXButton annuler;
  public JFXButton save;


  public Edition() {
    FXUtil.loadFXML("/fxml/popup/enseign/Edition.fxml", this, this);
  }

  @FXML
  private void initialize() {
    body.setPadding(new Insets(0, 0, 0, 24));


    emptyLabel.add(wrongDurationMessage);
    emptyLabel.add(wrongDateMessage);
    labelList.add(durationLabel);
    labelList.add(dateLabel);
    jfxTextFieldsList.add(durationInput);
    jfxTextFieldsList.add(dateInput);
    textFieldsList.add(emptyDurationInput);
    textFieldsList.add(emptyDateInput);

    durationLabel.setTranslateY(-20);
    dateLabel.setTranslateY(-20);
    durationInput.setTranslateY(10);
    dateInput.setTranslateY(10);


    animField();
    onDuration();
  }

  @FXML
  private void onDuration() {
    animField();
    durationLabel.setVisible(true);
    durationInput.setVisible(true);
    emptyDurationInput.setVisible(false);
    durationInput.requestFocus();
  }

  @FXML
  private void onDate() {
    animField();
    dateLabel.setVisible(true);
    dateInput.setVisible(true);
    emptyDateInput.setVisible(false);
    dateInput.requestFocus();
  }

  @FXML
  private void annul() {
  }

  @FXML
  private void saveButton() {
    animField();
    boolean isValid = true;
  }


  private void hideEmptyFieldMessage() {
    for (Label field : emptyLabel) {
      field.setVisible(false);
    }
  }

  private void animField() {
    hideEmptyFieldMessage();
    for (int i = 0; i < jfxTextFieldsList.size(); i++) {
      if (jfxTextFieldsList.get(i).getText().isEmpty()) {
        emptyLabel.get(i).setVisible(false);
        labelList.get(i).setVisible(false);
        jfxTextFieldsList.get(i).setVisible(false);
        textFieldsList.get(i).setVisible(true);
      }
    }
  }


}
