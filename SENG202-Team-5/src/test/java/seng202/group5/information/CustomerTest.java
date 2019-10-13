package seng202.group5.information;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.joda.money.Money.parse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    CustomerSettings customerSettings;
    Customer customer;
    Money noCash = parse("NZD 0.00");
    Money smallCash = parse("NZD 1.00");
    Money validCash = parse("NZD 10.00");
    Money justUnderCash = parse("NZD 9.99");

    @BeforeEach
    public void init() {
        customerSettings = new CustomerSettings();
        customerSettings.setRatio(10);
        customerSettings.setInitialPurchasePoints(1);
        customer = new Customer();
        customer.setPurchasePoints(10);

    }

    @Test
    public void testAddPurchasePointsSmallPurchase() {
        customer.purchasePoints(smallCash, customerSettings.getRatio());
        assertEquals(10, customer.getPurchasePoints());
    }

    @Test
    public void testAddPurchasePointsNoPurchase() {
        customer.purchasePoints(noCash, customerSettings.getRatio());
        assertEquals(10, customer.getPurchasePoints());
    }

    @Test
    public void testAddPurchasePointsJustUnderPurchase() {
        customer.purchasePoints(justUnderCash, customerSettings.getRatio());
        assertEquals(10, customer.getPurchasePoints());
    }

    @Test
    public void testAddPurchasePointsOnePoint() {
        customer.purchasePoints(validCash, customerSettings.getRatio());
        assertEquals(11, customer.getPurchasePoints());
    }

    @Test
    public void testAddPurchasePointsOnePointJustUnder() {
        customer.purchasePoints(justUnderCash.plus(validCash), customerSettings.getRatio());
        assertEquals(11, customer.getPurchasePoints());
    }

    @Test
    public void testAddPurchasePointsTwoPoints() {
        customer.purchasePoints(validCash.multipliedBy(2).plus((smallCash)), customerSettings.getRatio());
        assertEquals(12, customer.getPurchasePoints());
    }

    @Test
    public void testDiscountRemovedSmall() {
        Money result = customer.discount(1, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(parse("NZD 0.50"), result);
    }

    @Test
    public void testDiscountRemovedSmallPoints() {
        Money result = customer.discount(1, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(9, customer.getPurchasePoints());
    }

    @Test
    public void testDiscountRemovedLarge() {
        Money result = customer.discount(9, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(parse("NZD 4.50"), result);
    }

    @Test
    public void testDiscountRemovedLargePoints() {
        Money result = customer.discount(9, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(1, customer.getPurchasePoints());
    }


    @Test
    public void testDiscountRemovedMoreThanOrder() {
        customer.setPurchasePoints(30);
        Money result = customer.discount(21, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(parse("NZD 10.00"), result);
    }

    @Test
    public void testDiscountRemovedMoreThanOrderPoints() {
        customer.setPurchasePoints(30);
        Money result = customer.discount(21, validCash, Money.parse("NZD " + 0.01 * customerSettings.getPointValue()));
        assertEquals(10, customer.getPurchasePoints());
    }

}