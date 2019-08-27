package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng202.group5.MenuItem;
import seng202.group5.Order;
import seng202.group5.Recipe;

import static org.junit.Assert.*;

public class Stepdefs {
    private Order order;
    @Given("Order exists")
    public void Order_exists() {
        // Write code here that turns the phrase above into concrete actions
        order = new Order();
    }
    @When("Burger is added to order")
    public void Burger_is_added_to_order() {
        // Write code here that turns the phrase above into concrete actions
        Recipe burgerRecipe = new Recipe("burgerRecipe", "Text", (float) 5.00);
        MenuItem burger = new MenuItem("Burger", burgerRecipe,5.00, "testId");
        order.modifyItemQuantity(burger, 1);

    }

    @Then("Order costs ${int}")
    public void orderCosts$(int arg0) {
        throw new cucumber.api.PendingException();

    }

    @And("Order contains burger")
    public void orderContainsBurger() {
        throw new cucumber.api.PendingException();


    }

    @Given("there are no buns")
    public void there_are_no_buns() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }


    @Then("Receive error")
    public void receiveError() {
        throw new cucumber.api.PendingException();

    }
}
