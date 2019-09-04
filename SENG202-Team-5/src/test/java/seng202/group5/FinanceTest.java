package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;



/*
 * testRefund and testTotalCalculator need to be updated when Database is added
 */
@Disabled
public class FinanceTest {
    private Finance testFinance;
    private ArrayList<Money> payed;
    private Database testDatabase;
    /*
     * When Database is added initialise with test data before each
     */
    @BeforeEach
    public void init() {
        testFinance = new Finance(testDatabase);
        payed = new ArrayList<>();
        payed.add(Money.parse("NZD 10.00"));
        payed.add(Money.parse("NZD 5.00"));
        payed.add(Money.parse("NZD 5.00"));
    }

    @Test
    public void testPay() throws InsufficientCashException {
        ArrayList<Money> result;
        result = testFinance.pay(Money.parse("NZD 16.75"), payed, 300);
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 2.00"));
        expectedResult.add(Money.parse("NZD 1.00"));
        expectedResult.add(Money.parse("NZD 0.20"));
        assertEquals(result, expectedResult);
    }

    @Test
    public void testPayRounding() throws InsufficientCashException {
        ArrayList<Money> result;
        result = testFinance.pay(Money.parse("NZD 19.01"), payed, 300);
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 1.00"));
        assertEquals(result, expectedResult);
    }
    @Test
    public void testPaymentError() {
        assertThrows(InsufficientCashException.class, () -> {
            testFinance.pay(Money.parse("NZD 25.00"), payed, 300);
        });

    }

    @Test
    public void testCostError() {

        assertThrows(InsufficientCashException.class, () -> {
            testFinance.pay(Money.parse("NZD -25.00"), payed, 300);
        });

    }
    @Test
    public void testRefund() {
        ArrayList<Money> moneyRefund = new ArrayList<>();
//        assertEquals(testFinance.refund(orderID), moneyRefund);
    }

    @Test
    public void testTotalCalculator() {
        ArrayList<Money> total = new ArrayList<>();

//        assertEquals(testFinance.totalCalculator(date1, date2), total);
    }

}
