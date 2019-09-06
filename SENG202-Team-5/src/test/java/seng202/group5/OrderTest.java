package seng202.group5;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {

    private Order order;


    @BeforeEach
    void init() {
        order = new Order();
    }

    @Test
    void testApplyingDiscount() {
        HashMap<MenuItem, Integer> orderList = new HashMap<MenuItem, Integer>();
        Double cost = 125.50;
        order = new Order(orderList, cost, "ABC123");

        int percentage = 50;
        double value = cost * (1 - (percentage / 100.0));
        order.discount(percentage);

        assertTrue(value == order.getTotalCost());
    }


}
