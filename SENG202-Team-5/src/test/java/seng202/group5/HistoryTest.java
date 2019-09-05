//package seng202.group5;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//class HistoryTest {
//    private History history;
//
//    @BeforeEach
//    void init() {
//        HashMap<String, Order> tempTransactionHistory = new HashMap<>();
//        HashMap<MenuItem, Integer> tempOrderItems = new HashMap<MenuItem, Integer>();
//        History history = new History(tempTransactionHistory);
//        double tempTotalCost = 0;
//        String tempID = "1";
//
//        Order order = new Order(tempOrderItems, tempTotalCost, tempID);
//    }
//
//    @Test
//    void testViewReturnsOrder() {
//        HashMap<MenuItem, Integer> tempOrderItems = new HashMap<>();
//
//        history.addTransactionHistory();
//        String orderID = "1002";
//
//        Order order = history.view(orderID);
//        assertTrue(order instanceof Order);
//    }
//
//    @Test
//    void testViewOrderDoesNotExist() {
//        String orderID = "1000";
//        Order order = history.view(orderID);
//        assertNull(order);
//    }
//}
