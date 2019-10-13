package seng202.group5.logic;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.information.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
        denomination.add(Money.parse("NZD 100.00"));
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
        for (Money value : denomination) {
            testFinance.getTill().addDenomination(value, 10);
        }


    }

    @Test
    public void testChangeCalculatorWithoutGreedyAlg() throws InsufficientCashException {
        testFinance.getTill().removeDenomination(Money.parse("NZD 10.00"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 5.00"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 2.00"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 1.00"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 0.50"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 0.20"), 10);
        testFinance.getTill().removeDenomination(Money.parse("NZD 0.10"), 10);
        assertEquals(new ArrayList<>(Arrays.asList(Money.parse("NZD 20.00"), Money.parse("NZD 20.00"), Money.parse("NZD 20.00"))),
                testFinance.calcChange(Money.parse("NZD 60.00")));
    }

    @Test
    public void testPay() throws InsufficientCashException {
        ArrayList<Money> result;
        Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 16.75"), "tempid");
        result = testFinance.pay(payed, LocalDateTime.now(), testOrder);
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 2.00"));
        expectedResult.add(Money.parse("NZD 1.00"));
        expectedResult.add(Money.parse("NZD 0.20"));
        assertEquals(expectedResult, result);
    }

    @Test
    public void testPayRounding() throws InsufficientCashException {
        ArrayList<Money> result;
        Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 19.01"), "tempid");
        result = testFinance.pay(payed, LocalDateTime.now(), testOrder);
        ArrayList<Money> expectedResult = new ArrayList<>();
        expectedResult.add(Money.parse("NZD 1.00"));
        assertEquals(expectedResult, result);
    }

    @Test
    public void testPaymentError() {

        assertThrows(InsufficientCashException.class, () -> {
            Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 25.00"), "tempid");
            testFinance.pay(payed, LocalDateTime.now(), testOrder);
        });

    }

    @Test
    public void testCostError() {

        assertThrows(InsufficientCashException.class, () -> {
            Order testOrder = new Order(new HashMap<>(), Money.parse("NZD -25.00"), "tempid");
            testFinance.pay(payed, LocalDateTime.now(), testOrder);
        });

    }

    @Test
    public void testRefund() throws InsufficientCashException {
        Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 15.00"), "tempid");
        testFinance.pay(payed, LocalDateTime.now(), testOrder);
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
        Order testOrder = new Order(new HashMap<>(), Money.parse("NZD 15.00"), "tempid");
        testFinance.pay(payed, LocalDateTime.now(), testOrder);
        testOrder.setId("2");
        testFinance.pay(payed, LocalDateTime.now(), testOrder);
        testOrder.setId("3");
        testFinance.pay(payed, LocalDateTime.now(), testOrder);
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();
        total.add(Money.parse("NZD 45.00"));
        total.add(Money.parse("NZD 22.50"));
        total.add(Money.parse("NZD 45.00"));
        total.add(Money.parse("NZD 22.50"));
        assertEquals(total, testFinance.totalCalculator(startDate, endDate));
    }

    @Test
    public void getDenomination() {
        ArrayList<Money> denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD 100.00"));
        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));
        assertEquals(testFinance.getDenomination(), denomination);
    }

    @Test
    public void testGetTransactionHistory() {
        assertEquals(testFinance.getTransactionHistory(), new HashMap<>());
    }

}
