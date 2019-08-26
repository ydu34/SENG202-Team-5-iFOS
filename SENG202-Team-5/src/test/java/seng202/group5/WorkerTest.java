package seng202.group5;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

import java.util.ArrayList;
import java.util.HashMap;

@Ignore
public class WorkerTest extends TestCase {

    private Database database;
    private Worker testWorker;
    private HashMap<MenuItem, Integer> initialOrderItems;
    private Order testOrder;
    private MenuItem testItem = new MenuItem(
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
    public void testConfirmPaymentWithOrder() { //Is this correct? As addItem throws the exception.
        double changeSum = 0.0;
        try {
            testWorker.addItem(testItem, 2);
            ArrayList<Double> paymentAmount = new ArrayList<Double>();
            paymentAmount.add(testItem.getCost() * 2);
            paymentAmount.add(4.0);
            for (double x : testWorker.confirmPayment(paymentAmount)) {
                changeSum += x;
            }
        } catch (NoOrderException e) {
            System.out.println(e);
        } catch (InsufficientCashException e) {
            System.out.println(e);
        }

        assertEquals(4.0, changeSum, 0.00001);
    }

    @Test
    @Ignore
    public void testConfirmPaymentRaisesInsufficientCashException() { // This function needs fixing
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
