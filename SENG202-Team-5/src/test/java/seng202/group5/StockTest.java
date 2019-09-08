package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


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
        Ingredient tempIngredient = new Ingredient("Beef", "Kg", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient);
        assertFalse(stock.getIngredientStock().isEmpty());
        assertEquals(0, stock.getIngredientStock().get("ABC123"));
        // Adding an ingredient with our own quantity
        tempIngredient = new Ingredient("Apple", "apples", "Fruit", "Apple", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 10);
        assertEquals(10, stock.getIngredientStock().get("Apple"));
    }

    @Test
    public void testModifyQuantity() {
        // Starting from the value of 15, modifying it
        Ingredient tempIngredient = new Ingredient("Beef", "Kg", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertTrue(stock.modifyQuantity("ABC123", 10));

        assertEquals(10, stock.getIngredientStock().get("ABC123"));
    }

    @Test
    public void testGetIngredientQuantity() {
        Ingredient tempIngredient = new Ingredient("Beef", "Kg", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertEquals(15, stock.getIngredientQuantity("ABC123"));
        assertEquals(0, stock.getIngredientQuantity("NotInStock"));
    }

    @Test
    public void testGetIngredient() {
        Ingredient tempIngredient = new Ingredient("Beef", "Kg", "Meat", "ABC123", Money.parse("NZD 10.0"));
        stock.addNewIngredient(tempIngredient, 15);

        assertEquals(tempIngredient, stock.getIngredientFromID("ABC123"));
        assertNull(stock.getIngredientFromID("NotInStock"));
    }

    @Test
    public void testCloneStock() {
        Ingredient ing = new Ingredient("Name", "unit", "cate", "ID", Money.parse("NZD 10.0"));
        stock.addNewIngredient(ing);

        Stock newstock = stock.clone();

        assertTrue(stock.getIngredients().get("ID") == newstock.getIngredients().get("ID"));
        assertTrue(stock.getIngredientStock().get("ID") == newstock.getIngredientStock().get("ID"));
    }

}
