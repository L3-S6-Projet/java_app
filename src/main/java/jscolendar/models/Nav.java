package jscolendar.models;

import io.swagger.client.model.Role;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Nav {
  private Nav () {}

  public static class NavElement {
    public final String icon, name, linkTo;
    public final EnumSet<Role> visibilityRoles;

    private NavElement (String icon, String name, String linkTo, EnumSet<Role> visibilityRoles) {
      this.icon = icon;
      this.name = name;
      this.linkTo = linkTo;
      this.visibilityRoles = visibilityRoles;
    }

    public static class Builder {
      private final String name;
      private String icon, linkTo;
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

      public Builder withVisibilityRoles (Role ...roles) {
        this.visibilityRoles = EnumSet.noneOf(Role.class);
        visibilityRoles.addAll(Arrays.asList(roles));

        return this;
      }

      public NavElement build () {
        return new NavElement(icon, name, linkTo, visibilityRoles);
      }
    }
  }

  public static Stream<NavElement> create () {
    return Stream.of(
      new NavElement.Builder("Home").withIcon("mdi-home")
        .withLinkTo("/main/home")
        .withVisibilityRoles(Role.STU, Role.TEA).build(),
      new NavElement.Builder("Emploi du temps").withIcon("mdi-calendar-blank")
        .withLinkTo("/main/calendar")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder("Enseignants").withIcon("mdi-account-circle")
        .withLinkTo("/main/teachers")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder("Etudiants").withIcon("mdi-account")
        .withLinkTo("/main/students")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder("Salles").withIcon("mdi-map-marker")
        .withLinkTo("/main/rooms")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder("Classes").withIcon("mdi-format-list-bulleted")
        .withLinkTo("/main/classes")
        .withVisibilityRoles(Role.ADM).build(),
      new NavElement.Builder("Unités d'enseignement").withIcon("mdi-library-books")
        .withLinkTo("/main/ue")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder("Paramètres").withIcon("mdi-settings")
        .withLinkTo("/main/settings")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build(),
      new NavElement.Builder("Déconnexion").withIcon("mdi-login-variant")
        .withLinkTo("/main/logout")
        .withVisibilityRoles(Role.ADM, Role.STU, Role.TEA).build()
    );
  }

  public static Predicate<NavElement> visibilityFilter (Role role) {
    return navElement -> navElement.visibilityRoles.contains(role);
  }
}
