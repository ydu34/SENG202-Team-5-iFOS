package seng202.group5;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



public class TransactionTest {
    private Transaction testTransaction;
    private DateTime testTime;
    @BeforeEach
    public void init() {
        testTime = DateTime.now();
        testTransaction = new Transaction(testTime, 300, Money.parse("NZD 20.00"), Money.parse("NZD 30.00"));
    }

    @Test
    public void testGetDate(){
        assertEquals(testTime, testTransaction.getDate());

    }

    @Test
    public void testGetTime(){
        assertEquals(300, testTransaction.getTime());

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
        assertFalse(testTransaction.getRefunded());
        testTransaction.refund();
        assertTrue(testTransaction.getRefunded());

    }
}
