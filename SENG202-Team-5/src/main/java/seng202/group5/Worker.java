package seng202.group5;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage the operations of a worker using the app
 *
 * @author Daniel Harris
 */
public class Worker {

    /** The order currently being created */
    private Order currentOrder;

    /** A list of items currently on the menu */
    private ArrayList<MenuItem> menuItems;

    /** The stock of ingredients on the food truck */
    private Stock currentStock;

    /** The database managing the system */
    private Database database;

    /**
     * Creates a new worker class from the given database
     *
     * @param database the database managing the system
     */
    public Worker(Database database) {

    }

    /**
     * Adds an item to the current order
     *
     * @param menuItem the item to add to the order
     * @param quantity the quantity of the item to add to the order
     * @throws NoOrderException if there is no current order
     */
    public void addItem(MenuItem menuItem, int quantity) throws NoOrderException {

    }

    /**
     * Creates a new order, replacing the previous order if it exists after
     * confirmation
     */
    public void newOrder() {

    }

    /**
     * Confirms payment for the current order, sends the order to the history,
     * sends information about the transaction to Finance and retrieves the
     * cash amounts to be given as change
     *
     * @param denominations the cash given to the worker to pay for the item
     * @return the cash to be returned to the customer as change
     * @throws InsufficientCashException if the given cash amount is not enough
     *      to pay for the order
     */
    public List<Float> confirmPayment(List<Float> denominations) throws InsufficientCashException {
        return new ArrayList<Float>();
    }

}
