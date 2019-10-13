package seng202.group5.logic;

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
     * Creates a new OrderManager with the given order
     *
     * @param tempOrder The order to create the manager with
     * @param tempStock The stock to create the manager with
     */
    public OrderManager(Order tempOrder, Stock tempStock) {
        currentOrder = tempOrder;
        currentStock = tempStock;
    }

    /**
     * Creates a new OrderManager
     *
     * @param tempStock The stock to create the manager with
     */
    public OrderManager(Stock tempStock) {
        currentStock = tempStock;
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
     * Gets the current order
     *
     * @return the current order
     */
    public Order getOrder() {
        return currentOrder;
    }


    @Deprecated(since = "Order made to update temporary stock")
    public void setCurrentOrder(Order order) {
        currentOrder = order;
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

}
