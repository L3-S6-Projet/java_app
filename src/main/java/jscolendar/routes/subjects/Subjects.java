package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import io.swagger.client.model.SubjectListResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Subject;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class Subjects extends AbstractTableView<Subject> {
  @FXML private JFXTreeTableColumn<Subject, String> classname, name;

  private final SubjectsApi apiInstance = new SubjectsApi();
  private final FXApiService<Pair<String, Integer>, SubjectListResponse> fetchService = new FXApiService<>(
    request -> apiInstance.subjectsGet(request.getKey(), request.getValue())
  );
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteService = new FXApiService<>(
    apiInstance::subjectsDelete
  );

  @Override
  protected void initColumns () {
    classname.setCellValueFactory(param -> param.getValue().getValue().classNameProperty());
    name.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
  }

  @Override
  protected void fetchData () {
    var request = new Pair<>(search.getText(), page.get());
    fetchService.reset();
    fetchService.setRequest(request);
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var subjects = FXCollections.observableList(
        response.getSubjects().stream().map(Subject::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(subjects, RecursiveTreeObject::getChildren));
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
      .filter(Subject::isSelected)
      .map(Subject::getId)
      .collect(Collectors.toList()));
    deleteService.setRequest(idRequest);
    deleteService.setOnSucceeded(event -> {
      container.fireEvent(new NotificationEvent(I18n.get("subjects.delete.success")));
      fetchData();
      clearSelection();
    });
    deleteService.setOnFailed(dontCare ->
      container.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(deleteService.getException()))));

    deleteService.start();
  }

  @Override
  protected Region getModalContent () {
    return new CreateSubject();
  }

  @Override
  protected Region getDetailsView (Subject item) {
    return new SubjectDetails(item.getId());
  }
}
