package seng202.group5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

class HistoryTest extends groovy.util.GroovyTestCase {
    private History history = new History();

    @BeforeEach
    void init() {
        Order order = new Order();
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
