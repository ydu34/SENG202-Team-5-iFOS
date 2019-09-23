package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.joda.money.Money;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A controller for managing the invoice screen
 */
public class InvoiceController extends GeneralController {

    @FXML
    private Text totalChangeDisplay;

    @FXML
    private Text totalCostDisplay;

    @FXML
    private Text changeDisplay;

    /**
     * Displays the items in the order
     */
    @FXML
    private TableView<MenuItem> currentOrderTable;

    @FXML
    private TableColumn<MenuItem, String> itemNameCol;

    @FXML
    private TableColumn<MenuItem, String> itemQuantityCol;

    @FXML
    private TableColumn<MenuItem,String> itemPriceCol;

    @FXML
    private Button eftposButton;

    private ArrayList<Money> payment = new ArrayList<>();

    private Money total = Money.parse("NZD 0");

    private Order currentOrder;

    private Map<MenuItem, Integer> orderItemsMap;

    /**
     * The initializer for this controller
     */
    public void pseudoInitialize() {
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException ignored) {
        }
        Money totalCost = currentOrder.getTotalCost();

        totalCostDisplay.setText("Total Cost: "+ totalCost);
        currentOrderTable();
        eftposButton.setDisable(true);
    }

    /**
     * This method goes through the list which contains the list of menu items for the current order and displays the menu item
     * and the price and its quantity on the in the table view.
     */
    public void currentOrderTable() {
        orderItemsMap = currentOrder.getOrderItems();
        List<MenuItem> orderItems = new ArrayList<>(orderItemsMap.keySet());
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemPriceCol.setCellValueFactory(data -> {
                                             int quantity = orderItemsMap.get(data.getValue());
                                             Money totalPrice = data.getValue().getTotalCost().multipliedBy(quantity);
                                             return new SimpleStringProperty(totalPrice.toString());
                                         }
        );

        itemQuantityCol.setCellValueFactory(data -> {
            int quantity = orderItemsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));

        });

        currentOrderTable.setItems(FXCollections.observableArrayList(orderItems));
    }

    /**
     * Confirms order payment in cash
     */
    public void payCash() {
        try {
            if (getAppEnvironment().getOrderManager().getOrder().getTotalCost().equals(Money.parse("NZD 0.00"))) {
                throw new NoOrderException("No order exists to get");
            } else {
                try {
                    Order order = getAppEnvironment().getOrderManager().getOrder();
                    ArrayList<Money> change = getAppEnvironment().confirmPayment(payment);
                    StringBuilder display = new StringBuilder();
                    Money totalChange = Money.parse("NZD 0.00");
                    Money totalPayment = Money.parse("NZD 0.00");
                    for (Money money : change) {
                        display.append(money).append("\n");
                        totalChange = totalChange.plus(money);
                    }
                    for (Money money : payment) {
                        totalPayment = totalPayment.plus(money);
                    }
                    changeDisplay.setText(display.toString());
                    if (totalPayment.minus(totalChange).minus(order.getTotalCost()).isGreaterThan(Money.parse("NZD 0.00"))) {
                        totalChangeDisplay.setText("Change: " + totalChange + "\nMissing Change: " + totalPayment.minus(totalChange).minus(order.getTotalCost()));
                    } else {
                        totalChangeDisplay.setText("Change: " + totalChange);
                    }

                } catch (InsufficientCashException e) {
                    changeDisplay.setText("Amount payed is less than cost.\nTotal Payed: " + total);

                }
            }
        } catch (NoOrderException e) {
            changeDisplay.setText("There is no order to pay for.");
            total = Money.parse("NZD 0");

        }

    }


    public void editItem(){

    }

    /**
     * Resets the list containing the currently given denominations
     */
    public void clearPayment() {
        total = Money.parse("NZD 0");
        payment = new ArrayList<>();

        totalChangeDisplay.setText("Change: <amount>");
        changeDisplay.setText("");
    }

    /**
     * Adds the specified cash denomination to add to the total
     *
     * @param value the cash denomination to add in cents
     */
    private void addMoney(int value){
        Money money = Money.parse("NZD "+0.01*value);
        total = total.plus(money);
        payment.add(money);
        changeDisplay.setText("Total Payed: "+total);
    }

    /**
     * Cancels the current order
     */
    @FXML
    private void cancelOrder() {
//        clearPayment();
//        totalCost = Money.parse("NZD 0");
//        totalCostDisplay.setText("Total Cost: "+ totalCost);
        try {

            currentOrder = getAppEnvironment().getOrderManager().getOrder();
            currentOrder.resetStock(getAppEnvironment().getStock());
            currentOrder.clearItemsInOrder();
        } catch (NoOrderException e) {

        }
        pseudoInitialize();
    }

    @FXML
    private void addTenCent(){
        addMoney(10);
    }

    @FXML
    private void addTwentyCent(){
        addMoney(20);
    }

    @FXML
    private void addFiftyCent(){
        addMoney(50);
    }

    @FXML
    private void addOneDollar(){
        addMoney(100);
    }

    @FXML
    private void addTwoDollar(){
        addMoney(200);
    }

    @FXML
    private void addFiveDollar(){
        addMoney(500);
    }

    @FXML
    private void addTenDollar(){
        addMoney(1000);
    }

    @FXML
    private void addTwentyDollar(){
        addMoney(2000);
    }

    @FXML
    private void addFiftyDollar(){
        addMoney(5000);
    }

    @FXML
    private void addHundredDollar(){
        addMoney(10000);
    }

}
