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
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.models.Student;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

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

    fetchService.setOnFailed(dontCare -> {
      // @TODO
      System.out.println(APIErrorUtil.getErrorMessage(fetchService.getException()));
    });

    fetchService.start();
  }

  @Override
  protected void delete () {
    // @TODO
  }

  @Override
  protected Region getModalContent () {
    return new CreateStudent();
  }

  @Override
  protected Region getDetailsView (Student item) {
    return new StudentDetails(item.getId());
  }
}
