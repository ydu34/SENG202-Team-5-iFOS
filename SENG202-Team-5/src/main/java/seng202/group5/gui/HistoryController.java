package seng202.group5.gui;

/**
 * @author Shivin Gaba, Daniel Harris
 */


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import seng202.group5.History;
import seng202.group5.Order;
import seng202.group5.OrderManager;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class HistoryController extends GeneralController {


    @FXML
    private DatePicker historyStartDatePicker;

    @FXML
    private DatePicker historyEndDatePicker;

    @FXML
    private TextField historySearchbar;

    @FXML
    private TableView<Order> historyTable;

    @FXML
    private TableColumn rowID;

    @FXML
    private TableColumn rowDate;

    @FXML
    private TableColumn rowTime;

    @FXML
    private TableColumn rowOrder;

    @FXML
    private TableColumn rowAction;

    @Override
    public void pseudoInitialize() {
        rowID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        rowDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                ((TableColumn.CellDataFeatures<Order, String>) cellData).getValue().getDateTimeProcessed().
                        toLocalDate().toString()));
        rowTime.setCellValueFactory(cellData -> {
            LocalTime time = ((TableColumn.CellDataFeatures<Order, String>) cellData).getValue()
                    .getDateTimeProcessed().toLocalTime();
            return new ReadOnlyStringWrapper(String.format("%d:%d", time.getHour(), time.getMinute()));
        });
        rowOrder.setCellValueFactory(new PropertyValueFactory<>("totalCost"));

        historyTable.getItems().addAll(getAppEnvironment().getOrderManager().getHistory().getTransactionHistory().values());
    }

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
        updateVisibleOrders(actionEvent);
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
        updateVisibleOrders(actionEvent);
    }


    /**
     * Updates the orders that are visible in the order table
     *
     * @param event
     */
    public void updateVisibleOrders(javafx.event.Event event) {
        LocalDate firstDate = historyStartDatePicker.getValue();
        LocalDate lastDate = historyEndDatePicker.getValue();
        LocalDateTime firstTime;
        if (firstDate == null) {
            firstTime = LocalDateTime.MIN;
        } else {
            firstTime = LocalDateTime.of(firstDate, LocalTime.MIN);
        }
        LocalDateTime lastTime;
        if (lastDate == null) {
            lastTime = LocalDateTime.MAX;
        } else {
            lastTime = LocalDateTime.of(lastDate, LocalTime.MAX);
        }
        Collection<Order> historyValues = getAppEnvironment().getOrderManager().getHistory().getTransactionHistory().values();
        historyTable.getItems().removeAll(historyValues);
        String searchString = historySearchbar.getCharacters().toString();
        for (Order order : historyValues) {
            if (order.getDateTimeProcessed().isAfter(firstTime) &&
                    order.getDateTimeProcessed().isBefore(lastTime) &&
                    order.getID().matches(".*" + searchString + ".*")) {
                historyTable.getItems().add(order);
            }

        }
    }

    public void addNewOrder(Order order) {

        HashMap<String, Order> history = getAppEnvironment().getOrderManager().getHistory().getTransactionHistory();

        if (history.containsKey(order.getID())) {
            //TODO create a formal error display system
            System.out.println("Order already exists in history!");
        } else {
            history.put(order.getID(), order);
            updateVisibleOrders(new ActionEvent());
        }
    }

}
