package seng202.group5;

import org.joda.money.Money;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TillTest {


    private Till testTill;
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
    public testAddNewDenomination() {
        Money testMoney30 = null;
        testMoney30.parse("NZD 30.00");
        testTill.newDenomination(testMoney30);
        assertEquals(4, testTill.getDenominations().size());
    }

    @Test
    public testRemoveDenomination() {
        Money testMoney30 = null;
        testMoney30.parse("NZD 30.00");
        testTill.newDenomination(testMoney30);
        assertEquals(4, testTill.getDenominations().size());
        testTill.removeDenomination(testMoney20);
        assertEquals(3, testTill.getDenominations().size());

    }

    @Test
    public
}
