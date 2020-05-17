package jscolendar.routes.classes;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.model.ClassesList;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.ClassModel;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.I18n;

import java.util.stream.Collectors;

public class Classes extends AbstractTableView<ClassModel> {
  @FXML private JFXTreeTableColumn<ClassModel, String> name, level;

  private final ClassesApi apiInstance = new ClassesApi();
  private final FXApiService<Pair<String, Integer>, ClassesList> fetchService = new FXApiService<>(
    request -> apiInstance.classesGet(request.getKey(), request.getValue())
  );
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteService = new FXApiService<>(
    apiInstance::classesDelete
  );

  @Override
  protected void initColumns () {
    name.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
    level.setCellValueFactory(param -> param.getValue().getValue().levelProperty().asString());
  }

  @Override
  protected void fetchData () {
    var request = new Pair<>(search.getText(), page.get());
    fetchService.reset();
    fetchService.setRequest(request);
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var classes = FXCollections.observableList(
        response.getClasses().stream().map(ClassModel::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(classes, RecursiveTreeObject::getChildren));
    });

    deleteService.setOnFailed(dontCare ->
      container.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(deleteService.getException()))));

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
      .filter(ClassModel::isSelected)
      .map(ClassModel::getId)
      .collect(Collectors.toList()));
    deleteService.setRequest(idRequest);
    deleteService.setOnSucceeded(event -> {
      container.fireEvent(new NotificationEvent(I18n.get("class.delete.success")));
      fetchData();
      clearSelection();
    });
    deleteService.setOnFailed(dontCare ->
      container.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(deleteService.getException()))));

    deleteService.start();
  }

  @Override
  protected Region getModalContent () {
    return new CreatePromo();
  }

  @Override
  protected Region getDetailsView (ClassModel item) {
    return new PromoDetails(item.id);
  }
}
