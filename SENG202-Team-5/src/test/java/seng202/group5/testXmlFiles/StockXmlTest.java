package seng202.group5.testXmlFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.DietEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;

import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockXmlTest {
    AppEnvironment appEnvironment = new AppEnvironment();
    Stock stock;
    String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";

    @BeforeEach
    public void testUnmarshallStock() {
        appEnvironment.stockXmlToObject(testDirectory);
        stock = appEnvironment.getStock();
        assertTrue(stock.getIngredientStock().size() > 0);
        assertTrue(stock.getIngredients().size() > 0);

    }

    @Test
    public void testIngredientNameInStock() {
        String name = stock.getIngredients().get("2").getName();
        assertEquals("chicken", name);
    }

    @Test
    public void testIngredientIDInStock() {
        String id = stock.getIngredients().get("2").getID();
        assertEquals("2", id);
    }

    @Test
    public void testIngredientCategoryInStock() {
        String category = stock.getIngredients().get("2").getCategory();
        assertEquals("meat", category);
    }

    @Test
    public void testIngredientPriceInStock() {
        String price = stock.getIngredients().get("2").getCost().toString();
        assertEquals("NZD 10.00", price);
    }

    @Test
    public void testIngredientDietaryInformationInStock() {
        HashSet<DietEnum> dietInfo= stock.getIngredients().get("2").getDietInfo();
        assertTrue(dietInfo.contains(DietEnum.GLUTEN_FREE));
    }

    @Test
    public void testIngredientsMapContainsAllIngredients() {
        Map<String, Ingredient> ingredientMap = stock.getIngredients();
        assertEquals(4, ingredientMap.size());
    }

    @Test
    public void testIngredientsStockMapContainsALlQuantities() {
        Map<String, Integer> ingredientStockMap = stock.getIngredientStock();
        assertEquals(4, ingredientStockMap.size());
    }

}