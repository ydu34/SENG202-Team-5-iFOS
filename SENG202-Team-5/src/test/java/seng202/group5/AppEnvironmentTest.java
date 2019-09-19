package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.logic.History;
import seng202.group5.logic.MenuManager;
import seng202.group5.logic.Stock;

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
    String testDirectory = System.getProperty("user.dir") + "\\src\\test\\java\\seng202\\group5\\testXmlFiles";

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

        Ingredient cheese = new Ingredient("Cheese", "Count", "Dairy", "1", Money.parse("NZD 0.10"));
        cheeseBurgerIngredientMap.put(cheese, 10);
        Ingredient bun = new Ingredient("Bun", "Count", "Bread", "2", Money.parse("NZD 0.50"));
        cheeseBurgerIngredientMap.put(bun, 5);
        Ingredient beefPattie = new Ingredient("Beef Pattie", "Count", "Meat", "3", Money.parse("NZD 1.0"));
        Ingredient lettuce = new Ingredient("Lettuce", "Count", "Vegetable", "4", Money.parse("NZD 0.2"));
        Ingredient tomatoSauce = new Ingredient("Tomato Sauce", "ml", "Sauce", "5", Money.parse("NZD 0.1"));

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

    /**
     * Please do not touch my test case.
     */
    @Test
    void test() {
        Ingredient ing1 = new Ingredient("Milk", "L", "Liquid", "1", Money.parse("NZD 4.0"));
        Ingredient ing2 = new Ingredient("Apple", "kg", "Fruit", "2", Money.parse("NZD 1.0"));

        Stock stock = handler.getStock();
        stock = new Stock();
        stock.getIngredients().put("1", ing1);
        stock.getIngredients().put("2", ing2);

        handler.objectToXml(Stock.class, stock, "stockTest.xml", testDirectory);
        handler.setStock(stock);

        MenuManager menuManager = new MenuManager();
        menuManager.setItemList(new HashMap<String, MenuItem>());
        menuManager.getItemList().put("1", cheeseBurger);


        handler.objectToXml(MenuManager.class, menuManager, "menuTest.xml", testDirectory);
        HashMap<String, MenuItem> menuItems = menuManager.getItemList();
        handler.handleMenu(menuItems);
        System.out.print(menuItems.get("1").getRecipe().getIngredientsAmount());
    }

    @Test
    void testHistoryObjectToXml() {
        HashMap<MenuItem, Integer> orderItems = new HashMap<>();
        orderItems.put(cheeseBurger, 1);
        Order order = new Order(orderItems, cheeseBurger.calculateFinalCost(), "1");
        History history = new History();
        history.getTransactionHistory().put("1", order);
        handler.objectToXml(History.class, history, "historyTest.xml", testDirectory);
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
        } catch (NoOrderException e) {
            e.printStackTrace();
            fail();
        } catch (InsufficientCashException e) {
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
//
//    @Test
//    void testHistoryXmlToObjet() {
//        History history = new History();
//        history = (History) handler.xmlToObject(History.class, history, "historyTest.xml", testDirectory);
//        System.out.println(history.getTransactionHistory().get("1").getOrderItems().entrySet());
//    }

    // Test only works if stockTest.xml exists before running tests.
//    @Test
//    void testStockXmlToObject() {
//        Stock stock = new Stock();
//        stock = (Stock) handler.xmlToObject(Stock.class, stock, "stockTest.xml");
//        Ingredient ing1 = stock.getIngredients().get("1");
//        Ingredient ing2 = stock.getIngredients().get("2");
//        Assertions.assertEquals(ing1.getName(), "Milk");
//        Assertions.assertEquals(ing2.getName(), "Apple");
//    }

}