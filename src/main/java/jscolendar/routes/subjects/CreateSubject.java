package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.ClassesList;
import io.swagger.client.model.Subject;
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
import jscolendar.models.ClassModel;
import jscolendar.models.Teacher;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class CreateSubject extends VBox {
  @FXML private JFXTextField name;
  @FXML private JFXComboBox<ClassModel> promo;
  @FXML private JFXComboBox<Teacher> teacher;
  @FXML private Label errorLabel;
  @FXML private JFXButton cancel, save;


  public CreateSubject () {
    FXUtil.loadFXML("/fxml/subjects/CreateSubject.fxml", this, this, I18n.getBundle());
  }

  @FXML
  @SuppressWarnings("Duplicates")
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

    promo.setEditable(true);
    promo.setConverter(new StringConverter<>() {
      @Override
      public String toString (ClassModel classModel) {
        return classModel != null
          ? classModel.nameProperty().get()
          : "";
      }

      @Override
      public ClassModel fromString (String string) {
        return promo.getSelectionModel().getSelectedItem();
      }
    });

    promo.setCellFactory(combobox -> new ListCell<>() {
      @Override
      protected void updateItem (ClassModel item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);
        if (item == null || empty) return;

        setText(item.nameProperty().get());
      }
    });

    var classesApi = new ClassesApi();
    FXApiService<String, ClassesList> classesListFXApiService = new FXApiService<>(
      query -> classesApi.classesGet(query, 1)
    );
    var classesListAutoComplete = new AutoCompleteBox<>(promo, classesListFXApiService, classesList ->
      promo.setItems(FXCollections.observableList(classesListFXApiService.getValue().getClasses().stream()
        .map(ClassModel::new).collect(Collectors.toList())))
    );

    classesListAutoComplete.textProperty.addListener((observable, oldValue, newValue) ->
      errorLabel.setText(newValue));

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

  @FXML
  private void onCancel () {
    this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
  }

  @FXML
  private void onSubmit () {
    if (!validate()) return;

    var request = new Subject();
    request.setName(name.getText().strip());
    request.setClassId(promo.getSelectionModel().getSelectedItem().id);
    request.setTeacherInChargeId(teacher.getSelectionModel().getSelectedItem().id);
    doCreate(request);
  }

  private void doCreate (Subject request) {
    var apiInstance = new SubjectsApi();
    var service = new FXApiService<>(apiInstance::subjectsPost);
    service.setRequest(request);

    service.setOnSucceeded(dontCare -> {
      this.fireEvent(new ModalEvent(ModalEvent.CLOSE));
      this.fireEvent(new NotificationEvent(I18n.get("create.subject.success")));
    });

    service.setOnFailed(dontCare ->
      errorLabel.setText(APIErrorUtil.getErrorMessage(service.getException()))
    );

    service.start();
  }

  private boolean validate () {
    return name.validate() && promo.validate() && teacher.validate();
  }
}
