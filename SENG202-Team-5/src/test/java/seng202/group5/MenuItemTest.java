package seng202.group5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private MenuItem testBurger;
    private Order testOrder;
    private Worker testWorker;
    private Stock testStock;

    @BeforeEach
    void init() {
        HashMap<MenuItem, Integer> orderItems = new HashMap<MenuItem, Integer>();
        HashMap<String, Integer> ingredientStock = new HashMap<String, Integer>();
        HashMap<String, Order> transactionHistory = new HashMap<String, Order>();
        Order testOrder = new Order(orderItems, 0.00, "1234");
        Recipe testRecipe = new Recipe("Cheeseburger", "It's raw.");
        MenuItem testBurger = new MenuItem("Burger", testRecipe, 10.00, "BRG10");
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(testBurger);
        Stock testStock = new Stock(ingredientStock);
        History testHistory = new History(transactionHistory);
        Worker testWorker = new Worker(testOrder, menuItems, testStock, testHistory);
    }
    @Test
    void addStock() {
        testBurger.addStock(testWorker.getCurrentStock().getIngredientStock()); // getCurrentStock returns Stock yet addStock takes a map as input

    }

    @Test
    void removeStock() {
    }
}