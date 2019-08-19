package seng202.group5;

import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

/*
 * testRefund and testTotalCalculator need to be updated when Database is added
 */
public class FinanceTest extends TestCase {
    public Finance testFinance;

    /*
     * When Database is added initialise with test data before each
     */
    @BeforeEach
    public void init() {

    }

    @Test
    public void testPay() {
        ArrayList<Float> payed = new ArrayList<>();
        payed.add((float) 10.00);
        payed.add((float) 5.00);
        payed.add((float) 5.00);
        ArrayList<Float> result;
        result = testFinance.pay((float) 16.75, payed, 300);
        ArrayList<Float> expectedResult = new ArrayList<>();
        expectedResult.add((float) 2.00);
        expectedResult.add((float) 1.00);
        expectedResult.add((float) 0.20);
        assertEquals(result, expectedResult);
    }

    @Test
    public void testRefund() {
        ArrayList<Float> moneyRefund = new ArrayList<>();

        assertEquals(testFinance.refund(orderID), moneyRefund);
    }

    @Test
    public void testTotalCalculator() {
        ArrayList<Float> total = new ArrayList<>();

        assertEquals(testFinance.totalCalculator(date1, date2), total);
    }

}
