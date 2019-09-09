package seng202.group5;

import seng202.group5.exceptions.NoOrderException;

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

    /**
     * Creates a new order, replacing the previous order if it exists after
     * confirmation
     */
    public void newOrder() {
        currentOrder = new Order(currentStock);
    }

    /**
     * Prints the reciept of the order
     *
     * @return A string of the reciept of the order
     */
    public String printReceipt() {
        StringBuilder outputString = new StringBuilder();
        HashMap<MenuItem, Integer> orderItems = currentOrder.getOrderItems();
        for (Map.Entry<MenuItem, Integer> entry : orderItems.entrySet()) {
            MenuItem a = entry.getKey();
            Integer b = entry.getValue();
            outputString.append(format("%d %s(s) - %s\n",
                                       b,
                                       a.getItemName(),
                                       "$0.00"));
        }
        outputString.append("Total cost - $0.00");
        return outputString.toString();
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
