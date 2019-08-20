package seng202.group5;

import java.util.HashMap;

/**
 * The Order class keeps track of the current orders items and total cost. Contains methods to modify items to the order
 * and give a discount to the order.
 * @author Michael Morgoun
 */
public class Order {

    /** A HashMap of items in the order and their quantities. **/
    private HashMap<MenuItem, Integer> orderItems;

    /** The current total cost of the order including the discount **/
    private double totalCost = 0;

    /** The unique ID of th order given by the database **/
    String iD;

    public Order(HashMap<MenuItem, Integer> tempOrderItems, double tempTotalCost, String tempID) {
        orderItems = tempOrderItems;
        totalCost = tempTotalCost;
        iD = tempID;
    }


    /**
     * Returns the double, total cost.
     * @return the totalCost of the order.
     */
    public double getTotalCost() {
        return totalCost;
    }


    /**
     * Removes the item taken as a parameter from the order and returns a boolean true or false as to whether
     * it was successful or not.
     * @param item The item that is to be removed from the order.
     * @return The boolean success of the removal.
     */
    public boolean removeItem(MenuItem item) {
        return false;
    }

    /**
     * modifies the quantity of an item with the quantity parameter.
     * Returns a boolean true or false as to whether it was successful or not.
     * @param item The item that will have its quantity change.
     * @param quantity The new quantity for an item.
     * @return The boolean success of the quantity update.
     */
    public boolean modifyItemQuantity(MenuItem item, int quantity) {
        return false;
    }

    /**
     * Gives a discount to the order based off a percentage given as a parameter.
     * @param percentage The percentage of the discount.
     */
    public void discount(int percentage) {
        totalCost = totalCost - (totalCost * percentage/100);
    }

    public static void main(String[] args) {

    }


}
