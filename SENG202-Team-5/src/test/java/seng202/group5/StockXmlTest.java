package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.information.DietEnum;
import seng202.group5.information.Ingredient;
import seng202.group5.logic.Stock;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StockXmlTest {

    private static String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5";
    private AppEnvironment appEnvironment = new AppEnvironment(false);
    private Database database = appEnvironment.getDatabase();
    private Stock stock;


    @BeforeAll
    static void createAndMarshalStockData() {
        AppEnvironment oldAppEnvironment = new AppEnvironment(false);
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

        flour.setId("INGR1");
        oldAppEnvironment.getStock().getIngredientStock().put(flour.getID(), 0);
        oldAppEnvironment.getStock().getIngredients().put(flour.getID(), flour);

        chickenPatty.setId("INGR2");
        oldAppEnvironment.getStock().getIngredientStock().put(chickenPatty.getID(), 100);
        oldAppEnvironment.getStock().getIngredients().put(chickenPatty.getID(), chickenPatty);

        cheese.setId("INGR3");
        oldAppEnvironment.getStock().getIngredientStock().put(cheese.getID(), 200);
        oldAppEnvironment.getStock().getIngredients().put(cheese.getID(), cheese);

        vegePatty.setId("INGR4");
        oldAppEnvironment.getStock().getIngredientStock().put(vegePatty.getID(), 150);
        oldAppEnvironment.getStock().getIngredients().put(vegePatty.getID(), vegePatty);

        try {
            oldAppEnvironment.getDatabase().objectToXml(Stock.class, oldAppEnvironment.getStock(), "stock.xml", testDirectory);
        } catch (JAXBException e) {
            System.out.println("Failed to marshal object");
        }
    }

    @AfterAll
    static void teardown() {
        File file = new File(testDirectory + "/stock.xml");
        file.delete();
    }

    @BeforeEach
    void testUnmarshalStock() {
        try {
            database.stockXmlToObject(testDirectory);
            stock = appEnvironment.getStock();
            assertTrue(stock.getIngredientStock().size() > 0);
            assertTrue(stock.getIngredients().size() > 0);
        } catch (Exception e) {
            System.out.println("Error in marshalling/unmarshalling");
        }

    }

    @Test
    void testIngredientNameInStock() {
        String name = stock.getIngredients().get("INGR2").getName();
        assertEquals("Chicken", name);
    }

    @Test
    public void testIngredientIDInStock() {
        String id = stock.getIngredients().get("INGR2").getID();
        assertEquals("INGR2", id);
    }

    @Test
    public void testIngredientCategoryInStock() {
        String category = stock.getIngredients().get("INGR2").getCategory();
        assertEquals("Meat", category);
    }

    @Test
    public void testIngredientPriceInStock() {
        String price = stock.getIngredients().get("INGR2").getCost().toString();
        assertEquals("NZD 10.00", price);
    }

    @Test
    public void testIngredientDietaryInformationInStock() {
        HashSet<DietEnum> dietInfo = stock.getIngredients().get("INGR2").getDietInfo();
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