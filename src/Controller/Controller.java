package Controller;

import Model.Login;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        stage.setTitle("Scolendar");
        Scene scene = new Scene(root, 1268, 1024);
        stage.setScene(scene);
        new Login(root);
        stage.show();
    }
}
