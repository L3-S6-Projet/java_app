package jscolendar.components.modals;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.model.ModelClass;
import io.swagger.client.model.ModelClass.*;
import io.swagger.client.model.Level;
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

public class CreatePromo extends VBox {
  @FXML private JFXTextField name;
  @FXML private JFXComboBox<Level> level;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;

  public CreatePromo () {
    FXUtil.loadFXML("/fxml/modals/CreatePromo.fxml", this, this, I18n.getBundle());
  }

  @FXML
  public void initialize () {
    level.getItems().addAll(EnumSet.allOf(Level.class));
  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new CreateTeacher())
    );
  }

  @FXML
  private void onSubmit () {
    if (!validate()) return;

    var request = new ModelClass();
    request.setName(name.getText());
    request.setLevel(level.getValue());
    doCreate(request);
  }

  private void doCreate (ModelClass request) {
    var apiInstance = new ClassesApi();
    var service = new FXApiService<>(apiInstance::classesPost);
    service.setRequest(request);

    service.setOnSucceeded(dontCare -> {
        this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
        this.fireEvent(new NotificationEvent(I18n.get("create.class.success")));
    });

    service.setOnFailed(dontCare ->
      errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException())));
    service.start();
  }

  private boolean validate () {
    return name.validate() && level.validate();
  }
}
