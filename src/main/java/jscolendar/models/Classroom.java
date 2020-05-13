package jscolendar.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Classroom extends RecursiveTreeObject<Classroom> implements Selectable {
  public final int id;
  private final BooleanProperty selected = new SimpleBooleanProperty(false);
  private final StringProperty name = new SimpleStringProperty();
  private final IntegerProperty capacity = new SimpleIntegerProperty();

  public Classroom (io.swagger.client.model.Classroom classroom) {
    this.id = classroom.getId();
    this.name.set(classroom.getName());
    this.capacity.set(classroom.getCapacity());
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

  public IntegerProperty capacityProperty () {
    return capacity;
  }
}
