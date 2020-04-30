package jscolendar.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;


public class CalendarWeek extends StackPane {

  public VBox body;

  public HBox header;
  public Label day;
  public HBox dateBox;
  public Label date;
  public JFXButton previous;
  public JFXButton next;
  public JFXComboBox<Label> month;

  public StackPane layout;
  public Canvas canvas = new Canvas(1536, 859);//221 = height of header
  public HBox col;
  public VBox lund;
  public Label lundDate;
  public VBox mard;
  public Label mardDate;
  public VBox merc;
  public Label mercDate;
  public VBox jeud;
  public Label jeudDate;
  public VBox vend;
  public Label vendDate;
  public VBox sam;
  public Label samDate;
  public VBox dim;
  public Label dimDate;

  int xOrigin = 100;
  int yOrigin = 120;
  int beginHour = 8;

  public CalendarWeek() {
    FXUtil.loadFXML("/utils/CalendarWeek.fxml", this, this);
  }

  @FXML
  private void initialize() {
    initCanvas();
    month.setTranslateX(507);
    day.setTranslateX(-506);
    addElement(1, 15, 15, 60, " Algorithmique", " L3 Informatique", " F.01.1");
    addElement(2, 14, 0, 120, " Algorithmique", " L3 Informatique", " F.01.1");
    addElement(3, 10, 15, 90, " Algorithmique", " L3 Informatique", " F.01.1");
    addElement(4, 8, 30, 120, " Algorithmique", " L3 Informatique", " F.01.1");
    addElement(5, 17, 0, 60, " Algorithmique", " L3 Informatique", " F.01.1");
  }

  private void initCanvas() {
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    for (int i = 0; i < 12; i++) {
      if (beginHour + i < 10) {
        canvas.getGraphicsContext2D().fillText("0" + (beginHour + i) + ":00", xOrigin - 60, yOrigin + 5 + i * 64);
      } else {
        canvas.getGraphicsContext2D().fillText((beginHour + i) + ":00", xOrigin - 60, yOrigin + 5 + i * 64);
      }
      canvas.getGraphicsContext2D().strokeLine(xOrigin - 5, yOrigin + i * 64, xOrigin + 1436, yOrigin + i * 64);
    }
    for (int i = 0; i < 7; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin + i * 205, yOrigin - 5, xOrigin + i * 205, yOrigin + 739);
    }
    layout.getChildren().add(canvas);
  }


  private void addElement(int day, int beginHour, int BeginMinutes, int duration, String title, String subtitle, String salle) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16, 205, (duration / 15) * 16, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16, 205, (duration / 15) * 16, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(title, day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16 + 20, 205);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().fillText(subtitle, day * 205 - 105, 20 + yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16 + 20, 205);
    canvas.getGraphicsContext2D().fillText(salle, day * 205 - 105, 40 + yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16 + 20, 205);

  }

}
