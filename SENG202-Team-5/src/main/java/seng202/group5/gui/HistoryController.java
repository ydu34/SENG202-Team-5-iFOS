package seng202.group5.gui;

/**
 * @author Shivin Gaba, Daniel Harris
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.time.LocalDate;

public class HistoryController extends GeneralController {


    @FXML
    private DatePicker historyStartDatePicker;

    @FXML
    private DatePicker historyEndDatePicker;

    @FXML
    private TextField historySearchbar;


    /**
     * Sets the DateCell creators for the start date picker
     *
     * @param event
     */
    public void setStartDateUpdater(javafx.event.Event event) {
        historyStartDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate endDate = historyEndDatePicker.getValue();
                if (endDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.compareTo(endDate) > 0);
                }
            }
        });
        historyStartDatePicker.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate endDate = historyEndDatePicker.getValue();
                if (endDate != null && date.compareTo(endDate) > 0) {
                    date = endDate;
                }
                return date;
            }
        });
    }

    /**
     * Sets the DateCell creators for the end date picker
     *
     * @param event
     */
    public void setEndDateUpdater(javafx.event.Event event) {
        historyEndDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate startDate = historyStartDatePicker.getValue();
                if (startDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.compareTo(startDate) < 0);
                }
            }
        });
        historyEndDatePicker.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate startDate = historyStartDatePicker.getValue();
                if (startDate != null && date.compareTo(startDate) < 0) {
                    date = startDate;
                }
                return date;
            }
        });
    }

    /**
     * Iterates through the available end dates and sets which are selectable depending on the start date
     *
     * @param actionEvent
     */
    public void updateSelectableEndDates(javafx.event.ActionEvent actionEvent) {
        for (Node element : historyEndDatePicker.getChildrenUnmodifiable()) {
            if (element instanceof DateCell) {
                DateCell dateElement = (DateCell) element;
                dateElement.updateItem(dateElement.getItem(), dateElement.isEmpty());
            }
        }
        //TODO add code to add and remove orders from the table in a separate function
    }

    /**
     * Iterates through the available start dates and sets which are selectable depending on the end date
     *
     * @param actionEvent
     */
    public void updateSelectableStartDates(javafx.event.ActionEvent actionEvent) {
        for (Node element : historyStartDatePicker.getChildrenUnmodifiable()) {
            if (element instanceof DateCell) {
                DateCell dateElement = (DateCell) element;
                dateElement.updateItem(dateElement.getItem(), dateElement.isEmpty());
            }
        }
        //TODO add code to add and remove orders from the table in a separate function
    }



}
