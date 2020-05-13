package jscolendar.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import jscolendar.events.ModalEvent;
import jscolendar.models.Selectable;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractTableView<T extends RecursiveTreeObject<T>> implements Initializable {
  @FXML protected StackPane container;
  @FXML protected HBox header, headerSelected;
  @FXML protected Label countLabel, paginationLabel;
  @FXML protected JFXTreeTableView<T> table;
  @FXML protected JFXTreeTableColumn<T, Boolean> select;
  @FXML protected JFXButton prevButton, nextButton;

  private final IntegerProperty selectionCount = new SimpleIntegerProperty(0);
  protected final IntegerProperty page = new SimpleIntegerProperty(1);
  protected final IntegerProperty total = new SimpleIntegerProperty(0);
  private final int itemPerPage = 10;

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    countLabel.textProperty().bind(selectionCount.asString());

    selectionCount.addListener(((observable, oldValue, newValue) -> {
      if (newValue.intValue() > 0) {
        headerSelected.toFront();
        header.visibleProperty().set(false);
      } else {
        header.toFront();
        header.visibleProperty().set(true);
      }
    }));

    select.setCellValueFactory(param -> ((Selectable) param.getValue().getValue()).selectedProperty());
    select.setCellFactory(dontCare -> new TreeTableCell<>() {
      @Override
      protected void updateItem (Boolean item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);

        if (item == null || empty) return;

        var checkBox = new JFXCheckBox();
        checkBox.getStyleClass().add("table-check-box");
        checkBox.selectedProperty().set(item);
        // FIXME :: bind
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->  {
          ((Selectable) table.getSelectionModel()
            .getModelItem(getTreeTableRow().getIndex()).getValue()).selectedProperty().set(newValue);
          if (newValue)
            selectionCount.set(selectionCount.get() + 1);
          else
            selectionCount.set(selectionCount.get() - 1);
        });
        setGraphic(checkBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
      }
    });

    initColumns();

    var checkbox = new JFXCheckBox();
    checkbox.getStyleClass().add("table-check-box");
    checkbox.selectedProperty().addListener(((observable, oldValue, newValue) ->
      table.getRoot().getChildren().forEach(tTreeItem -> {
        ((Selectable) tTreeItem.getValue()).selectedProperty().set(newValue);
        selectionCount.set(newValue ? table.getCurrentItemsCount() : 0);
      })
    ));

    select.setText(null);
    select.setGraphic(checkbox);

    table.setShowRoot(false);
    table.setEditable(false);
    table.setPrefHeight((itemPerPage + 1) * table.getFixedCellSize() + 1);

    table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showDetails);

    fetchData();

    page.addListener(((observable, oldValue, newValue) -> fetchData()));
    total.addListener(((observable, oldValue, newValue) -> updatePaginationLabel()));
    prevButton.disableProperty().bind(page.isEqualTo(1));
    nextButton.disableProperty().bind(page.multiply(itemPerPage).greaterThanOrEqualTo(total));
  }
  protected abstract void initColumns ();
  protected abstract void fetchData ();
  protected abstract void delete ();
  protected abstract Region getModalContent ();
  protected abstract Region getDetailsView (T item);

  private void showDetails (MouseEvent event) {
    if (!event.getButton().equals(MouseButton.PRIMARY)) return;
    if (event.getClickCount() < 2) return;

    var item = table.getSelectionModel().getSelectedItem().getValue();
    container.getChildren().add(getDetailsView(item));
  }

  @FXML protected void onNextPage () {
    page.set(page.get() + 1);
    clearSelection();
    updatePaginationLabel();
  }

  private void updatePaginationLabel () {
    int last = page.get() * itemPerPage;
    int first = last - itemPerPage + 1;
    paginationLabel.textProperty().set(String.format("%d-%d of %d", first, last, total.get()));
  }

  @FXML protected void onPreviousPage () {
    page.set(page.get() -1);
    clearSelection();
    updatePaginationLabel();
  }

  private void clearSelection () {
    ((CheckBox) select.getGraphic()).selectedProperty().set(false);
    selectionCount.set(0);
  }

  @FXML protected void onDelete () {
    delete();
  }

  @FXML protected void onAdd (ActionEvent event) {
    event.consume();
    container.fireEvent(new ModalEvent(ModalEvent.OPEN, getModalContent()));
  }
}
