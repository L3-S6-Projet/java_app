package jscolendar.components.modals;

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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.ClassModel;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

// @TODO :: a lot
public class CreateStudent extends VBox {
  @FXML private JFXTextField firstName, lastName;
  @FXML private JFXComboBox<ClassModel> comboBox;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;

  private final ClassesApi apiInstance = new ClassesApi();
  private final FXApiService<String, ClassesList> fetchService = new FXApiService<>(
    query -> apiInstance.classesGet(query, 1)
  );

  public CreateStudent () {
    FXUtil.loadFXML("/fxml/modals/CreateStudent.fxml", this, this, I18n.getBundle());
  }

  @FXML
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

    comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
      comboBox.getSelectionModel().select(newValue.intValue()));

    comboBox.getEditor().focusedProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue) {
        comboBox.show();
      } else {
        if (comboBox.getSelectionModel().isEmpty())
         comboBox.getEditor().setText("");
        comboBox.hide();
      }
    }));

    comboBox.setOnKeyReleased(event -> {
      comboBox.hide();
      var keyCode = event.getCode();
      if (keyCode == KeyCode.UP || keyCode == KeyCode.DOWN ||
        keyCode == KeyCode.RIGHT || keyCode == KeyCode.LEFT ||
        keyCode == KeyCode.TAB || keyCode == KeyCode.ENTER) return;

      if (keyCode == KeyCode.BACK_SPACE)
        comboBox.getSelectionModel().clearSelection();

      if (keyCode == KeyCode.SPACE && comboBox.getSelectionModel().getSelectedIndex() != -1) {
        comboBox.getEditor().positionCaret(comboBox.getEditor().getText().length());
        return;
      }

      fetchService.reset();
      fetchService.setRequest(comboBox.getEditor().getText());
      fetchService.setOnSucceeded(dontCare -> {
        comboBox.setItems(FXCollections.observableList(fetchService.getValue().getClasses().stream()
          .map(ClassModel::new).collect(Collectors.toList())));
        comboBox.show();
        comboBox.getEditor().positionCaret(comboBox.getEditor().getText().length());
      });

      fetchService.setOnFailed(dontCare ->
        errorLabel.setText(APIErrorUtil.getErrorMessage(fetchService.getException())));

      fetchService.start();
    });
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

    service.setOnFailed(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(service.getException())));
    });

    service.start();
  }

  private boolean validate () {
    return firstName.validate() && lastName.validate() && comboBox.validate();
  }
}
