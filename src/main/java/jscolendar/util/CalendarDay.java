package jscolendar.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
  public HBox col;
  public VBox dayLabel;
  public Label dayDate;


  int xOrigin = 100;
  int yOrigin = 120;
  int beginHour = 8;

}
