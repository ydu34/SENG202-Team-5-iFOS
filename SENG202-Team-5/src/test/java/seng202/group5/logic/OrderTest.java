package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.TypeEnum;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    private Order order;


    @BeforeEach
    public void init() {
        order = new Order(new Stock());
    }

    @Test
    public void testApplyingDiscount() {
        HashMap<MenuItem, Integer> orderList = new HashMap<MenuItem, Integer>();
        Money cost = Money.parse("NZD 125.50");
        order = new Order(orderList, cost, "ABC123");

        Money amount = Money.parse("NZD 20.00");
        Money value = cost.minus(amount);

        order.setDiscount(amount);

        assertEquals(value, order.getTotalCost());
    }

    @Test
    public void testAddItem() {
        order = new Order();

        Ingredient ingredient = new Ingredient("Name", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);
        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true, TypeEnum.MAIN);

        HashMap<String, Ingredient> ingredientStock = new HashMap<String, Ingredient>();
        ingredientStock.put(ingredient.getID(), ingredient);
        HashMap<String, Integer> numberStock = new HashMap<String, Integer>();
        numberStock.put(ingredient.getID(), 1);
        Stock stock = new Stock(ingredientStock, numberStock);

        order = new Order(stock);
        assertTrue(order.addItem(item, 1));

        assertEquals(order.getStock().getIngredientQuantity("ABC123"), 0);

        assertFalse(order.addItem(item, 1));
    }


    @Test
    public void testRemoveItem() {
        Ingredient ingredient = new Ingredient("Name", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);
        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true, TypeEnum.MAIN);

        HashMap<String, Ingredient> ingredientStock = new HashMap<String, Ingredient>();
        ingredientStock.put(ingredient.getID(), ingredient);
        HashMap<String, Integer> numberStock = new HashMap<String, Integer>();
        numberStock.put(ingredient.getID(), 1);
        Stock stock = new Stock(ingredientStock, numberStock);

        order = new Order(stock);
        order.addItem(item, 1);


        assertTrue(order.removeItem(item, true));

        assertEquals(order.getStock().getIngredientQuantity("ABC123"), 1);

        assertFalse(order.removeItem(item, true));
    }

    @Test
    public void testOneRemoveItemRemovesItemFromOrder() {
        Ingredient ingredient = new Ingredient("Name", "Cate", "ABC123", Money.parse("NZD 10.0"));
        MenuItem item = new MenuItem();

        HashMap<String, Ingredient> ingredientStock = new HashMap<String, Ingredient>();
        ingredientStock.put(ingredient.getID(), ingredient);
        HashMap<String, Integer> numberStock = new HashMap<String, Integer>();
        numberStock.put(ingredient.getID(), 1);
        Stock stock = new Stock(ingredientStock, numberStock);

        order = new Order(stock);
        order.addItem(item, 5);
        order.removeItem(item, false);
        assertEquals(4, (int) order.getOrderItems().get(item));
    }

    @Test
    public void testModifyItemQuantity() {
        Ingredient ingredient = new Ingredient("Name", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);

        Stock stock = new Stock();
        stock.addNewIngredient(ingredient, 2);
        order = new Order(stock);

        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true, TypeEnum.MAIN);

        HashMap<MenuItem, Integer> orderItems = new HashMap<MenuItem, Integer>();
        orderItems.put(item, 1);

        Money tempTotalCost = order.getTotalCost();
        order.addItem(item, 1);
        stock = order.getStock();
        Order order = new Order(orderItems, tempTotalCost, "1", stock);

        assertTrue(order.modifyItemQuantity(item, 2));

        assertEquals(2, (int) order.getOrderItems().get(item));
        assertFalse(order.modifyItemQuantity(item, 3));
        assertEquals(2, (int) order.getOrderItems().get(item));


        MenuItem temp = new MenuItem(null, null, null, false, null);
        assertFalse(order.modifyItemQuantity(temp, 3));
    }

    @Test
    public void testClearItems() {
        MenuItem item = new MenuItem();
        order.addItem(item, 2);

        assertFalse(order.getOrderItems().isEmpty());

        order.clearItemsInOrder();

        assertTrue(order.getOrderItems().isEmpty());
    }

    @Test
    public void testPrintReceipt() {
        MenuItem item = new MenuItem();

        order.addItem(item, 3);
        assertEquals("3 (s) - NZD 0.00\nTotal cost - NZD 0.00", order.printReceipt());
    }
}
