package jscolendar.routes.students;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.api.StudentsApi;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import io.swagger.client.model.StudentListResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Student;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class Students extends AbstractTableView<Student> {
  @FXML private JFXTreeTableColumn<Student, String> firstName, lastName, className;

  private final StudentsApi apiInstance = new StudentsApi();
  private final FXApiService<Pair<String, Integer>, StudentListResponse> fetchService = new FXApiService<>(
    request -> apiInstance.studentsGet(request.getKey(), request.getValue())
  );
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteService = new FXApiService<>(
    apiInstance::studentsDelete
  );

  @Override
  protected void initColumns () {
    firstName.setCellValueFactory(param -> param.getValue().getValue().firstNameProperty());
    lastName.setCellValueFactory(param -> param.getValue().getValue().lastNameProperty());
    className.setCellValueFactory(param -> param.getValue().getValue().classNameProperty());
  }

  @Override
  protected void fetchData () {
    var request = new Pair<>(search.getText(), page.get());
    fetchService.reset();
    fetchService.setRequest(request);
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var students = FXCollections.observableList(
        response.getStudents().stream().map(Student::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(students, RecursiveTreeObject::getChildren));
    });

    fetchService.setOnFailed(dontCare -> container.fireEvent(
      new NotificationEvent(APIErrorUtil.getErrorMessage(fetchService.getException()))));

    fetchService.start();
  }

  @SuppressWarnings("Duplicates")
  @Override
  protected void delete () {
    deleteService.reset();

    var idRequest = new IDRequest();
    idRequest.addAll(table.getRoot().getChildren()
      .stream()
      .map(TreeItem::getValue)
      .filter(Student::isSelected)
      .map(Student::getId)
      .collect(Collectors.toList()));
    deleteService.setRequest(idRequest);
    deleteService.setOnSucceeded(event -> {
      container.fireEvent(new NotificationEvent(I18n.get("students.delete.success")));
      fetchData();
      clearSelection();
    });
    deleteService.setOnFailed(dontCare ->
      container.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(deleteService.getException()))));

    deleteService.start();
  }

  @Override
  protected Region getModalContent () {
    return new CreateStudent();
  }

  @Override
  protected Region getDetailsView (Student item) {
    return new StudentDetails(item);
  }
}
