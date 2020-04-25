package jscolendar.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class PrettyField {

  private final StackPane layout = new StackPane();

  public PrettyField(String label, String promptTextInput, String promptTexteEmptyInput) {
    Label name = new Label();
    name.setText(label);
    JFXTextField input = new JFXTextField();
    input.setPromptText(promptTextInput);
    TextField emptyInput = new TextField();
    emptyInput.setPromptText(promptTexteEmptyInput);
    layout.getChildren().addAll(name, input, emptyInput);
  }

  public StackPane getLayout() {
    return layout;
  }

  public void isClicked() {

  }

}
