package jscolendar.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class CalendarDay extends StackPane {

  public VBox body;

  public HBox header;
  public Label day;
  public HBox dateBox;
  public Label date;
  public JFXButton previous;
  public JFXButton next;
  public JFXComboBox<Label> select;

  public StackPane layout;
  public Canvas canvas = new Canvas(1536, 859);//221 = height of header
  public VBox dayDisplay;
  public Label dayLabel;
  public Label dayDate;


  int xOrigin = 100;
  int yOrigin = 120;
  int beginHour = 8;

  private final ArrayList<CellContent> calendarContent = new ArrayList<CellContent>();
  double xClosePos, yClosePos;
  boolean popIsShow = false;
  Point2D popOrigin;

  public CalendarDay() {
    FXUtil.loadFXML("/utils/CalendarDay.fxml", this, this);
  }

  @FXML
  private void initialize() {
    initCanvas();
    select.getSelectionModel().selectFirst();
    select.setTranslateX(507);
    day.setTranslateX(-506);
    dayDate.setTranslateX(75);

    canvas.setOnMouseClicked(event -> onClick(event.getX(), event.getY()));

    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 8, 15, 90)));
    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 10, 0, 120)));
    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 13, 0, 60)));
    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 14, 15, 90)));
    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 16, 0, 60)));
    addElement(new CellContent("Algorithmique", "Group 2", "L3 Info", "IDK", "Amphi 4", new Date(22, 4, 2020, 17, 15, 90)));
    layout.getChildren().add(canvas);
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
  }

  private void addElement(CellContent content) {
    System.out.println(content.date);
    calendarContent.add(content);
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    int topLeftY = yOrigin + ((content.date.beginHour - 8) * 64) + (content.date.beginMinute / 15) * 16;
    int duration = (content.date.duration / 15) * 16;

    canvas.getGraphicsContext2D().fillRoundRect(xOrigin, topLeftY, 1436, duration, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin, topLeftY, 1436, duration, 20, 20);

    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, xOrigin + 5, topLeftY + 20, 1436);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().fillText(content.room, xOrigin + 5, topLeftY + 40, 1436);

  }

  @FXML
  private void selectCalendarType() {
    body.getChildren().removeAll(header, layout);
    switch (select.getSelectionModel().getSelectedItem().getId()) {
      case "jour":
        body.getChildren().add(new CalendarDay());
        break;
      case "semaine":
        body.getChildren().add(new CalendarWeek());
        break;
      case "mois":
        body.getChildren().add(new CalendarMonth());
        break;
      default:
        System.out.println("error selection type");
        break;
    }
  }

  private void onClick(double x, double y) {


    //here

  }

  private void redraw() {
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    initCanvas();
    for (CellContent cell : calendarContent) {
      //draw
    }
  }


  private boolean clicOnClose(double x, double y) {
    popIsShow = false;
    return x > xClosePos && x < xClosePos + 20 && y > yClosePos && y < yClosePos + 20;
  }

  private boolean clicOnPop(double x, double y) {
    return x > popOrigin.getX() || x < popOrigin.getX() + 300 || y > popOrigin.getY() || y < popOrigin.getY() + 250;
  }
}
