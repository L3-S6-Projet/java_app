package jscolendar.components;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import jscolendar.models.Teacher;

public class TabView {

  @FXML
  private JFXTreeTableView<Teacher> table;
  @FXML
  private JFXTextField searchField;


  @FXML
  public void initialize () {

    TreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn = new TreeTableColumn();
    TreeTableColumn<Teacher, String> firstNameColumn = new TreeTableColumn("pré");
    TreeTableColumn<Teacher, String> lastNameColumn = new TreeTableColumn("nom");
    TreeTableColumn<Teacher, String> emailColumn = new TreeTableColumn("mail");
    TreeTableColumn<Teacher, String> phoneNumberColumn = new TreeTableColumn("tel");

    checkBoxColumn.setMinWidth(100);
    firstNameColumn.setMinWidth(100);
    lastNameColumn.setMinWidth(100);
    emailColumn.setMinWidth(100);
    phoneNumberColumn.setMinWidth(100);
    table.setStyle("-fx-text-alignment: center");

    table.getColumns().addAll(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn);
    ObservableList<Teacher> data = FXCollections.observableArrayList(
      new Teacher("0", "prénom", "nom", "mail1", "01"),
      new Teacher("1", "prénom", "sdfds", "mail2", "tel"),
      new Teacher("2", "prénom", "fdsfdsfdsfrt", "mail3", "tel"),
      new Teacher("3", "prénom", "opkhj,", "mail4", "tel"),
      new Teacher("4", "prénom", "awvbhnu", "mail0", "tel"),
      new Teacher("5", "prénom", "plml;n,b", "mail8", "tel")
    );

    checkBoxColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, JFXCheckBox>("isSelected"));
    firstNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("firstName"));
    lastNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("lastName"));
    emailColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("email"));
    phoneNumberColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("phoneNumber"));

    TreeItem<Teacher> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
    table.setRoot(root);
    table.setShowRoot(false);
    table.setTableMenuButtonVisible(true);
//https://www.youtube.com/watch?v=Qjio2mhT4qs

  }
}
