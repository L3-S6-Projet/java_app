package jscolendar.routes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.util.I18n;

public class HomeStudent {


  public VBox container;
  public Label header;


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

  }
}
