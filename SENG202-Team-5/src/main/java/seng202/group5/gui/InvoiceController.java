package seng202.group5.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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

public class InvoiceController extends GeneralController {

    @FXML
    private Text totalChangeDisplay;

    @FXML
    private Text totalCostDisplay;

    @FXML
    private Text changeDisplay;


    @FXML
    private TableView<MenuItem> currentOrderTable;

    @FXML
    private TableColumn<MenuItem, String> itemNameCol;

    @FXML
    private TableColumn<MenuItem, String> itemQuantityCol;

    private Money totalCost;

    private ArrayList<Money> payment = new ArrayList<>();

    private Money total = Money.parse("NZD 0");

    private Order currentOrder;

    private Map<MenuItem, Integer> orderItemsMap;

    public void pseudoInitialize() {
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
        } catch (NoOrderException e) {
        }
        totalCost = currentOrder.getTotalCost();

        totalCostDisplay.setText("Total Cost: "+ totalCost);
        currentOrderTable();
    }

    public void currentOrderTable() {
        orderItemsMap = currentOrder.getOrderItems();
        List<MenuItem> orderItems = new ArrayList<>(orderItemsMap.keySet());
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        itemQuantityCol.setCellValueFactory(data -> {
            int quantity = orderItemsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));

        });

        currentOrderTable.setItems(FXCollections.observableArrayList(orderItems));
    }
    public void payCash() {
        try {
            if (getAppEnvironment().getOrderManager().getOrder().getTotalCost().equals(Money.parse("NZD 0.00"))) {
                throw new NoOrderException("No order exists to get");
            } else {
                System.out.println((getAppEnvironment().getOrderManager().getOrder().getTotalCost()));
                try {
                    Order order = getAppEnvironment().getOrderManager().getOrder();
                    ArrayList<Money> change = super.getAppEnvironment().getFinance().pay(totalCost, payment, LocalDateTime.now(), order.getID());
                    String display = "";
                    Money totalChange = Money.parse("NZD 0.00");
                    Money totalPayment = Money.parse("NZD 0.00");
                    for (Money money : change) {
                        display += money + "\n";
                        totalChange = totalChange.plus(money);
                    }
                    for (Money money : payment) {
                        totalPayment = totalPayment.plus(money);
                    }
                    changeDisplay.setText(display);
                    if (totalPayment.minus(totalChange).minus(order.getTotalCost()).isGreaterThan(Money.parse("NZD 0.00"))) {
                        totalChangeDisplay.setText("Change: " + totalChange + "\nMissing Change: " + totalPayment.minus(totalChange).minus(order.getTotalCost()));
                    } else {
                        totalChangeDisplay.setText("Change: " + totalChange);
                    }

                    int quantity = 0;
                    super.getAppEnvironment().getHistory().setTransactionHistory(order.getID(), order);
                    for(MenuItem item: order.getOrderItems().keySet()) {
                        for (Ingredient i :item.getRecipe().getIngredientsAmount().keySet()) {
                            quantity = item.getRecipe().getIngredientsAmount().get(i)*orderItemsMap.get(item);
                            super.getAppEnvironment().getStock().reduceQuantity(i.getID(), quantity);
                        }
                    }
                    super.getAppEnvironment().getOrderManager().newOrder();
                } catch (InsufficientCashException e) {
                    changeDisplay.setText("Amount payed is less than cost.\nTotal Payed: " + total);

                }
            }
        } catch (NoOrderException e) {
            changeDisplay.setText("There is no order to pay for.");
            total = Money.parse("NZD 0");

        }

    }


    public void clearPayment() {
        total = Money.parse("NZD 0");
        payment = new ArrayList<>();

        totalChangeDisplay.setText("Change: <amount>");
        changeDisplay.setText("");
    }

    private void addMoney(int value){
        Money money = Money.parse("NZD "+0.01*value);
        total = total.plus(money);
        payment.add(money);
        changeDisplay.setText("Total Payed: "+total);
    }
    @FXML
    private void cancelOrder() {
//        clearPayment();
//        totalCost = Money.parse("NZD 0");
//        totalCostDisplay.setText("Total Cost: "+ totalCost);
        super.getAppEnvironment().getOrderManager().newOrder();
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
