package jscolendar.components.popup.login;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jscolendar.util.FXUtil;

public class Conditions extends StackPane {

  public VBox body;
  public Label title;
  public Text text;

  public Conditions() {
    FXUtil.loadFXML("/fxml/popup/login/Conditions.fxml", this, this);
  }

  @FXML
  private void initialize() {
    title.setPadding(new Insets(10, 0, 0, 10));
    title.setText("Conditions");
    text.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
    text.setWrappingWidth(500);

  }
}
