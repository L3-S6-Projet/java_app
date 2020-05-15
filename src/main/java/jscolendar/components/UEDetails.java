package jscolendar.components;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import io.swagger.client.ApiException;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.SubjectResponse;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.modals.EditSubject;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;


public class UEDetails extends StackPane {

  private final Integer id;
  public Label teacherName;
  @FXML
  private VBox calendar;
  @FXML
  private VBox subLeft;
  @FXML
  private JFXTabPane menu;
  @FXML
  private Label name, promo, services;

  public UEDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/UEDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    SubjectsApi apiInstance = new SubjectsApi();
    SubjectResponse result = null;

    try {
      result = apiInstance.subjectsIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      name.setText(result.getSubject().getName());
      promo.setText(result.getSubject().getClassName());
      services.setText(I18n.get("calendar.details.ue.menu.info.serviceFirstPart") + " " + result.getSubject().getTotalHours() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));


      //todo other menu
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
      new ModalEvent(ModalEvent.OPEN, new EditSubject())
    );
  }

}
