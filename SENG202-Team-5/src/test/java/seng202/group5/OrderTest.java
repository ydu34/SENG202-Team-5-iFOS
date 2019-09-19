package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.logic.Stock;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.math.RoundingMode;
import java.util.*;

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

        int percentage = 50;
        Money value = cost.multipliedBy(1 - (percentage / 100.0), RoundingMode.UP);
        order.discount(percentage);

        assertEquals(value, order.getTotalCost());
    }

    @Test
    public void testAddItem() {
        order = new Order();

        Ingredient ingredient = new Ingredient("Name", "Unit", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);
        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true);

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
        Ingredient ingredient = new Ingredient("Name", "Unit", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);
        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true);

        HashMap<String, Ingredient> ingredientStock = new HashMap<String, Ingredient>();
        ingredientStock.put(ingredient.getID(), ingredient);
        HashMap<String, Integer> numberStock = new HashMap<String, Integer>();
        numberStock.put(ingredient.getID(), 1);
        Stock stock = new Stock(ingredientStock, numberStock);

        order = new Order(stock);
        order.addItem(item, 1);


        assertTrue(order.removeItem(item));

        assertEquals(order.getStock().getIngredientQuantity("ABC123"), 1);

        assertFalse(order.removeItem(item));

    }

    @Test
    public void testModifyItemQuantity() {
        Ingredient ingredient = new Ingredient("Name", "Unit", "Cate", "ABC123", Money.parse("NZD 10.0"));
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        ingredients.put(ingredient, 1);
        HashMap<String, Integer> ingredientIDs = new HashMap<String, Integer>();
        ingredientIDs.put(ingredient.getID(), 1);

        Stock stock = new Stock();
        stock.addNewIngredient(ingredient, 2);
        order = new Order(stock);

        Recipe recipe = new Recipe("Name", "Text", ingredients, ingredientIDs);
        MenuItem item = new MenuItem("SomeName", recipe, Money.parse("NZD 10.0"), "FoodID", true);

        HashMap<MenuItem, Integer> orderItems = new HashMap<MenuItem, Integer>();
        orderItems.put(item, 1);

        Money tempTotalCost = order.getTotalCost();
        order.addItem(item, 1);
        stock = order.getStock();
        Order order = new Order(orderItems, tempTotalCost, "1", stock);

        assertTrue(order.modifyItemQuantity(item, 2));

        assertEquals(order.getOrderItems().get(item), 2);
        assertFalse(order.modifyItemQuantity(item, 3));
        assertEquals(order.getOrderItems().get(item), 2);


        MenuItem temp = new MenuItem(null, null, null, false);
        assertFalse(order.modifyItemQuantity(temp, 3));
    }

}
