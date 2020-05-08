package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.models.Nav;
import jscolendar.router.AppRouter;
import jscolendar.router.ContentManageable;

public class Main {
  @FXML private VBox viewContainer;

  @FXML public void initialize () {
    System.out.println(AppRouter.getData());
    System.out.println(UserSession.getInstance().getToken());

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

  @FXML public void onLogout () {
    AppRouter.goTo("/login");
  }
}
