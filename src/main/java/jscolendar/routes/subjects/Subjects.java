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
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.models.Subject;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

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

    fetchService.setOnFailed(dontCare -> System.out.println(APIErrorUtil.getErrorMessage(fetchService.getException())));
    fetchService.start();
  }

  @Override
  protected void delete () {
    // @TODO
    deleteService.reset();
  }

  @Override
  protected Region getModalContent () {
    return new CreateSubject();
  }

  @Override
  protected Region getDetailsView (Subject item) {
    // @TODO
    return new SubjectDetails(item.getId());
  }
}
