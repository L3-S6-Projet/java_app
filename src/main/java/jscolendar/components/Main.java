package jscolendar.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.models.Nav;
import jscolendar.router.AppRouter;
import jscolendar.router.ContentManageable;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
  @FXML private VBox viewContainer;

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    AppRouter.bind("main", new ContentManageable() {
      private final VBox container = viewContainer;

      @Override
      public void setContent (Parent root) {
        container.getChildren().clear();
        container.getChildren().add(root);
      }
    });
    Nav.create()
      .filter(Nav.visibilityFilter(UserSession.getInstance().getUser().getKind()))
      .forEach(navElement -> AppRouter.when(navElement.linkTo, navElement.fxml));
  }
}
