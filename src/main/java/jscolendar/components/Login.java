package jscolendar.components;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import jscolendar.components.popup.Confirmation;
import jscolendar.util.FXUtil;


public class Login extends HBox {


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
  public JFXPopup popConditions;


  public Login() {
    FXUtil.loadFXML("/fxml/LoginView.fxml", this, this);
  }

  @FXML
  private void initialize() {
    left.setMinHeight(610);
    userInputs.setPadding(new Insets(266, 0, 0, 0));
    passwordLabel.setVisible(false);
    emptyIdInput.setStyle("-fx-max-height: 50px");
    emptyIdInput.setStyle("-fx-background-color: lightgray");
    idInput.setPadding(new Insets(20, 0, 0, 0));
    PWDInput.setPadding(new Insets(20, 0, 0, 0));
    PWDShowInput.setPadding(new Insets(20, 0, 0, 0));
    idLabel.setPadding(new Insets(-20, 0, 0, 0));
    passwordLabel.setPadding(new Insets(-20, 0, 0, 0));
    idLabel.setVisible(true);
    idInput.setVisible(false);
    show.setTranslateX(170);
    PWDShowInput.setVisible(false);
    wrongLoginMessage.setVisible(false);
    wrongLoginMessage.setPadding(new Insets(0, 0, 28, 0));
    showIdLabel();
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
    conditions();
    //TODO add action when link is clicked
  }

  @FXML
  private void connexion() {
    String userId = idInput.getText();
    String mdp = PWDInput.getText();
    if (userId.equals(userId) && mdp.equals(mdp)) {//TODO add condition with BDD
      wrongLoginMessage.setVisible(true);
      idInput.setStyle("-jfx-unfocus-color : #FF0C3E");
      PWDInput.setStyle("-jfx-unfocus-color : #FF0C3E");
      idLabel.setStyle("-fx-text-fill: #FF0C3E");
      passwordLabel.setStyle("-fx-text-fill: #FF0C3E");
    }
    new Admin();

  }

  @FXML
  private void conditions() {


    TextArea text = new TextArea("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
      " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,");
    VBox lol = new VBox();
    lol.getChildren().addAll(new Label("fefzef"), text);
    linkCopyRight.setStyle("-fx-fill: #3F51B5");
    popConditions = new JFXPopup();
    popConditions.setPopupContent(new Confirmation());
    //popConditions.setPopupContent(text);
    popConditions.getPopupContent().setMaxHeight(600);
    popConditions.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    popConditions.show(title);
    //TODO make a beautiful popup and change the text
  }

}
