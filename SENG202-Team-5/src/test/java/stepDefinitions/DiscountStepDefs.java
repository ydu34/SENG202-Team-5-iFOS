package stepDefinitions;

import org.joda.money.Money;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Customer;
import seng202.group5.information.MenuItem;
import seng202.group5.logic.*;
import static org.joda.money.Money.parse;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountStepDefs {

    private OrderManager manager;
    private Stock stock;
    private MenuItem item1;
    private Customer customer;

    @Before
    public void Before() {
        stock = new Stock();
        manager = new OrderManager(stock);
        manager.newOrder();
        item1 = new MenuItem();
        manager.newOrder();
        customer = new Customer();
    }


    @Given("Customer has {int} purchase points")
    public void customer_has_purchase_points(Integer int1) {
        customer.setPurchasePoints(int1);
    }

    @When("Customer uses {int} purchase points on an order which costs ${double}")
    public void customer_uses_purchase_points(Integer int1, Double dub1) {
        Money currentCost = parse("NZD " + dub1);
        System.out.println(currentCost);
        item1.setTotalCost(currentCost);
        System.out.println(item1.getTotalCost());

        try {
            manager.getOrder().addItem(item1, 1);
            System.out.println(manager.getOrder().getTotalCost());
        } catch (NoOrderException e) {
            fail();
        }

        Money result = customer.discount(int1, parse("NZD " + dub1));
        try {
            manager.getOrder().applyDiscount(result);
        } catch (NoOrderException e) {
            fail();
        }
    }

    @Then("Order now Costs ${double}")
    public void order_now_Costs_$(Double double1) {
        try {
            assertEquals(double1, manager.getOrder().getTotalCost());
        } catch (NoOrderException e) {
            fail();
        }
    }

    @Then("Customer now has {int} purchase points")
    public void customer_now_has_purchase_points(Integer int1) {
        assertEquals(int1, customer.getPurchasePoints());
    }

    @When("Order gets payed")
    public void order_gets_payed() {
    }

}

