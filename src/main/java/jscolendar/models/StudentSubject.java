package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.StudentSubjectsGroups;
import io.swagger.client.model.StudentSubjectsSubjects;
import io.swagger.client.model.TeacherSubjectsTeachers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class StudentSubject extends RecursiveTreeObject<StudentSubject> {
  private final StudentSubjectsSubjects studentSubjectsSubjects;
  private final StringProperty classname, name;
  private final ObjectProperty<List<TeacherSubjectsTeachers>> teachers;
  private final ObjectProperty<List<StudentSubjectsGroups>> groups;

  public StudentSubject (io.swagger.client.model.StudentSubjectsSubjects subject) {
    this.classname = new SimpleStringProperty(subject.getClassName());
    this.name = new SimpleStringProperty(subject.getName());
    this.teachers = new SimpleObjectProperty<>(subject.getTeachers());
    this.groups = new SimpleObjectProperty<>(subject.getGroups());
    this.studentSubjectsSubjects = subject;
  }

  public StringProperty classnameProperty () {
    return classname;
  }

  public StringProperty nameProperty () {
    return name;
  }

  public ObjectProperty<List<TeacherSubjectsTeachers>> teachersProperty() {
    return teachers;
  }

  public ObjectProperty<List<StudentSubjectsGroups>> groupsProperty() {
    return groups;
  }

  public StudentSubjectsSubjects getStudentSubjectsSubjects() {
    return studentSubjectsSubjects;
  }
}
