package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerTest {

    private OrderManager testOrderManager;
    private Ingredient bun = new Ingredient("Bun", "Bread", "ARZ4O2", Money.parse("NZD 1.2"));
    private Ingredient patty = new Ingredient("Patty", "Meat", "5ES240", Money.parse("NZD 3.4"));
    private MenuItem testItem = new MenuItem(
            "Burger Item",
            new Recipe("Burger",
                       "Add items to burger",
                       new HashMap<>() {{
                           put(bun, 2);
                           put(patty, 1);
                       }}),
            Money.parse("NZD 5.80"),
            "14328",
            true
    );

    @BeforeEach
    public void init() {
        Stock stock = new Stock();
        stock.addNewIngredient(bun, 200);
        stock.addNewIngredient(patty, 100);
        testOrderManager = new OrderManager(new Order(stock), stock);

    }

    @Test
    public void testAddItemAddsItem() {
        Stock stock = new Stock();
        stock.addNewIngredient(bun, 200);
        stock.addNewIngredient(patty, 100);
        testOrderManager = new OrderManager(new Order(stock), stock);

        HashMap<MenuItem, Integer> initialOrderItems = (HashMap<MenuItem, Integer>)
                testOrderManager.getOrder().getOrderItems().clone();
        initialOrderItems.put(testItem, 3);
        testOrderManager.getOrder().addItem(testItem, 3);
        assertEquals(initialOrderItems, testOrderManager.getOrder().getOrderItems());
    }

    @Test
    public void testNewOrderReplacesOrder() {
        Order testOrder = testOrderManager.getOrder();
        testOrderManager.newOrder();
        assertNotSame(testOrder, testOrderManager.getOrder());
        testOrder = testOrderManager.getOrder();
        testOrderManager.newOrder();
        assertNotSame(testOrder, testOrderManager.getOrder());
    }

    @Test
    public void testStockSetCorrectly() {
        Stock stock = new Stock();
        testOrderManager = new OrderManager(new Order(stock), stock);
        assertEquals(stock, testOrderManager.getStock());
        stock = new Stock();
        testOrderManager = new OrderManager(stock);
        assertEquals(stock, testOrderManager.getStock());
        stock = new Stock();
        testOrderManager.setStock(stock);
        assertEquals(stock, testOrderManager.getStock());
    }

}
