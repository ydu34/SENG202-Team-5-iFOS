package seng202.group5.gui;

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
import javafx.util.Callback;
import javafx.util.converter.LocalDateStringConverter;
import org.joda.money.Money;
import seng202.group5.*;
import seng202.group5.MenuItem;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * A controller to manage the History screen
 *
 * @author Shivin Gaba, Daniel Harris
 */
public class HistoryController extends GeneralController {


    @FXML
    private DatePicker historyStartDatePicker;

    @FXML
    private DatePicker historyEndDatePicker;

    /** The TextField for searching for an order by ID */
    @FXML
    private TextField historySearchbar;

    /** The table that displays the history of the orders */
    @FXML
    private TableView<Order> historyTable;

    @FXML
    private TableColumn<Order, String> rowID;

    @FXML
    private TableColumn<Order, String> rowDate;

    @FXML
    private TableColumn<Order, String> rowTime;

    @FXML
    private TableColumn<Order, String> rowOrder;

    @FXML
    private TableColumn<Order, Void> rowAction;

    private HashMap<String, Transaction> orderIDTransactionIndex;

    @Override
    public void pseudoInitialize() {
        orderIDTransactionIndex = new HashMap<>();
        for (Transaction transaction : getAppEnvironment().getFinance().getTransactions().values()) {
            orderIDTransactionIndex.put(transaction.getOrderID(), transaction);
        }

        rowID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        rowDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getDateTimeProcessed().toLocalDate().toString()));
        rowTime.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue().getDateTimeProcessed().toLocalTime();
            return new ReadOnlyStringWrapper(String.format("%d:%d", time.getHour(), time.getMinute()));
        });
        rowOrder.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        rowAction.setCellFactory(createCellFactory());

        //TODO make the refund button disabled if the order has already been refunded

        historyTable.getItems().addAll(getAppEnvironment().getOrderManager().getHistory().getTransactionHistory().values());
    }

    private Callback<TableColumn<Order, Void>, TableCell<Order, Void>> createCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Order, Void> call(final TableColumn<Order, Void> tableColumn) {
                final TableCell<Order, Void> cell = new TableCell<>() {
                    private final Button refundButton = new Button("Refund");

                    {
                        refundButton.setOnAction((ActionEvent event) -> {
                            Order order = getTableView().getItems().get(getIndex());
                            refundOrder(order);
                            setDisable(true);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(refundButton);
                        }
                    }
                };
                return cell;
            }
        };
    }

    /**
     * Refunds the given order
     *
     * @param orderToRefund the order to refund
     */
    private void refundOrder(Order orderToRefund) {
        //TODO this is unfinished.
        String transactionID = orderIDTransactionIndex.get(orderToRefund.getID()).getTransactionID();
        for (Money coin : getAppEnvironment().getFinance().refund(transactionID)) {
            System.out.println(coin);
        }
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
