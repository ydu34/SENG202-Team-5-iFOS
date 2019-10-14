package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.*;
import seng202.group5.logic.Finance;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.OrderManager;
import seng202.group5.logic.Settings;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

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
        handler = new AppEnvironment(false);
        cheeseBurgerRecipe = new Recipe("Cheese Burger", "PlaceholderRecipe");
        cheeseBurgerIngredients = new HashMap<>();
        cheeseBurgerIngredients.put("1", 10);
        cheeseBurgerIngredients.put("2", 5);
        HashMap<Ingredient, Integer> cheeseBurgerIngredientMap = new HashMap<>();
        cheeseBurger = new MenuItem("Cheese Burger", cheeseBurgerRecipe, Money.parse("NZD 5.0"), "1", true, TypeEnum.MAIN);
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

        for (Money value : denomination) {
            handler.getFinance().getTill().addDenomination(value, 10);
        }
    }

    @Test
    public void testConfirmPaymentWithOrder() { // Need recipe and finance to be implemented properly
        Money changeSum = Money.parse("NZD 0");
        try {
            handler.getOrderManager().getOrder().addItem(cheeseBurger, 2);
            ArrayList<Money> paymentAmount = new ArrayList<>();
            paymentAmount.add(cheeseBurger.getTotalCost().multipliedBy(2));
            paymentAmount.add(Money.parse("NZD 4.0"));
            ArrayList<Money> change = handler.confirmPayment(paymentAmount);
            for (Money x : change) changeSum = changeSum.plus(x);
        } catch (InsufficientCashException e) {

            fail("No order!");
        }

        assertTrue(changeSum.isEqual(Money.parse("NZD 4.0")));
    }

    @Test
    public void testConfirmPaymentRaisesInsufficientCashException() { // Need finance implemented properly so confirmPayment raises exception
        handler.getOrderManager().getOrder().addItem(cheeseBurger, 2);
        ArrayList<Money> paymentAmount = new ArrayList<>();
        paymentAmount.add(cheeseBurger.getTotalCost());
        paymentAmount.add(cheeseBurger.getTotalCost().dividedBy(2, RoundingMode.DOWN));
        assertThrows(InsufficientCashException.class, () -> handler.confirmPayment(paymentAmount));
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

    @Test
    void testSetGetCustomers() {
        Customers customers = new Customers();
        handler.setCustomers(customers);
        assertEquals(customers, handler.getCustomers());
    }

    @Test
    void testSetGetCustomerSettings() {
        CustomerSettings customerSettings = new CustomerSettings();
        handler.setCustomers(new Customers());
        handler.getCustomers().setCustomerSettings(customerSettings);
        assertEquals(customerSettings, handler.getCustomers().getCustomerSettings());
    }

    @Test
    void testSetSettings() {
        Settings settings = new Settings();
        handler.setSettings(settings);
        assertEquals(settings, handler.getSettings());
    }

}