package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import seng202.group5.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class Stepdefs {
    private Order order;
    private MenuItem burger;
    private Stock stock;
    @Given("Order exists")
    public void Order_exists() {
        // Write code here that turns the phrase above into concrete actions
        order = new Order();
    }
    @When("Burger is added to order")
    public void Burger_is_added_to_order() {
        // Write code here that turns the phrase above into concrete actions

        order.modifyItemQuantity(burger, 1);

    }

    @Then("Orders total cost is ${double}")
    public void ordersTotalCostIs$(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        assertEquals(order.getTotalCost(), arg0, 0.10);
    }

    @And("Order contains burger")
    public void orderContainsBurger() {
        HashMap itemList = order.getOrderItems();

        throw new cucumber.api.PendingException();

    }

    @Given("there are no buns")
    public void there_are_no_buns() {
        // Write code here that turns the phrase above into concrete actions
        stock = new Stock();
        Ingredient buns = new Ingredient("Buns", "bun", "Bread", "TestBun", 5.00);
        stock.addNewIngredient(buns);
        throw new cucumber.api.PendingException();
    }


    @Then("Receive error")
    public void receiveError() {
        throw new cucumber.api.PendingException();

    }
    @And("A Burger costs ${double}")
    public void aBurgerCosts$(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        Recipe burgerRecipe = new Recipe("burgerRecipe", "Text");
        burger = new MenuItem("Burger", burgerRecipe, d, "testId");
    }



    @Given("Stock has {int} of Buns")
    public void stockHasOfBuns(int arg0) {
        throw new cucumber.api.PendingException();
    }


    @When("{int} Buns are used")
    public void bunsAreUsed(int arg0) {
        throw new cucumber.api.PendingException();
    }

    @Then("Stock now has {int} of Buns")
    public void stockNowHasOfBuns(int arg0) {
        throw new cucumber.api.PendingException();
    }

    @Given("Order Costs ${double}")
    public void orderCosts$(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        throw new cucumber.api.PendingException();
    }

    @When("Payment of ${int} if confirmed")
    public void paymentOf$IfConfirmed(int arg0) {
        throw new cucumber.api.PendingException();
    }


    @Then("The change ${double} is is displayed and removed from till")
    public void theChange$IsIsDisplayedAndRemovedFromTill(int arg0, int arg1) {
        double d = Double.parseDouble(arg0 + "." + arg1);
        throw new cucumber.api.PendingException();
    }



}
