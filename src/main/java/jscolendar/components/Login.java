package jscolendar.components;

import com.jfoenix.controls.JFXDialog;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jscolendar.components.popup.login.Conditions;
import jscolendar.components.popup.login.ForgotPWD;
import jscolendar.util.FXUtil;


public class Login extends StackPane {


  public HBox body;
  public VBox left;
  public Label title;
  public Label subtitle;

  public VBox userInputs;
  public Label wrongLoginMessage;
  public StackPane idLayout;
  public Label idLabel;
  public JFXTextField idInput;
  public TextField emptyIdInput;

  public StackPane PWDLayout;
  public Label passwordLabel;
  public JFXPasswordField PWDInput;
  public JFXTextField PWDShowInput;
  public TextField emptyPWDInput;
  public Button show;


  public Label linkForgotPWD;
  public Button connexion;
  public Text copyRight;
  public Text linkCopyRight;
  public JFXDialog popup;

  public HBox connectItems;
  public VBox copyRightItems;
  public VBox rigth;

  //window
  Stage stage;
  int width;
  int height;

  public Login(Stage stage, int width, int height) {
    this.stage = stage;
    this.width = width;
    this.height = height;

    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }

  @FXML
  private void initialize() {//todo finish resize of button and copyRight + (background image?)+check when you return on this page
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      width = newVal.intValue();
      System.out.println(width + " " + oldVal.intValue());
      setItemsOnWidth();
    });
    stage.heightProperty().addListener((obs, oldVal, newVal) -> {
      height = newVal.intValue();
      setItemsOnHeight();
    });


    idLabel.setVisible(true);
    idInput.setVisible(false);

    passwordLabel.setVisible(false);
    PWDShowInput.setVisible(false);
    wrongLoginMessage.setVisible(false);

    connexion.setOnAction(event -> connexion());
    showIdLabel();
  }

  private void setItemsOnHeight() {
    body.setMinHeight(height);
    int temp = (height / (13 / 4));
    userInputs.setTranslateY(height / 3);
    PWDLayout.setTranslateY((height / 3) * 0.05);

    idInput.setMinHeight(temp * 0.15);
    emptyIdInput.setMinHeight(temp * 0.15);
    PWDInput.setMinHeight(temp * 0.15);
    emptyPWDInput.setMinHeight(temp * 0.15);
    PWDShowInput.setMinHeight(temp * 0.15);

    connectItems.setTranslateY((height / 3) + ((height / 3) * 0.05) + ((temp * 0.15) * 0.5));

    connexion.setMinHeight(temp * 0.18);
    connexion.setMaxHeight(temp * 0.18);
    linkForgotPWD.setTranslateY(-8 + (temp * 0.15) / 2);


    copyRightItems.setTranslateY(height - (temp * 1.3));

    rigth.setMinHeight(height);
  }

  private void setItemsOnWidth() {
    int temp = width / (13 / 4);
    left.setMinWidth(temp);
    left.setPadding(new Insets((height / (13 / 4)) * 0.1, 0, 0, temp * 0.1));

    idLayout.setMaxWidth(temp - temp * 0.2);
    PWDLayout.setMaxWidth(temp - temp * 0.2);

    show.setTranslateX((temp - temp * 0.2) / 2.25);
    linkForgotPWD.setTranslateX(10);

    rigth.setMinWidth(width - temp);
  }

  @FXML
  private void showIdLabel() {

    idInput.setStyle("-jfx-focus-color : #3F51B5");
    PWDInput.setStyle("-jfx-focus-color : #3F51B5");
    idLabel.setStyle("-fx-text-fill: #3F51B5");
    passwordLabel.setStyle("-fx-text-fill: #3F51B5");
    wrongLoginMessage.setVisible(false);

    idLabel.setVisible(true);
    idInput.setVisible(true);
    emptyIdInput.setVisible(false);
    idInput.requestFocus();
    if (PWDInput.getText().isEmpty()) {
      passwordLabel.setVisible(false);
      PWDInput.setVisible(false);
      emptyPWDInput.setVisible(true);
    }
  }

  @FXML
  private void showPasswordLabel() {

    idInput.setStyle("-jfx-focus-color : #3F51B5");
    PWDInput.setStyle("-jfx-focus-color : #3F51B5");
    idLabel.setStyle("-fx-text-fill: #3F51B5");
    passwordLabel.setStyle("-fx-text-fill: #3F51B5");
    wrongLoginMessage.setVisible(false);

    passwordLabel.setVisible(true);
    PWDInput.setVisible(true);
    emptyPWDInput.setVisible(false);
    PWDInput.requestFocus();
    if (idInput.getText().isEmpty()) {
      idLabel.setVisible(false);
      idInput.setVisible(false);
      emptyIdInput.setVisible(true);
    }
  }

  @FXML
  private void showMDP() {
    if (PWDShowInput.isVisible()) {
      PWDInput.setText(PWDShowInput.getText());
      PWDShowInput.setVisible(false);
      PWDInput.setVisible(true);
    } else {
      PWDInput.setVisible(false);
      PWDShowInput.setVisible(true);
      PWDShowInput.setText(PWDInput.getText());
    }
  }

  @FXML
  private void forgotPassword() {
    linkForgotPWD.setStyle("-fx-text-fill: #3F51B5");
    popup = new JFXDialog();
    popup.setContent(new ForgotPWD());
    popup.show(this);
    //TODO add action when link is clicked
  }

  @FXML
  private void connexion() {
    String userId = idInput.getText();
    String mdp = PWDInput.getText();
    if (userId.equals(userId) && mdp.equals(mdp) && true) {//TODO add condition with BDD
      wrongLoginMessage.setVisible(true);
      idInput.setStyle("-jfx-unfocus-color : #FF0C3E");
      PWDInput.setStyle("-jfx-unfocus-color : #FF0C3E");
      idLabel.setStyle("-fx-text-fill: #FF0C3E");
      passwordLabel.setStyle("-fx-text-fill: #FF0C3E");
    }
    this.getChildren().clear();
    this.getChildren().add(new Etu(stage, width, height));

  }

  @FXML
  private void conditions() {
    linkCopyRight.setStyle("-fx-fill: #3F51B5");
    popup = new JFXDialog();
    popup.setContent(new Conditions());
    popup.show(this);
    //TODO make a beautiful popup and change the text
  }

}
