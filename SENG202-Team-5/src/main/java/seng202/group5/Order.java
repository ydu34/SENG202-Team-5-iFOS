package seng202.group5;

import java.util.HashMap;

/**
 * The Order class keeps track of the current orders items and total cost. Contains methods to modify items to the order
 * and give a discount to the order.
 *
 * @author Michael Morgoun
 */
public class Order {

    /**
     * A HashMap of items in the order and their quantities.
     **/
    private HashMap<MenuItem, Integer> orderItems;

    /**
     * The current total cost of the order including the discount
     **/
    private double totalCost = 0;

    /**
     * The unique ID of the order given by the database
     **/
    private String id;

    /**
     * The Stock to update when creating this order
     */
    private Stock temporaryStock;


    /**
     * The builder for an Order object.
     *
     * @param tempOrderItems An order with an initial order list of items.
     * @param tempTotalCost  The total cost of an existing order.
     * @param tempID         The unique ID of the order.
     */
    public Order(HashMap<MenuItem, Integer> tempOrderItems, double tempTotalCost, String tempID) {
        orderItems = tempOrderItems;
        totalCost = tempTotalCost;
        id = tempID;

    }

    /**
     * The builder for an Order object.
     *
     * @param tempOrderItems An order with an initial order list of items.
     * @param tempTotalCost  The total cost of an existing order.
     * @param tempID         The unique ID of the order.
     * @param tempStock      The temporary stock to update when adding/removing items
     */
    public Order(HashMap<MenuItem, Integer> tempOrderItems, double tempTotalCost, String tempID, Stock tempStock) {
        orderItems = tempOrderItems;
        totalCost = tempTotalCost;
        id = tempID;
        temporaryStock = tempStock.clone();
    }


    /**
     * The builder for an Order object with no initial values.
     */
    public Order(Stock tempStock) {
        orderItems = new HashMap<MenuItem, Integer>();
        totalCost = 0;
        id = "ABC123"; // THIS NEEDS TO BE CHANGED, CURRENTLY HAS DEFAULT VALUE SINCE THERE IS NO ID MAKER YET
        temporaryStock = tempStock.clone();
    }


    /**
     * Gives a discount to the order based off a percentage given as a parameter.
     *
     * @param percentage The percentage of the discount.
     */
    public void discount(int percentage) {
        totalCost = totalCost - (totalCost * percentage / 100);
    }

    /**
     * Adds a new item to the order
     *
     * @param item     The item to add to the order
     * @param quantity The quantity of the item to add to the order
     */
    public void addItem(MenuItem item, int quantity) {

    }

    /**
     * Removes the item taken as a parameter from the order and returns a boolean true or false as to whether
     * it was successful or not.
     *
     * @param item The item that is to be removed from the order.
     * @return The boolean success of the removal.
     */
    public boolean removeItem(MenuItem item) {
        return false;
    }

    /**
     * modifies the quantity of an item with the quantity parameter.
     * Returns a boolean true or false as to whether it was successful or not.
     *
     * @param item     The item that will have its quantity change.
     * @param quantity The new quantity for an item.
     * @return The boolean success of the quantity update.
     */
    public boolean modifyItemQuantity(MenuItem item, int quantity) {
        return false;
    }

    /**
     * Gets the ID of this order
     *
     * @return the ID of this order
     */
    public String getID() {
        return id;
    }

    /**
     * A getter for the current order list.
     *
     * @return A HashMap<MenuItem, Integer> orderItems
     */
    public HashMap<MenuItem, Integer> getOrderItems() {
        return orderItems;
    }

    /**
     * Returns the total cost.
     *
     * @return the totalCost of the order.
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Gets the stock that is being updated
     *
     * @return the stock that is being updated
     */
    public Stock getStock() {
        return temporaryStock;
    }

}
