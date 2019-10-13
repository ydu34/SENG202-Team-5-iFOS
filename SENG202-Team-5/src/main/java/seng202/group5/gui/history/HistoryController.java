package seng202.group5.gui.history;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.skins.JFXDatePickerContent;
import com.jfoenix.skins.JFXDatePickerSkin;
import javafx.application.Platform;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import org.joda.money.Money;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Transaction;
import seng202.group5.logic.Order;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    private TextField historySearchBar;

    /**
     * The table that displays the history of the orders
     */
    @FXML
    private TableView<Transaction> historyTable;

    // These are the rows of the history table
    @FXML
    private TableColumn<Transaction, String> rowID;

    @FXML
    private TableColumn<Transaction, String> rowDate;

    @FXML
    private TableColumn<Transaction, String> rowTime;

    @FXML
    private TableColumn<Transaction, String> rowOrder;

    @FXML
    private TableColumn<Transaction, String> rowCost;

    @FXML
    private TableColumn<Transaction, JFXButton> rowAction;

    @FXML
    private JFXButton addPastOrderButton;

    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        setEndDateUpdater();
        setStartDateUpdater();

        // Listener for the historySearchBar text field to not allow letters and only numbers
        historySearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}?")) {
                historySearchBar.setText(oldValue);
            }
        });


        // Setting the factories for creating values to display for each order
        rowID.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        rowDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getDateTime().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))));

        rowTime.setCellValueFactory(cellData -> {
            LocalTime time = cellData.getValue().getDateTime().toLocalTime();
            time = time.minusSeconds(time.getSecond());
            time = time.minusNanos(time.getNano());
            return new ReadOnlyStringWrapper(time.toString());
        });

        rowOrder.setCellValueFactory(cellData -> {
            String output = cellData.getValue().getOrder().printReceipt();
            output = output.replace("\n", ", ");
            return new ReadOnlyStringWrapper(output);
        });

        rowCost.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        // The factory for this is quite complicated since it uses a button instead
        rowAction.setCellValueFactory(param -> {
            JFXButton refundButton = new JFXButton("Refund");
            Order order = param.getValue().getOrder();
            // Disable the button if the order cannot be refunded
            HashMap<String, Transaction> transactions = getAppEnvironment().getFinance().getTransactionHistory();
            if (transactions.containsKey(order.getId())) {
                refundButton.setDisable(transactions.get(order.getId()).isRefunded());
            } else {
                refundButton.setDisable(true);
            }
            refundButton.setOnAction((ActionEvent event) -> {
                refundButton.setDisable(true);
                refundOrder(order, refundButton);
            });
            return new ReadOnlyObjectWrapper<>(refundButton);
        });

        try {
            addPastOrderButton.setDisable(!getAppEnvironment().getOrderManager().getOrder().getOrderItems().isEmpty());
        } catch (NoOrderException e) {
            e.printStackTrace();
        }

        historyTable.getItems().addAll(getAppEnvironment().getFinance().getTransactionHistory().values());
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
            controller.setAppEnvironment(getAppEnvironment());
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
     * Sets the DateCell creators for the start date picker
     *
     */
    public void setStartDateUpdater() {
        historyStartDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate endDate = historyEndDatePicker.getValue();
                historyEndDatePicker.valueProperty().addListener((unused, old, newObj) -> this.setDisable(date.isAfter(newObj)));
                if (endDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.isAfter(endDate));
                }
            }
        });
        historyStartDatePicker.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate endDate = historyEndDatePicker.getValue();
                if (endDate != null && date.isAfter(endDate)) {
                    date = endDate;
                }
                return date;
            }
        });
    }

    /**
     * Sets the DateCell creators for the end date picker
     *
     */
    public void setEndDateUpdater() {
        historyEndDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate startDate = historyStartDatePicker.getValue();
                historyStartDatePicker.valueProperty().addListener((unused, old, newObj) -> this.setDisable(date.isBefore(newObj)));
                if (startDate == null) {
                    setDisable(empty);
                } else {
                    setDisable(empty || date.isBefore(startDate));
                }
            }
        });
        historyEndDatePicker.setConverter(new LocalDateStringConverter() {
            @Override
            public LocalDate fromString(String input) {
                LocalDate date = super.fromString(input);
                LocalDate startDate = historyStartDatePicker.getValue();
                if (startDate != null && date.isBefore(startDate)) {
                    date = startDate;
                }
                return date;
            }
        });
    }

    /**
     * Updates the orders that are visible in the order table
     *
     */
    public void updateVisibleOrders() {
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
        Collection<Transaction> historyValues = getAppEnvironment().getFinance().getTransactionHistory().values();
        historyTable.getItems().removeAll(historyValues);
        String searchString = historySearchBar.getCharacters().toString();
        for (Transaction transaction : historyValues) {
            Order order = transaction.getOrder();
            if (transaction.getDateTime().isAfter(firstTime) &&
                    transaction.getDateTime().isBefore(lastTime) &&
                    order.getId().matches(".*" + searchString + ".*")) {
                historyTable.getItems().add(transaction);
            }

        }
    }

    /**
     * Adds a new order to the history
     *
     * @param order the order to add to the history
     */
    public void addNewOrder(Order order, LocalDateTime datetime) {

        HashMap<String, Transaction> history = getAppEnvironment().getFinance().getTransactionHistory();

        if (history.containsKey(order.getId())) {
            //TODO create a formal error display system
            System.out.println("Order already exists in history!");
        } else {
            Transaction tempTransaction = new Transaction(datetime, order.getTotalCost(), order);
            history.put(order.getId(), tempTransaction);
            updateVisibleOrders();
        }
    }

    /**
     * Uses a duplicate of the Order screen to create an order to put in the history
     *
     * @param event an event that caused this to happen
     */
    public void addPastOrder(ActionEvent event) {
        AddPastOrderController controller = AddPastOrderController.changeToPastOrderScreen(event, this);
        controller.resetOrder();
    }

}
