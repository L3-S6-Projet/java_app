package jscolendar.routes.subjects;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.api.SubjectsApi;
import io.swagger.client.model.IDRequest;
import io.swagger.client.model.Occupancies;
import io.swagger.client.model.SimpleSuccessResponse;
import io.swagger.client.model.SubjectResponse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.models.Subject;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import org.kordamp.ikonli.javafx.FontIcon;

import java.text.MessageFormat;

import static jscolendar.util.datePickerContent.getContent;

public class SubjectDetails extends BorderPane {
  @FXML private JFXComboBox<Label> select;
  @FXML private VBox subLeft, teachersTarget, groupsTarget;
  @FXML private Label title, name, promo, services;

  private final Subject subject;
  private final SubjectsApi api = new SubjectsApi();
  private final FXApiService<IDRequest, SimpleSuccessResponse> deleteTeacherService;
  private final FXApiService<Integer, SimpleSuccessResponse> deleteGroupService;

  private static class ListElement extends VBox {
    private final int id;

    private static class Builder {
      private final int id;
      private final HBox box = new HBox();
      private final Label subtitle = new Label();

      Builder (String title, int id) {
        this.id = id;
        box.setAlignment(Pos.CENTER_LEFT);
        final Label header = new Label(title);
        header.getStyleClass().add("details-item-header");
        box.getChildren().add(header);
      }

      Builder withHandler (EventHandler<ActionEvent> handler) {
        var spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        var icon = new FontIcon("mdi-delete");
        icon.setIconSize(24);
        icon.setIconColor(Color.GRAY);
        var button = new JFXButton();
        button.setGraphic(icon);
        button.setOnAction(handler);
        box.getChildren().addAll(spacer, button);

        return this;
      }

      Builder withSubtitle (String text) {
        subtitle.setText(text);
        subtitle.getStyleClass().add("details-item");

        return this;
      }

      ListElement build () {
        return new ListElement(box, subtitle, id);
      }
    }

    ListElement (Node title, Node subtitle, int id) {
      this.id = id;
      getChildren().addAll(title, subtitle);
    }
  }

  public SubjectDetails(Subject subject) {
    this.subject = subject;
    deleteTeacherService = new FXApiService<>(request ->
      api.subjectsIdTeachersDelete(subject.getId(), request));
    deleteGroupService = new FXApiService<>(api::subjectsIdGroupsDelete);

    FXUtil.loadFXML("/fxml/subjects/SubjectDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize() {
    title.setText(I18n.get("calendar.title.ue") + " \"" + subject.nameProperty().get() + '\"');
    name.setText(subject.nameProperty().get());
    promo.setText(subject.classNameProperty().get());

    FXApiService<Integer, SubjectResponse> subjectService = new FXApiService<>(api::subjectsIdGet);

    subjectService.setOnSucceeded(dontCare -> {
      var response = subjectService.getValue().getSubject();
      services.setText(MessageFormat.format(
        I18n.get("calendar.details.ue.menu.info.service"), response.getTotalHours()));

      for (var teacher : response.getTeachers()) {
        var item = new ListElement.Builder(
          teacher.getFirstName().concat(" ").concat(teacher.getLastName()), teacher.getId());

        if (teacher.getInCharge())
          item.withSubtitle((I18n.get("calendar.details.ue.menu.teacher.responsable")));
        else
          item.withHandler(this::onDeleteTeacher);

        teachersTarget.getChildren().add(item.build());
      }

      response.getGroups().forEach(group ->
        groupsTarget.getChildren().add(new ListElement.Builder(group.getName(), group.getId())
          .withSubtitle(group.getCount() + I18n.get("calendar.details.ue.menu.group.student"))
          .withHandler(this::onDeleteGroup).build()));
    });

    subjectService.setOnFailed(dontCare ->
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(subjectService.getException()))));

    subjectService.setRequest(subject.getId());
    subjectService.start();

    FXApiService<Pair<Integer, Integer>, Occupancies> service = new FXApiService<>(request ->
      api.subjectsIdOccupanciesGet(subject.getId(), request.getKey(), request.getValue(), 0));
    var manager = new CalendarDataManager(new Calendar(), service);
    CalendarComponent calendarComponent = new CalendarComponent(manager);
    CalendarView calendarView = calendarComponent.getView();

    select.getSelectionModel().select(2);

    select.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      switch (newValue.getId()) {
        case "day":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.DAY_VIEW) return;
          calendarView.showDayPage();
          break;
        case "week":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.WEEK_VIEW) return;
          calendarView.showWeekPage();
          break;
        case "month":
          if (calendarView.getSelectedPage().getPrintViewType() == ViewType.MONTH_VIEW) return;
          calendarView.showMonthPage();
          break;
      }
    });

    JFXDatePicker jfxDatePicker = new JFXDatePicker();
    jfxDatePicker.setOnAction(event -> calendarView.getSelectedPage().setDate(jfxDatePicker.getValue()));
    Node datePicker = getContent(jfxDatePicker);
    if (datePicker != null)
      subLeft.getChildren().add(datePicker);

    setCenter(calendarView);
  }

  @FXML
  private void returnToPrevView() {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  private void onDeleteTeacher (ActionEvent event) {
    final var node = ((Node) event.getSource()).getParent().getParent();

    deleteTeacherService.setOnSucceeded(dontCare -> teachersTarget.getChildren().remove(node));
    deleteTeacherService.setOnFailed(dontCare -> this.fireEvent(
      new NotificationEvent(APIErrorUtil.getErrorMessage(deleteTeacherService.getException()))));

    var request = new IDRequest();
    request.add(((ListElement) node).id);
    deleteTeacherService.setRequest(request);
    deleteTeacherService.restart();
  }

  private void onDeleteGroup (ActionEvent event) {
    final var node = ((Node) event.getSource()).getParent().getParent();

    deleteGroupService.setOnSucceeded(dontCare -> groupsTarget.getChildren().remove(node));
    deleteGroupService.setOnFailed(dontCare -> this.fireEvent(
      new NotificationEvent(APIErrorUtil.getErrorMessage(deleteTeacherService.getException()))));

    deleteGroupService.setRequest(subject.getId());
    deleteGroupService.restart();
  }


  @FXML
  private void editButton() {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new EditSubject()));
  }

  @FXML
  private void onAddTeacher () {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new AddSubjectTeacher(subject.id)));
    // TODO :: refresh
  }

  @FXML
  private void onAddGroup () {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new AddSubjectGroup(subject.id)));
    // @TODO :: refresh
  }
}
