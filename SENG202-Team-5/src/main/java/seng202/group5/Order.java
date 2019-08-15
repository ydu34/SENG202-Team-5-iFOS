package seng202.group5;

import java.util.HashMap;

/**
 * The Order class keeps track of the current orders items and total cost. Contains methods to modify items to the order
 * and give a discount to the order.
 * @author Michael Morgoun
 */
public class Order {


    private HashMap<MenuItem, Integer> orderItems; // A HashMap of items in the order and their quantites.
    private float totalCost = 0; // The current total cost of the order.
    String iD; // The unique ID of the order given by the database.


    /**
     * Removes the item taken as a parameter from the order and returns a boolean true or false as to whether
     * it was successful or not.
     * @param item
     * @return
     */
    public boolean removeItem(MenuItem item) {
        return false;
    }


    /**
     * modifies the quantity of an item taken in as a parameter already in the order with the quantity parameter.
     * Returns a boolean true or false as to whether it was successful or not.
     * @param item
     * @param quantity
     * @return
     */
    public boolean modifyItemQuantity(MenuItem item, int quantity) {
        return false;
    }


    /**
     * Gives a discount to the order based off a percentage given as a parameter.
     * @param percentage
     */
    public void discount(int percentage) {

    }


    public static void main(String[] args) {

    }


}
