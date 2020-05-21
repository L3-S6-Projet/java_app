package jscolendar.routes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.util.I18n;

public class HomeStudent {


  public VBox container;
  public Label header;
  public VBox graph;


  @FXML
  public void initialize () {
    ///profile/last-occupancies-modifications
    String headerContent = I18n.get("home.student.header.first") + " " + "->nb<-" + I18n.get("home.student.header.minutes") + " " +
      I18n.get("home.student.header.en") + "->la salle<-" +
      I18n.get("home.student.header.de") + "->beginHour<-" + I18n.get("home.student.header.a") +
      "->endHour<-" + "\n" + I18n.get("home.student.header.second") + "->nom du cours<-" +
      I18n.get(("home.student.header.avec")) + "->Nom du prof<-";
    header.setText(headerContent);
    header.setWrapText(true);


    ObservableList<PieChart.Data> valueList = FXCollections.observableArrayList(
      new PieChart.Data("", 33),
      new PieChart.Data("77%", 77));
    // create a pieChart with valueList data.
    PieChart pieChart = new PieChart(valueList);
    pieChart.setTitle(I18n.get("home.student.graph.first"));
    graph.getChildren().addAll(pieChart, new Label(I18n.get("home.student.graph.second")));
  }
}
