package seng202.group5.information;

import org.joda.money.Money;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import seng202.group5.information.DietEnum;
import seng202.group5.information.Ingredient;
import static org.junit.jupiter.api.Assertions.*;

import static org.joda.money.Money.parse;

class CustomerTest {

    Customer customer;
    Money noCash = parse("NZD 0.00");
    Money smallCash = parse("NZD 1.00");
    Money validCash = parse("NZD 10.00");
    Money justUnderCash = parse("NZD 9.99");

    @BeforeEach
    public void init() {
        customer = new Customer();
        customer.setPurchasePoints(0);

    }

    @Disabled
    @Test
    public void testAddPurchasePointsSmallPurchase() {
        customer.purchasePoints(smallCash);
        assertEquals(0, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testAddPurchasePointsNoPurchase() {
        customer.purchasePoints(noCash);
        assertEquals(0, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testAddPurchasePointsJustUnderPurchase() {
        customer.purchasePoints(justUnderCash);
        assertEquals(0, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testAddPurchasePointsOnePoint() {
        customer.purchasePoints(validCash);
        assertEquals(1, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testAddPurchasePointsOnePointJustUnder() {
        customer.purchasePoints(justUnderCash.plus(validCash));
        assertEquals(1, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testAddPurchasePointsTwoPoints() {
        customer.purchasePoints(validCash.multipliedBy(2).plus((smallCash)));
        assertEquals(2, customer.getPurchasePoints());
    }

    @Disabled
    @Test
    public void testDiscountRemoved() {
        customer.setPurchasePoints(10);
    }

}