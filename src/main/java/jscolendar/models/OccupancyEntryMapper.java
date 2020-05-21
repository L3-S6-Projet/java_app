package jscolendar.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class OccupancyEntryMapper {
  private OccupancyEntryMapper () {}

  public static OccupancyEntry map (Occupancy occupancy) {
    var entry = new OccupancyEntry();

    entry.setId(String.valueOf(occupancy.id));
    entry.setTitle(occupancy.subjectName.get().concat(" \n").concat(occupancy.classname.get()));
    var start = LocalDateTime.ofInstant(Instant.ofEpochSecond(occupancy.start.get()), ZoneId.systemDefault());
    var end = LocalDateTime.ofInstant(Instant.ofEpochSecond(occupancy.end.get()), ZoneId.systemDefault());
    entry.setInterval(start, end);
    entry.setUserObject(occupancy);

    return entry;
  }
}
