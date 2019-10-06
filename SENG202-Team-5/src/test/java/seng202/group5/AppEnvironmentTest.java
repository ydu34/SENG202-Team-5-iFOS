package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.logic.Finance;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.OrderManager;


import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AppEnvironmentTest {

    AppEnvironment handler;
    Recipe cheeseBurgerRecipe;
    HashMap<String, Integer> cheeseBurgerIngredients;
    MenuItem cheeseBurger;
    Ingredient cheese;
    Ingredient bun;
    Ingredient beefPattie;
    Ingredient lettuce;
    Ingredient tomatoSauce;
    String testDirectory = System.getProperty("user.dir") + "/src/test/java/seng202/group5/testXmlFiles";

    @BeforeEach
    void init() {
        handler = new AppEnvironment();
        cheeseBurgerRecipe = new Recipe("Cheese Burger", "PlaceholderRecipe");
        cheeseBurgerIngredients = new HashMap<>();
        cheeseBurgerIngredients.put("1", 10);
        cheeseBurgerIngredients.put("2",5);
        HashMap<Ingredient, Integer> cheeseBurgerIngredientMap = new HashMap<>();
        cheeseBurger = new MenuItem("Cheese Burger", cheeseBurgerRecipe, Money.parse("NZD 5.0"), "1", true);
        cheeseBurgerRecipe.setIngredientIDs(cheeseBurgerIngredients);

        Ingredient cheese = new Ingredient("Cheese", "Dairy", "1", Money.parse("NZD 0.10"));
        cheeseBurgerIngredientMap.put(cheese, 10);
        Ingredient bun = new Ingredient("Bun", "Bread", "2", Money.parse("NZD 0.50"));
        cheeseBurgerIngredientMap.put(bun, 5);
        Ingredient beefPattie = new Ingredient("Beef Pattie", "Meat", "3", Money.parse("NZD 1.0"));
        Ingredient lettuce = new Ingredient("Lettuce", "Vegetable", "4", Money.parse("NZD 0.2"));
        Ingredient tomatoSauce = new Ingredient("Tomato Sauce", "Sauce", "5", Money.parse("NZD 0.1"));

        cheeseBurgerRecipe.setIngredientsAmount(cheeseBurgerIngredientMap);

        handler.getStock().addNewIngredient(cheese, 100);
        handler.getStock().addNewIngredient(bun, 100);
        handler.getStock().addNewIngredient(beefPattie, 100);
        handler.getStock().addNewIngredient(lettuce, 100);
        handler.getStock().addNewIngredient(tomatoSauce, 100);
        handler.getOrderManager().newOrder();
        ArrayList<Money> denomination = new ArrayList<>();

        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));

        for (Money value: denomination) {
            handler.getFinance().getTill().addDenomination(value, 10);
        }
    }

    @Test
    public void testConfirmPaymentWithOrder() { // Need recipe and finance to be implemented properly
        Money changeSum = Money.parse("NZD 0");
        try {
            handler.getOrderManager().getOrder().addItem(cheeseBurger, 2);
            ArrayList<Money> paymentAmount = new ArrayList<>();
            paymentAmount.add(cheeseBurger.calculateFinalCost().multipliedBy(2));
            paymentAmount.add(Money.parse("NZD 4.0"));
            ArrayList<Money> change = handler.confirmPayment(paymentAmount);
            for (Money x : change) changeSum = changeSum.plus(x);
        } catch (NoOrderException | InsufficientCashException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(changeSum.isEqual(Money.parse("NZD 4.0")));
    }

    @Test
    public void testConfirmPaymentRaisesInsufficientCashException() { // Need finance implemented properly so confirmPayment raises exception
        try {
            handler.getOrderManager().getOrder().addItem(cheeseBurger, 2);
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
        ArrayList<Money> paymentAmount = new ArrayList<>();
        paymentAmount.add(cheeseBurger.calculateFinalCost());
        paymentAmount.add(cheeseBurger.calculateFinalCost().dividedBy(2, RoundingMode.DOWN));
        assertThrows(InsufficientCashException.class, () -> handler.confirmPayment(paymentAmount));
    }

    @Test
    void testConfirmPaymentWithoutOrder() {
        handler.getOrderManager().setCurrentOrder(null);

        ArrayList<Money> paymentAmount = new ArrayList<>();

        try {
            handler.getOrderManager().getOrder();
        } catch (NoOrderException e) {
            assertEquals("No order exists to get", e.getMessage());
        }
    }

    @Test
    void testSetGetAcceptedFiles() {
        HashSet<String> testSet = new HashSet<>(Arrays.asList("one.xml", "two.xml", "three.txt"));
        HashSet<String> clone = new HashSet<>(testSet);
        handler.setAcceptedFiles(testSet);
        assertEquals(clone, handler.getAcceptedFiles());
    }

    @Test
    void testSetGetOrderManager() {
        OrderManager orderManager = new OrderManager(handler.getStock());
        handler.setOrderManager(orderManager);
        assertEquals(orderManager, handler.getOrderManager());
    }

    @Test
    void testSetGetFinance() {
        Finance finance = new Finance();
        handler.setFinance(finance);
        assertEquals(finance, handler.getFinance());
    }

    @Test
    void testSetGetMenuManager() {
        MenuManager menuManager = new MenuManager();
        handler.setMenuManager(menuManager);
        assertEquals(menuManager, handler.getMenuManager());
    }

    @Test
    void testSetGetIDGenerator() {
        IDGenerator idGenerator = new IDGenerator();
        handler.setIdGenerator(idGenerator);
        assertEquals(idGenerator, handler.getIdGenerator());
    }

}