package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.logic.Finance;
import seng202.group5.logic.Till;
import seng202.group5.information.Transaction;

import java.time.LocalDateTime;
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
        ArrayList<Money> denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));
        testFinance.setTill(new Till(denomination));
        for (Money value: denomination) {
            testFinance.getTill().addDenomination(value, 10);
        }


    }

    @Test
    public void testPay() throws InsufficientCashException {
        ArrayList<Money> result;
        result = testFinance.pay(Money.parse("NZD 16.75"), payed, LocalDateTime.now(), "");
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 2.00"));
        expectedResult.add(Money.parse("NZD 1.00"));
        expectedResult.add(Money.parse("NZD 0.20"));
        assertEquals(result, expectedResult);
    }

    @Test
    public void testPayRounding() throws InsufficientCashException {
        ArrayList<Money> result;
        result = testFinance.pay(Money.parse("NZD 19.01"), payed, LocalDateTime.now(), "");
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 1.00"));
        assertEquals(expectedResult, result);
    }
    @Test
    public void testPaymentError() {

        assertThrows(InsufficientCashException.class, () -> {
            testFinance.pay(Money.parse("NZD 25.00"), payed, LocalDateTime.now(), "");
        });

    }

    @Test
    public void testCostError() {

        assertThrows(InsufficientCashException.class, () -> {
            testFinance.pay(Money.parse("NZD -25.00"), payed, LocalDateTime.now(), "");
        });

    }
    @Test
    public void testRefund() throws InsufficientCashException {
        testFinance.pay(Money.parse("NZD 15.00"), payed, LocalDateTime.now(), "");
        ArrayList<Money> moneyRefund = new ArrayList<>();
        moneyRefund.add(Money.parse("NZD 10.00"));
        moneyRefund.add(Money.parse("NZD 5.00"));
        for (Transaction transaction : testFinance.getTransactionHistoryClone().values()) {
            assertEquals(testFinance.refund(transaction.getTransactionID()), moneyRefund);
        }
    }

    @Test
    public void testTotalCalculator() throws InsufficientCashException {
        ArrayList<Money> total = new ArrayList<>();
        testFinance.pay(Money.parse("NZD 15.00"), payed, LocalDateTime.now(), "");
        testFinance.pay(Money.parse("NZD 15.00"), payed, LocalDateTime.now(), "");
        testFinance.pay(Money.parse("NZD 15.00"), payed, LocalDateTime.now(), "");
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        total.add(Money.parse("NZD 45.00"));
        total.add(Money.parse("NZD 22.50"));
        assertEquals(total, testFinance.totalCalculator(startDate, endDate));
    }

}
