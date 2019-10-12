package seng202.group5.gui.invoice;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.gui.GeneralController;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.Order;

import java.io.IOException;
import java.util.*;

/**
 * A controller for managing the invoice screen
 * @author Tasman Berry, Shivin Gaba, Michael Morgoun
 */
public class InvoiceController extends GeneralController {

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
    private Label totalCostLabel;

    @FXML
    private Label currentlyPayedLabel;

    @FXML
    private Text denomDollarLabel;

    @FXML
    private Text denomCentLabel;

    @FXML
    private Label warningLabel;

    @FXML
    private Button removeItem;

    @FXML
    private Button payCashButton;

    private HashMap<Money, Integer> currentPayment = new HashMap<>();

    private ArrayList<Money> paymentArray = new ArrayList<>();

    private Money total = Money.parse("NZD 0");

    private Order currentOrder;

    private Map<MenuItem, Integer> orderItemsMap;


    /**
     * The initializer for this controller
     */
    public void pseudoInitialize() {
        super.pseudoInitialize();
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
            removeItem.setDisable(true);
        } catch (NoOrderException ignored) {
        }
        Money totalCost = currentOrder.getTotalCost();

        totalCostLabel.setText("$" + totalCost.toString().replaceAll("[^\\d.]", ""));
        currentOrderTable();

        currentOrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                removeItem.setDisable(false);
            }
        });

        // Disabling payCashButton
        payCashButton.setDisable(true);
        // Settings all labels to default
        currentlyPayedLabel.setText("$0.00");
        warningLabel.setText("");
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
            Money totalPrice = data.getValue().calculateFinalCost().multipliedBy(quantity);
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
            // Pass through the payment to AppEnvironment
            ArrayList<Money> change = getAppEnvironment().confirmPayment(paymentArray);

            initialiseChangeScreen(change);

            // Refresh table
            pseudoInitialize();
            clearPayment();
        } catch (InsufficientCashException e) { ;
        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("Not enough money in the till for change!");
        }
    }

    /**
     * This method intialises the screen used for a successful payment.
     * @param change An ArrayList<Money> which has all the change required.
     */
    public void initialiseChangeScreen(ArrayList<Money> change) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/paymentSuccess.fxml"));
            Parent root = loader.load();

            PaymentSuccessController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.initModality(Modality.APPLICATION_MODAL);

            controller.setChange(change);
            controller.pseudoInitialize();

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the list containing the currently given denominations
     */
    public void clearPayment() {
        total = Money.parse("NZD 0");
        currentPayment = new HashMap<>();
        paymentArray = new ArrayList<>();

        currentlyPayedLabel.setText("$0.00");
        denomDollarLabel.setText("");
        denomCentLabel.setText("");

        payCashButton.setDisable(true);
    }

    /**
     * A method used to create the string to visualise the denominations payed in the current payment.
     * @param builder The StringBuilder.
     * @param key The String key which is the denomination.
     * @param tempKey The Money tempKey which is used to get the number of denominations.
     * @return The final StringBuilder built.
     */
    public StringBuilder stringBuilder(StringBuilder builder, String key, Money tempKey) {
        builder.append("$");
        builder.append(Float.parseFloat(key));
        builder.append(": ");
        builder.append(currentPayment.get(tempKey));
        builder.append("\n");

        return builder;
    }

    /**
     * Adds the specified cash denomination to add to the total
     *
     * @param value the cash denomination to add in cents
     */
    private void addMoney(int value){
        if (currentOrder.getOrderItems().isEmpty()) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("There is no order to pay for!");
        } else {
            // Setting the label to nothing in case it's set to something already
            warningLabel.setText("");

            // Adding the money to the total
            Money money = Money.parse("NZD " + 0.01 * value);
            total = total.plus(money);

            // Adding money to a HashSet containing it's quantity
            if (currentPayment.containsKey(money)) {
                currentPayment.replace(money, currentPayment.get(money) + 1);
            } else {
                currentPayment.put(money, 1);
            }

            // Disable pay button when there isn't enough money payed yet
            if (total.isGreaterThan(currentOrder.getTotalCost()) || total.isEqual(currentOrder.getTotalCost())) {
                payCashButton.setDisable(false);
            }

            // Adding the money to the array that will be passed into the AppEnvironment
            paymentArray.add(money);

            ArrayList<Money> keyArray = new ArrayList<>(currentPayment.keySet());

            // Creating the strings to visualise the amount payed thus far
            StringBuilder tempDollar = new StringBuilder();
            StringBuilder tempCent = new StringBuilder();
            for (Money tempKey : keyArray) {
                if (tempKey.isLessThan(Money.parse("NZD 5.00"))) {
                    String key = tempKey.toString().replaceAll("[^\\d.]", "");
                    tempCent = stringBuilder(tempCent, key, tempKey);
                } else {
                    String key = tempKey.toString().replaceAll("[^\\d.]", "");
                    tempDollar = stringBuilder(tempDollar, key, tempKey);
                }
            }
            // Setting the labels to the strings.
            denomCentLabel.setText(tempCent.toString());
            denomDollarLabel.setText(tempDollar.toString());
            currentlyPayedLabel.setText("$" + total.toString().replaceAll("[^\\d.]", ""));
        }
    }

    /**
     * Cancels the current order.
     */
    @FXML
    private void cancelOrder() {
        try {
            currentOrder = getAppEnvironment().getOrderManager().getOrder();
            currentOrder.resetStock(getAppEnvironment().getStock());
            currentOrder.clearItemsInOrder();
        } catch (NoOrderException ignored) {

        }
        // Refresh currentOrderTable
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

    /**
     * This function removes the selected menu item from the current order.
     */
    @FXML
    private void deleteRowFromTable() {
        boolean someOrder;

        currentOrder.removeItem(currentOrderTable.getSelectionModel().getSelectedItem());
        someOrder =  this.currentOrderTable.getItems().remove(this.currentOrderTable.getSelectionModel().getSelectedItem());
        if(someOrder){
            removeItem.setDisable(false);
        }
    }
}
