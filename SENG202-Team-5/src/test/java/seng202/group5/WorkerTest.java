package seng202.group5;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

@Ignore
public class WorkerTest extends TestCase {

    Database database;
    Worker testWorker;
    HashMap<MenuItem, Integer> initialOrderItems;
    Order testOrder;
    MenuItem testItem = new MenuItem(
            "Burger Item",
            new Recipe("Burger", "Add items to burger"),
            (float) 5.80,
            "14328"
    );

    @BeforeEach
    public void init() {
        database = new Database();
        testWorker = new Worker(database);

    }

    @Test
    public void testAddItemAddsItem() {
        initialOrderItems = (HashMap<MenuItem, Integer>)
                testWorker.getCurrentOrder().getOrderItems().clone();
        initialOrderItems.put(testItem, 3);
        testWorker.addItem(testItem, 3);
        assertEquals(initialOrderItems, testWorker.getCurrentOrder().getOrderItems());
    }

    @Test
    public void testNewOrderReplacesOrder() {
        testOrder = testWorker.getCurrentOrder();
        testWorker.newOrder();
        assertNotSame(testOrder, testWorker.getCurrentOrder());
        testOrder = testWorker.getCurrentOrder();
        testWorker.newOrder();
        assertNotSame(testOrder, testWorker.getCurrentOrder());
    }

    @Test
    public void testConfirmPaymentReturnsCorrectChange() {
        testWorker.addItem(testItem, 2);
        ArrayList<Double> paymentAmount = new ArrayList<Double>();
        paymentAmount.add(testItem.getCost() * 2);
        paymentAmount.add(4.0);
        double changeSum = 0.0;
        for (double x : testWorker.confirmPayment(paymentAmount)) {
            changeSum += x;
        }
        assertEquals(4.0, changeSum, 0.00001);
    }

    @Test
    public void testConfirmPaymentRaisesInsufficientCashException() {
        testWorker.addItem(testItem, 2);
        ArrayList<Double> paymentAmount = new ArrayList<Double>();
        paymentAmount.add(testItem.getCost());
        paymentAmount.add(testItem.getCost() / 2);
        try {
            testWorker.confirmPayment(paymentAmount);
        } catch (InsufficientCashException) {
            return;
        }
        fail();
    }
}
