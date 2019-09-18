package stepDefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Disabled;
import seng202.group5.*;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Disabled
public class Stepdefs {
    private Order order;
    private MenuItem burger;
    private Stock stock;
    private Money burgerCost;
    private Money chipCost;
    private Recipe burgerRecipe;
    private MenuItem chip;
    private MenuManager manager;
    private Ingredient buns = new Ingredient("Buns", "Kg", "Bread", "TestBun", Money.parse("NZD 5.00"));
    private boolean error;



    @Before
    public void Before() {
        manager = new MenuManager();
        burgerCost = Money.zero(CurrencyUnit.of("NZD"));
        burgerRecipe = manager.createRecipe("burgerRecipe", new HashMap<>(), "Text");
        stock = new Stock();
        stock.addNewIngredient(buns);
        error = false;
        manager.createItem("Burger", burgerRecipe, burgerCost,"testId", true);
        burger = manager.getItemList().get("testId");
    }
    @Given("Order exists")
    public void Order_exists() {

        order = new Order(new HashMap<>(), Money.parse("NZD 0.00"), "Test01", stock);

    }

    @When("Burger is added to order")
    public void Burger_is_added_to_order() {
        error = !order.addItem(burger, 1);

    }

    @Then("Orders total cost is ${double}")
    public void ordersTotalCostIs$(double arg0) {
        DecimalFormat df = new DecimalFormat("#.00");
        Money newArg = Money.parse("NZD " + df.format(arg0));
        assertEquals(newArg, order.getTotalCost());
    }

    @Then("Receive error")
    public void receiveError() {
        assertTrue(error);

    }

    @And("A Burger costs ${double}")
    public void aBurgerCosts$(double arg0) {
        Money currentCost = manager.getItemList().get("testId").calculateMakingCost();
        manager.removeItem("testId");
        DecimalFormat df = new DecimalFormat("#.00");
        Money newArg = Money.parse("NZD " + df.format(arg0));
        manager.removeItem("testId");
        manager.createItem("Burger", burgerRecipe, newArg.minus(currentCost), "testId", true);
        burger = manager.getItemList().get("testId");
    }


    @Given("Stock has {int} of Buns")
    public void stockHasOfBuns(int arg0) {
        stock.modifyQuantity("TestBun", arg0);
    }


    @When("{int} Buns are used")
    public void bunsAreUsed(int arg0) {
        stock.modifyQuantity("TestBun", stock.getIngredientQuantity("TestBun") - arg0);
    }

    @Then("Stock now has {int} of Buns")
    public void stockNowHasOfBuns(int arg0) {
        assertEquals(arg0, stock.getIngredientQuantity("TestBun"));
    }

    @Then("Order contains a burger")
    public void orderContainsABurger() {
        assertTrue(order.getOrderItems().containsKey(burger));
    }

    @When("Chips are added to order")
    public void chipsAreAddedToOrder() {

        Recipe chipRecipe = manager.createRecipe("chipRecipe", new HashMap<>(), "Text");
        manager.createItem("chip", chipRecipe, chipCost, "chipId", true);
        chip = manager.getItemList().get("chipId");
        order.addItem(chip, 1);
    }

    @And("Chips cost ${double}")
    public void chipsCost$(double arg0) {
        DecimalFormat df = new DecimalFormat("#.00");
        chipCost = Money.parse("NZD " + df.format(arg0));
    }

    @And("Burger is in order")
    public void burgerIsInOrder() {
        Burger_is_added_to_order();
    }

    @And("order contains chips")
    public void orderContainsChips() {
        assertTrue(order.getOrderItems().containsKey(chip));
    }

    @And("A Burger contains buns")
    public void aBurgerContainsBuns() {
        burgerRecipe.addIngredient(buns, 1);
        manager.removeItem("testId");
        manager.createItem("Burger", burgerRecipe, burgerCost,"testId", true);
        burger = manager.getItemList().get("testId");

    }

    @And("Order Does not contain burger")
    public void orderDoesNotContainBurger() {
        assertFalse(order.getOrderItems().containsKey(burger));
    }

    @When("burger Recipe is modified to contain {int} buns")
    public void burgerRecipeIsModifiedToContainBuns(int arg0) {
        burgerRecipe.addIngredient(buns, arg0);
    }

    @Then("Burger in the order contains {int} buns")
    public void burgerInTheOrderContainsBuns(int arg0) {
        boolean pass = false;
        for (MenuItem item : order.getOrderItems().keySet()) {
            if (item.getID().equals(burger.getID()) &&
                    item.getRecipe().getIngredientsAmount().containsKey(buns) &&
                    item.getRecipe().getIngredientsAmount().get(buns) == arg0) {
                pass = true;
                break;
            }
        }
        assertTrue(pass);
    }

    @When("Burger is removed from order")
    public void burgerIsRemovedFromOrder() {
        order.removeItem(burger);
    }


    @When("Order is viewed")
    public void orderIsViewed() {
    }

    @When("^Order is canceled$")
    public void orderIsCanceled() {
        // Write code here that turns the phrase above into concrete actions
        order = new Order(new HashMap<>(), Money.parse("NZD 0.00"), "Test01", stock);
    }
}
