package jscolendar;

import jscolendar.util.FXRouter;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  public static void main (String[] args) {
    launch(args);
  }

  @Override
  public void start (Stage stage) throws Exception {
    FXRouter.bind(stage, "JScolendar", 1920, 1080);
    FXRouter.when("login", "fxml/LoginView.fxml");
    FXRouter.goTo("login");
  }

  @Override
  public void init () throws Exception {
    super.init();
  }

  @Override
  public void stop () throws Exception {
    super.stop();
  }
}
