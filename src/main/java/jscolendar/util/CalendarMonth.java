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

import java.util.ArrayList;

public class CalendarMonth extends StackPane {
  //todo extract header in a new component to reformat code

  public VBox body;

  public HBox header;
  public Label day;
  public HBox dateBox;
  public Label date;
  public JFXButton previous;
  public JFXButton next;
  public JFXComboBox<Label> select;

  public StackPane layout;
  public Canvas canvas = new Canvas(1536, 934);//221 = height of header
  public HBox col;

  private final ArrayList<ArrayList<CellContent>> calendarContent = new ArrayList<ArrayList<CellContent>>();
  private final int[][] cells = new int[7][5];

  int xOrigin = 38;
  int yOrigin = 50;


  //// Get the number of days in that month
  //YearMonth yearMonthObject = YearMonth.of(1999, 2);
  //int daysInMonth = yearMonthObject.lengthOfMonth(); //28

  public CalendarMonth() {
    FXUtil.loadFXML("/utils/CalendarMonth.fxml", this, this);
  }

  @FXML
  private void initialize() {
    //initCells();
    initList();
    initTable();
    select.getSelectionModel().selectLast();
    addContent(new CellContent(0, 0, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(4, 3, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));

    addContent(new CellContent(5, 2, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));

    addContent(new CellContent(0, 4, "Algèbre", "Group 2", "Lundi 30", "L3 Info", "PAS d'idées", "Amphi 4"));


    /* addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 4);
    addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 4);
    addElement("Algèbre", "Amphi 7", 3, 1);
    addElement("Algèbre", "Amphi 7", 4, 1);
    addElement("Algèbre", "Amphi 7", 5, 2);
    addElement("Algèbre", "Amphi 7", 6, 2);
    addElement("Algèbre", "Amphi 7", 6, 2);
    addElement("Algèbre", "Amphi 7", 6, 4);*/
    canvas.setOnMouseClicked(event -> onClick(event.getX(), event.getY()));

    layout.getChildren().add(canvas);
  }

  private void initTable() {
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
  }

  private void initList() {
    for (int i = 0; i < 35; i++) {
      calendarContent.add(new ArrayList<CellContent>());
    }
  }

  private void addContent(CellContent content) {
    //todo refactor
    int x = content.x;
    int y = content.y;
    calendarContent.get(flatIndex(x, y)).add(content);
    if (calendarContent.get(flatIndex(x, y)).size() == 1) {
      firstLevelContent(content);
    } else if (calendarContent.get(flatIndex(x, y)).size() == 2) {
      secondLevelContent(content);
    } else if (calendarContent.get(flatIndex(x, y)).size() >= 3) {
      thirdLevelContent(x, y);
    }
  }

  private void thirdLevelContent(int x, int y) {
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 14));
    canvas.getGraphicsContext2D().clearRect(xOrigin + x * 250, yOrigin + 46 + y * 175 + 20 + 55 + 50 - 14, 112, 15);
    canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(x, y)).size() - 2 + " de plus", xOrigin + x * 250, yOrigin + 46 + y * 175 + 20 + 55 + 50, 112);
  }

  private void secondLevelContent(CellContent content) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 55, 214, 50, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 55, 214, 50, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 20 + 55);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
    canvas.getGraphicsContext2D().fillText(content.room, xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 36 + 55, 214);
  }

  private void firstLevelContent(CellContent content) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(xOrigin + content.x * 214, yOrigin + 46 + content.y * 175, 214, 50, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + content.x * 214, yOrigin + 46 + content.y * 175, 214, 50, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 20);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
    canvas.getGraphicsContext2D().fillText(content.room, xOrigin + content.x * 214, yOrigin + 46 + content.y * 175 + 36, 214);
  }

  private int flatIndex(int x, int y) {
    return y * 7 + x;
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
    initTable();
    for (int i = 0; i < 35; i++) {
      for (int j = 0; j < calendarContent.get(i).size(); i++) {
        if (calendarContent.get(i).size() == 0) continue;
        else {
          CellContent content = calendarContent.get(i).get(j);
          if (j == 0) {
            firstLevelContent(content);
          } else if (j == 1) {
            secondLevelContent(content);
          } else {
            thirdLevelContent(content.x, content.y);
          }
        }
      }
    }
  }


  private void onClick(double x, double y) {
    int cellX = (int) x / (1498 / 7);
    int cellY = (int) y / (884 / 5);
    if (calendarContent.get(flatIndex(cellX, cellY)).size() > 0) {
      redraw();
      canvas.getGraphicsContext2D().setFill(Color.WHITE);
      canvas.getGraphicsContext2D().fillRect(cellX * 214 + 38, cellY * 175 + 50, 300, 250);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 18));

      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).name, cellX * 214 + 38 + 56, cellY * 175 + 50 + 40, 240);

      canvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).date, cellX * 214 + 38 + 56, cellY * 175 + 50 + 40 + 18, 240);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).group, cellX * 214 + 38 + 56, cellY * 175 + 50 + 96, 240);
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).promo, cellX * 214 + 38 + 56, cellY * 175 + 50 + 126, 240);
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).prof, cellX * 214 + 38 + 56, cellY * 175 + 50 + 156, 240);
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(0).room, cellX * 214 + 38 + 56, cellY * 175 + 50 + 186, 240);
    }

  }


}
