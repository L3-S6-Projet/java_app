package jscolendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jscolendar.components.Login;

public class App extends Application {
  public static void main (String[] args) {
    launch(args);
  }

  @Override
  public void start (Stage stage) {
    var root = new Login();
    Scene scene = new Scene(root, 1920, 1080);
    scene.getStylesheets().add("styles.css");

    stage.setScene(scene);
    stage.setTitle("JScolendar");
    stage.show();
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
