package jscolendar.routes.teachers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class EditTeacher extends VBox {
  public EditTeacher () {
    FXUtil.loadFXML("/fxml/teachers/EditTeacher.fxml", this, this, I18n.getBundle());
  }

  @FXML
  public void initialize () {

  }

  @FXML
  private void onCancel () {
    this.fireEvent(
      new ModalEvent(ModalEvent.CLOSE, new CreateTeacher())
    );
  }

  @FXML
  private void onCreate () {

  }

}
