package jscolendar.components;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.ApiException;
import io.swagger.client.api.TeacherApi;
import io.swagger.client.model.TeacherListResponse;
import io.swagger.client.model.TeacherListResponseTeachers;
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

    TeacherApi apiInstance = new TeacherApi();
    String query = ""; // String |
    Integer page = 0; // Integer |
    TeacherListResponse result = null;
    try {
      result = apiInstance.teachersGet(query, page);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeacherApi#teachersGet");
      e.printStackTrace();
    }


    TreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn = new TreeTableColumn<>();
    TreeTableColumn<Teacher, String> firstNameColumn = new TreeTableColumn<>("pré");
    TreeTableColumn<Teacher, String> lastNameColumn = new TreeTableColumn<>("nom");
    TreeTableColumn<Teacher, String> emailColumn = new TreeTableColumn<>("mail");
    TreeTableColumn<Teacher, String> phoneNumberColumn = new TreeTableColumn<>("tel");

    checkBoxColumn.setMinWidth(100);
    firstNameColumn.setMinWidth(100);
    lastNameColumn.setMinWidth(100);
    emailColumn.setMinWidth(100);
    phoneNumberColumn.setMinWidth(100);

    table.getColumns().addAll(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn);
    assert result != null;
    ObservableList<Teacher> data = FXCollections.observableArrayList();
    for (TeacherListResponseTeachers teacher : result.getTeachers()) {
      data.add(new Teacher(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(), teacher.getPhoneNumber()));
    }


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
