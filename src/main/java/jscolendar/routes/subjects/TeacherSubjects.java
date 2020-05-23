package jscolendar.routes.subjects;

import com.jfoenix.controls.JFXTreeTableColumn;
import io.swagger.client.api.TeacherApi;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import jscolendar.UserSession;
import jscolendar.components.AbstractSmallTableView;
import jscolendar.events.NotificationEvent;
import jscolendar.models.TeacherSubject;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.util.stream.Collectors;

public class TeacherSubjects extends AbstractSmallTableView<TeacherSubject> {
  @FXML private JFXTreeTableColumn<TeacherSubject, String> classname, name;

  private final TeacherApi api = new TeacherApi();
  private final FXApiService<Integer, io.swagger.client.model.TeacherSubjects> service = new FXApiService<>(
    api::teachersIdSubjectsGet
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
      items = response.getSubjects().stream().map(TeacherSubject::new).collect(Collectors.toList());
      setItems();
    });

    service.setOnFailed(dontCare -> container.fireEvent(
      new NotificationEvent(APIErrorUtil.getErrorMessage(service.getException()))
    ));

    service.start();
  }

  @Override
  protected Region getDetailsView (TeacherSubject item) {
    return new TeacherSubjectDetails(item);
  }
}
