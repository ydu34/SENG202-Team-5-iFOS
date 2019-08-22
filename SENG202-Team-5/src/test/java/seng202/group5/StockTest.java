package seng202.group5;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.*;

@Ignore
public class StockTest extends TestCase {

    private Stock stock = new Stock();



    @BeforeEach
    public void init() {
        HashMap<String, Integer> ingredientStock = new HashMap<>();
        Stock stock = new Stock(ingredientStock);
    }


    @Test
    public void testAddIngredientsToStock() {
        // Adding an ingredient with a default quantity
        stock.addIngredient("ABC123", "Kg", "Meat");
        assertFalse(stock.getIngredientStock().isEmpty());
        assertEquals(0, stock.getIngredientQuantity("ABC123"));

        // Adding an ingredient with our own quantity
        stock.addIngredient("Apple", "Kg", "Fruit", 10);
        assertEquals(10, stock.getIngredientQuantity("Apple"));
    }

    @Test
    public void testModifyQuantity() {
        // Starting from the default value of 0, modifying it
        stock.addIngredient("ABC123", "Yes", "Category");

        assertTrue(stock.modifyQuantity("ABC123", 10));

        assertEquals(10, stock.getIngredientQuantity("ABC123"));
    }
}
