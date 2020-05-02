package jscolendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jscolendar.components.Login;

public class App extends Application {

  int width = 1920;
  int height = 1080;//dont touch this is my screen resolution

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) {
    //var root = new Admin();
    //var root = new Etu();

    var root = new Login();
    Scene scene = new Scene(root, width, height);
    stage.setResizable(false);//todo make autoresize before remove it
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
