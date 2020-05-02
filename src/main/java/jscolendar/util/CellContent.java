package jscolendar.util;

public class CellContent {
  public int x, y;
  public String name, group, promo, professor, room;
  public Date date;

  public CellContent(int x, int y, String name, String group, Date date, String promo, String professor, String room) {
    this.x = x;
    this.y = y;
    this.name = name;
    this.group = group;
    this.date = date;
    this.promo = promo;
    this.professor = professor;
    this.room = room;
  }

  public CellContent(String name, String group, String promo, String professor, String room, Date date) {
    this.name = name;
    this.group = group;
    this.promo = promo;
    this.professor = professor;
    this.room = room;
    this.date = date;
  }
}
