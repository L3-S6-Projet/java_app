package jscolendar.components.popup.admin;

import com.jfoenix.controls.JFXButton;
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

public class NewUE extends StackPane {
  private final ArrayList<Label> emptyFiledLabel = new ArrayList<>();
  private final ArrayList<JFXTextField> jfxTextFields = new ArrayList<>();
  private final ArrayList<TextField> textFields = new ArrayList<>();
  private final ArrayList<Label> labelOfField = new ArrayList<>();

  public VBox body;
  public Label title;


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

  public VBox enseignant;
  public Label emptyFieldEnseignant;
  public StackPane enseignantLayout;
  public Label enseignantLabel;
  public JFXTextField enseignantInput;
  public TextField emptyEnseignantInput;

  public HBox buttons;
  public JFXButton annuler;
  public JFXButton create;

  public NewUE() {
    FXUtil.loadFXML("/fxml/popup/admin/NewUE.fxml", this, this);
  }

  @FXML
  private void initialize() {
    body.setPadding(new Insets(0, 0, 0, 24));
    initList();

    nomLabel.setTranslateY(-20);
    classeLabel.setTranslateY(-20);
    enseignantLabel.setTranslateY(-20);
    nomInput.setTranslateY(10);
    classeInput.setTranslateY(10);
    enseignantInput.setTranslateY(10);

    fieldNom();
  }

  private void initList() {
    emptyFiledLabel.add(emptyFieldNom);
    emptyFiledLabel.add(emptyFieldClasse);
    emptyFiledLabel.add(emptyFieldEnseignant);

    labelOfField.add(nomLabel);
    labelOfField.add(classeLabel);
    labelOfField.add(enseignantLabel);

    jfxTextFields.add(nomInput);
    jfxTextFields.add(classeInput);
    jfxTextFields.add(enseignantInput);

    textFields.add(emptyNomInput);
    textFields.add(emptyClasseInupt);
    textFields.add(emptyEnseignantInput);
  }

  private void hideEmptyFieldMessage() {
    for (Label field : emptyFiledLabel) {
      field.setVisible(false);
    }
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

  @FXML
  private void fieldEnseignant() {
    animField();
    enseignantLabel.setVisible(true);
    enseignantInput.setVisible(true);
    emptyEnseignantInput.setVisible(false);
    enseignantInput.requestFocus();
  }

  @FXML
  private void annul() {

  }

  @FXML
  private void create() {
    animField();
    boolean isValid = true;
    if (enseignantInput.getText().isEmpty()) {
      emptyFieldEnseignant.setVisible(true);
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
}
