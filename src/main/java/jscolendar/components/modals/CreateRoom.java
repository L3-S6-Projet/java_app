package jscolendar.components.modals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.ClassroomApi;
import io.swagger.client.model.ClassroomCreationRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class CreateRoom extends VBox {
  @FXML private JFXTextField name, capacity;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;

  public CreateRoom () {
    FXUtil.loadFXML("/fxml/modals/CreateRoom.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML
  private void onSubmit () {
    if (!validate()) return;

    var request = new ClassroomCreationRequest();
    request.setName(name.getText());
    request.setCapacity(Integer.parseInt(capacity.getText()));
    doCreate(request);
  }

  private void doCreate (ClassroomCreationRequest request) {
    var apiInstance = new ClassroomApi();
    var service = new FXApiService<>(apiInstance::classroomsPost);
    service.setRequest(request);

    service.setOnSucceeded(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(I18n.get("create.classroom.success")));
    });

    service.setOnFailed(dontCare ->
      errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException())));

    service.start();
  }

  private boolean validate () {
    return name.validate() && capacity.validate();
  }
}
