package jscolendar.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractSmallTableView<T extends RecursiveTreeObject<T>> implements Initializable {
  @FXML protected StackPane container;
  @FXML protected JFXTreeTableView<T> table;
  @FXML protected JFXButton prevButton, nextButton;
  @FXML protected Label paginationLabel;

  protected List<T> items = new ArrayList<>();
  protected final IntegerProperty page = new SimpleIntegerProperty(1);
  protected final IntegerProperty total = new SimpleIntegerProperty(0);
  private final int itemPerPage = 10;

  @Override
  public void initialize (URL location, ResourceBundle resources) {
    initColumns();
    table.setShowRoot(false);
    table.setEditable(false);
    table.setPrefHeight((itemPerPage + 1) * table.getFixedCellSize() + 1);
    table.addEventHandler(MouseEvent.MOUSE_CLICKED, this::showDetails);
    fetchData();

    page.addListener((observable, oldValue, newValue) -> setItems());
    prevButton.disableProperty().bind(page.isEqualTo(1));
    nextButton.disableProperty().bind(page.multiply(itemPerPage).greaterThanOrEqualTo(total));
  }

  private void showDetails (MouseEvent event) {
    if (!event.getButton().equals(MouseButton.PRIMARY)) return;
    if (event.getClickCount() < 2) return;

    var item = table.getSelectionModel().getSelectedItem().getValue();
    container.getChildren().add(getDetailsView(item));
  }

  protected void setItems () {
    var slice = FXCollections.observableList(
      items.subList((page.get() -1) * itemPerPage, Math.min(itemPerPage * page.get(), items.size()))
    );
    table.setRoot(new RecursiveTreeItem<>(slice, RecursiveTreeObject::getChildren));
    updatePaginationLabel();
  }

  protected abstract void initColumns ();
  protected abstract void fetchData ();
  protected abstract Region getDetailsView (T item);

  @FXML
  private void onPreviousPage () {
    page.set(page.get() -1);
  }

  @FXML
  private void onNextPage () {
    page.set(page.get() + 1);
  }

  private void updatePaginationLabel () {
    int last = page.get() * itemPerPage;
    int first = total.greaterThan(0).get() ? last - itemPerPage + 1 : total.get();
    paginationLabel.textProperty().set(String.format(
      "%d-%d of %d", first, Math.min(last, total.get()), total.get()));
  }
}
