package jscolendar.components;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import jscolendar.UserSession;
import jscolendar.components.popup.CreateTeacher;
import jscolendar.events.ModalEvent;
import jscolendar.models.Nav;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;
import java.util.stream.Collectors;

public class SideBar extends GridPane {
  @FXML private Label username, userRoleLabel;
  @FXML private ListView<Nav.NavElement> nav;

  private static class NavElementListCell extends ListCell<Nav.NavElement> {
    private final HBox cellContent = new HBox();

    NavElementListCell () {
      cellContent.setAlignment(Pos.CENTER_LEFT);
      cellContent.getStyleClass().add("nav-cell-container");
    }

    @Override
    protected void updateItem (Nav.NavElement item, boolean empty) {
      super.updateItem(item, empty);
      if (empty) return;

      setGraphic(null);
      setText(null);

      var icon = new FontIcon(item.icon);
      icon.setIconSize(24);

      cellContent.getChildren().clear();
      cellContent.getChildren().addAll(List.of(
        icon,
        new Label(item.name)
      ));

      setGraphic(cellContent);
      setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
  }

  public SideBar () {
    FXUtil.loadFXML("/fxml/SideBar.fxml", this, this, I18n.getBundle());

    var user = UserSession.getInstance().getUser();
    username.textProperty().set(user.getFirstName().concat(" ").concat(user.getLastName()));
    userRoleLabel.textProperty().set(I18n.get("sidebar.role.".concat(user.getKind().toString())));

    nav.setCellFactory(listView -> new NavElementListCell());
    nav.setItems(FXCollections.observableList(
      Nav.create().filter(Nav.visibilityFilter(user.getKind())).collect(Collectors.toList())
    ));
    // @TODO :: handle logout case
    nav.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
      this.fireEvent(
        new ModalEvent(ModalEvent.OPEN, new CreateTeacher()))
    );
    //AppRouter.goTo(newValue.linkTo));
  }
}
