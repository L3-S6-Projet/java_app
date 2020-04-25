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
  public StackPane PWDLayout;
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

    emptyFiledLabel.add(emptyFieldPrenom);
    emptyFiledLabel.add(emptyFieldNom);
    emptyFiledLabel.add(emptyFieldClasse);
    title.setText("Edition");
    title.setPadding(new Insets(21, 24, 27, 24));

    emptyPrenomInput.setEditable(false);
    emptyNomInput.setEditable(false);

    prenomLabel.setPadding(new Insets(-40, 0, 0, -150));


    nomLabel.setPadding(new Insets(-40, 0, 0, -150));
    nomLabel.setVisible(false);

    msg.setText("A remplir seulement pour changer de mot de passe.");
    msg.setPadding(new Insets(0, 24, 35, 24));
    buttons.setPadding(new Insets(0, 16, 8, 80));
    annuler.setText("ANNULER");
    save.setText("SAVE");

    fieldPrenom();
  }

  @FXML
  private void fieldPrenom() {
    hideEmptyFieldMessage();

    prenomLabel.setVisible(true);
    prenomInput.setVisible(true);
    emptyPrenomInput.setVisible(false);

    if (nomInput.getText().isEmpty()) {
      nomLabel.setVisible(false);
      nomInput.setVisible(false);
      emptyNomInput.setVisible(true);
    }
  }

  @FXML
  private void fieldNom() {
    hideEmptyFieldMessage();

    nomLabel.setVisible(true);
    nomInput.setVisible(true);
    emptyNomInput.setVisible(false);


    if (prenomInput.getText().isEmpty()) {
      prenomLabel.setVisible(false);
      prenomInput.setVisible(false);
      emptyPrenomInput.setVisible(true);
    }

  }


  @FXML
  private void annul() {

  }

  @FXML
  private void saveButton() {
  }


  private void hideEmptyFieldMessage() {
    for (Label field : emptyFiledLabel) {
      field.setVisible(false);
    }
  }
}
