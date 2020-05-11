package jscolendar.components;

import com.jfoenix.controls.JFXDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import jscolendar.UserSession;
import jscolendar.events.ModalEvent;
import jscolendar.models.Nav;
import jscolendar.router.AppRouter;
import jscolendar.router.ContentManageable;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
  @FXML private StackPane root;
  @FXML private VBox viewContainer;
  private final JFXDialog modal;

  public Main () {
    this.modal = new JFXDialog();
    this.modal.setCacheContainer(true);
    this.modal.setOverlayClose(false);
  }

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    root.addEventHandler(ModalEvent.OPEN, this::onOpenModalRequest);
    root.addEventHandler(ModalEvent.CLOSE, event -> modal.close());

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
    modal.setContent(event.getContent());
    modal.show(root);
  }
}
