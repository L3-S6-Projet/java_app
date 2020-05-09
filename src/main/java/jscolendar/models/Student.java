package jscolendar.models;

import com.jfoenix.controls.JFXCheckBox;

public class Student {

  private JFXCheckBox isSelected;
  private String id, firstName, lastName, className;

  public Student (String id, String firstName, String lastName, String className) {
    isSelected = new JFXCheckBox();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.className = className;
  }

  public JFXCheckBox getIsSelected () {
    return isSelected;
  }

  public void setIsSelected (JFXCheckBox isSelected) {
    this.isSelected = isSelected;
  }

  public String getId () {
    return id;
  }

  public void setId (String id) {
    this.id = id;
  }

  public String getFirstName () {
    return firstName;
  }

  public void setFirstName (String firstName) {
    this.firstName = firstName;
  }

  public String getLastName () {
    return lastName;
  }

  public void setLastName (String lastName) {
    this.lastName = lastName;
  }

  public String getClassName () {
    return className;
  }

  public void setClassName (String className) {
    this.className = className;
  }
}
