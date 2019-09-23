package seng202.group5.testXmlFiles;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.information.DietEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;

import javax.xml.bind.JAXBException;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockXmlTest {
    AppEnvironment appEnvironment = new AppEnvironment();
    Stock stock;
    String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";



    @BeforeAll
    public static void createAndMarshalStockData() {
        String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";
        AppEnvironment oldAppEnvironment = new AppEnvironment();
        Ingredient flour = new Ingredient("Flour", "Flour", Money.parse("NZD 7.00"));
        HashSet<DietEnum> ingredientInfo1 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
        }};
        HashSet<DietEnum> ingredientInfo2 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient chickenPatty = new Ingredient("Chicken", "Meat", Money.parse("NZD 10"), ingredientInfo1);
        Ingredient cheese = new Ingredient("Cheese", "Dairy", Money.parse("NZD 5"), ingredientInfo2);
        HashSet<DietEnum> ingredientInfo3 = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);
            add(DietEnum.VEGETARIAN);
        }};
        Ingredient vegePatty = new Ingredient("Vegetables", "Vegetable", Money.parse("NZD 10"), ingredientInfo3);

        flour.setId("1'");
        oldAppEnvironment.getStock().getIngredientStock().put(flour.getID(), 0);
        oldAppEnvironment.getStock().getIngredients().put(flour.getID(), flour);

        chickenPatty.setId("2");
        oldAppEnvironment.getStock().getIngredientStock().put(chickenPatty.getID(), 100);
        oldAppEnvironment.getStock().getIngredients().put(chickenPatty.getID(), chickenPatty);

        cheese.setId("3");
        oldAppEnvironment.getStock().getIngredientStock().put(cheese.getID(), 200);
        oldAppEnvironment.getStock().getIngredients().put(cheese.getID(), cheese);

        vegePatty.setId("4");
        oldAppEnvironment.getStock().getIngredientStock().put(vegePatty.getID(), 150);
        oldAppEnvironment.getStock().getIngredients().put(vegePatty.getID(), vegePatty);

        try {
            oldAppEnvironment.objectToXml(Stock.class, oldAppEnvironment.getStock(), "stock.xml", testDirectory);
        } catch (JAXBException e) {
            System.out.println("Failed to marshal object");
        }
    }

    @BeforeEach
    public void testUnmarshalStock() {
        try {
            appEnvironment.stockXmlToObject(testDirectory);
            stock = appEnvironment.getStock();
            assertTrue(stock.getIngredientStock().size() > 0);
            assertTrue(stock.getIngredients().size() > 0);
        } catch (Exception e) {
            System.out.println("Error in marshalling/unmarshalling");
        }

    }


    @Test
    public void testIngredientNameInStock() {
        String name = stock.getIngredients().get("2").getName();
        assertEquals("Chicken", name);
    }

    @Test
    public void testIngredientIDInStock() {
        String id = stock.getIngredients().get("2").getID();
        assertEquals("2", id);
    }

    @Test
    public void testIngredientCategoryInStock() {
        String category = stock.getIngredients().get("2").getCategory();
        assertEquals("Meat", category);
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