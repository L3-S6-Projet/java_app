package jscolendar.routes.classrooms;

import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.ClassroomApi;
import io.swagger.client.model.ClassroomGetResponse;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;

public class RoomDetails extends StackPane {


  private final Integer id;
  public Label title;
  @FXML
  private VBox calendar;
  @FXML
  private VBox subLeft;
  @FXML
  private Label name, capacity;

  public RoomDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/classrooms/RoomDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {

    ClassroomApi apiInstance = new ClassroomApi();
    ClassroomGetResponse result = null;

    try {
      result = apiInstance.classroomsIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.room") + " \"" + result.getClassroom().getName() + '\"');
      name.setText(result.getClassroom().getName());
      capacity.setText(String.valueOf(result.getClassroom().getCapacity()));
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
      new ModalEvent(ModalEvent.OPEN, new EditRoom())
    );
  }


}
