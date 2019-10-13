package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TillTest {


    private Till testTill;
    private Money testMoney20 = Money.parse("NZD 20.00");
    private Money testMoney10 = Money.parse("NZD 10.00");

    @BeforeEach
    public void init() {
        HashMap<Money, Integer> testDenominations = new HashMap<Money, Integer>();
        testTill = new Till(testDenominations);
    }

    @Test
    void testInitialiseUsingList() {
        ArrayList<Money> moneyList = new ArrayList<Money>();
        HashMap<Money, Integer> expectedMap = new HashMap<Money, Integer>();
        expectedMap.put(testMoney10, 0);
        expectedMap.put(testMoney20, 0);
        moneyList.add(testMoney10);
        moneyList.add(testMoney20);
        Till testTillList = new Till(moneyList);
        assertEquals(expectedMap, testTillList.getDenominations());
    }

    @Test
    public void testAddNewDenomination() {
        Money testMoney30 = Money.parse("NZD 30.00");
        testTill.addDenomination(testMoney30, 1);
        assertEquals(1, testTill.getDenominations().size());
        assertEquals(testMoney30, testTill.totalValue());
    }

    @Test
    public void testRemoveDenomination() {
        Money testMoney30 = Money.parse("NZD 30.00");
        Money testMoney0 = Money.parse("NZD 0.00");
        testTill.addDenomination(testMoney30, 1);
        assertEquals(testMoney30, testTill.totalValue());
        try {
            testTill.removeDenomination(testMoney30, 1);
        } catch (InsufficientCashException e) {
            System.out.println(e);
        }
        assertEquals(testMoney0, testTill.totalValue());

    }

    @Test
    public void testTotalValueOneDenomination() {
        testTill.addDenomination(testMoney20, 1);
        assertEquals(Money.parse("NZD 20.00"), testTill.totalValue());
        testTill.addDenomination(testMoney20, 4);
        assertEquals(Money.parse("NZD 100.00"), testTill.totalValue());
    }

    @Test
    public void testTotalValueMultipleDenominations() {
        testTill.addDenomination(testMoney20, 5);
        assertEquals(Money.parse("NZD 100.00"), testTill.totalValue());
        testTill.addDenomination(testMoney10, 2);
        assertEquals(Money.parse("NZD 120.00"), testTill.totalValue());
    }

    @Test
    public void testAddMultipleDenominations() {
        HashMap<Money, Integer> denominationCount = new HashMap<Money, Integer>();
        denominationCount.put(testMoney10, 1);
        denominationCount.put(testMoney20, 2);
        testTill.addDenominations(denominationCount);
        assertEquals(Money.parse("NZD 50.00"), testTill.totalValue());
    }

    @Test
    void testRemovingDenominationsThrowsInsufficientCashException() {
        Throwable exception = null;
        Money testMoney30 = Money.parse("NZD 30.00");
        testTill.addDenomination(testMoney30, 1);
        try {
            testTill.removeDenomination(testMoney30, 2);
        } catch (Throwable e) {
            exception = e;
        }
        assertTrue(exception instanceof InsufficientCashException);
    }

}
