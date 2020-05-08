package jscolendar.components;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.router.AppRouter;

public class Main {
  @FXML private VBox viewContainer;

  @FXML public void initialize () {
    System.out.println(AppRouter.getData());
    System.out.println(UserSession.getInstance().getToken());
  }

  @FXML public void onLogout () {
    AppRouter.goTo("/login");
  }
}
