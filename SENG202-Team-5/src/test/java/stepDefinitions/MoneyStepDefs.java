package stepDefinitions;

import org.joda.money.Money;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.logic.Finance;
import seng202.group5.logic.Till;
import seng202.group5.information.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyStepDefs {
    private ArrayList<Money> change;
    private Finance finance;
    private Money cost;
    private ArrayList<Money> salesData;


    @Before
    public void Before() {
        change = new ArrayList<>();
        finance = new Finance();

    }
    @Given("Order Costs ${double}")
    public void orderCosts$(double arg0) {
        cost = Money.parse("NZD "+arg0);
    }

    @When("Payment of ${double} is confirmed")
    public void paymentOf$IfConfirmed(double arg0) throws InsufficientCashException {
        ArrayList<Money> payment= new ArrayList<>();
        payment.add(Money.parse("NZD "+arg0));
        change = finance.pay(cost, payment, LocalDateTime.now(), "");
    }

    @Then("${double} is displayed to be returned")
    public void $IsDisplayedToBeReturned(double arg0) {
        double sum = 0;
        for (Money money: change)
        {
            sum += money.getAmountMinorLong()/100.0;
        }
        assertEquals(arg0, sum, 0.10);
    }

    @When("Orders is refunded")
    public void ordersIsRefunded() {
        for (Transaction transaction : finance.getTransactionHistoryClone().values()) {
            change = finance.refund(transaction.getTransactionID());
        }
    }

    @And("Order has already been payed for")
    public void orderHasAlreadyBeenPayedFor() throws InsufficientCashException {
        ArrayList<Money> payment= new ArrayList<>();
        payment.add(cost);
        finance.pay(cost, payment, LocalDateTime.now(), "");
    }

    @Given("till starts with {int} ${double} notes")
    public void tillStartsWith$Notes(int arg0, double arg1) {
        ArrayList<Money> denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD "+ arg1));
        finance.setTill(new Till(denomination));

        finance.getTill().addDenomination(Money.parse("NZD "+ arg1), arg0);

    }

    @When("an order is payed for with {int} ${double} note")
    public void anOrderIsPayedForWith$Note(int arg0, double arg1) {
        finance.getTill().addDenomination(Money.parse("NZD "+ arg1), arg0);
    }

    @Then("till has {int} ${double} notes")
    public void tillHas$Notes(int arg0, double arg1) {
        HashMap<Money, Integer> expected = new HashMap<>();
        expected.put(Money.parse("NZD "+ arg1), arg0);
        assertEquals(expected, finance.getTill().getDenominations());

    }

    @And("till starts with change")
    public void tillStartsWithChange() {
        ArrayList<Money> denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));
        finance.setTill(new Till(denomination));
        for (Money value: denomination) {
            finance.getTill().addDenomination(value, 10);
        }
    }

    @When("Sales data is viewed")
    public void salesDataIsViewed() {
        salesData = finance.totalCalculator(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1), null);
    }

    @Then("Order is in sales data")
    public void orderIsInSalesData() {
        assertTrue(salesData.get(0).isGreaterThan(Money.parse("NZD 0.00")));
    }
}
