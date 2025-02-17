package jscolendar.routes.teachers;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import io.swagger.client.model.TeacherListResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Teacher;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class Teachers extends AbstractTableView<Teacher> {
  @FXML private JFXTreeTableColumn<Teacher, String> firstName, lastName, email, phoneNumber;

  private final TeacherApi apiInstance = new TeacherApi();
  private final FXApiService<Pair<String, Integer>, TeacherListResponse> fetchService = new FXApiService<>(
    request -> apiInstance.teachersGet(request.getKey(), request.getValue())
  );
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteService = new FXApiService<>(
    apiInstance::teachersDelete);

  @Override
  protected void initColumns () {
    firstName.setCellValueFactory(param -> param.getValue().getValue().firstNameProperty());
    lastName.setCellValueFactory(param -> param.getValue().getValue().lastNameProperty());
    email.setCellValueFactory(param -> param.getValue().getValue().emailProperty());
    phoneNumber.setCellValueFactory(param -> param.getValue().getValue().phoneNumberProperty());
  }

  @Override
  protected void fetchData () {
    var request = new Pair<>(search.getText(), page.get());
    fetchService.reset();
    fetchService.setRequest(request);
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var teachers = FXCollections.observableList(
        response.getTeachers().stream().map(Teacher::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(teachers, RecursiveTreeObject::getChildren));
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
      .filter(Teacher::isSelected)
      .map(Teacher::getId)
      .collect(Collectors.toList()));
    deleteService.setRequest(idRequest);
    deleteService.setOnSucceeded(event -> {
      container.fireEvent(new NotificationEvent(I18n.get("teachers.delete.success")));
      fetchData();
      clearSelection();
    });
    deleteService.setOnFailed(dontCare ->
      container.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(deleteService.getException()))));

    deleteService.start();
  }

  @Override
  protected Region getModalContent () {
    return new CreateTeacher();
  }

  @Override
  protected Region getDetailsView (Teacher teacher) {
    return new TeacherDetails(teacher);
  }
}
