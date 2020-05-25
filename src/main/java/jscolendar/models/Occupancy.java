package jscolendar.models;

import io.swagger.client.model.OccupanciesOccupancies;
import io.swagger.client.model.OccupancyType;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Occupancy {
  public final int id;
  public final LongProperty start, end;
  public final StringProperty subjectName, classname, classroomName, groupName, name, teacherName;
  public final OccupancyType occupancyType;

  public Occupancy (OccupanciesOccupancies occupancy) {
    this.id = occupancy.getId();
    this.start = new SimpleLongProperty(occupancy.getStart());
    this.end = new SimpleLongProperty(occupancy.getEnd());
    this.classname = new SimpleStringProperty(occupancy.getClassName());
    this.subjectName = new SimpleStringProperty(occupancy.getSubjectName());
    this.classroomName = new SimpleStringProperty(occupancy.getClassroomName());
    this.groupName = new SimpleStringProperty(occupancy.getGroupName());
    this.name = new SimpleStringProperty(occupancy.getName());
    this.teacherName = new SimpleStringProperty(occupancy.getTeacherName());
    this.occupancyType = occupancy.getOccupancyType();
  }
}
