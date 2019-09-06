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



public class FinanceTest {
    private Finance testFinance;
    private ArrayList<Money> payed;

    @BeforeEach
    public void init() {
        testFinance = new Finance();
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
        assertEquals(expectedResult, result);
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
    public void testRefund() throws InsufficientCashException {
        testFinance.pay(Money.parse("NZD 15.00"), payed, 300);
        ArrayList<Money> moneyRefund = new ArrayList<>();
        moneyRefund.add(Money.parse("NZD 10.00"));
        moneyRefund.add(Money.parse("NZD 5.00"));
        assertEquals(testFinance.refund("test0"), moneyRefund);
    }

    @Test
    public void testTotalCalculator() throws InsufficientCashException {
        ArrayList<Money> total = new ArrayList<>();
        testFinance.pay(Money.parse("NZD 15.00"), payed, 300);
        testFinance.pay(Money.parse("NZD 15.00"), payed, 300);
        testFinance.pay(Money.parse("NZD 15.00"), payed, 300);
        DateTime startDate = new DateTime(DateTimeZone.UTC).minusDays(1);
        DateTime endDate = new DateTime(DateTimeZone.UTC);
        total.add(Money.parse("NZD 45.00"));
        total.add(Money.parse("NZD 22.50"));
        assertEquals(total, testFinance.totalCalculator(startDate, endDate));
    }

}
