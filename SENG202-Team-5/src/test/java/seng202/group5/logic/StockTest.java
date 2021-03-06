package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.Ingredient;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class StockTest {

    private Stock stock = new Stock();


    @BeforeEach
    public void init() {
        HashMap<String, Integer> ingredientStock = new HashMap<>();
        HashMap<String, Ingredient> ingredients = new HashMap<String, Ingredient>();
        stock = new Stock(ingredients, ingredientStock);
    }


    @Test
    public void testAddIngredientsToStock() {
        // Adding an ingredient with a default quantity
        Ingredient tempIngredient = new Ingredient("Beef", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient);
        assertFalse(stock.getIngredientStock().isEmpty());
        assertEquals(0, (int) stock.getIngredientStock().get("ABC123"));
        // Adding an ingredient with our own quantity
        tempIngredient = new Ingredient("Apple", "Fruit", "Apple", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 10);
        assertEquals(10, (int) stock.getIngredientStock().get("Apple"));
        // Adding an ingredient that is already in the stock
        stock.addNewIngredient(tempIngredient);
    }

    @Test
    public void testModifyQuantity() {
        // Starting from the value of 15, modifying it
        Ingredient tempIngredient = new Ingredient("Beef", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertTrue(stock.modifyQuantity("ABC123", 10));

        assertEquals(10, (int) stock.getIngredientStock().get("ABC123"));

        // Attempting to modify an ingredient that is not present
        Ingredient ingredient = new Ingredient("Jerky", "Meat", "DBC247", Money.parse("NZD 9.0"));

        assertFalse(stock.modifyQuantity("DBC247", 10));

    }

    @Test
    public void testGetIngredientQuantity() {
        Ingredient tempIngredient = new Ingredient("Beef", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertEquals(15, stock.getIngredientQuantity("ABC123"));
        assertEquals(0, stock.getIngredientQuantity("NotInStock"));
    }

    @Test
    public void testGetIngredient() {
        Ingredient tempIngredient = new Ingredient("Beef", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertEquals(tempIngredient, stock.getIngredientFromID("ABC123"));
        assertNull(stock.getIngredientFromID("NotInStock"));
    }

    @Test
    public void testCloneStock() {
        Ingredient ing = new Ingredient("Name", "cate", "ID", Money.parse("NZD 10.0"));
        stock.addNewIngredient(ing);

        Stock newstock = stock.clone();

        assertTrue(stock.getIngredients().get("ID") == newstock.getIngredients().get("ID"));
        assertTrue(stock.getIngredientStock().get("ID") == newstock.getIngredientStock().get("ID"));
    }

    @Test
    public void testRemoveIngredient() {
        assertTrue(stock.getIngredientStock().isEmpty());
        Ingredient tempIngredient = new Ingredient("Beef", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient);
        assertFalse(stock.getIngredientStock().isEmpty());
        stock.removeIngredient("ABC123");
        assertNull(stock.getIngredientFromID("ABC123"));
        assertNull(stock.getIngredientStock().get("ABC123"));

    }

}
