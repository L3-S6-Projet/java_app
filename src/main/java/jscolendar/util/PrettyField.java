package jscolendar.util;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class PrettyField extends StackPane {

  private final StackPane layout;
  private final Label label;
  private final JFXTextField input;
  private final TextField emptyInput;

  public PrettyField() {
    layout = new StackPane();
    label = new Label();
    input = new JFXTextField();
    emptyInput = new TextField();
    layout.getChildren().addAll(label, input, emptyInput);
  }

  public void setLabelContent(String label) {
    this.label.setText(label);
  }

  public void setInput(String promptText) {
    input.setPromptText(promptText);
  }

  public void setEmptyInput(String promptText) {
    emptyInput.setPromptText(promptText);
  }

  public StackPane getLayout() {
    return layout;
  }

  public void isClicked() {
    input.setStyle("-fx-background-color: #FF0C3E");
    layout.setStyle("-fx-background-color: #3F51B5");
    input.requestFocus();
    emptyInput.setVisible(false);
    label.setVisible(true);
  }

}
