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

public class Edition extends VBox {

  public VBox body;
  public Label title;

  public Label emptyField;

  public VBox prenom;
  public StackPane prenomLayout;
  public Label prenomLabel;
  public JFXTextField prenomInput;
  public TextField emptyPrenomInput;


  public Label msg;
  public HBox buttons;
  public JFXButton annuler;
  public JFXButton save;

  public Edition() {
    FXUtil.loadFXML("/fxml/popup/admin/Edition.fxml", this, this);
  }

  @FXML
  private void initialize() {
    title.setText("Edition");
    msg.setText("A remplir seulement pour changer de mot de passe.");
    annuler.setText("ANNULER");
    save.setText("SAVE");
    emptyField.setText("Champ vide");
    emptyField.setVisible(false);


    prenomLabel.setVisible(true);
    prenomLabel.setPadding(new Insets(-40, 0, 0, 0));

    prenomInput.setVisible(true);
    emptyPrenomInput.setVisible(false);


    title.setPadding(new Insets(21, 24, 27, 24));
    msg.setPadding(new Insets(0, 24, 35, 24));
    buttons.setPadding(new Insets(0, 16, 8, 80));
  }

  @FXML
  private void fieldPrenom() {
    emptyField.setVisible(false);
  }


  @FXML
  private void annul() {

    emptyField.setVisible(true);
  }

  @FXML
  private void saveButton() {
    emptyField.setVisible(true);
  }
}
