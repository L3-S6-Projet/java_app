package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.TeacherListResponseTeachers;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Teacher extends RecursiveTreeObject<Teacher> implements Selectable {
  public final int id;
  private final BooleanProperty selected = new SimpleBooleanProperty(false);
  private final StringProperty firstName, lastName, email, phoneNumber;

  public Teacher (TeacherListResponseTeachers response) {
    this.id = response.getId();
    this.firstName = new SimpleStringProperty(response.getFirstName());
    this.lastName = new SimpleStringProperty(response.getLastName());
    this.email = new SimpleStringProperty(response.getEmail());
    this.phoneNumber = new SimpleStringProperty(response.getPhoneNumber());
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

  public StringProperty emailProperty () {
    return email;
  }

  public StringProperty phoneNumberProperty () {
    return phoneNumber;
  }
}
