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
import seng202.group5.exceptions.NoOrderException;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InvoiceController extends GeneralController {

    @FXML
    private Text totalChangeDisplay;

    @FXML
    private Text totalCostDisplay;

    @FXML
    private Text changeDisplay;

    @FXML
    private Text orderDisplay;

    private Money totalCost;

    private ArrayList<Money> payment = new ArrayList<>();

    private Money total = Money.parse("NZD 0");

    public void pseudoInitialize() {
        try {
            totalCost = super.getAppEnvironment().getOrderManager().getOrder().getTotalCost();
        } catch (NoOrderException ignored) {
            totalCost = Money.parse("NZD 0");
        }
        totalCostDisplay.setText("Total Cost: "+ totalCost);
    }
    public void payCash() {
        try {
            if (super.getAppEnvironment().getOrderManager().getOrder().getTotalCost().equals(Money.parse("NZD 0.00"))) {
                throw new NoOrderException("No order exists to get");
            } else {
                System.out.println((super.getAppEnvironment().getOrderManager().getOrder().getTotalCost()));
                try {
                    ArrayList<Money> change = super.getAppEnvironment().getFinance().pay(totalCost, payment, LocalDateTime.now());
                    String display = "";
                    Money totalChange = Money.parse("NZD 0.00");
                    for (Money money : change) {
                        display += money + "\n";
                        totalChange = totalChange.plus(money);
                    }
                    changeDisplay.setText(display);
                    totalChangeDisplay.setText("Change: " + totalChange);
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
        clearPayment();
        totalCost = Money.parse("NZD 0");
        totalCostDisplay.setText("Total Cost: "+ totalCost);
        orderDisplay.setText("");
        super.getAppEnvironment().getOrderManager().newOrder();
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
