package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.StudentListResponseStudents;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student extends RecursiveTreeObject<Student> implements Selectable {
  private final int id;
  private final BooleanProperty selected = new SimpleBooleanProperty(false);
  private final StringProperty firstName, lastName, className;

  public Student (StudentListResponseStudents student) {
    this.id = student.getId();
    this.firstName = new SimpleStringProperty(student.getFirstName());
    this.lastName = new SimpleStringProperty(student.getLastName());
    this.className = new SimpleStringProperty(student.getClassName());
  }

  public int getId () {
    return id;
  }

  @Override
  public BooleanProperty selectedProperty () {
    return selected;
  }

  public boolean isSelected () {
    return selected.get();
  }

  public StringProperty firstNameProperty () {
    return firstName;
  }

  public StringProperty lastNameProperty () {
    return lastName;
  }

  public StringProperty classNameProperty () {
    return className;
  }
}
