package jscolendar.util;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public final class I18n {
  private final ObjectProperty<ResourceBundle> bundleProperty = new SimpleObjectProperty<>();
  // supported locales
  private final Map<String, Locale> locales = Map.ofEntries(
    Map.entry("fr_FR", new Locale("fr", "FR")),
    Map.entry("en_UK", new Locale("en", "UK"))
  );

  private static class I18nHolder {
    private final static I18n instance = new I18n();
  }

  private I18n () {
    var locale = locales.getOrDefault(
      Locale.getDefault().toString(), locales.get("fr_FR"));
    bundleProperty.set(ResourceBundle.getBundle("i18n", locale));
  }

  private static I18n getInstance () {
    return I18nHolder.instance;
  }

  public static String get (String key) {
    return getInstance().bundleProperty.get().getString(key);
  }

  public static ResourceBundle getBundle () {
    return getInstance().bundleProperty.get();
  }

  public static void setLocale (String key) {
    var i18n = getInstance();
    var locale = i18n.locales.getOrDefault(key, i18n.locales.get("fr_FR"));
    i18n.bundleProperty.set(ResourceBundle.getBundle("i18n", locale));
  }
}
