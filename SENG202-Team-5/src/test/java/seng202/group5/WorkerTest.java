package seng202.group5;

import junit.framework.TestCase;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
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
            new Recipe("Burger",
                       "Add items to burger",
                       (float) 5.80),
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
        try {
            initialOrderItems = (HashMap<MenuItem, Integer>)
                    testWorker.getCurrentOrder().getOrderItems().clone();
            initialOrderItems.put(testItem, 3);
            testWorker.addItem(testItem, 3);
            assertEquals(initialOrderItems, testWorker.getCurrentOrder().getOrderItems());
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNewOrderReplacesOrder() {
        try {
            testOrder = testWorker.getCurrentOrder();
            testWorker.newOrder();
            assertNotSame(testOrder, testWorker.getCurrentOrder());
            testOrder = testWorker.getCurrentOrder();
            testWorker.newOrder();
            assertNotSame(testOrder, testWorker.getCurrentOrder());
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConfirmPaymentWithOrder() { //Is this correct? As addItem throws the exception.
        Money changeSum = Money.parse("NZD 0");
        try {
            testWorker.addItem(testItem, 2);
            ArrayList<Money> paymentAmount = new ArrayList<Money>();
            paymentAmount.add(Money.of(CurrencyUnit.of("NZD"), testItem.getCost() * 2));
            paymentAmount.add(Money.parse("NZD 4.0"));
            ArrayList<Money> change = testWorker.confirmPayment(paymentAmount);
            for (Money x : change) {
                changeSum.plus(x);
            }
        } catch (NoOrderException e) {
            e.printStackTrace();
        } catch (InsufficientCashException e) {
            e.printStackTrace();
        }

        assertTrue(changeSum.isEqual(Money.parse("NZD 4.0")));
    }

    @Test
    @Ignore
    public void testConfirmPaymentRaisesInsufficientCashException() { // This function needs fixing
        try {
            testWorker.addItem(testItem, 2);
        } catch (NoOrderException e) {
            e.printStackTrace();
        }
        ArrayList<Money> paymentAmount = new ArrayList<Money>();
        paymentAmount.add(Money.of(CurrencyUnit.of("NZD"), testItem.getCost()));
        paymentAmount.add(Money.of(CurrencyUnit.of("NZD"), testItem.getCost() / 2));
        try {
            testWorker.confirmPayment(paymentAmount);
        } catch (InsufficientCashException e) {
            return;
        }
        fail();
    }
}
