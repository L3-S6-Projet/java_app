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
import javafx.scene.text.FontSmoothingType;

import java.util.ArrayList;


public class CalendarWeek extends StackPane {

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

  private final ArrayList<ArrayList<CellContent>> calendarContent = new ArrayList<>();
  boolean popIsShow = false;
  Point2D popOrigin;
  Point2D closePos;

  public CalendarWeek() {
    FXUtil.loadFXML("/utils/CalendarWeek.fxml", this, this);
  }

  @FXML
  private void initialize() {
    select.getSelectionModel().select(1);
    initCanvas();
    initList();
    canvas.setOnMouseClicked(event -> onClick(event.getX(), event.getY()));
    select.setTranslateX(507);
    day.setTranslateX(-506);
    addContent(new CellContent(0, 0, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 0", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "math", "Group 1", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(4, 3, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(5, 2, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(0, 4, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));

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

  private void initList() {
    for (int i = 0; i < 7 * 12; i++) {
      calendarContent.add(new ArrayList<>());
    }
  }

  private int flatIndex(int x, int y) {
    return y * 7 + x;
  }


  private void addContent(CellContent content) {
    calendarContent.get(flatIndex(content.x, content.y)).add(content);
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(content.date.day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (content.date.beginMinute / 15) * 16, 205, (content.date.duration / 15) * 16, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(content.date.day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (content.date.beginMinute / 15) * 16, 205, (content.date.duration / 15) * 16, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, content.date.day * 205 - 105, yOrigin + (beginHour - 8) * 64 + (content.date.beginMinute / 15) * 16 + 20, 205);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().fillText(content.promo, content.date.day * 205 - 105, 20 + yOrigin + (beginHour - 8) * 64 + (content.date.beginMinute / 15) * 16 + 20, 205);
    canvas.getGraphicsContext2D().fillText(content.room, content.date.day * 205 - 105, 40 + yOrigin + (beginHour - 8) * 64 + (content.date.beginMinute / 15) * 16 + 20, 205);

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

  private void redraw() {
    canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    initCanvas();
    for (ArrayList<CellContent> cell : calendarContent) {
      for (CellContent content : cell) {
        addContent(content);
      }
    }


  }

  private int getIndexOfSelection(double x, double y) {
    if (x < xOrigin || y < yOrigin || y > 925) return -1;
    for (int i = 0; i < 5; i++) {
      if (yOrigin + 157 + i * 175 < y && y < yOrigin + 172 + i * 175) {
        return 2;
      } else if (yOrigin + 101 + i * 175 < y && y < yOrigin + 151 + i * 175) {
        return 1;
      } else if (yOrigin + 46 + i * 175 < y && y < yOrigin + 96 + i * 175) {
        return 0;
      }
    }
    return -1;
  }


  private void onClick(double x, double y) {

    if (x > xOrigin && y > yOrigin) {
      System.out.println("on calendar");
    } else {
      System.out.println("OOB");
    }

  }

  private boolean clickOnClose(double x, double y) {
    popIsShow = false;
    return x > closePos.getX() && x < closePos.getX() + 20 && y > closePos.getY() && y < closePos.getY() + 20;
  }

  private boolean clickOnPop(double x, double y) {
    return x > popOrigin.getX() && x < popOrigin.getX() + 300 && y > popOrigin.getY() && y < popOrigin.getY() + 250;
  }
}
