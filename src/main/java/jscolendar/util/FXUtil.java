package jscolendar.util;

import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class FXUtil {
  private FXUtil () {}

  public static void loadFXML (String name, Object component, Object controller) {
    FXMLLoader loader = new FXMLLoader(FXUtil.class.getResource(name));
    loader.setRoot(component);
    loader.setController(controller);

    try {
      loader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
