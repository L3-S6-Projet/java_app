package jscolendar.util;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.ResourceBundle;

public class FXUtil {
  private FXUtil () {}

  public static void loadFXML (String name, Object component, Object controller, ResourceBundle bundle) {
    FXMLLoader loader = new FXMLLoader(FXUtil.class.getResource(name));
    loader.setResources(bundle);
    loader.setRoot(component);
    loader.setController(controller);

    try {
      loader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
