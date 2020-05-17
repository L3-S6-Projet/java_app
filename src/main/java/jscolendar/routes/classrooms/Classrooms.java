package jscolendar.routes.classrooms;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.api.ClassroomApi;
import io.swagger.client.model.ClassroomsList;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.SimpleSuccessResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import jscolendar.components.AbstractTableView;
import jscolendar.models.Classroom;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.util.stream.Collectors;

public class Classrooms extends AbstractTableView<Classroom> {
  @FXML private JFXTreeTableColumn<Classroom, String> name;
  @FXML private JFXTreeTableColumn<Classroom, Integer> capacity;

  private final ClassroomApi apiInstance = new ClassroomApi();
  private final FXApiService<Pair<String, Integer>, ClassroomsList> fetchService = new FXApiService<>(
    request -> apiInstance.classroomsGet(request.getKey(), request.getValue())
  );
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteService = new FXApiService<>(
    apiInstance::classroomsDelete
  );

  @Override
  protected void initColumns () {
    name.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
    capacity.setCellValueFactory(param -> param.getValue().getValue().capacityProperty().asObject());
  }

  @Override
  protected void fetchData () {
    var request = new Pair<>(search.getText(), page.get());
    fetchService.reset();
    fetchService.setRequest(request);
    fetchService.setOnSucceeded(event -> {
      var response = fetchService.getValue();
      total.set(response.getTotal());
      var classrooms = FXCollections.observableList(
        response.getClassrooms().stream().map(Classroom::new).collect(Collectors.toList())
      );
      table.setRoot(new RecursiveTreeItem<>(classrooms, RecursiveTreeObject::getChildren));
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
    return new CreateRoom();
  }

  @Override
  protected Region getDetailsView (Classroom item) {
    // @TODO
    return new RoomDetails(item.id);
  }
}
