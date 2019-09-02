package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.*;
import seng202.group5.exceptions.InsufficientCashException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TillTest {


    private Till testTill;
    private Money testMoney10;
    private Money testMoney20;

    @BeforeEach
    public void init() {
    Money testMoney10 = null;
    Money testMoney20 = null;
    Money testMoney50 = null;
    testMoney10.parse("NZD 10.00");
    testMoney20.parse("NZD 20.00");
    testMoney50.parse("NZD 50.00");
    HashMap<Money, Integer> testDenominations = new HashMap<Money, Integer>();
    testTill = new Till(testDenominations);


    }

    @Test
    public void testAddNewDenomination() {
        Money testMoney30 = null;
        testMoney30.parse("NZD 30.00");
        testTill.addDenomination(testMoney30, 1);
        assertEquals(4, testTill.getDenominations().size());
    }

    @Test
    public void testRemoveDenomination() {
        Money testMoney30 = null;
        testMoney30.parse("NZD 30.00");
        testTill.addDenomination(testMoney30, 1);
        assertEquals(4, testTill.getDenominations().size());
        try {
            testTill.removeDenomination(testMoney20, 1);
        } catch (InsufficientCashException e) {
            System.out.println(e);
        }
        assertEquals(3, testTill.getDenominations().size());

    }

    @Test
    public void testTotalValueOneDenomination() {
        testTill.addDenomination(testMoney20, 1);
        assertEquals(20, testTill.totalValue());
        testTill.addDenomination(testMoney20, 4);
        assertEquals(100, testTill.totalValue());
    }

    @Test
    public void testTotalValueMultipleDenominations() {
        testTill.addDenomination(testMoney20, 5);
        assertEquals(100, testTill.totalValue());
        testTill.addDenomination(testMoney10, 2);
        assertEquals(120, testTill.totalValue());
    }

    public void testAddMultipleDenominations() {
        HashMap<Money, Integer> denominationCount = new HashMap<Money, Integer>();
        denominationCount.put(testMoney10, 1);
        denominationCount.put(testMoney20, 2);
        testTill.addDenominations(denominationCount);
        assertEquals(50, testTill.totalValue());
    }

}
