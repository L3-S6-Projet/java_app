package jscolendar.models;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Teacher extends RecursiveTreeObject<Teacher> {

  private JFXCheckBox isSelected;
  private Integer id;
  private String firstName, lastName, email, phoneNumber;

  public Teacher (Integer id, String firstName, String lastName, String email, String phoneNumber) {
    isSelected = new JFXCheckBox();
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Integer getId () {
    return id;
  }

  public void setId (Integer id) {
    this.id = id;
  }

  public JFXCheckBox getIsSelected () {
    return isSelected;
  }

  public void setIsSelected (JFXCheckBox isSelected) {
    this.isSelected = isSelected;
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

  public String getEmail () {
    return email;
  }

  public void setEmail (String email) {
    this.email = email;
  }

  public String getPhoneNumber () {
    return phoneNumber;
  }

  public void setPhoneNumber (String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
