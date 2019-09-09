package seng202.group5.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.joda.money.Money;
import seng202.group5.Finance;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.ArrayList;

public class InvoiceController {

    @FXML
    private Button launchOrderScreenButton;

    @FXML
    private Button launchStockScreenButton;

    @FXML
    private Button launchAdminScreenButton;

    @FXML
    private Button launchHistoryScreenButton;

    @FXML
    private Text totalChangeDisplay;

    @FXML
    private Text totalCostDisplay;

    @FXML
    private Text changeDisplay;

    @FXML
    private Text orderDisplay;

    private Order order;

    private Money totalCost;

    private Finance finance;

    private ArrayList<Money> payment = new ArrayList<>();

    private Money total = Money.parse("NZD 0");

    public void changeScreen(ActionEvent event, String scenePath){
        Parent sampleScene = null;
        try {
            sampleScene = FXMLLoader.load(getClass().getResource(scenePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (sampleScene != null) {
            Stage oldStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            oldStage.setScene(new Scene(sampleScene, 821, 628));
        }

    }

    public void launchOrderScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/order.fxml");
    }

    public void launchStockScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/stock.fxml");
    }

    public void launchAdminScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/admin.fxml");
    }

    public void launchHistoryScreen(javafx.event.ActionEvent actionEvent) {
        changeScreen(actionEvent, "/gui/history.fxml");
    }

    public void payCash() {
        if (order != null) {
            try {
                ArrayList<Money> change = finance.pay(total, payment, 300);
                String display = "";
                Money totalChange = Money.parse("NZD 0.00");
                for (Money money: change) {
                    display += money + "\n";
                    totalChange = totalChange.plus(money);
                }
                changeDisplay.setText(display);
                totalChangeDisplay.setText("Change: " + totalChange);
            } catch (InsufficientCashException e) {
                changeDisplay.setText("Amount payed is less than cost.\nTotal Payed: "+total);

            }
        } else {
            changeDisplay.setText("There is no order to pay for.");
            total = Money.parse("NZD 0");

        }

    }

    public void setOrder(Order order) {
        this.order = order;
        totalCost = order.getTotalCost();
        totalCostDisplay.setText("Total Cost: " + totalCost);

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
        clearPayment();
        totalCostDisplay.setText("Total Cost: <amount>");
        orderDisplay.setText("");
        order = null;
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
