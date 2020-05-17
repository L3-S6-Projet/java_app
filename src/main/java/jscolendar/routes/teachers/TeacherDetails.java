package jscolendar.routes.teachers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.TeacherResponse;
import io.swagger.client.model.TeacherResponseTeacherServices;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import static jscolendar.util.datePickerContent.getContent;


public class TeacherDetails extends StackPane {

  private final Integer id;
  public HBox header;
  //todo add margin to infoContent witch don't have icons

  @FXML
  private Label title, serviceDetails, name, userName, email, phoneNumber, teacher;
  @FXML
  private VBox calendar, subLeft;
  @FXML
  private JFXComboBox<Label> select;
  @FXML
  private JFXListView<Label> infoContent;

  public TeacherDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/teachers/TeacherDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    infoContent = new JFXListView<>();
    select.getSelectionModel().selectLast();


    TeacherApi apiInstance = new TeacherApi();
    TeacherResponse result = null;

    try {
      result = apiInstance.teachersIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeacherApi#teachersGet");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.enseinant") + " \"" + result.getTeacher().getFirstName() + " " + result.getTeacher().getLastName() + '\"');
      name.setText(result.getTeacher().getFirstName() + " " + result.getTeacher().getLastName());
      userName.setText(result.getTeacher().getUsername());
      email.setText(result.getTeacher().getEmail());
      phoneNumber.setText(result.getTeacher().getPhoneNumber());
      teacher.setText("Professeur");

      var services = result.getTeacher().getServices();
      StringBuilder serviceContent = new StringBuilder();
      for (TeacherResponseTeacherServices service : services) {
        buildServiceString(serviceContent, service);
      }
      serviceContent.append("\n").append(I18n.get("calendar.details.services.value")).append(" ").append(result.getTeacher().getTotalService()).append(I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
      serviceDetails.setText(serviceContent.toString());
      serviceDetails.setWrapText(true);
    }
    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> {
      System.out.println(jfxDatePicker.getValue());
      //todo show the daily calendar

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
    CalendarRoute calendarRoute = new CalendarRoute();
    calendar.getChildren().add(calendarRoute);
  }

  private void buildServiceString (StringBuilder serviceContent, TeacherResponseTeacherServices service) {
    serviceContent.append(I18n.get("calendar.details.teacher.firstPart")).append(service.getPropertyClass()).append(',').append(I18n.get("calendar.details.teacher.secondPart")).append(",\n");
    if (service.getCm() != null && service.getCm() != 0) {
      serviceContent.append(" ").append(service.getCm()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.cm")).append(',');
    }
    if (service.getTp() != null && service.getTp() != 0) {
      serviceContent.append(" ").append(service.getTp()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.tp")).append(',');
    }
    if (service.getTd() != null && service.getTd() != 0) {
      serviceContent.append(" ").append(service.getTd()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.td")).append(',');
    }
    if (service.getProject() != null && service.getProject() != 0) {
      serviceContent.append(" ").append(service.getProject()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.projet")).append(',');
    }
    if (service.getAdministration() != null && service.getAdministration() != 0) {
      serviceContent.append(" ").append(service.getAdministration()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.admin")).append(',');
    }
    if (service.getExternal() != null && service.getExternal() != 0) {
      serviceContent.append(" ").append(service.getExternal()).append(I18n.get("calendar.details.teacher.hours")).append(" ").append(I18n.get("calendar.details.teacher.extern")).append(',');
    }
    serviceContent.deleteCharAt(serviceContent.length() - 1);
    serviceContent.append(".\n");
  }


  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditTeacher())
    );

  }

  @FXML
  private void selectCalendarType () {
    //todo change calendar type
  }

}
