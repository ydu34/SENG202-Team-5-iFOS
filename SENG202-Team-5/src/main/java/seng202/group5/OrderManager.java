package seng202.group5;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import seng202.group5.exceptions.InsufficientCashException;
import seng202.group5.exceptions.NoOrderException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;


/**
 * A class to manage the operations of a worker using the app
 *
 * @author Daniel Harris
 */
public class OrderManager {

    /**
     * The order currently being created
     */
    private Order currentOrder;

    /**
     * The stock of ingredients on the food truck
     */
    private Stock currentStock;

    /**
     * The history of orders in this food truck
     */
    private History currentHistory;

    /**
     * Creates a new OrderManager with the given order
     *
     * @param tempOrder   The order to create the manager with
     * @param tempStock   The stock to create the manager with
     * @param tempHistory The history to create the manager with
     */
    public OrderManager(Order tempOrder, Stock tempStock, History tempHistory) {
        currentOrder = tempOrder;
        currentStock = tempStock;
        currentHistory = tempHistory;
    }

    /**
     * Creates a new OrderManager
     *
     * @param tempStock   The stock to create the manager with
     * @param tempHistory The history to create the manager with
     */
    public OrderManager(Stock tempStock, History tempHistory) {
        currentStock = tempStock;
        currentHistory = tempHistory;
        newOrder();
    }

    //    /**
    //     * Adds an item to the current order
    //     *
    //     * @param menuItem the item to add to the order
    //     * @param quantity the quantity of the item to add to the order
    //     * @throws NoOrderException if there is no current order
    //     */
    //    public void addItem(MenuItem menuItem, int quantity) throws NoOrderException {
    //        if (currentOrder == null) {
    //            throw new NoOrderException("No Order to add the item to.");
    //        } else {
    //            currentOrder.getOrder().addItem(menuItem, quantity);
    //        }
    //    }
    // This is done by using OrderManager.getOrder().addItem()

    /**
     * Creates a new order, replacing the previous order if it exists after
     * confirmation
     */
    public void newOrder() {
        currentOrder = new Order(currentStock);
    }

    //    /**
    //     * Confirms payment for the current order, sends the order to the history,
    //     * sends information about the transaction to Finance and retrieves the
    //     * cash amounts to be given as change
    //     *
    //     * @param denominations the cash given to the worker to pay for the item
    //     * @return the cash to be returned to the customer as change
    //     * @throws InsufficientCashException if the given cash amount is not enough
    //     *                                   to pay for the order
    //     */
    //    public ArrayList<Money> confirmPayment(ArrayList<Money> denominations) throws InsufficientCashException {
    //        Money totalPayment = Money.parse("NZD 0");
    //        for (Money coin : denominations) totalPayment = totalPayment.plus(coin);
    //        ArrayList<Money> change = new ArrayList<Money>();
    //
    //        currentHistory.getTransactionHistory().put(currentOrder.getID(), currentOrder);
    //        // Need to implement database. Also, this is based on the system clock,
    //        // which may be problematic. This part throws the exception
    //        change = getFinance().pay(Money.of(CurrencyUnit.of("NZD"), currentOrder.getTotalCost()),
    //                                           denominations,
    //                                           Instant.now().getEpochSecond());
    //
    //        return change;
    //    }

    /**
     * Prints the reciept of the order
     *
     * @return A string of the reciept of the order
     */
    public String printReceipt() {
        String outputString = "";
        HashMap<MenuItem, Integer> orderItems = currentOrder.getOrderItems();
        for (Map.Entry<MenuItem, Integer> entry : orderItems.entrySet()) {
            MenuItem a = entry.getKey();
            Integer b = entry.getValue();
            outputString += format("%d %s(s) - %s\n",
                                   b,
                                   a.getItemName(),
                                   "$0.00");
        }
        outputString += "Total cost - $0.00";
        return outputString;
    }
    //TODO formalize receipt structure

    /**
     * Gets the current order
     *
     * @return the current order
     * @throws NoOrderException if there is no current order
     */
    public Order getOrder() throws NoOrderException {
        if (currentOrder == null) {
            throw new NoOrderException("No order exists to get");
        }
        return currentOrder;
    }

    /**
     * Gets the current stock on the food truck
     *
     * @return the current stock
     */
    public Stock getStock() {
        return currentStock;
    }

    /**
     * Sets the current stock to a new stock
     *
     * @param newStock The new stock
     */
    public void setStock(Stock newStock) {
        currentStock = newStock;
    }

    /**
     * Gets the history of orders processed by this order manager
     *
     * @return The history of orders
     */
    public History getHistory() {
        return currentHistory;
    }

}
