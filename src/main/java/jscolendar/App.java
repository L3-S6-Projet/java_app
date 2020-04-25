package jscolendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jscolendar.components.Admin;

public class App extends Application {

  int width = 1024;
  int height = 720;
//TODO waring an hover css on all field i don't know d'où ça sort

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    var root = new Admin();
    //var root = new Etu();
    Scene scene = new Scene(root, width, height);
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
