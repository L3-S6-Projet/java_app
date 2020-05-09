package jscolendar.components;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import jscolendar.models.Teacher;

public class TabView {

  @FXML
  private JFXTreeTableView<Teacher> table;
  @FXML
  private JFXTreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn;
  @FXML
  private JFXTreeTableColumn<Teacher, String> firstNameColumn;
  @FXML
  private JFXTreeTableColumn<Teacher, String> lastNameColumn;
  @FXML
  private JFXTreeTableColumn<Teacher, String> emailColumn;
  @FXML
  private JFXTreeTableColumn<Teacher, String> phoneNumberColumn;
  @FXML
  private JFXTextField searchField;


  @FXML
  public void initialize () {
    table.getColumns().addAll(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn);
    /*ObservableList<Teacher> data = new FXCollections.observableArrayList(
      new Teacher("lol","prénom","nom","mail","tel")
    );
    https://www.youtube.com/watch?v=Qjio2mhT4qs
    */

  }
}
