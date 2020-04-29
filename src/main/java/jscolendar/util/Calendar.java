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
import javafx.scene.text.FontPosture;


public class Calendar extends StackPane {

  public VBox body;

  public HBox header;
  public Label day;
  public HBox dateBox;
  public Label date;
  public JFXButton previous;
  public JFXButton next;
  public JFXComboBox<Label> month;

  public StackPane layout;
  public Canvas canvas = new Canvas(750, 650);
  public HBox col;
  public VBox lund;
  public VBox mard;
  public VBox merc;
  public VBox jeud;
  public VBox vend;
  public VBox sam;
  public VBox dim;

  int xOrigin = 50;
  int yOrigin = 55;
  int beginHour = 8;

  public Calendar() {
    FXUtil.loadFXML("/utils/Calendar.fxml", this, this);
  }

  @FXML
  private void initialize() {
    //todo replace hour here by an other object where font and fill work on it
    canvas.getGraphicsContext2D().setFont(Font.font("Roboto", FontPosture.REGULAR, 14));
    for (int i = 0; i < 12; i++) {
      if (beginHour + i < 10) {
        canvas.getGraphicsContext2D().strokeText("0" + (beginHour + i) + ":00", xOrigin - 40, yOrigin + 5 + i * 45);
      } else {
        canvas.getGraphicsContext2D().strokeText((beginHour + i) + ":00", xOrigin - 40, yOrigin + 5 + i * 45);
      }
      canvas.getGraphicsContext2D().strokeLine(xOrigin - 5, yOrigin + i * 45, xOrigin + 750, yOrigin + i * 45);
      //vBox.getChildren().add(new Line(xOrigin,yOrigin+i*50,xOrigin+750,yOrigin+i*50));
    }
    for (int i = 0; i < 7; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin + i * 100, yOrigin - 5, xOrigin + i * 100, yOrigin + 650);
    }
    layout.getChildren().add(canvas);

  }


}
