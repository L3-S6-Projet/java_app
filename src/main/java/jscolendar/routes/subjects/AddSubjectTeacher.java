package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXComboBox;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import io.swagger.client.model.TeacherListResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import jscolendar.components.AutoCompleteBox;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Teacher;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class AddSubjectTeacher extends VBox {
  private final int subjectId;
  @FXML private Label errorLabel;
  @FXML private JFXComboBox<Teacher> teacher;

  public AddSubjectTeacher (int subjectId) {
    this.subjectId = subjectId;
    FXUtil.loadFXML("/fxml/subjects/addSubjectTeacher.fxml", this, this, I18n.getBundle());
  }

  @FXML
  public void initialize () {
    teacher.setEditable(true);
    teacher.setConverter(new StringConverter<>() {
      @Override
      public String toString (Teacher teacher) {
        return teacher != null
          ? teacher.firstNameProperty().get().concat(" ").concat(teacher.lastNameProperty().get())
          : "";
      }

      @Override
      public Teacher fromString (String string) {
        return teacher.getSelectionModel().getSelectedItem();
      }
    });

    teacher.setCellFactory(combobox -> new ListCell<>() {
      @Override
      protected void updateItem (Teacher item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item == null || empty) return;

        setText(item.firstNameProperty().get().concat(" ").concat(item.lastNameProperty().get()));
      }
    });

    var teacherApi = new TeacherApi();
    FXApiService<String, TeacherListResponse> teacherListFXApiService = new FXApiService<>(
      query -> teacherApi.teachersGet(query, 1)
    );
    var teachersListAutoComplete = new AutoCompleteBox<>(teacher, teacherListFXApiService, teacherListResponse ->
      teacher.setItems(FXCollections.observableList(teacherListFXApiService.getValue().getTeachers().stream()
        .map(Teacher::new).collect(Collectors.toList())))
    );

    teachersListAutoComplete.textProperty.addListener(((observable, oldValue, newValue) ->
      errorLabel.setText(newValue)));
  }

  @FXML private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML private void onSubmit () {
    if (!teacher.validate()) return;

    var request = new IDRequest();
    request.add(teacher.getValue().id);
    doPost(request);
  }

  private void doPost (IDRequest request) {
    var api = new SubjectsApi();
    FXApiService<IDRequest, SimpleSuccessResponse> service = new FXApiService<>(req ->
      api.subjectsIdTeachersPost(subjectId, req));
    service.setRequest(request);

    service.setOnSucceeded(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(I18n.get("modal.addSubjectTeacher.success")));
    });

    service.setOnFailed(dontCare -> errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException())));

    service.restart();
  }
}
