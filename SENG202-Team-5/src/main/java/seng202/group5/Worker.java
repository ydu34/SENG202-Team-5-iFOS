package seng202.group5;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A class to manage the operations of a worker using the app
 *
 * @author Daniel Harris
 */
public class Worker {

    /**
     * The order currently being created
     */
    private Order currentOrder;

    /**
     * A list of items currently on the menu
     */
    private ArrayList<MenuItem> menuItems;

    /**
     * The stock of ingredients on the food truck
     */
    private Stock currentStock;

    /**
     * The history of orders in this food truck
     */
    private History currentHistory;

    /**
     * The database managing the system
     */
    private Database database;


    /**
     * Creates a new worker class from the given database
     */
    public Worker(Database tempDatabase) {
        database = tempDatabase;
        this.newOrder();
        currentStock = new Stock();
        currentHistory = new History(new HashMap<>());

    }

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
        if (currentOrder == null) {
            throw new NoOrderException("No Order to add the item to.");
        } else {
            currentOrder.getOrderItems().put(menuItem, quantity);
        }
    }

    /**
     * Creates a new order, replacing the previous order if it exists after
     * confirmation
     */
    public void newOrder() {
        currentOrder = new Order();
    }

    /**
     * Confirms payment for the current order, sends the order to the history,
     * sends information about the transaction to Finance and retrieves the
     * cash amounts to be given as change
     *
     * @param denominations the cash given to the worker to pay for the item
     * @return the cash to be returned to the customer as change
     * @throws InsufficientCashException if the given cash amount is not enough
     *                                   to pay for the order
     */
    public ArrayList<Money> confirmPayment(ArrayList<Money> denominations) throws InsufficientCashException {
        Money totalPayment = Money.parse("NZD 0");
        for (Money coin : denominations) totalPayment = totalPayment.plus(coin);
        ArrayList<Money> change = new ArrayList<Money>();

        currentHistory.getTransactionHistory().put(currentOrder.getID(), currentOrder);
        // Need to implement database. Also, this is based on the system clock,
        // which may be problematic. This part throws the exception
        // change = database.getFinance().pay(Money.of(CurrencyUnit.of("NZD"), currentOrder.getTotalCost()),
        //                           denominations,
        //                           Instant.now().getEpochSecond());

        return change;
    }

    /**
     * Gets the current order
     *
     * @return the current order
     * @throws NoOrderException if there is no current order
     */
    public Order getCurrentOrder() throws NoOrderException {
        if (currentOrder == null) {
            throw new NoOrderException("No order exists to get");
        }
        return currentOrder;
    }

    /**
     * Gets the items on the menu
     *
     * @return the items on the menu
     */
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Gets the current stock on the food truck
     *
     * @return the current stock
     */
    public Stock getCurrentStock() {
        return currentStock;
    }

    /**
     * Gets the database
     *
     * @return the database
     */
    public Database getDatabase() {
        return database;
    }


}
