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

public class CalendarDay extends StackPane {

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
  public VBox dayDisplay;
  public Label dayLabel;
  public Label dayDate;


  int xOrigin = 100;
  int yOrigin = 120;
  int beginHour = 8;

  public CalendarDay() {
    FXUtil.loadFXML("/utils/CalendarDay.fxml", this, this);
  }

  @FXML
  private void initialize() {
    initCanvas();
    month.setTranslateX(507);
    day.setTranslateX(-506);
    dayDate.setTranslateX(75);

    addElement(8, 30, 90, "Algorithmique", "Amphi. 4");
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
    canvas.getGraphicsContext2D().strokeLine(xOrigin, yOrigin - 5, xOrigin, yOrigin + 739);
    layout.getChildren().add(canvas);
  }


  private void addElement(int beginHour, int BeginMinutes, int duration, String title, String salle) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);

    canvas.getGraphicsContext2D().fillRoundRect(xOrigin, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16, 1436, (duration / 15) * 16, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16, 1436, (duration / 15) * 16, 20, 20);

    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(title, xOrigin, yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16 + 20, 1436);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().fillText(salle, xOrigin, 20 + yOrigin + (beginHour - 8) * 64 + (BeginMinutes / 15) * 16 + 20, 1436);

  }
}
