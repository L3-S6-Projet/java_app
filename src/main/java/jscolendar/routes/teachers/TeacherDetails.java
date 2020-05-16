package jscolendar.routes.teachers;

import com.jfoenix.controls.JFXButton;
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

  public Label serviceDetails;
  //todo add margin to infoContent witch don't have icons
  @FXML
  private VBox calendar;
  @FXML
  private HBox body, header;
  @FXML
  private Label name, userName, email, phoneNumber, teacher;
  @FXML
  private Label title;
  @FXML
  private VBox services, subLeft, all, info;
  @FXML
  private JFXButton returnButton, edit;
  @FXML
  private JFXComboBox<Label> select;
  @FXML
  private JFXListView<Label> infoContent;
  private final Integer id;

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
      System.out.println(services);
      StringBuilder serviceContent = new StringBuilder();
      for (TeacherResponseTeacherServices service : services) {
        serviceContent.append(I18n.get("calendar.details.teacher.firstPart") + service.getClass().getName() + ',' + I18n.get("calendar.details.teacher.secondPart") + ',');
        if (service.getCm() != 0) {
          serviceContent.append(" " + service.getCm() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.cm") + ',');
        }
        if (service.getTp() != 0) {
          serviceContent.append(" " + service.getTp() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.tp") + ',');
        }
        if (service.getTd() != 0) {
          serviceContent.append(" " + service.getTd() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.td") + ',');
        }
        if (service.getProject() != 0) {
          serviceContent.append(" " + service.getProject() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.projet") + ',');
        }
        if (service.getAdministration() != 0) {
          serviceContent.append(" " + service.getAdministration() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.admin") + ',');
        }
        if (service.getExternal() != 0) {
          serviceContent.append(" " + service.getExternal() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart") + " " + I18n.get("calendar.details.teacher.extern") + ',');
        }
        serviceContent.substring(0, serviceContent.length() - 1);
        serviceContent.append(".\n");
      }
      serviceContent.append("\n" + I18n.get("calendar.details.services.value") + " " + result.getTeacher().getTotalService() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
      serviceDetails.setWrapText(true);
      serviceDetails.setText(serviceContent.toString());
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
