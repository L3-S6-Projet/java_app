package jscolendar.util;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.skins.JFXDatePickerSkin;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class datePickerContent {

  public static Node getContent (JFXDatePicker datePicker) {
    datePicker.setDefaultColor(Color.color(0.24609375, 0.31640625, 0.70703125, 1));
    datePicker.setStyle("-fx-alignment: bottom-center;");
    var datePickerSkin = new JFXDatePickerSkin(datePicker);
    Class<?> clazz = datePickerSkin.getClass();
    Method getPopupContent = null;
    try {
      getPopupContent = clazz.getDeclaredMethod("getPopupContent");
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    assert getPopupContent != null;
    getPopupContent.setAccessible(true);
    try {
      return (Node) getPopupContent.invoke(datePickerSkin);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

}
