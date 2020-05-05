package jscolendar.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;

import java.util.ArrayList;

public class CalendarMonth extends StackPane {
  //todo extract header in a new component to reformat code

  int width;
  int height;

  private final ArrayList<ArrayList<CellContent>> calendarContent = new ArrayList<>();
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
  int xOrigin = 38;
  int yOrigin = 50;

  //cell
  int cellWidth = 214;
  int cellHeight = 175;


  //popup
  double xClosePos = -20, yClosePos = -20;
  boolean popIsShow = false;
  Point2D popOrigin = new Point2D(-1000, -1000);
  int popupWidth = 300;
  int poppupHeight = 250;


  //// Get the number of days in that month
  //YearMonth yearMonthObject = YearMonth.of(1999, 2);
  //int daysInMonth = yearMonthObject.lengthOfMonth(); //28

  public CalendarMonth() {
    FXUtil.loadFXML("/utils/CalendarMonth.fxml", this, this);
  }


  @FXML
  private void initialize() {
    initList();
    initTable();
    select.setTranslateX(507);
    day.setTranslateX(-506);
    select.getSelectionModel().selectLast();

    addContent(new CellContent(0, 0, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 0", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "math", "Group 1", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(1, 0, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(4, 3, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(5, 2, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));
    addContent(new CellContent(0, 4, "Algèbre", "Group 2", new Date(10, 4, 2020, 8, 30, 90), "L3 Info", "PAS d'idées", "Amphi 4"));


    canvas.setOnMouseClicked(event -> onClick(event.getX(), event.getY()));

    layout.getChildren().add(canvas);
  }

  private void initTable() {
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);

    for (int i = 0; i < 6; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin, yOrigin + i * cellHeight, 1536, yOrigin + i * cellHeight);
    }
    for (int i = 0; i < 7; i++) {
      canvas.getGraphicsContext2D().strokeLine(xOrigin + i * cellWidth, yOrigin, xOrigin + i * cellWidth, 925);
    }
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 36));

    for (int line = 0; line < 5; line++) {
      for (int col = 0; col < 7; col++) {
        canvas.getGraphicsContext2D().fillText("10", xOrigin - 20 + 107 + cellWidth * col, yOrigin + 36 + cellHeight * line);
      }
    }
  }

  private void initList() {
    for (int i = 0; i < 35; i++) {
      calendarContent.add(new ArrayList<>());
    }
  }

  private void addContent(CellContent content) {
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
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 14));
    canvas.getGraphicsContext2D().fillRect(xOrigin + x * 250, yOrigin + 157 + y * cellHeight, 112, 15);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(x, y)).size() - 2 + " de plus", xOrigin + x * 250, yOrigin + 46 + y * cellHeight + 20 + 55 + 50, 112);
  }

  private void secondLevelContent(CellContent content) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 55, cellWidth, 50, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 55, cellWidth, 50, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 20 + 55);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
    canvas.getGraphicsContext2D().fillText(content.room, xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 36 + 55, cellWidth);
  }

  private void firstLevelContent(CellContent content) {
    canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
    canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    canvas.getGraphicsContext2D().fillRoundRect(xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight, cellWidth, 50, 20, 20);
    canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight, cellWidth, 50, 20, 20);
    canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
    canvas.getGraphicsContext2D().setFill(Color.BLACK);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
    canvas.getGraphicsContext2D().fillText(content.name, xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 20);
    canvas.getGraphicsContext2D().setFill(Color.GRAY);
    canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
    canvas.getGraphicsContext2D().fillText(content.room, xOrigin + content.x * cellWidth, yOrigin + 46 + content.y * cellHeight + 36, cellWidth);
  }

  private int flatIndex(int x, int y) {
    return y * 7 + x;
  }


  @FXML
  private void selectCalendarType() {//fix pos of selection
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
    for (ArrayList<CellContent> cell : calendarContent) {
      int rank = 0;
      for (CellContent content : cell) {
        if (rank == 0) {
          firstLevelContent(content);
        } else if (rank == 1) {
          secondLevelContent(content);
        } else {
          thirdLevelContent(content.x, content.y);
        }
        rank++;
      }
    }


  }

  private int getIndexOfSelection(double x, double y) {
    if (x < xOrigin || y < yOrigin || y > 925) return -1;
    for (int i = 0; i < 5; i++) {
      if (yOrigin + 157 + i * cellHeight < y && y < yOrigin + 172 + i * cellHeight) {
        return 2;
      } else if (yOrigin + 101 + i * cellHeight < y && y < yOrigin + 151 + i * cellHeight) {
        return 1;
      } else if (yOrigin + 46 + i * cellHeight < y && y < yOrigin + 96 + i * cellHeight) {
        return 0;
      }
    }
    return -1;
  }


  private void onClick(double x, double y) {

    //todo refactor
    int cellX = (int) (x - xOrigin) / cellWidth;
    int cellY = (int) (y - yOrigin) / cellHeight;
    int modifierX = cellWidth, modifierY = 0;
    if (cellX > 4) {
      modifierX = -300;
    }
    if (cellY > 3) {
      modifierY = -150;
    }
    if (popIsShow) {
      if (clickOnClose(x, y)) {
        popIsShow = false;
        redraw();
        return;
      } else if (clickOnPop(x, y)) {
        popIsShow = true;
        return;
      } else {
        redraw();
        popIsShow = false;
      }
    }


    int index = getIndexOfSelection(x, y);
    if (calendarContent.get(flatIndex(cellX, cellY)).size() > 0 && index != -1) {
      if (index == 2) {
        body.getChildren().removeAll(header, layout);
        body.getChildren().add(new CalendarDay());
      }
      redraw();
      canvas.getGraphicsContext2D().setFill(Color.WHITE);
      canvas.getGraphicsContext2D().fillRect(cellX * cellWidth + xOrigin + modifierX, cellY * cellHeight + yOrigin + modifierY, popupWidth, poppupHeight);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 18));

      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).name, cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 40 + modifierY, 240);

      canvas.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).date.toString(), cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 40 + 18 + modifierY, 240);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));


      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).group, cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 96 + modifierY, 240);
      canvas.getGraphicsContext2D().drawImage(new Image("images/group.png"), cellX * cellWidth + 56 + modifierX, cellY * cellHeight + xOrigin + 96 + modifierY);

      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).promo, cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 126 + modifierY, 240);
      canvas.getGraphicsContext2D().drawImage(new Image("images/promo.png"), cellX * cellWidth + 56 + modifierX, cellY * cellHeight + xOrigin + 126 + modifierY);

      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).professor, cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 156 + modifierY, 240);
      canvas.getGraphicsContext2D().drawImage(new Image("images/prof.png"), cellX * cellWidth + 56 + modifierX, cellY * cellHeight + xOrigin + 156 + modifierY);

      canvas.getGraphicsContext2D().fillText(calendarContent.get(flatIndex(cellX, cellY)).get(index).room, cellX * cellWidth + xOrigin + 56 + modifierX, cellY * cellHeight + yOrigin + 186 + modifierY, 240);
      canvas.getGraphicsContext2D().drawImage(new Image("images/room.png"), cellX * cellWidth + 56 + modifierX, cellY * cellHeight + xOrigin + 186 + modifierY);

      xClosePos = cellX * cellWidth + modifierX + 260 + xOrigin;
      yClosePos = cellY * cellHeight + modifierY + 20 + 50;

      canvas.getGraphicsContext2D().strokeLine(xClosePos, yClosePos, xClosePos + 15, yClosePos + 15);
      canvas.getGraphicsContext2D().strokeLine(xClosePos, yClosePos + 15, xClosePos + 15, yClosePos);
      popOrigin = new Point2D(cellX * cellWidth + modifierX, cellY * cellHeight + modifierY);
      popIsShow = true;

    }
  }

  private boolean clickOnClose(double x, double y) {
    popIsShow = false;
    return x > xClosePos && x < xClosePos + 20 && y > yClosePos && y < yClosePos + 20;
  }

  private boolean clickOnPop(double x, double y) {
    return x > popOrigin.getX() && x < popOrigin.getX() + popupWidth && y > popOrigin.getY() && y < popOrigin.getY() + poppupHeight;
  }


}
