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

  private final int[][] cells = new int[7][5];

  int xOrigin = 38;
  int yOrigin = 50;


  public CalendarMonth() {
    FXUtil.loadFXML("/utils/CalendarMonth.fxml", this, this);
  }

  @FXML
  private void initialize() {
    for (int x = 0; x < 7; x++) {
      for (int y = 0; y < 5; y++) {
        cells[x][y] = 0;
      }
    }

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
    addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 4);
    addElement("Algèbre", "Amphi 7", 2, 1);
    addElement("Algèbre", "Amphi 7", 2, 4);
    addElement("Algèbre", "Amphi 7", 3, 1);
    addElement("Algèbre", "Amphi 7", 4, 1);
    addElement("Algèbre", "Amphi 7", 5, 2);
    addElement("Algèbre", "Amphi 7", 6, 2);
    addElement("Algèbre", "Amphi 7", 6, 2);
    addElement("Algèbre", "Amphi 7", 6, 4);
    layout.getChildren().add(canvas);
  }


  private void addElement(String name, String salle, int x, int y) {
    cells[x][y]++;
    if (cells[x][y] == 1) {
      canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
      canvas.getGraphicsContext2D().setStroke(Color.BLACK);
      canvas.getGraphicsContext2D().fillRoundRect(xOrigin + x * 214, yOrigin + 46 + y * 175, 214, 50, 20, 20);
      canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + x * 214, yOrigin + 46 + y * 175, 214, 50, 20, 20);
      canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
      canvas.getGraphicsContext2D().fillText(name, xOrigin + x * 214, yOrigin + 46 + y * 175 + 20);
      canvas.getGraphicsContext2D().setFill(Color.GRAY);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
      canvas.getGraphicsContext2D().fillText(salle, xOrigin + x * 214, yOrigin + 46 + y * 175 + 36, 214);
    } else if (cells[x][y] == 2) {
      canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
      canvas.getGraphicsContext2D().setStroke(Color.BLACK);
      canvas.getGraphicsContext2D().fillRoundRect(xOrigin + x * 214, yOrigin + 46 + y * 175 + 55, 214, 50, 20, 20);
      canvas.getGraphicsContext2D().strokeRoundRect(xOrigin + x * 214, yOrigin + 46 + y * 175 + 55, 214, 50, 20, 20);
      canvas.getGraphicsContext2D().setFontSmoothingType(FontSmoothingType.GRAY);
      canvas.getGraphicsContext2D().setFill(Color.BLACK);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 20));
      canvas.getGraphicsContext2D().fillText(name, xOrigin + x * 214, yOrigin + 46 + y * 175 + 20 + 55);
      canvas.getGraphicsContext2D().setFill(Color.GRAY);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 16));
      canvas.getGraphicsContext2D().fillText(salle, xOrigin + x * 214, yOrigin + 46 + y * 175 + 36 + 55, 214);
    } else if (cells[x][y] >= 3) {
      canvas.getGraphicsContext2D().setFill(Color.GRAY);
      canvas.getGraphicsContext2D().setFont(new Font("Roboto Light", 14));
      canvas.getGraphicsContext2D().clearRect(xOrigin + x * 250, yOrigin + 46 + y * 175 + 20 + 55 + 50 - 14, 112, 15);
      canvas.getGraphicsContext2D().fillText(cells[x][y] - 2 + " de plus", xOrigin + x * 250, yOrigin + 46 + y * 175 + 20 + 55 + 50, 112);
    }


  }

}
