package jscolendar.routes;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.events.ModalEvent;
import jscolendar.events.NotificationEvent;
import jscolendar.models.Nav;
import jscolendar.router.AppRouter;
import jscolendar.router.ContentManageable;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
  @FXML private StackPane root;
  @FXML private VBox viewContainer;
  private JFXDialog modal;
  private final JFXSnackbar notificationBar = new JFXSnackbar();

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    root.addEventHandler(ModalEvent.OPEN, this::onOpenModalRequest);
    root.addEventHandler(ModalEvent.CLOSE, event -> modal.close());

    notificationBar.registerSnackbarContainer(root);
    notificationBar.toFront();

    root.addEventHandler(NotificationEvent.MESSAGE, this::onMessage);

    AppRouter.bind("main", new ContentManageable() {
      private final VBox container = viewContainer;

      @Override
      public void setContent (Parent root) {
        container.getChildren().clear();
        container.getChildren().add(root);
      }
    });
    Nav.create()
      .filter(Nav.visibilityFilter(UserSession.getInstance().getUser().getKind()))
      .forEach(navElement -> AppRouter.when(navElement.linkTo, navElement.fxml));
  }

  private void onOpenModalRequest (ModalEvent event) {
    event.consume();
    modal = new JFXDialog();
    modal.setCacheContainer(false); // FIXME :: prevent page jump
    modal.setOverlayClose(false);
    modal.setContent(event.getContent());
    modal.show(root);
  }

  private void onMessage (NotificationEvent event) {
    event.consume();
    notificationBar.enqueue(new SnackbarEvent(new JFXSnackbarLayout(event.message)));
  }
}
