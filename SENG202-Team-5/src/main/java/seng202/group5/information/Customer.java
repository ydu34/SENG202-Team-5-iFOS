package seng202.group5.information;

import org.joda.money.Money;
import seng202.group5.IDGenerator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Customer class holds information about each Customer who is considered a member and have a unique ID and an
 * amount of loyalty points that can purchase food with.
 *
 * @author Michael Morgoun, James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    private String customerID = IDGenerator.newCustomerID();

    private String name;

    private String phoneNumber;

    private int purchasePoints = 1;

    public Customer() {

    }

    /**
     * Calculates and adds purchase points to a customers account when they have spent money.
     * Default Rate: NZD $10 gives 1 Point = $0.50 discount
     *
     * @param spentMoney The total amount of money spent on an order, disregarding discounts.
     */
    public void purchasePoints(Money spentMoney, int ratio) {
        int roundedMoney = spentMoney.getAmountMajorInt();
        if (roundedMoney <= 0) {
            roundedMoney = 0;
        }
        addPurchasePoints(roundedMoney / ratio);
    }

    /**
     * Subtracts a given amount of purchase points from an order and returns the amount of money discounted to an order
     * Based on how many points were used.
     *
     * @param usedPoints        The amount of points to be used for discounts.
     * @param currentOrderPrice The price of the customers order.
     * @return discountMoney The amount of money to be removed from the order.
     */
    public Money discount(int usedPoints, Money currentOrderPrice, Money pointValue) {
        Money discountMoney;
        int maxPoints = (int) Math.ceil(currentOrderPrice.getAmountMinorInt() / 50);

        if (usedPoints > purchasePoints) {
            usedPoints = purchasePoints;
        }
        if (usedPoints > maxPoints) {
            usedPoints = maxPoints;
        }
        discountMoney = (pointValue.multipliedBy(usedPoints));
        addPurchasePoints(-usedPoints);
        return discountMoney;
    }

    public void addPurchasePoints(int tempPurchasePoints) {
        purchasePoints += tempPurchasePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    public int getPurchasePoints() {
        return purchasePoints;
    }

    public void setPurchasePoints(int tempPurchasePoints) {
        purchasePoints = tempPurchasePoints;
    }

    public String getID() {
        return customerID;
    }

}
