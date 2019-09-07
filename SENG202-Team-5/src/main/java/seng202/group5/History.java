package seng202.group5;

import seng202.group5.exceptions.NoPastOrderException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the transactionHistory which contains the Order and their IDs.
 */
public class History {

    private HashMap<String, Order> transactionHistory;

    public History(HashMap<String, Order> tempTransactionHistory) {
        transactionHistory = tempTransactionHistory;
    }


    /**
     * Returns an Order object that represents a customer's order.
     *
     * @param orderID a string representing the Order's ID
     * @return an Order of the specified ID
     * @see Order
     */
    public Order view(String orderID) throws NoPastOrderException { //BROKEN PLS FIX
        Order viewedOrder = getTransactionHistory().get(orderID);
        if (viewedOrder == null) {
            throw new NoPastOrderException("No order exists with ID " + orderID);
        }
        return viewedOrder;
    }

    /**
     * @return the transaction history containing all the Orders and their ID.
     */
    public HashMap<String, Order> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * @param stringID holds the ID of the order for reference later.
     * @param tempOrder the object of the order to be stored.
     */

    public void setTransactionHistory(String stringID, Order tempOrder) {
        transactionHistory.put(stringID, tempOrder);
    }

}
