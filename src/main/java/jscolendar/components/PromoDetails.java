package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class PromoDetails extends StackPane {


  public PromoDetails () {
    FXUtil.loadFXML("/fxml/PromoDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void selectCalendarType () {
  }

  @FXML
  private void editButton () {
  }
}
