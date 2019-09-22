package seng202.group5.testXmlFiles;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.DietEnum;
import seng202.group5.IDGenerator;
import seng202.group5.Order;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.Recipe;
import seng202.group5.information.Transaction;
import seng202.group5.logic.Finance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceXmlTest {

    AppEnvironment appEnvironment = new AppEnvironment();
    Finance finance;
    String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";

    @BeforeAll
    public static void createAndMarshalFinanceData() {
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

        Recipe testRecipe = new Recipe("Chicken burger", "1) Get some Chicken\n2) Get some cheese\n3) Throw the chicken on the grill and let it fry\n");
        Recipe testRecipe2 = new Recipe("Vege burger", "Steps to make pad thai");
        testRecipe.addIngredient(chickenPatty, 1);
        testRecipe.addIngredient(cheese, 1);
        testRecipe2.addIngredient(vegePatty, 1);

        oldAppEnvironment.getMenuManager().createItem("Chicken Burger", testRecipe, Money.parse("NZD 5"), "1220", true);
        oldAppEnvironment.getMenuManager().createItem("Vege Burger", testRecipe2, Money.parse("NZD 7"), "1222", true);

        oldAppEnvironment.getOrderManager().newOrder();
        Order tempOrder = new Order(oldAppEnvironment.getStock());
        tempOrder.setId("8");
        tempOrder.addItem(oldAppEnvironment.getMenuManager().getItemMap().get("1220"), 4);
        tempOrder.setDateTimeProcessed(LocalDateTime.now());

        Transaction transaction = new Transaction(tempOrder.getDateTimeProcessed(), Money.parse("NZD 0.00"), tempOrder.getTotalCost(), tempOrder.getId());
        transaction.setTransactionID("9");
        oldAppEnvironment.getFinance().getTransactionHistory().put(transaction.getTransactionID(), transaction);

        oldAppEnvironment.getHistory().getTransactionHistory().put(tempOrder.getId(), tempOrder);

        oldAppEnvironment.objectToXml(Finance.class, oldAppEnvironment.getFinance(), "finance.xml", testDirectory);
    }
    @BeforeEach
    public void testUnmarshallFinance() {
        try {
            appEnvironment.financeXmlToObject(testDirectory);
            finance = appEnvironment.getFinance();
            assertEquals(1, finance.getTransactionHistory().size());
        } catch (Exception e) {

        }
    }

    @Test
    public void testTransactionDateTimeIsInFinance() {
        LocalDateTime dateTime = finance.getTransactionHistory().get("9").getDateTime();
        assertTrue(dateTime instanceof LocalDateTime);
    }

    @Test
    public void testTransactionChangeIsInFinance() {
        String change = finance.getTransactionHistory().get("9").getChange().toString();
        assertEquals("NZD 0.00", change);
    }

    @Test
    public void testTransactionTotalPriceIsInFinance() {
        String totalPrice = finance.getTransactionHistory().get("9").getTotalPrice().toString();
        assertEquals("NZD 80.00", totalPrice);
    }

    @Test
    public void testTransactionIDIsInFinance() {
        String transactionId = finance.getTransactionHistory().get("9").getTransactionID();
        assertEquals("9", transactionId);
    }

    @Test
    public void testTransactionIsRefundedBooleanIsInFinance() {
        Boolean isRefunded = finance.getTransactionHistory().get("9").isRefunded();
        assertFalse(isRefunded);
    }

    @Test
    public void testTransactionOrderIdIsInFinance() {
        String orderId = finance.getTransactionHistory().get("9").getOrderID();
        assertEquals("8", orderId);
    }
}
