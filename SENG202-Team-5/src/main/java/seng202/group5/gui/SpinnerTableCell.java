package seng202.group5.gui;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;

public class SpinnerTableCell<T> extends TableCell<T, Integer> {
    private final Spinner<Integer> spinner;

    public SpinnerTableCell(int min, int max, int initial, int step) {
        spinner = new Spinner<>();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, initial, step));
        setEditable(true);
    }


    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(spinner);
        }
    }

}