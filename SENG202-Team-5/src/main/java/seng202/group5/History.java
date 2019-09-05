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
        return getTransactionHistory().get(ID);
    }

    /**
     * @return the transaction history containing all the Orders and their ID.
     */
    public HashMap<String, Order> getTransactionHistory() {
        return transactionHistory;
    }

    /**
     * @param tempTransactionHistory the transaction history containing all the Orders and their IDs.
     */

    public void setTransactionHistory(HashMap<String, Order> tempTransactionHistory) {
        transactionHistory = tempTransactionHistory;
    }
    public void addTransactionHistory(HashMap<String, Order> tempTransaction) {
        for (String stringKey : tempTransaction.keySet()) {
            Order tempOrder = tempTransaction.get(stringKey);
            transactionHistory.put(stringKey, tempOrder);
        }
    }

}
