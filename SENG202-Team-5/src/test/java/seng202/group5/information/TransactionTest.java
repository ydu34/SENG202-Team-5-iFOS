package seng202.group5.information;

import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.logic.Order;

import static org.junit.jupiter.api.Assertions.*;



public class TransactionTest {

    private Transaction testTransaction;
    private LocalDateTime testDateTime;

    @BeforeEach
    public void init() {
        testDateTime = LocalDateTime.now();
        Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 30.00"), "tempid");
        testTransaction = new Transaction(testDateTime, Money.parse("NZD 20.00"), testOrder);
    }

    @Test
    public void testGetDate(){
        assertEquals(testDateTime, testTransaction.getDateTime());

    }

    @Test
    public void testGetChange(){
        assertEquals(Money.parse("NZD 20.00"), testTransaction.getChange());
    }

    @Test
    public void testGetTotalPrice(){
        assertEquals(Money.parse("NZD 30.00"), testTransaction.getTotalPrice());

    }

    @Test
    public void testGetAndSetRefunded(){
        assertFalse(testTransaction.isRefunded());
        testTransaction.refund();
        assertTrue(testTransaction.isRefunded());

    }
}
