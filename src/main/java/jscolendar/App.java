package jscolendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jscolendar.components.Admin;

public class App extends Application {

  int width = 720;
  int height = 480;


  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    //var root = new Login();
    var root = new Admin();
    Scene scene = new Scene(root, width, height);
    //scene.getStylesheets().add("styles.css");
    scene.getStylesheets().add("stylesMenu.css");
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
