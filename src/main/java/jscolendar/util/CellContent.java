package jscolendar.util;

public class CellContent {
  public int x, y;
  public String name, group, date, promo, prof, room;

  public CellContent(int x, int y, String name, String group, String date, String promo, String prof, String room) {
    this.x = x;
    this.y = y;
    this.name = name;
    this.group = group;
    this.date = date;
    this.promo = promo;
    this.prof = prof;
    this.room = room;
  }
}
