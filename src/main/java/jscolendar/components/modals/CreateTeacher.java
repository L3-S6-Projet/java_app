package jscolendar.components.modals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.Rank;
import io.swagger.client.model.TeacherCreationRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.EnumSet;

public class CreateTeacher extends VBox {
  @FXML private JFXTextField firstName, lastName, email, phoneNumber;
  @FXML private JFXComboBox<Rank> grade;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;


  public CreateTeacher () {
    FXUtil.loadFXML("/fxml/modals/CreateTeacher.fxml", this, this, I18n.getBundle());
    grade.getItems().addAll(EnumSet.allOf(Rank.class));
  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE)
    );
  }

  @FXML
  private void onSubmit () {
    if (!validate()) return;

    var request = new TeacherCreationRequest();
    request.setFirstName(firstName.getText());
    request.setLastName(lastName.getText());
    request.setEmail(email.getText());
    request.setPhoneNumber(phoneNumber.getText());
    request.setRank(grade.getValue());
    doCreate(request);
  }

  private void doCreate (TeacherCreationRequest request) {
    var apiInstance = new TeacherApi();
    var service = new FXApiService<>(apiInstance::teachersPost);
    service.setRequest(request);

    service.setOnSucceeded(event -> {
      var response = service.getValue();
        this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
        this.fireEvent(new ModalEvent(
          ModalEvent.OPEN, new CreateUserSuccess(response.getUsername(), response.getPassword())));
    });

    service.setOnFailed(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(service.getException())));
    });

    service.start();
  }

  private boolean validate () {
    return firstName.validate() && lastName.validate() &&
        email.validate() && phoneNumber.validate() && grade.validate();
  }
}
