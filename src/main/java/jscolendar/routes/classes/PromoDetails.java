package jscolendar.routes.classes;

import com.calendarfx.view.CalendarView;
import com.calendarfx.view.print.ViewType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import io.swagger.client.ApiException;
import io.swagger.client.api.ClassesApi;
import io.swagger.client.model.ClassResponse;
import io.swagger.client.model.Occupancies;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import jscolendar.components.CalendarComponent;
import jscolendar.events.ModalEvent;
import jscolendar.models.Calendar;
import jscolendar.models.CalendarDataManager;
import jscolendar.util.FXApiService;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

import java.text.MessageFormat;

import static jscolendar.util.datePickerContent.getContent;

public class PromoDetails extends StackPane {

  private final Integer id;
  public Label title;
  @FXML
  private VBox calendar;
  @FXML
  private VBox subLeft;
  @FXML
  private Label name, level, services;
  @FXML
  private JFXComboBox<Label> select;

  public PromoDetails (Integer id) {
    this.id = id;
    FXUtil.loadFXML("/fxml/classes/PromoDetails.fxml", this, this, I18n.getBundle());
  }

  @FXML
  private void initialize () {

    ClassesApi apiInstance = new ClassesApi();
    ClassResponse result = null;

    try {
      result = apiInstance.classesIdGet(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling api");
      e.printStackTrace();
    }
    if (result != null) {
      title.setText(I18n.get("calendar.title.promo") + " \"" + result.getPropertyClass().getName() + '\"');
      name.setText(result.getPropertyClass().getName());
      level.setText(result.getPropertyClass().getLevel().name());
      services.setText(MessageFormat.format(I18n.get("calendar.details.ue.menu.info.service"), result.getTotalService()));
      services.setWrapText(true);
    }



    FXApiService<Pair<Integer, Integer>, Occupancies> service = null;
    var promoAPI = new ClassesApi();
    service = new FXApiService<>(request ->
      promoAPI.classesIdOccupanciesGet(id, request.getKey(), request.getValue(), 0));


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

  @FXML
  private void returnToPrevView () {
    ((StackPane) this.getParent()).getChildren().remove(this);
  }


  @FXML
  private void editButton () {
    this.fireEvent(
      new ModalEvent(ModalEvent.OPEN, new EditPromo())
    );
  }
}
