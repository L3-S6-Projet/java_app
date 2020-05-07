package jscolendar.components;

import javafx.fxml.FXML;
import jscolendar.router.AppRouter;

public class Main {
  @FXML public void initialize () {
    System.out.println(AppRouter.getData());
  }

  @FXML public void onLogout () {
    AppRouter.goTo("/login");
  }
}
