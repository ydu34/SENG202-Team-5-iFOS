package seng202.group5.logic;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.*;
import seng202.group5.exceptions.NoPastOrderException;
import seng202.group5.logic.Order;
import seng202.group5.logic.History;
import seng202.group5.information.MenuItem;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;


class HistoryTest {
    private History history;
    private Order order;

    @BeforeEach
    void init() {
        HashMap<String, Order> tempTransactionHistory = new HashMap<>();
        history = new History(tempTransactionHistory);
        Money tempTotalCost = Money.zero(CurrencyUnit.of("NZD"));
        HashMap<MenuItem, Integer> tempOrderItems = new HashMap<MenuItem, Integer>();
        order = new Order(tempOrderItems, tempTotalCost, "1");
        String orderID = "1002";
        history.setTransactionHistory(orderID, order);
    }

    @Test
    void testViewReturnsOrder() {
        try {
            Order newOrder = history.view("1002");
            assertTrue(newOrder instanceof Order);
        } catch(NoPastOrderException e) {
            System.out.println("Error " + e);
        }
    }

    @Test
    void testViewOrderDoesNotExist() {
        Throwable exception = null;
        String orderID = "1000";
        try {
            Order order = history.view(orderID);
        } catch (Throwable e) {
                exception = e;
        }
        assertTrue(exception instanceof NoPastOrderException);
    }
}
