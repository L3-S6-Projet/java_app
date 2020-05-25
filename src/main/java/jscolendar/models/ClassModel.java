package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.swagger.client.model.ClassWithId;
import io.swagger.client.model.Level;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassModel extends RecursiveTreeObject<ClassModel> implements Selectable {
  public final int id;
  private final BooleanProperty selected = new SimpleBooleanProperty(false);
  private final StringProperty name;
  private final ObjectProperty<Level> level = new SimpleObjectProperty<>();

  public ClassModel (ClassWithId classWithId) {
    this.id = classWithId.getId();
    this.name = new SimpleStringProperty(classWithId.getName());
    this.level.set(classWithId.getLevel());
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

  public StringProperty nameProperty () {
    return name;
  }

  public ObjectProperty<Level> levelProperty () {
    return level;
  }
}
