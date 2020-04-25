package jscolendar.components.popup.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.util.FXUtil;

import java.util.ArrayList;

public class Edition extends VBox {

  private final ArrayList<Label> emptyFiledLabel = new ArrayList<>();
  private final ArrayList<JFXTextField> jfxTextFields = new ArrayList<>();
  private final ArrayList<TextField> textFields = new ArrayList<>();
  private final ArrayList<Label> labelOfField = new ArrayList<>();

  public VBox body;
  public Label title;


  public VBox prenom;
  public Label emptyFieldPrenom;
  public StackPane prenomLayout;
  public Label prenomLabel;
  public JFXTextField prenomInput;
  public TextField emptyPrenomInput;

  public VBox nom;
  public Label emptyFieldNom;
  public StackPane nomLayout;
  public Label nomLabel;
  public JFXTextField nomInput;
  public TextField emptyNomInput;

  public VBox classe;
  public Label emptyFieldClasse;
  public StackPane classeLayout;
  public Label classeLabel;
  public JFXTextField classeInput;
  public TextField emptyClasseInupt;


  public VBox mdp;
  public Label emptyFieldMdp;
  public StackPane mdpLayout;
  public Label mdpLabel;
  public JFXPasswordField mdpInput;
  public JFXTextField mdpShowInput;
  public TextField emptyMdpInput;
  public Button show;


  public Label msg;
  public HBox buttons;
  public JFXButton annuler;
  public JFXButton save;

  public Edition() {
    FXUtil.loadFXML("/fxml/popup/admin/Edition.fxml", this, this);
  }

  @FXML
  private void initialize() {
    initList();

    title.setText("Edition");
    title.setPadding(new Insets(21, 24, 27, 24));

    emptyPrenomInput.setEditable(false);
    emptyNomInput.setEditable(false);

    prenomLabel.setTranslateY(-20);
    nomLabel.setTranslateY(-20);
    classeLabel.setTranslateY(-20);
    mdpLabel.setTranslateY(-20);
//todo modif margin of fields
    prenomInput.setTranslateY(10);
    nomInput.setTranslateY(10);
    classeInput.setTranslateY(10);
    mdpInput.setTranslateY(10);
    mdpShowInput.setTranslateY(10);

    show.setTranslateX(150);
    msg.setText("A remplir seulement pour changer de mot de passe.");
    msg.setPadding(new Insets(0, 24, 35, 24));
    buttons.setPadding(new Insets(0, 16, 8, 80));
    annuler.setText("ANNULER");
    save.setText("SAVE");

    fieldPrenom();
  }

  private void animField() {
    hideEmptyFieldMessage();
    for (int i = 0; i < jfxTextFields.size(); i++) {
      if (jfxTextFields.get(i).getText().isEmpty()) {
        emptyFiledLabel.get(i).setVisible(false);
        labelOfField.get(i).setVisible(false);
        jfxTextFields.get(i).setVisible(false);
        textFields.get(i).setVisible(true);
      }
    }
    animMdpField();
  }


  private void initList() {
    emptyFiledLabel.add(emptyFieldPrenom);
    emptyFiledLabel.add(emptyFieldNom);
    emptyFiledLabel.add(emptyFieldClasse);

    labelOfField.add(prenomLabel);
    labelOfField.add(nomLabel);
    labelOfField.add(classeLabel);
    labelOfField.add(mdpLabel);

    jfxTextFields.add(prenomInput);
    jfxTextFields.add(nomInput);
    jfxTextFields.add(classeInput);

    textFields.add(emptyPrenomInput);
    textFields.add(emptyNomInput);
    textFields.add(emptyClasseInupt);
  }

  @FXML
  private void fieldPrenom() {
    animField();
    prenomLabel.setVisible(true);
    prenomInput.setVisible(true);
    emptyPrenomInput.setVisible(false);
    prenomInput.requestFocus();

  }

  @FXML
  private void fieldNom() {
    animField();
    nomLabel.setVisible(true);
    nomInput.setVisible(true);
    emptyNomInput.setVisible(false);
    nomInput.requestFocus();

  }

  @FXML
  private void fieldClasse() {
    animField();
    classeLabel.setVisible(true);
    classeInput.setVisible(true);
    emptyClasseInupt.setVisible(false);
    classeInput.requestFocus();

  }

  private void animMdpField() {
    if (mdpInput.getText().isEmpty()) {
      emptyMdpInput.setVisible(true);
      mdpInput.setVisible(false);
      mdpLabel.setVisible(false);
    }
  }

  @FXML
  private void fieldMDP() {
    animField();
    mdpLabel.setVisible(true);
    mdpInput.setVisible(true);
    mdpInput.requestFocus();
    emptyMdpInput.setVisible(false);
    mdpShowInput.setVisible(false);
  }


  @FXML
  private void showButton() {
    if (mdpInput.isVisible()) {
      mdpInput.setVisible(false);
      mdpShowInput.setVisible(true);
      mdpShowInput.setText(mdpInput.getText());
    } else {
      mdpInput.setVisible(true);
      mdpShowInput.setVisible(false);
      mdpInput.setText(mdpShowInput.getText());
      emptyMdpInput.setVisible(false);
    }
  }


  @FXML
  private void annul() {
    //todo add action
  }

  @FXML
  private void saveButton() {
    boolean isValid = true;
    if (prenomInput.getText().isEmpty()) {
      emptyFieldPrenom.setVisible(true);
      isValid = false;
    }
    if (nomInput.getText().isEmpty()) {
      emptyFieldNom.setVisible(true);
      isValid = false;
    }
    if (classeInput.getText().isEmpty()) {
      emptyFieldClasse.setVisible(true);
      isValid = false;
    }
    if (isValid) {
      //todo add action
    }

  }


  private void hideEmptyFieldMessage() {
    for (Label field : emptyFiledLabel) {
      field.setVisible(false);
    }
  }
}
