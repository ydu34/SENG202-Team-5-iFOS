package seng202.group5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */

class MenuItemTest {

    private MenuItem testBurger;
    private Order testOrder;
    private OrderManager testOrderManager;
    private Stock testStock;

    @BeforeEach
    void init() {
        HashMap<MenuItem, Integer> orderItems = new HashMap<MenuItem, Integer>();
        HashMap<String, Ingredient> ingredients = new HashMap<String, Ingredient>();
        HashMap<String, Integer> ingredientStock = new HashMap<String, Integer>();
        HashMap<String, Order> transactionHistory = new HashMap<String, Order>();
        Order testOrder = new Order(orderItems, 0.00, "1234");
        Recipe testRecipe = new Recipe("Cheeseburger", "It's raw.");
        MenuItem testBurger = new MenuItem("Burger", testRecipe, 3.00, 10.00,  "BRG10");
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(testBurger);
        Stock testStock = new Stock(ingredients, ingredientStock);
        History testHistory = new History(transactionHistory);
        OrderManager testOrderManager = new OrderManager(testOrder, menuItems, testStock, testHistory);
    }
//    @Test
//    void testAddStock() {
//        testBurger.addStock(testOrderManager.getCurrentStock().getIngredientStock()); // getCurrentStock returns Stock yet addStock takes a map as input
//
//    }

    @Test
    void testRemoveStock() {
    }
}