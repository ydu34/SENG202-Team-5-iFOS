package seng202.group5.gui.history;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.joda.money.Money;
import seng202.group5.*;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Transaction;

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

    /**
     * The TextField for searching for an order by ID
     */
    @FXML
    private TextField historySearchbar;

    /**
     * The table that displays the history of the orders
     */
    @FXML
    private TableView<Order> historyTable;

    // These are the rows of the history table

    @FXML
    private TableColumn<Order, String> rowID;

    @FXML
    private TableColumn<Order, String> rowDate;

    @FXML
    private TableColumn<Order, String> rowTime;

    @FXML
    private TableColumn<Order, String> rowOrder;

    @FXML
    private TableColumn<Order, Button> rowAction;

    /**
     * A map from order IDs to the related transactions
     */
    private HashMap<String, Transaction> orderIDTransactionIndex;

    @Override
    public void pseudoInitialize() {
        orderIDTransactionIndex = new HashMap<>();
        for (Transaction transaction : getAppEnvironment().getFinance().getTransactionHistoryClone().values()) {
            orderIDTransactionIndex.put(transaction.getOrderID(), transaction);
        }

        // This sets the factories for creating values to display for each order
        rowID.setCellValueFactory(new PropertyValueFactory<>("iD"));
        rowDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getDateTimeProcessed().toLocalDate().toString()));
        rowTime.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue().getDateTimeProcessed().toLocalTime();
            time = time.minusSeconds(time.getSecond());
            time = time.minusNanos(time.getNano());
            return new ReadOnlyStringWrapper(time.toString());
        });
        rowOrder.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        // The factory for this is quite complicated since it uses a button instead
        rowAction.setCellValueFactory(param -> {
            Button refundButton = new Button("Refund");
            Order order = param.getValue();
            // Disable the button if the order cannot be refunded
            if (orderIDTransactionIndex.containsKey(order.getId())) {
                refundButton.setDisable(orderIDTransactionIndex.get(order.getId()).isRefunded());
            } else {
                refundButton.setDisable(true);
            }
            refundButton.setOnAction((ActionEvent event) -> {
                refundOrder(order, refundButton);
                refundButton.setDisable(true);
            });
            return new ReadOnlyObjectWrapper<>(refundButton);
        });

        historyTable.getItems().addAll(getAppEnvironment().getOrderManager().getHistory().getTransactionHistory().values());
    }

    /**
     * Refunds the given order
     *
     * @param orderToRefund the order to refund
     */
    private void refundOrder(Order orderToRefund, Button button) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/refundOrder.fxml"));
            Parent root = loader.load();

            ConfirmRefundController controller = loader.getController();
            controller.setSource(this);
            controller.setButton(button);
            controller.setOrder(orderToRefund);

            Stage stage = new Stage();
            stage.setTitle("Confirm Refund");
            stage.setScene(new Scene(root, 600, 200));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Confirms the refund of an order
     *
     * @param orderID the ID of the order to refund
     * @return a list of coins to give back to the customer
     */
    public ArrayList<Money> confirmOrderRefund(String orderID) {
        return getAppEnvironment().getFinance().refund(orderIDTransactionIndex.get(orderID).getTransactionID());
    }

    /**
     * Sets the DateCell creators for the start date picker
     *
     * @param event an event that caused this to happen
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
     * @param event an event that caused this to happen
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
     * @param actionEvent an event that caused this to happen
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
     * @param actionEvent an event that caused this to happen
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
     * @param event an event that caused this to happen
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
                    order.getId().matches(".*" + searchString + ".*")) {
                historyTable.getItems().add(order);
            }

        }
    }

    /**
     * Adds a new order to the history
     *
     * @param order the order to add to the history
     */
    public void addNewOrder(Order order) {

        HashMap<String, Order> history = getAppEnvironment().getOrderManager().getHistory().getTransactionHistory();

        if (history.containsKey(order.getId())) {
            //TODO create a formal error display system
            System.out.println("Order already exists in history!");
        } else {
            history.put(order.getId(), order);
            updateVisibleOrders(new ActionEvent());
        }
    }

    /**
     * Uses a duplicate of the Order screen to create an order to put in the history
     *
     * @param event an event that caused this to happen
     */
    public void addPastOrder(ActionEvent event) {
        AddPastOrderController.changeToPastOrderScreen(event, this);
    }

}
