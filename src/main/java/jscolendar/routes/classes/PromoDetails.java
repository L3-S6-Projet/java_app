package jscolendar.routes.classes;

import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.model.ClassResponse;
import io.swagger.client.model.Occupancies;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.models.ClassModel;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.text.MessageFormat;

import static jscolendar.util.datePickerContent.getContent;

public class PromoDetails extends BorderPane {
  private final ClassModel classModel;
  @FXML private Label title;
  @FXML private StackPane datePickerPane;
  @FXML private Label name, level, services;
  @FXML private JFXComboBox<Label> select;

  public PromoDetails (ClassModel classModel) {
    this.classModel = classModel;
    FXUtil.loadFXML("/fxml/classes/PromoDetails.fxml", this, this, I18n.getBundle());
  }

  @SuppressWarnings("Duplicates")
  @FXML
  private void initialize () {
    title.setText(I18n.get("calendar.title.promo") + " \"" + classModel.nameProperty().get() + '\"');
    name.setText(classModel.nameProperty().get());
    level.setText(classModel.levelProperty().get().name());

    var apiInstance = new ClassesApi();
    FXApiService<Integer, ClassResponse> classesService = new FXApiService<>(apiInstance::classesIdGet);

    classesService.setOnSucceeded(_e ->
      services.setText(MessageFormat.format(
        I18n.get("calendar.details.ue.menu.info.service"), classesService.getValue().getTotalService())));

    classesService.setOnFailed(_e ->
      this.fireEvent(new NotificationEvent(APIErrorUtil.getErrorMessage(classesService.getException()))));

    classesService.setRequest(classModel.getId());
    classesService.start();

    FXApiService<Pair<Integer, Integer>, Occupancies> service = new FXApiService<>(request ->
      apiInstance.classesIdOccupanciesGet(classModel.getId(), request.getKey(), request.getValue(), 0));
    var manager = new CalendarDataManager(new Calendar(), service);
    var calendarComponent = new CalendarComponent(manager);
    var calendarView = calendarComponent.getView();

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
    jfxDatePicker.setDialogParent(datePickerPane);
    jfxDatePicker.setOnAction(event -> calendarView.getSelectedPage().setDate(jfxDatePicker.getValue()));
    Node datePicker = getContent(jfxDatePicker);

    if (datePicker != null) {
      StackPane.setAlignment(datePicker, Pos.BOTTOM_LEFT);
      datePickerPane.getChildren().add(datePicker);
    }

    setCenter(calendarView);
    BorderPane.setMargin(calendarView, new Insets(0, 0, 15, 0));
  }

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }

  @FXML
  private void editButton () {
    this.fireEvent(new ModalEvent(ModalEvent.OPEN, new EditPromo()));
  }
}
