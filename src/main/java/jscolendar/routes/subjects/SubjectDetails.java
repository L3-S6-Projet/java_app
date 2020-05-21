package jscolendar.routes.subjects;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import io.swagger.client.ApiException;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.SubjectResponse;
import io.swagger.client.model.SubjectResponseSubjectGroups;
import io.swagger.client.model.SubjectResponseSubjectTeachers;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.UserSession;
import jscolendar.components.CalendarComponent;
import jscolendar.components.CalendarRoute;
import jscolendar.events.ModalEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import org.kordamp.ikonli.javafx.FontIcon;

import static jscolendar.util.datePickerContent.getContent;


public class SubjectDetails extends StackPane {

  private final Integer id;
  @FXML
  private JFXComboBox<Label> select;

  @FXML
  private Tab menuEnseig, menuGroup;

  @FXML
  private HBox header;

  @FXML
  private VBox calendar, subLeft;
  @FXML
  private Label title, name, promo, services;

  public SubjectDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/subjects/SubjectDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {
    SubjectsApi apiInstance = new SubjectsApi();
    SubjectResponse result = null;

    try {
      result = apiInstance.subjectsIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.ue") + " \"" + result.getSubject().getName() + '\"');
      name.setText(result.getSubject().getName());
      promo.setText(result.getSubject().getClassName());
      services.setText(I18n.get("calendar.details.ue.menu.info.serviceFirstPart") + " " + result.getSubject().getTotalHours() + I18n.get("calendar.details.ue.menu.info.serviceSecondPart"));
      var enseign = result.getSubject().getTeachers();
      JFXListView<VBox> enseignContent = new JFXListView<>();
      for (SubjectResponseSubjectTeachers teachers : enseign) {
        VBox content = new VBox();
        if (teachers.getInCharge()) {
          Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
          Label responsibility = new Label(I18n.get("calendar.details.ue.menu.teacher.responsable"));
          content.getChildren().addAll(teacherName, responsibility);
        } else {
          Label teacherName = new Label(teachers.getFirstName() + " " + teachers.getLastName());
          FontIcon icon = new FontIcon("mdi-delete");
          icon.setOnMouseClicked(event -> supprElement(event));
          teacherName.setGraphic(new FontIcon("mdi-delete"));
          content.getChildren().addAll(teacherName);
        }
        enseignContent.getItems().add(content);
      }
      menuEnseig.setContent(enseignContent);
      var groups = result.getSubject().getGroups();

      int nb = 0;
      JFXListView<VBox> groupContent = new JFXListView<>();
      for (SubjectResponseSubjectGroups group : groups) {
        VBox content = new VBox();
        Label groupName = new Label(group.getName());
        Label subtitle = new Label(group.getCount() + I18n.get("calendar.details.ue.menu.group.student"));
        if (nb > 1) {
          FontIcon icon = new FontIcon("mdi-delete");
          icon.setOnMouseClicked(event -> supprElement(event));
          groupName.setGraphic(new FontIcon("mdi-delete"));
        }
        nb++;
        content.getChildren().addAll(groupName, subtitle);
        groupContent.getItems().add(content);
      }
      Label groupNote = new Label(I18n.get("calendar.details.ue.menu.group.note"));
      groupNote.setWrapText(true);
      groupNote.setStyle("-fx-alignment: top-left");
      VBox box = new VBox();
      box.getChildren().addAll(groupNote, groupContent);
      menuGroup.setContent(box);
    }





    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var subjectApi = new SubjectsApi();
    service = new FXApiService<>(request ->
      subjectApi.subjectsIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));
    var manager = new CalendarDataManager(new Calendar(), service);
    CalendarComponent calendarComponent = new CalendarComponent(manager);
    CalendarView calendarView = calendarComponent.getView();


    select.getSelectionModel().select(2);

    select.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->  {
      switch (newValue.getId()) {
        case "day":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.DAY_VIEW) return;
          calendarView.showDayPage(); break;
        case "week":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.WEEK_VIEW) return;
          calendarView.showWeekPage(); break;
        case "month":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.MONTH_VIEW) return;
          calendarView.showMonthPage(); break;
      }
    });

    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> {
      calendarView.getSelectedPage().setDate(jfxDatePicker.getValue());

    });
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);
    calendar.getChildren().add(calendarView);
  }

  private void supprElement (MouseEvent event) {
    //todo make suppr popup
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }


  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditSubject())
    );
  }

}
