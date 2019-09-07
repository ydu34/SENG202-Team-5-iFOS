package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class OrderManagerTest {

    private Database database;
    private OrderManager testOrderManager;
    private HashMap<MenuItem, Integer> initialOrderItems;
    private Order testOrder;
    private MenuItem testItem = new MenuItem(
            "Burger Item",
            new Recipe("Burger",
                       "Add items to burger",
                       new HashMap<>(){{
                           put(new Ingredient("Bun", "buns", "Bread", "ARZ4O2", 1.2), 2);
                           put(new Ingredient("Patty", "patties", "Meat", "5ES240", 3.4), 1);
                       }}),
            5.80,
            0.00,
            "14328"
    );

    @BeforeEach
    public void init() {
        database = new Database();
        testOrderManager = new OrderManager(database);

    }

    @Test
    public void testAddItemAddsItem() {
        try {
            initialOrderItems = (HashMap<MenuItem, Integer>)
                    testOrderManager.getOrder().getOrderItems().clone();
            initialOrderItems.put(testItem, 3);
            testOrderManager.getOrder().addItem(testItem, 3);
            assertEquals(initialOrderItems, testOrderManager.getOrder().getOrderItems());
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNewOrderReplacesOrder() {
        try {
            testOrder = testOrderManager.getOrder();
            testOrderManager.newOrder();
            assertNotSame(testOrder, testOrderManager.getOrder());
            testOrder = testOrderManager.getOrder();
            testOrderManager.newOrder();
            assertNotSame(testOrder, testOrderManager.getOrder());
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
    }

    //These tests may be useful for AppEnvironment
//    @Test
//    @Disabled
//    public void testConfirmPaymentWithOrder() { // Need recipe and finance to be implemented properly
//        Money changeSum = Money.parse("NZD 0");
//        try {
//            testOrderManager.getOrder().addItem(testItem, 2);
//            ArrayList<Money> paymentAmount = new ArrayList<>();
//            paymentAmount.add(testItem.calculateMakingCost().multipliedBy(2));
//            paymentAmount.add(Money.parse("NZD 4.0"));
//            ArrayList<Money> change = testOrderManager.confirmPayment(paymentAmount);
//            for (Money x : change) {
//                changeSum = changeSum.plus(x);
//            }
//        } catch (NoOrderException e) {
//            e.printStackTrace();
//            fail();
//        } catch (InsufficientCashException e) {
//            e.printStackTrace();
//            fail();
//        }
//
//        assertTrue(changeSum.isEqual(Money.parse("NZD 4")));
//    }

//    @Test
//    @Disabled
//    public void testConfirmPaymentRaisesInsufficientCashException() { // Need finance implemented properly so confirmPayment raises exception
//        try {
//            testOrderManager.getOrder().addItem(testItem, 2);
//        } catch (NoOrderException e) {
//            e.printStackTrace();
//        }
//        ArrayList<Money> paymentAmount = new ArrayList<>();
//        paymentAmount.add(testItem.calculateMakingCost());
//        paymentAmount.add(testItem.calculateMakingCost().dividedBy(2, RoundingMode.DOWN));
//        try {
//            testOrderManager.confirmPayment(paymentAmount);
//            fail();
//        } catch (InsufficientCashException e) {
//
//        }
//    }
}
