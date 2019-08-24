package seng202.group5;

import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.*;


@Ignore
/*
 * testRefund and testTotalCalculator need to be updated when Database is added
 */
public class FinanceTest extends TestCase {
    private Finance testFinance;

    /*
     * When Database is added initialise with test data before each
     */
    @BeforeEach
    public void init() {
    }

    @Test
    public void testPay() {
        ArrayList<Double> payed = new ArrayList<>();
        payed.add(10.00);
        payed.add(5.00);
        payed.add(5.00);
        ArrayList<Double> result;
        result = testFinance.pay(16.75, payed, 300);
        ArrayList<Double> expectedResult = new ArrayList<>();
        expectedResult.add(2.00);
        expectedResult.add(1.00);
        expectedResult.add(0.20);
        assertEquals(result, expectedResult);
    }

    @Test
    public void testPayRounding() {
        ArrayList<Double> payed = new ArrayList<>();
        payed.add(10.00);
        payed.add(5.00);
        payed.add(5.00);
        ArrayList<Double> result;
        result = testFinance.pay(19.01, payed, 300);
        ArrayList<Double> expectedResult = new ArrayList<>();
        expectedResult.add(1.00);
        assertEquals(result, expectedResult);
    }
    @Test
    public void testPayError() {
        ArrayList<Double> payed = new ArrayList<>();
        payed.add(10.00);
        payed.add(5.00);
        payed.add(5.00);
        assertThrows(InsufficientCashException.class, () -> {
            testFinance.pay(25, payed, 300);
            });

    }

    @Test
    public void testRefund() {
        ArrayList<Double> moneyRefund = new ArrayList<>();

        assertEquals(testFinance.refund(orderID), moneyRefund);
    }

    @Test
    public void testTotalCalculator() {
        ArrayList<Double> total = new ArrayList<>();

        assertEquals(testFinance.totalCalculator(date1, date2), total);
    }

}
