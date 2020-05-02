package jscolendar.util;

public class CellContent {
  public int x, y;
  public String name, location, date, promo, professor, room;

  public CellContent(int x, int y, String name, String location, String date, String promo, String professor, String room) {
    this.x = x;
    this.y = y;
    this.name = name;
    this.location = location;
    this.date = date;
    this.promo = promo;
    this.professor = professor;
    this.room = room;
  }
}
