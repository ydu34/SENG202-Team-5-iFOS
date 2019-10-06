package seng202.group5.information;

import org.joda.money.Money;
import seng202.group5.IDGenerator;

import static org.joda.money.Money.parse;

/**
 * The Customer class holds information about each Customer who is considered a member and have a unique ID and an
 * amount of loyalty points that can purchase food with.
 * @author Michael Morgoun, James Kwok
 */
public class Customer {

    private IDGenerator generator = new IDGenerator();
    private String customerID = generator.newID();

    private String name;
    private int purchasePoints = 10;


    /**
     *Calculates and adds purchase points to a customers account when they have spent money.
     * @param spentMoney The total amount of money spent on an order, disregarding discounts.
     */
    public void purchasePoints(Money spentMoney) {

    }

    /**
     * Subtracts a given amount of purchase points from an order and returns the amount of money discounted to an order
     * Based on how many points were used.
     * @param purchasePoints The amount of points to be used for discounts.
     * @param currentOrderPrice The price of the customers order.
     * @return discountMoney The amount of money to be removed from the order.
     */
    public Money discount(int purchasePoints, Money currentOrderPrice) {
        Money discountMoney = parse("NZD 0.00");
        return discountMoney;
    }


    public void setName(String newName) {
        name = newName;
    }

    public void setPurchasePoints(int tempPurchasePoints) {
        purchasePoints = tempPurchasePoints;
    }

    public String getName() { return name; }

    public int getPurchasePoints() {
        return purchasePoints;
    }

    public String getID() { return customerID; }



}
