package jscolendar.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jscolendar.util.APIErrorUtil;
import jscolendar.util.FXApiService;

import java.util.function.Consumer;

public class AutoCompleteBox<T, S> implements EventHandler<KeyEvent> {
  private final ComboBox<T> comboBox;
  private final FXApiService<String, S> service;
  private final Consumer<S> consumer;
  public final StringProperty textProperty = new SimpleStringProperty("");

  public AutoCompleteBox (ComboBox<T> comboBox, FXApiService<String, S> service, Consumer<S> consumer) {
    this.comboBox = comboBox;
    this.service = service;
    this.consumer = consumer;

    this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
      this.comboBox.getSelectionModel().select(newValue.intValue()));

    this.comboBox.getEditor().focusedProperty().addListener(((observable, oldValue, newValue) -> {
      if (newValue) {
        this.comboBox.show();
      } else {
        if (this.comboBox.getSelectionModel().isEmpty())
         this.comboBox.getEditor().setText("");
        this.comboBox.hide();
      }
    }));

    this.comboBox.setOnKeyReleased(AutoCompleteBox.this);
  }

  @Override
  public void handle (KeyEvent event) {
    comboBox.hide();
    var keyCode = event.getCode();
    if (keyCode == KeyCode.UP || keyCode == KeyCode.DOWN ||
      keyCode == KeyCode.RIGHT || keyCode == KeyCode.LEFT ||
      keyCode == KeyCode.TAB || keyCode == KeyCode.ENTER) return;

    if (keyCode == KeyCode.BACK_SPACE)
      comboBox.getSelectionModel().clearSelection();

    if (keyCode == KeyCode.SPACE && comboBox.getSelectionModel().getSelectedIndex() != -1) {
      comboBox.getEditor().positionCaret(comboBox.getEditor().getText().length());
      return;
    }

    service.reset();
    service.setRequest(comboBox.getEditor().getText());
    service.setOnSucceeded(dontCare -> {
      consumer.accept(service.getValue());
      comboBox.show();
      comboBox.getEditor().positionCaret(comboBox.getEditor().getText().length());
    });

   service.setOnFailed(dontCare ->
      textProperty.set(APIErrorUtil.getErrorMessage(service.getException())));

    service.start();
  }
}
