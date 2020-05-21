package jscolendar.components;

import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.ArrayList;
import java.util.List;

public class CalendarRoute extends VBox {

  private final List<VBox> toLayout = new ArrayList<>();
  @FXML
  private GridPane pane;
  @FXML
  private GridPane days;
  @FXML
  private StackPane header;
  @FXML
  private JFXComboBox<Label> select;

  public CalendarRoute () {
    FXUtil.loadFXML("/fxml/CalendarDetails.fxml", this, this, I18n.getBundle());
  }

  private static Region spacer () {
    Region region = new Region();
    VBox.setVgrow(region, Priority.ALWAYS);
    return region;
  }

  private Node makeCell (int x, int y, int day) {
    VBox vBox = new VBox();
    vBox.setAlignment(Pos.TOP_CENTER);
    vBox.setPadding(new Insets(8));

    Label dayIndicator = new Label(String.valueOf(day));
    dayIndicator.setPadding(new Insets(0, 0, 4, 0));
    Label more = new Label("5 de plus");
    more.setPadding(new Insets(4, 0, 0, 0));
    more.setStyle("-fx-text-fill: #757575; -fx-font-size: 10px;");
    vBox.getChildren().addAll(dayIndicator, event(), event(), spacer(), more);

    double rightWidth = (x == 6) ? 0.0 : 1.0;

    vBox.setBorder(new Border(new BorderStroke(Color.web("#BDBDBD"),
      BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.0, rightWidth, 0.0, 0.0))));

    toLayout.add(vBox);
    return vBox;
  }

  private Node event () {
    String name = "Algèbre et analyse";
    String className = "L3 MIASHS";

    Label nameLabel = new Label(name);

    Label classNameLabel = new Label(className);
    classNameLabel.setStyle("-fx-text-fill: #757575; -fx-font-size: 12px;");

    VBox vBox = new VBox();
    vBox.getChildren().addAll(nameLabel, classNameLabel);

    Color col = Color.WHITE;
    CornerRadii corn = new CornerRadii(5);
    Background background = new Background(new BackgroundFill(col, corn, Insets.EMPTY));
    vBox.setBackground(background);

    var borderColor = Color.web("#757575");
    vBox.setBorder(new Border(new BorderStroke(borderColor, BorderStrokeStyle.SOLID, corn, BorderWidths.DEFAULT)));

    vBox.setPadding(new Insets(8));

    /*Pane container = new Pane();
    container.getChildren().add(vBox);
    container.setPadding(new Insets(4, 0, 4, 0));

    return container;*/

    VBox.setMargin(vBox, new Insets(4, 0, 4, 0));
    toLayout.add(vBox);
    return vBox;
  }

  @FXML
  public void initialize () {
    List<Node> nodes = new ArrayList<>();

    for (int x = 0; x < 7; x++) {
      for (int y = 0; y < 5; y++) {
        var b = makeCell(x, y, 30);
        GridPane.setConstraints(b, x, y);
        GridPane.setVgrow(b, Priority.ALWAYS);
        GridPane.setHgrow(b, Priority.ALWAYS);
        nodes.add(b);

        if (x == 0) {
          RowConstraints rowConstraints = new RowConstraints();
          rowConstraints.setPercentHeight(100.0 / 5.0);
          pane.getRowConstraints().add(rowConstraints);
        }
      }

      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(100.0 / 7.0);
      pane.getColumnConstraints().add(columnConstraints);
    }

    pane.getChildren().addAll(nodes);

    // TODO: find a better way to do this
    Platform.runLater(() -> {
      var window = pane.getScene().getWindow();
      var height = window.heightProperty();

      pane.prefHeightProperty().bind(height.subtract(header.heightProperty()));
      pane.layout();

      toLayout.forEach(VBox::layout);

      /*nodes.forEach(node -> {
        node.minHeight(50);
        node.prefHeight(50);
        node.maxHeight(50);
      });*/
    });

    new Thread(() -> {
      try {
        Thread.sleep(2500);
        Platform.runLater(() -> {
          pane.layout();
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    var days = List.of("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");

    int index = 0;

    for (String day : days) {
      Label label = new Label(day);
      label.setStyle("-fx-font-size: 18px;");
      label.setMinHeight(56);
      label.setMaxHeight(56);
      label.setAlignment(Pos.CENTER);
      GridPane.setColumnIndex(label, index);
      GridPane.setHalignment(label, HPos.CENTER);
      this.days.getChildren().add(label);
      ColumnConstraints columnConstraints = new ColumnConstraints();
      columnConstraints.setPercentWidth(100.0 / days.size());
      this.days.getColumnConstraints().add(columnConstraints);
      index++;
    }
  }

}
