//package stepDefinitions;
//
//import cucumber.api.PendingException;
//import cucumber.api.java.Before;
//import cucumber.api.java.en.And;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import org.junit.jupiter.api.Disabled;
//import seng202.group5.*;
//
//
//import static org.junit.Assert.*;
//
//@Disabled
//public class Stepdefs {
//    private Order order;
//    private MenuItem burger;
//    private Stock stock;
//    private double burgerCost;
//    private double chipCost;
//    private Recipe burgerRecipe;
//    private MenuItem chip;
//    private Ingredient buns = new Ingredient("Buns", "bun", "Bread", "TestBun", 5.00);
//
//
//    @Before
//    public void Before() {
//        burgerCost = 0.00;
//        burgerRecipe = new Recipe("burgerRecipe", "Text");
//        stock = new Stock();
//        burger = new MenuItem("Burger", burgerRecipe, burgerCost, 0.00, "testId");
//    }
//    @Given("Order exists")
//    public void Order_exists() {
//        order = new Order();
//    }
//    @When("Burger is added to order")
//    public void Burger_is_added_to_order() {
//        order.modifyItemQuantity(burger, 1);
//    }
//
//    @Then("Orders total cost is ${double}")
//    public void ordersTotalCostIs$(int arg0, int arg1) {
//        double d = Double.parseDouble(arg0 + "." + arg1);
//        assertEquals(order.getTotalCost(), d, 0.10);
//    }
//
//    @Then("Receive error")
//    public void receiveError() {
//        throw new cucumber.api.PendingException();
//
//    }
//    @Disabled
//    @And("A Burger costs ${double}")
//    public void aBurgerCosts$(int arg0, int arg1) {
//        double d = Double.parseDouble(arg0 + "." + arg1);
//        burger = new MenuItem("Burger", burgerRecipe, d, 0.00, "testId");
//    }
//
//
//    @Given("Stock has {int} of Buns")
//    public void stockHasOfBuns(int arg0) {
//        stock.addNewIngredient(buns, arg0);
//    }
//
//
//    @When("{int} Buns are used")
//    public void bunsAreUsed(int arg0) {
//        stock.modifyQuantity("TestBun", stock.getIngredientQuantity("TestBun") - arg0);
//    }
//
//    @Then("Stock now has {int} of Buns")
//    public void stockNowHasOfBuns(int arg0) {
//        assertEquals(stock.getIngredientQuantity("TestBun"), arg0);
//    }
//
//    @Then("Order contains a burger")
//    public void orderContainsABurger() {
//        assertTrue(order.getOrderItems().containsKey(burger));
//    }
//
//    @When("Chips are added to order")
//    public void chipsAreAddedToOrder() {
//        Recipe chipRecipe = new Recipe("chipRecipe", "Text");
//        chip = new MenuItem("chip", chipRecipe, chipCost, 0.00, "chipId");
//    }
//
//    @And("Chips cost ${double}")
//    public void chipsCost$(int arg0, int arg1) {
//        chipCost = Double.parseDouble(arg0 + "." + arg1);
//    }
//
//    @And("Burger is in order")
//    public void burgerIsInOrder() {
//        Burger_is_added_to_order();
//    }
//
//    @And("order contains chips")
//    public void orderContainsChips() {
//        assertTrue(order.getOrderItems().containsKey(chip));
//    }
//
//    @And("A Burger contains buns")
//    public void aBurgerContainsBuns() {
//        burgerRecipe.addIngredient(buns, 1);
//    }
//
//    @And("Order Does not contain burger")
//    public void orderDoesNotContainBurger() {
//        assertFalse(order.getOrderItems().containsKey(burger));
//    }
//
//    @When("burger Recipe is modified to contain {int} buns")
//    public void burgerRecipeIsModifiedToContainBuns(int arg0) {
//        burgerRecipe.addIngredient(buns, arg0);
//    }
//
//    @Then("Burger in the order contains {int} buns")
//    public void burgerInTheOrderContainsBuns(int arg0) {
//        throw new cucumber.api.PendingException();
//    }
//
//    @When("Burger is removed from order")
//    public void burgerIsRemovedFromOrder() {
//        order.removeItem(burger);
//    }
//
//
//    @When("Order is viewed")
//    public void orderIsViewed() {}
//}
