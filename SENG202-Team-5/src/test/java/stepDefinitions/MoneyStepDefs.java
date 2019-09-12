package stepDefinitions;

import org.joda.money.Money;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng202.group5.*;
import seng202.group5.exceptions.InsufficientCashException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyStepDefs {
    private ArrayList<Money> change;
    private Finance finance;
    private Money cost;
    private Till till;


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
        change = finance.pay(cost, payment, 100);
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
        change = finance.refund("test0");
    }

    @And("Order has already been payed for")
    public void orderHasAlreadyBeenPayedFor() throws InsufficientCashException {
        ArrayList<Money> payment= new ArrayList<>();
        payment.add(cost);
        finance.pay(cost, payment, 100);
    }

    @Given("till starts with {int} ${double} notes")
    public void tillStartsWith$Notes(int arg0, double arg1) {

        till = new Till();
        till.addDenomination(Money.parse("NZD "+ arg1), arg0);

    }

    @When("an order is payed for with {int} ${double} note")
    public void anOrderIsPayedForWith$Note(int arg0, double arg1) {
        till.addDenomination(Money.parse("NZD "+ arg1), arg0);
    }

    @Then("till has {int} ${double} notes")
    public void tillHas$Notes(int arg0, double arg1) {
        HashMap<Money, Integer> expected = new HashMap<>();
        expected.put(Money.parse("NZD "+ arg1), arg0);
        assertEquals(expected, till.getDenominations());

    }
}
