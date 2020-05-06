package jscolendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jscolendar.components.Login;

import java.io.IOException;

public class App extends Application {

  int width = 1920;
  int height = 1080;//dont touch this is my screen resolution

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    // var root = new Admin();
    //var root = new Etu();
    stage.setMaximized(true);
    var root = new Login(stage, width, height);
    Scene scene = new Scene(root, root.getWidth(), root.getHeight());
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
