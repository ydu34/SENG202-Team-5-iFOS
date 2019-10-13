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
import seng202.group5.gui.GeneralController;
import seng202.group5.information.Customer;
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
    private Label remainingCostLabel;

    @FXML
    private Label discountLabel;

    @FXML
    private Label totalPayedLabel;

    @FXML
    private Label warningLabel;

    @FXML
    private Label customerNameLabel;

    @FXML
    private Label customerPointsLabel;

    @FXML
    private Text denomDollarLabel;

    @FXML
    private Text denomCentLabel;

    @FXML
    private Button removeItem;

    @FXML
    private Button payCashButton;

    @FXML
    private Button existingMemberButton;

    @FXML
    private Button newMemberButton;

    private HashMap<Money, Integer> currentPayment = new HashMap<>();

    private ArrayList<Money> paymentArray = new ArrayList<>();

    private Money totalPayed = Money.parse("NZD 0");

    private Order currentOrder;

    private Map<MenuItem, Integer> orderItemsMap;

    private Customer currentCustomer;

    private int customerPoints;


    /**
     * The initializer for this controller
     */
    public void pseudoInitialize() {
        super.pseudoInitialize();

        // Attempts to get the current order and customer
        currentOrder = getAppEnvironment().getOrderManager().getOrder();
        currentCustomer = currentOrder.getCurrentCustomer();
        removeItem.setDisable(true);

        // Sets the total cost of the order
        Money totalCost = currentOrder.getTotalCost();
        remainingCostLabel.setText("$" + totalCost.toString().replaceAll("[^\\d.]", ""));
        totalPayedLabel.setText("$" + currentOrder.getDiscount().plus(currentOrder.getTotalCost()).toString().replaceAll("[^\\d.]", ""));
        currentOrderTable();

        // Creates a new listener for the removeItem button
        currentOrderTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null){
                removeItem.setDisable(false);
            }
        });

        // If a customer exists, displays information about the customer
        if (currentCustomer != null) {
            customerNameLabel.setText("Selected Member: " + currentCustomer.getName());
            customerPointsLabel.setText("Current Points: " + currentCustomer.getPurchasePoints());

            // Change button labels to when a customer exists
            existingMemberButton.setText("Clear Selected Member");
            newMemberButton.setText("Apply Discount");
        }

        // Disabling payCashButton
        if (currentOrder.getTotalCost().isPositive() || currentOrder.getOrderItems().isEmpty()) {
            payCashButton.setDisable(true);
            payCashButton.setTextFill(Color.GREY);
        } else {
            payCashButton.setDisable(false);
            payCashButton.setTextFill(Color.GREEN);
        }
        // Settings all labels to default
        warningLabel.setText("");
    }

    /**
     * This method goes through the list which contains the list of menu items for the current order and displays the menu item
     * and the price and its quantity on the in the table view.
     */
    private void currentOrderTable() {
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
     * Opens a screen to search and choose and existing member of the loyalty club.
     * Or if a customer already exists, it clears the selected customer.
     */
    @FXML
    private void existingMember() {
        if (currentCustomer != null) {
            clearSelectedMember();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/existingCustomer.fxml"));
                Parent root = loader.load();

                ExistingCustomerController controller = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 545, 650));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Find an existing Member!");

                controller.setCustomers(getAppEnvironment().getCustomers());

                // Set AppEnvironment so that it can change the current customer
                controller.setAppEnvironment(getAppEnvironment());
                controller.pseudoInitialize();

                // Waits for the window to close before reinitialising
                stage.showAndWait();
                pseudoInitialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method opens the menu for a new Member.
     * Or if a customer is already a part of the order, it opens the Apply Discount screen.
     */
    @FXML
    private void newMember() {
        if (currentCustomer != null) {
            applyDiscount();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/newCustomer.fxml"));
                Parent root = loader.load();

                NewCustomerController controller = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Create a new Member!");

                controller.setCustomers(getAppEnvironment().getCustomers());
                controller.pseudoInitialize();

                stage.showAndWait();

                // If a new customer was created, show that information on the screen
                Customer customer = controller.getCustomer();
                if (customer != null) {
                    // Sets the currentCustomer of the order to the new one
                    currentOrder.setCurrentCustomer(customer);

                    pseudoInitialize();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void applyDiscount() {
        if (currentOrder.getOrderItems().isEmpty()) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("There is no order to discount!");
        } else if (!paymentArray.isEmpty()) {
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Can not apply discount after inputting cash!");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/applyDiscount.fxml"));
                Parent root = loader.load();

                ApplyDiscountController controller = loader.getController();

                Stage stage = new Stage();
                stage.setScene(new Scene(root, 600, 400));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Apply a discount!");

                controller.setAppEnvironment(getAppEnvironment());
                controller.setCustomer(currentCustomer);
                controller.setMaxPrice(currentOrder.getTotalCost().minus(totalPayed));
                controller.pseudoInitialize();

                stage.showAndWait();

                // Set the discount
                Money moneySaved = controller.getMoneySaved();
                currentOrder.setDiscount(moneySaved);

                customerPoints = controller.getPoints();
                discountLabel.setText("$" + Money.parse("NZD " + discountLabel.getText().replace("$", "")).plus(moneySaved).toString().replaceAll("[^\\d.]", ""));
                remainingCostLabel.setText("$" + currentOrder.getTotalCost().toString().replaceAll("[^\\d.]", ""));
                pseudoInitialize();
            } catch (IOException e) {}
        }
    }

    private void clearSelectedMember() {
        currentOrder.setCurrentCustomer(null);
        currentOrder.setDiscount(Money.parse("NZD 0"));
        if (customerPoints != 0) {
            currentCustomer.addPurchasePoints(customerPoints);
        }
        currentCustomer = null;
        customerPoints = 0;
        // Clear labels
        discountLabel.setText("$0.00");
        customerNameLabel.setText("");
        customerPointsLabel.setText("");
        existingMemberButton.setText("Existing Member");
        newMemberButton.setText("New Member");

        pseudoInitialize();
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
            cancelOrder();
            clearPayment();
            clearSelectedMember();
        } catch (InsufficientCashException e) {
        warningLabel.setTextFill(Color.RED);
        warningLabel.setText("Not enough money in the till for change!");
        }
    }

    /**
     * This method intialises the screen used for a successful payment.
     * @param change An ArrayList<Money> which has all the change required.
     */
    private void initialiseChangeScreen(ArrayList<Money> change) {
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
        // Clear local variables
        totalPayed = Money.parse("NZD 0");
        currentPayment = new HashMap<>();
        paymentArray = new ArrayList<>();
        remainingCostLabel.setText("$" + currentOrder.getTotalCost().toString().replaceAll("[^\\d.]", ""));

        // Clear money labels
        totalPayedLabel.setText("$0.00");
        denomDollarLabel.setText("");
        denomCentLabel.setText("");

        // Disable the pay button
        payCashButton.setDisable(true);
        payCashButton.setTextFill(Color.GREY);
    }

    /**
     * A method used to create the string to visualise the denominations payed in the current payment.
     * @param builder The StringBuilder.
     * @param key The String key which is the denomination.
     * @param tempKey The Money tempKey which is used to get the number of denominations.
     * @return The final StringBuilder built.
     */
    private StringBuilder stringBuilder(StringBuilder builder, String key, Money tempKey) {
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
            totalPayed = totalPayed.plus(money);

            // Minus the money from the visual total
            remainingCostLabel.setText("$" + Money.parse("NZD " + remainingCostLabel.getText().replace("$", "")).minus(money).toString().replaceAll("NZD ", ""));

            // Adding money to a HashSet containing it's quantity
            if (currentPayment.containsKey(money)) {
                currentPayment.replace(money, currentPayment.get(money) + 1);
            } else {
                currentPayment.put(money, 1);
            }

            // Adding the money to the array that will be passed into the AppEnvironment
            paymentArray.add(money);

            // Disable pay button when there isn't enough money payed yet
            if (Money.parse("NZD " + remainingCostLabel.getText().replace("$", "")).isPositive()) {
                payCashButton.setDisable(true);
                payCashButton.setTextFill(Color.GREY);
            } else {
                payCashButton.setDisable(false);
                payCashButton.setTextFill(Color.GREEN);
            }

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
            totalPayedLabel.setText("$" + totalPayed.toString().replaceAll("[^\\d.]", ""));
        }
    }

    /**
     * Cancels the current order.
     */
    @FXML
    private void cancelOrder() {
        clearPayment();
        remainingCostLabel.setText("$0.00");

        currentOrder = getAppEnvironment().getOrderManager().getOrder();
        currentOrder.resetStock(getAppEnvironment().getStock());
        currentOrder.clearItemsInOrder();

        // Clear customer labels
        discountLabel.setText("$0.00");
        customerPoints = 0;
        currentOrder.setCurrentCustomer(null);
        customerNameLabel.setText("");
        customerPointsLabel.setText("");

        // Reset Customer Buttons
        existingMemberButton.setText("Existing Member");
        newMemberButton.setText("New Member");
        // Refresh currentOrderTable
        currentOrderTable();
    }

    /**
     * Adds 10 Cents to the payment.
     */
    @FXML
    private void addTenCent(){
        addMoney(10);
    }

    /**
     * Adds 20 cents to the payment.
     */
    @FXML
    private void addTwentyCent(){
        addMoney(20);
    }

    /**
     * Adds 50 cents to the payment.
     */
    @FXML
    private void addFiftyCent(){
        addMoney(50);
    }

    /**
     * Adds 1 dollar to the payment.
     */
    @FXML
    private void addOneDollar(){
        addMoney(100);
    }

    /**
     * Adds 2 dollars to the payment.
     */
    @FXML
    private void addTwoDollar(){
        addMoney(200);
    }

    /**
     * Adds 5 dollars to the payment.
     */
    @FXML
    private void addFiveDollar(){
        addMoney(500);
    }

    /**
     * Adds 10 dollars to the payment.
     */
    @FXML
    private void addTenDollar(){
        addMoney(1000);
    }

    /**
     * Adds 20 dollars to the payment.
     */
    @FXML
    private void addTwentyDollar(){
        addMoney(2000);
    }

    /**
     * Adds 50 dollars to the payment.
     */
    @FXML
    private void addFiftyDollar(){
        addMoney(5000);
    }

    /**
     * Adds 100 dollars to the payment.
     */
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

        currentOrder.removeItem(currentOrderTable.getSelectionModel().getSelectedItem(), true);
        someOrder =  this.currentOrderTable.getItems().remove(this.currentOrderTable.getSelectionModel().getSelectedItem());
        if(someOrder){
            removeItem.setDisable(false);
        }
    }
}
