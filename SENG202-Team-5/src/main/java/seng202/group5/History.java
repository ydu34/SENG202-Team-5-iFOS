package seng202.group5;

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
     * @param ID a string representing the Order's ID
     * @return an Order of the specified ID
     * @see Order
     */
    public Order view(String ID) {
        return null;
    }

    /**
     * @return the transaction history containing all the Orders and their ID.
     */
    public HashMap<String, Order> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * @param transactionHistory the transaction history containing all the Orders and their IDs.
     */

    public void setTransactionHistory(HashMap<String, Order> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

}
