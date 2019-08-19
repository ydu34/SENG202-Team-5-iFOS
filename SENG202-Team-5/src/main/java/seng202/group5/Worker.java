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

    private History currentHistory;

    /** The database managing the system */
   // private Database database;


    /**
     * Creates a new worker class from the given database
     *
     * @param database the database managing the system
     */
    //public Worker(Database database) {

    //}
    public Worker(Order tempOrder, ArrayList<MenuItem> tempMenuItems, Stock tempStock, History tempHistory) {
        currentOrder = tempOrder;
        menuItems = tempMenuItems;
        currentStock = tempStock;
        currentHistory = tempHistory;

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
    public ArrayList<Double> confirmPayment(ArrayList<Double> denominations) throws InsufficientCashException {
        return new ArrayList<Double>();
    }

    /**
     * Gets the current order
     * @return the current order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Gets the items on the menu
     * @return the items on the menu
     */
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Gets the current stock on the food truck
     * @return the current stock
     */
    public Stock getCurrentStock() {
        return currentStock;
    }

    /**
     * Gets the database
     * @return the database
     */
    //public Database getDatabase() {
    //    return database;
    //}


}
