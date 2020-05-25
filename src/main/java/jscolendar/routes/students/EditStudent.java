package jscolendar.routes.students;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jscolendar.routes.teachers.CreateTeacher;
import jscolendar.events.ModalEvent;
import jscolendar.util.FXUtil;
import jscolendar.util.I18n;

public class EditStudent extends VBox {

  public EditStudent () {
    FXUtil.loadFXML("/fxml/students/EditStudent.fxml", this, this, I18n.getBundle());
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
