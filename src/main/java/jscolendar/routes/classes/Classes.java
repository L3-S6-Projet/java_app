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
import javafx.scene.layout.Region;
import jscolendar.components.AbstractTableView;
import jscolendar.models.ClassModel;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.util.stream.Collectors;

public class Classes extends AbstractTableView<ClassModel> {
  @FXML private JFXTreeTableColumn<ClassModel, String> name, level;

  private final ClassesApi apiInstance = new ClassesApi();
  private final FXApiService<Integer, ClassesList> fetchService = new FXApiService<>(
    page -> apiInstance.classesGet("", page)
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
    fetchService.reset();

    fetchService.setRequest(page.get());
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var classes = FXCollections.observableList(
        response.getClasses().stream().map(ClassModel::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(classes, RecursiveTreeObject::getChildren));
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
    return new CreatePromo();
  }

  @Override
  protected Region getDetailsView (ClassModel item) {
    // @TODO
    return new PromoDetails(item.id);
  }
}
