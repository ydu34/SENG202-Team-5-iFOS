package seng202.group5;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Ignore
class HistoryTest {
    private History history;

    @BeforeEach
    void init() {
        HashMap<String, Order> tempTransactionHistory = new HashMap<>();
        History history = new History(tempTransactionHistory);
        HashMap<MenuItem, Integer> tempOrderItems = new HashMap<>();
        double tempTotalCost = 0;
        String tempID = "1";
        Order order = new Order(tempOrderItems, tempTotalCost, tempID);
    }

    @Test
    void testViewReturnsOrder() {

        String orderID = "1002";
        Order order = history.view(orderID);
        assertTrue(order instanceof Order);
    }

    @Test
    void testViewOrderDoesNotExist() {
        String orderID = "1000";
        Order order = history.view(orderID);
        assertNull(order);
    }
}
