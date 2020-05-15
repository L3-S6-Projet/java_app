package jscolendar.components;

import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.model.ClassResponse;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.modals.EditPromo;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class PromoDetails extends StackPane {

  private final Integer id;
  public Label title;
  @FXML
  private VBox calendar;
  @FXML
  private VBox subLeft;
  @FXML
  private Label name, level, services;

  public PromoDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/PromoDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {

    ClassesApi apiInstance = new ClassesApi();
    ClassResponse result = null;

    try {
      result = apiInstance.classesIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.promo") + " \"" + result.getPropertyClass().getName() + '\"');
      name.setText(result.getPropertyClass().getName());
      level.setText(result.getPropertyClass().getLevel().name());
      services.setText(I18n.get("calendar.details.ue.menu.info.serviceFirstPart") + " " + result.getTotalService() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
    }

    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> {
      System.out.println(jfxDatePicker.getValue());

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
    calendar.getChildren().add(new CalendarRoute());
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
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditPromo())
    );
  }
}
