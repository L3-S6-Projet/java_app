package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXTreeTableColumn;
import io.swagger.client.api.StudentsApi;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import jscolendar.UserSession;
import jscolendar.components.AbstractSmallTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.StudentSubject;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.util.stream.Collectors;

public class StudentSubjects extends AbstractSmallTableView<StudentSubject> {
  @FXML private JFXTreeTableColumn<StudentSubject, String> classname, name;

  private final StudentsApi api = new StudentsApi();
  private final FXApiService<Integer, io.swagger.client.model.StudentSubjects> service = new FXApiService<>(
    api::studentsIdSubjectsGet
  );

  @Override
  protected void initColumns () {
    classname.setCellValueFactory(param -> param.getValue().getValue().classnameProperty());
    name.setCellValueFactory(param -> param.getValue().getValue().nameProperty());
  }

  @SuppressWarnings("Duplicates")
  @Override
  protected void fetchData () {
    service.setRequest(UserSession.getInstance().getUser().getId());
    service.setOnSucceeded(event ->  {
      var response = service.getValue();
      total.set(response.getSubjects().size());
      items = response.getSubjects().stream().map(StudentSubject::new).collect(Collectors.toList());
      setItems();
    });

    service.setOnFailed(dontCare -> container.fireEvent(
      new NotificationEvent(APIErrorUtil.getErrorMessage(service.getException()))
    ));

    service.start();
  }

  @Override
  protected Region getDetailsView (StudentSubject item) {
    // @TODO
    return null;
  }
}
