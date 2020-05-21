package jscolendar.models;

import com.calendarfx.model.Entry;

import java.util.List;

public class Calendar extends com.calendarfx.model.Calendar {

  public Calendar () {
    super();
  }

  public OccupancyEntry createEntry () {
    // @TODO
    return new OccupancyEntry();
  }


  @Override
  public List<Entry<?>> findEntries (String searchText) {
    return super.findEntries(searchText);
  }
}
