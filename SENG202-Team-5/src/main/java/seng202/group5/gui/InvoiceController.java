package seng202.group5.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.joda.money.Money;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

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
            if (getAppEnvironment().getOrderManager().getOrder().getTotalCost().equals(Money.parse("NZD 0.00"))) {
                throw new NoOrderException("No order exists to get");
            } else {
                System.out.println((getAppEnvironment().getOrderManager().getOrder().getTotalCost()));
                try {
                    Order order = getAppEnvironment().getOrderManager().getOrder();
                    ArrayList<Money> change = super.getAppEnvironment().getFinance().pay(totalCost, payment, LocalDateTime.now(), order.getID());
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
