package jscolendar.routes.students;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.model.ClassesList;
import io.swagger.client.model.StudentCreationRequest;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import jscolendar.components.AutoCompleteBox;
import jscolendar.components.modals.CreateUserSuccess;
import jscolendar.events.ModalEvent;
import jscolendar.models.ClassModel;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class CreateStudent extends VBox {
  @FXML private JFXTextField firstName, lastName;
  @FXML private JFXComboBox<ClassModel> comboBox;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;

  public CreateStudent () {
    FXUtil.loadFXML("/fxml/students/CreateStudent.fxml", this, this, I18n.getBundle());
  }


  @FXML
  @SuppressWarnings("Duplicates")
  public void initialize () {
    comboBox.setEditable(true);
    comboBox.setConverter(new StringConverter<>() {
      @Override
      public String toString (ClassModel classModel) {
        return classModel != null
          ? classModel.nameProperty().get()
          : "";
      }

      @Override
      public ClassModel fromString (String string) {
        return comboBox.getSelectionModel().getSelectedItem();
      }
    });

    comboBox.setCellFactory(combobox -> new ListCell<>() {
      @Override
      protected void updateItem (ClassModel item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item == null || empty) return;

        setText(item.nameProperty().get());
      }
    });

    var apiInstance = new ClassesApi();
    FXApiService<String, ClassesList> service = new FXApiService<>(
      query -> apiInstance.classesGet(query, 1)
    );
    var autoComplete = new AutoCompleteBox<>(comboBox, service, classesList ->
      comboBox.setItems(FXCollections.observableList(service.getValue().getClasses().stream()
        .map(ClassModel::new).collect(Collectors.toList())))
    );

    autoComplete.textProperty.addListener((observable, oldValue, newValue) ->
      errorLabel.setText(newValue));
  }

  @FXML
  private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML
  private void onSubmit () {
    if (!validate()) return;

    var request = new StudentCreationRequest();
    request.setFirstName(firstName.getText().strip());
    request.setLastName(lastName.getText().strip());
    request.setClassId(comboBox.getSelectionModel().getSelectedItem().id);
    doCreate(request);
  }

  @SuppressWarnings("Duplicates")
  private void doCreate (StudentCreationRequest request) {
    var apiInstance = new StudentsApi();
    var service = new FXApiService<>(apiInstance::studentsPost);
    service.setRequest(request);

    service.setOnSucceeded(event -> {
      var response = service.getValue();
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new ModalEvent(
        ModalEvent.OPEN, new CreateUserSuccess(response.getUsername(), response.getPassword())));
    });

    service.setOnFailed(dontCare ->
      errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException()))
    );

    service.start();
  }

  private boolean validate () {
    return firstName.validate() && lastName.validate() && comboBox.validate();
  }
}
