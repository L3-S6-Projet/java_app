package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.SubjectListResponseSubjects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject extends RecursiveTreeObject<Subject> implements Selectable {
  public final int id;
  private final BooleanProperty selected = new SimpleBooleanProperty(false);
  private final StringProperty className, name;

  public Subject (SubjectListResponseSubjects subject) {
    this.id = subject.getId();
    this.name = new SimpleStringProperty(subject.getName());
    this.className = new SimpleStringProperty(subject.getClassName());
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

  public StringProperty classNameProperty () {
    return className;
  }

  public StringProperty nameProperty () {
    return name;
  }
}
