package jscolendar.routes.subjects;

import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.SimpleSuccessResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class AddSubjectGroup extends VBox {
  private final int subjectId;
  @FXML private Label errorLabel;

  public AddSubjectGroup (int subjectId) {
    this.subjectId = subjectId;
    FXUtil.loadFXML("/fxml/subjects/addSubjectGroup.fxml", this, this, I18n.getBundle());
  }

  @FXML private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML private void onSubmit () {
    var api = new SubjectsApi();
    FXApiService<Integer, SimpleSuccessResponse> service = new FXApiService<>(api::subjectsIdGroupsPost);
    service.setOnSucceeded(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(I18n.get("modal.addSubjectGroup.success")));
    });
    service.setOnFailed(dontCare -> errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException())));
    service.setRequest(subjectId);
    service.restart();
  }
}
