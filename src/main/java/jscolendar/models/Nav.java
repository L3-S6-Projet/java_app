package jscolendar.models;

import io.swagger.client.model.Role;
import jscolendar.util.I18n;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Nav {
  private Nav () {}

  public static class NavElement {
    public final String icon, name, linkTo, fxml;
    public final EnumSet<Role> visibilityRoles;

    private NavElement (String icon, String name, String linkTo, String fxml, EnumSet<Role> visibilityRoles) {
      this.icon = icon;
      this.name = name;
      this.linkTo = linkTo;
      this.fxml = fxml;
      this.visibilityRoles = visibilityRoles;
    }

    public static class Builder {
      private final String name;
      private String icon, linkTo, fxml;
      private EnumSet<Role> visibilityRoles;

      public Builder (String name) {
        this.name = name;
      }

      public Builder withIcon (String icon) {
        this.icon = icon;
        return this;
      }

      public Builder withLinkTo (String linkTo) {
        this.linkTo = linkTo;
        return this;
      }

      public Builder withFXML (String filename) {
        this.fxml = filename;
        return this;
      }

      public Builder withVisibilityRoles (Role ...roles) {
        this.visibilityRoles = EnumSet.noneOf(Role.class);
        visibilityRoles.addAll(Arrays.asList(roles));

        return this;
      }

      public NavElement build () {
        return new NavElement(icon, name, linkTo, fxml, visibilityRoles);
      }
    }
  }

  public static Stream<NavElement> create () {
    return Stream.of(
      new NavElement.Builder(I18n.get("sidebar.home")).withIcon("mdi-home")
        .withLinkTo("main/home").withFXML("HomeStudent")
        .withVisibilityRoles(Role.STU, Role.TEA).build(),
      new NavElement.Builder(I18n.get("sidebar.calendar")).withIcon("mdi-calendar-blank")
        .withLinkTo("main/calendar").withFXML("Calendar")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder(I18n.get("sidebar.teachers")).withIcon("mdi-account-circle")
        .withLinkTo("main/teachers").withFXML("Teachers")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder(I18n.get("sidebar.students")).withIcon("mdi-account")
        .withLinkTo("main/students").withFXML("Students")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder(I18n.get("sidebar.rooms")).withIcon("mdi-map-marker")
        .withLinkTo("main/rooms").withFXML("Rooms")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder(I18n.get("sidebar.classes")).withIcon("mdi-format-list-bulleted")
        .withLinkTo("main/classes").withFXML("Classes")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder(I18n.get("sidebar.ue")).withIcon("mdi-library-books")
        .withLinkTo("main/ue").withFXML("Subjects")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder(I18n.get("sidebar.settings")).withIcon("mdi-settings")
        .withLinkTo("main/settings").withFXML("Settings")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder(I18n.get("sidebar.logout")).withIcon("mdi-login-variant")
        .withLinkTo("main/logout")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build()
    );
  }

  public static Predicate<NavElement> visibilityFilter (Role role) {
    return navElement -> navElement.visibilityRoles.contains(role);
  }
}
