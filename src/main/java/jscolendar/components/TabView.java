package jscolendar.components;

import com.jfoenix.controls.JFXButton;
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

  private final TreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn = new TreeTableColumn<>();
  private final TreeTableColumn<Teacher, String> firstNameColumn = new TreeTableColumn<>("Prénom");
  private final TreeTableColumn<Teacher, String> lastNameColumn = new TreeTableColumn<>("Nom");
  private final TreeTableColumn<Teacher, String> emailColumn = new TreeTableColumn<>("Mail");
  private final TreeTableColumn<Teacher, String> phoneNumberColumn = new TreeTableColumn<>("Numéro de téléphone");
  private int actualPage = 0;
  @FXML
  private JFXTreeTableView<Teacher> table;//https://www.youtube.com/watch?v=Qjio2mhT4qs
  @FXML
  private JFXTextField searchField;//todo use this : https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
  @FXML
  private JFXButton previous, next;

  @FXML
  public void initialize () {

    TeacherListResponse result = getTeacherListResponse();


    initTableWithColums(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn);

    //i comment this because it's considered "unsafe" by intellij
//    table.getColumns().addAll(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn);

    ObservableList<Teacher> data = createDataFromAPI(result);
    addDataToTable(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, data);


  }

  private void addDataToTable (TreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn, TreeTableColumn<Teacher, String> firstNameColumn, TreeTableColumn<Teacher, String> lastNameColumn, TreeTableColumn<Teacher, String> emailColumn, TreeTableColumn<Teacher, String> phoneNumberColumn, ObservableList<Teacher> data) {
    checkBoxColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, JFXCheckBox>("isSelected"));
    firstNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("firstName"));
    lastNameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("lastName"));
    emailColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("email"));
    phoneNumberColumn.setCellValueFactory(new TreeItemPropertyValueFactory<Teacher, String>("phoneNumber"));

    TreeItem<Teacher> root = new RecursiveTreeItem<>(data, RecursiveTreeObject::getChildren);
    table.setRoot(root);
    table.setShowRoot(false);
    table.setTableMenuButtonVisible(true);
  }

  private ObservableList<Teacher> createDataFromAPI (TeacherListResponse result) {
    assert result != null;
    ObservableList<Teacher> data = FXCollections.observableArrayList();
    for (TeacherListResponseTeachers teacher : result.getTeachers()) {
      data.add(new Teacher(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(), teacher.getPhoneNumber()));
    }
    return data;
  }

  private void initTableWithColums (TreeTableColumn<Teacher, JFXCheckBox> checkBoxColumn, TreeTableColumn<Teacher, String> firstNameColumn, TreeTableColumn<Teacher, String> lastNameColumn, TreeTableColumn<Teacher, String> emailColumn, TreeTableColumn<Teacher, String> phoneNumberColumn) {
    table.getColumns().add(checkBoxColumn);
    table.getColumns().add(firstNameColumn);
    table.getColumns().add(lastNameColumn);
    table.getColumns().add(emailColumn);
    table.getColumns().add(phoneNumberColumn);
  }

  private TeacherListResponse getTeacherListResponse () {
    TeacherApi apiInstance = new TeacherApi();
    String query = ""; // String |
    Integer page = actualPage; // Integer |
    TeacherListResponse result = null;
    try {
      result = apiInstance.teachersGet(query, page);
    } catch (ApiException e) {
      System.err.println("Exception when calling TeacherApi#teachersGet");
      e.printStackTrace();
    }
    return result;
  }

  @FXML
  private void previousPage () {
    if (actualPage != 0) {
      table.getColumns().clear();
      actualPage--;
      TeacherListResponse result = getTeacherListResponse();
      ObservableList<Teacher> data = createDataFromAPI(result);
      addDataToTable(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, data);

    }
  }

  @FXML
  private void nextPage () {
    TeacherListResponse result = getTeacherListResponse();
    if (result != null) {
      actualPage++;
      table.getColumns().clear();
    } else {
      return;
    }
    ObservableList<Teacher> data = createDataFromAPI(result);
    addDataToTable(checkBoxColumn, firstNameColumn, lastNameColumn, emailColumn, phoneNumberColumn, data);
  }
}
