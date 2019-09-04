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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyStepDefs {
    private ArrayList<Money> change;
    private Finance finance;
    private Money cost;


    @Before
    public void Before() {
        change = new ArrayList<>();
        finance = new Finance();

    }
    @Given("Order Costs ${double}")
    public void orderCosts$(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        cost = Money.parse("NZD "+d);
    }

    @When("Payment of ${double} if confirmed")
    public void paymentOf$IfConfirmed(int arg0, int arg1) throws InsufficientCashException {
        double d = Double.parseDouble(arg0 + "." + arg1);
        ArrayList<Money> payment= new ArrayList<>();
        payment.add(Money.parse("NZD "+d));
        change = finance.pay(cost, payment, 100);
    }

    @Then("${double} is displayed to be returned")
    public void $IsDisplayedToBeReturned(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        double sum = 0;
        for (Money money: change)
        {
            sum += money.getAmountMajorLong();
        }
        assertEquals(sum, d, 0.10);
    }

    @When("Orders is refunded")
    public void ordersIsRefunded() {
        change = finance.refund("ABC123");
    }

    @And("Order has already been payed for")
    public void orderHasAlreadyBeenPayedFor() throws InsufficientCashException {
        ArrayList<Money> payment= new ArrayList<>();
        payment.add(Money.parse("NZD "+cost));
        finance.pay(cost, payment, 100);
    }
}
