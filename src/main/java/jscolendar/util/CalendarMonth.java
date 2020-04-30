package jscolendar.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CalendarMonth extends StackPane {
  //todo extract header in a new component to reformat code

  public VBox body;

  public HBox header;
  public Label day;
  public HBox dateBox;
  public Label date;
  public JFXButton previous;
  public JFXButton next;
  public JFXComboBox<Label> month;

  public StackPane layout;
  public Canvas canvas = new Canvas(1536, 934);//221 = height of header
  public HBox col;

  int xOrigin = 38;
  int yOrigin = 50;


  public CalendarMonth() {
    FXUtil.loadFXML("/utils/CalendarMonth.fxml", this, this);
  }

  @FXML
  private void initialize() {

    for (int i = 0; i < 6; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin, yOrigin + i * 175, 1536, yOrigin + i * 175);
    }
    for (int i = 0; i < 7; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin + i * 214, yOrigin, xOrigin + i * 214, 925);
    }
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 36));

    for (int line = 0; line < 5; line++) {
      for (int col = 0; col < 7; col++) {
        canvas.getGraphicsContext2D().fillText("10", xOrigin - 20 + 107 + 214 * col, yOrigin + 36 + 175 * line);
      }
    }
    layout.getChildren().add(canvas);
  }


}
