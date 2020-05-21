package jscolendar;

import jscolendar.router.AppRouter;
import jscolendar.router.ContentManageable;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {

  public static void main (String[] args) {
    launch(args);
  }

  @Override
  public void start (Stage stage) {
    AppRouter.bind(new ContentManageable() {
      private final Stage primaryStage = stage;

      @Override
      public void setContent (Parent root) {
        primaryStage.setTitle("JScolendar");
        primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();
      }
    });
    AppRouter.when("/login", "LoginView");
    AppRouter.when("/main", "MainPane");
    AppRouter.goTo("/login");
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
