package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.TeacherSubjectsGroups;
import io.swagger.client.model.TeacherSubjectsTeachers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class TeacherSubject extends RecursiveTreeObject<TeacherSubject> {
  public final int id;
  private final StringProperty classname, name;
  private final ObjectProperty<List<TeacherSubjectsTeachers>> teachers;
  private final ObjectProperty<List<TeacherSubjectsGroups>> groups;

  public TeacherSubject (io.swagger.client.model.TeacherSubjectsSubjects subject) {
    this.id = subject.getId();
    this.classname = new SimpleStringProperty(subject.getClassName());
    this.name = new SimpleStringProperty(subject.getName());
    this.teachers = new SimpleObjectProperty<>(subject.getTeachers());
    this.groups = new SimpleObjectProperty<>(subject.getGroups());
  }

  public StringProperty classnameProperty () {
    return classname;
  }

  public StringProperty nameProperty () {
    return name;
  }

  public ObjectProperty<List<TeacherSubjectsTeachers>> teachersProperty () {
    return teachers;
  }

  public ObjectProperty<List<TeacherSubjectsGroups>> groupsProperty () {
    return groups;
  }
}
