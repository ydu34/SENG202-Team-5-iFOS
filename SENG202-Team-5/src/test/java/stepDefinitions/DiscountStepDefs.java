package stepDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.joda.money.Money;
import seng202.group5.information.*;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.OrderManager;
import seng202.group5.logic.Stock;

import java.util.HashMap;

import static org.joda.money.Money.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seng202.group5.information.TypeEnum.MAIN;

public class DiscountStepDefs {

    private OrderManager manager;
    private Stock stock;
    private MenuItem item1;
    private Customer customer;
    private Recipe itemRecipe;
    private MenuManager menuManager;
    private Ingredient ingredient;
    private CustomerSettings customerSettings;

    @Before
    public void Before() {
        stock = new Stock();
        ingredient = new Ingredient("Bun", "veg", parse("NZD 0.00"));
        stock.addNewIngredient(ingredient, 2000);
        manager = new OrderManager(stock);
        manager.newOrder();
        customerSettings = new CustomerSettings();
        customerSettings.setRatio(10);
        customerSettings.setInitialPurchasePoints(1);
        customer = new Customer();
    }


    @Given("Customer has {int} purchase points")
    public void customer_has_purchase_points(Integer int1) {
        customer.setPurchasePoints(int1);
    }

    @When("Customer uses {int} purchase points on an order which costs ${double}")
    public void customer_uses_purchase_points(Integer int1, Double dub1) {
        Money currentCost = parse("NZD " + dub1);
        ingredient.setPrice(currentCost);
        HashMap<Ingredient, Integer> ingredientMap = new HashMap<>();
        ingredientMap.put(ingredient, 1);

        itemRecipe = new Recipe("recipe", "recipe", ingredientMap);
        MenuItem item1 = new MenuItem("burger", itemRecipe, parse("NZD 0.00"), true, MAIN);
        boolean ans = manager.getOrder().addItem(item1, 1);

        Money result = customer.discount(int1, parse("NZD " + dub1), Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        manager.getOrder().setDiscount(result);
    }

    @Then("Order now Costs ${double}")
    public void order_now_Costs_$(Double double1) {
        assertEquals(parse("NZD " + double1), manager.getOrder().getTotalCost());
    }

    @Then("Customer now has {int} purchase points")
    public void customer_now_has_purchase_points(Integer int1) {
        assertEquals((int) int1, customer.getPurchasePoints());
    }

    @When("Order gets payed ${double}")
    public void order_gets_payed(double double1) {
        customer.purchasePoints(parse("NZD " + double1), customerSettings.getRatio());
    }

}

